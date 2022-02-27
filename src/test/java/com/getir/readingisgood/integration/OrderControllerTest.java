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

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Book getBook() {
        Book book = new Book();
        book.setName("Hobbit");
        book.setPrice(32.8);
        BookStock bookStock = new BookStock(8L, 25L, 1);
        book.setBookStock(bookStock);
        bookStock.setBook(book);
        return book;
    }

    private static Customer getCustomer() {
        Customer customer = new Customer(82L, "zkaram@gmail.com", "Zeynep", "Kara");
        return customer;
    }

    private static CustomerOrderRequestDTO getOrderDTO() {
        CustomerOrderRequestDTO orderRequestDTO = new CustomerOrderRequestDTO();
        orderRequestDTO.setTotalPrice(25.0);
        orderRequestDTO.setCustomerId(82L);

        OrderBookRequestDTO orderBookRequestDTO = new OrderBookRequestDTO();
        orderBookRequestDTO.setQuantity(12);
        orderBookRequestDTO.setBookId(85L);
        orderRequestDTO.setBookOrders(Arrays.asList(orderBookRequestDTO));
        return orderRequestDTO;
    }

    private static CustomerOrder getOrder() {
        CustomerOrder order = new CustomerOrder();

        OrderBook orderBook = new OrderBook();
        orderBook.setBook(getBook());
        orderBook.setQuantity(5);

        order.setOrderBooks(Arrays.asList(orderBook));
        order.setCustomer(getCustomer());
        order.setStatus(OrderStatus.CREATED);
        order.setOrderDate(Instant.now());
        return order;
    }

//    @Test
//    public void createOrderTest() throws Exception {
//        given(customerService.findCustomerById(82L)).willReturn(getCustomer());
//        given(bookService.findBookById(85L)).willReturn(getBook());
//
//        this.mock.perform(post("/api/orders")
//                        .content(asJsonString(getOrderDTO()))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
//        ;
//
//    }

    @Test
    public void getOrderTest() throws Exception {
        given(orderRepository.findById(5L)).willReturn(Optional.of(getOrder()));
        this.mock.perform(get("/api/orders/5")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(jsonPath("$.content.customerName", is("Zeynep")));

    }

    @Test
    public void listOrdersByStartDateAndEndDate() throws Exception {
        Instant date = Instant.now();
        given(orderRepository.findByOrderDateBetween(date, date)).willReturn(Arrays.asList(getOrder()));
        this.mock.perform(get("/api/orders")
                        .param("startDate", date.toString())
                        .param("endDate", date.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(jsonPath("$.content", hasSize(1)));

    }
}
