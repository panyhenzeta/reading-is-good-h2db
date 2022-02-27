package com.getir.readingisgood.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.readingisgood.model.*;
import com.getir.readingisgood.model.dto.CustomerOrderRequestDTO;
import com.getir.readingisgood.model.dto.CustomerOrderResponseDTO;
import com.getir.readingisgood.model.dto.OrderBookRequestDTO;
import com.getir.readingisgood.model.enums.OrderStatus;
import com.getir.readingisgood.repository.OrderRepository;
import com.getir.readingisgood.service.BookService;
import com.getir.readingisgood.service.CustomerService;
import com.getir.readingisgood.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase
public class OrderControllerTest {

    @Autowired
    private MockMvc mock;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private BookService bookService;

    @MockBean
    private OrderRepository orderRepository;

    private Customer customer;
    private Book book;
    private CustomerOrder order;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        setCustomer();
        setBook();
        setOrder();
    }

    private void setCustomer() {
        customer = new Customer();
        customer.setEmail("getir@getir.com");
        customer.setName("Getir");
        customer.setSurname("Getir");
    }

    private void setBook() {
        Double price = Double.valueOf(Math.random() * 10);
        Long stock = Double.valueOf(Math.random() * 15).longValue();

        book = new Book();
        book.setName("Hobbit");
        book.setPrice(price);

        BookStock bookStock = new BookStock();
        bookStock.setStock(stock);
        book.setBookStock(bookStock);
        bookStock.setBook(book);
    }

    private static CustomerOrderRequestDTO getOrderDTO() {
        Long id = Double.valueOf(Math.random() * 10).longValue();
        Double price = Double.valueOf(Math.random() * 10);
        Integer stock = Double.valueOf(Math.random() * 5).intValue();

        CustomerOrderRequestDTO orderRequestDTO = new CustomerOrderRequestDTO();
        orderRequestDTO.setTotalPrice(price);
        orderRequestDTO.setCustomerId(id);

        OrderBookRequestDTO orderBookRequestDTO = new OrderBookRequestDTO();
        orderBookRequestDTO.setQuantity(stock);
        orderBookRequestDTO.setBookId(id);
        orderRequestDTO.setBookOrders(Arrays.asList(orderBookRequestDTO));
        return orderRequestDTO;
    }

    public void setOrder() {
        Integer stock = Double.valueOf(Math.random() * 2).intValue();

        order = new CustomerOrder();

        OrderBook orderBook = new OrderBook();
        orderBook.setBook(this.book);
        orderBook.setQuantity(stock);

        order.setOrderBooks(Arrays.asList(orderBook));
        order.setCustomer(this.customer);
        order.setStatus(OrderStatus.CREATED);
        order.setOrderDate(Instant.now());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void whenSaveOrder_shouldReturnSuccess() throws Exception {
        given(customerService.findCustomerById(ArgumentMatchers.any(Long.class))).willReturn(customer);
        given(bookService.findBookById(ArgumentMatchers.any(Long.class))).willReturn(book);
        given(orderRepository.save(ArgumentMatchers.any(CustomerOrder.class))).willReturn(order);

        this.mock.perform(post("/api/orders")
                        .content(asJsonString(getOrderDTO()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
        ;

    }

    @Test
    public void whenGetOrder_shouldReturnOrder() throws Exception {
        given(orderRepository.findById(ArgumentMatchers.any(Long.class))).willReturn(Optional.of(order));

        this.mock.perform(get("/api/orders/5")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(jsonPath("$.content.customerName", is(order.getCustomer().getName())));

    }

    @Test
    public void whenListOrderByDate_shouldReturnOrderList() throws Exception {
        Instant date = Instant.now();
        given(orderRepository.findByOrderDateBetween(date, date)).willReturn(Arrays.asList(order));
        this.mock.perform(get("/api/orders")
                        .param("startDate", date.toString())
                        .param("endDate", date.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(jsonPath("$.content", hasSize(1)));

    }
}
