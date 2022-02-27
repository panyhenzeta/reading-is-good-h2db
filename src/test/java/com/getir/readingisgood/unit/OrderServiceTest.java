package com.getir.readingisgood.unit;

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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
@AutoConfigureTestDatabase
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private BookService bookService;

    @Mock
    private CustomerService customerService;

    private CustomerOrder order;
    private Customer customer;
    private Book book;

    @Before
    public void init(){
        setCustomer();
        setBook();
        setOrder();
    }

    public void setCustomer(){
        customer = new Customer();
        customer.setName("test");
        customer.setSurname("user");
        customer.setEmail("testuser@gmail.com");
    }

    private void setBook(){
        this.book = new Book();
        this.book.setName("Getir");
        this.book.setPrice(25.0);

        BookStock bookStock = new BookStock(5L);
        this.book.setBookStock(bookStock);
        bookStock.setBook(book);
    }

    public void setOrder(){
        Integer quantity = Double.valueOf(Math.random()*10).intValue();
        Double price = Double.valueOf(Math.random()*100);

        OrderBook orderBook = new OrderBook();
        orderBook.setQuantity(quantity);
        orderBook.setBook(book);

        order = new CustomerOrder();
        order.setOrderBooks(Arrays.asList(orderBook));
        order.setCustomer(customer);
        order.setTotalPrice(price);
        order.setOrderDate(Instant.now());
        order.setStatus(OrderStatus.CREATED);
    }

    private CustomerOrderRequestDTO getOrderRequest(){
        Double price = Double.valueOf(Math.random() * 100);
        Long customerId = Double.valueOf(Math.random() * 10).longValue();
        Long bookId = Double.valueOf(Math.random() * 10).longValue();
        Integer quantity = Double.valueOf(Math.random() * 4).intValue();

        OrderBookRequestDTO orderBookDTO = new OrderBookRequestDTO();
        orderBookDTO.setBookId(bookId);
        orderBookDTO.setQuantity(quantity);

        CustomerOrderRequestDTO orderRequestDTO = new CustomerOrderRequestDTO();
        orderRequestDTO.setCustomerId(customerId);
        orderRequestDTO.setTotalPrice(price);
        orderRequestDTO.setBookOrders(Arrays.asList(orderBookDTO));
        return orderRequestDTO;
    }

    @Test
    public void whenSaveOrder_shouldReturnOrderDTO() {
        when(customerService.findCustomerById(ArgumentMatchers.any(Long.class))).thenReturn(customer);
        when(bookService.findBookById(ArgumentMatchers.any(Long.class))).thenReturn(book);
        when(orderRepository.save(ArgumentMatchers.any(CustomerOrder.class))).thenReturn(order);

        CustomerOrderResponseDTO responseDTO = orderService.createOrder(getOrderRequest());

        assertThat(responseDTO.getCustomerName()).isEqualTo(customer.getName());
        verify(customerService).findCustomerById(ArgumentMatchers.any(Long.class));
        verify(bookService).findBookById(ArgumentMatchers.any(Long.class));
    }

    @Test
    public void whenGetOrder_shouldReturnOrderDTO() {
        CustomerOrderResponseDTO orderDTO = new CustomerOrderResponseDTO(order);
        when(orderRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(order));

        CustomerOrderResponseDTO responseOrderDTO = orderService.getOrderById(ArgumentMatchers.any(Long.class));

        assertThat(orderDTO.getCustomerName()).isEqualTo(responseOrderDTO.getCustomerName());
        verify(orderRepository).findById(ArgumentMatchers.any(Long.class));
    }

    @Test
    public void whenFindAllOrder_shouldReturnOrderList() {
        List<CustomerOrder> orderList = Arrays.asList(order);
        when(orderRepository.findAll()).thenReturn(orderList);

        List<CustomerOrder> serviceOrderList = orderService.findAll();

        assertThat(serviceOrderList.size()).isEqualTo(orderList.size());
        verify(orderRepository).findAll( );
    }

    @Test
    public void whenFindOrderById_shouldReturnOrder() {
        when(orderRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(order));

        CustomerOrder newOrder = orderService.findOrderById(ArgumentMatchers.any(Long.class));

        assertThat(order).isSameAs(newOrder);
        verify(orderRepository).findById(ArgumentMatchers.any(Long.class));
    }

}
