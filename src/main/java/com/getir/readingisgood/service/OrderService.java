package com.getir.readingisgood.service;

import com.getir.readingisgood.exception.CustomValidationException;
import com.getir.readingisgood.model.*;
import com.getir.readingisgood.model.dto.CustomerOrderRequestDTO;
import com.getir.readingisgood.model.dto.CustomerOrderResponseDTO;
import com.getir.readingisgood.model.dto.OrderBookRequestDTO;
import com.getir.readingisgood.model.enums.OrderStatus;
import com.getir.readingisgood.model.response.SuccessResponse;
import com.getir.readingisgood.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Order service that create, get and list operations.
 */
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final BookService bookService;
    private final CustomerService customerService;

    public OrderService(OrderRepository orderRepository,  BookService bookService, CustomerService customerService){
        this.orderRepository = orderRepository;
        this.bookService = bookService;
        this.customerService = customerService;
    }

    /**
     * method that create order.
     *
     * @param order DTO object for order.
     * @return saved object.
     */
    public CustomerOrderResponseDTO createOrder(CustomerOrderRequestDTO order) {
        CustomerOrder newOrder = new CustomerOrder();
        newOrder.setTotalPrice(order.getTotalPrice());
        newOrder.setOrderDate(Instant.now());
        newOrder.setStatus(OrderStatus.CREATED);

        Customer customer = customerService.findCustomerById(order.getCustomerId());
        newOrder.setCustomer(customer);

        List<OrderBook> orderBookList = getOrderBooks(order.getBookOrders(), newOrder);
        newOrder.setOrderBooks(orderBookList);
        CustomerOrder customerOrder = orderRepository.save(newOrder);
        CustomerOrderResponseDTO orderResponseDTO = new CustomerOrderResponseDTO(customerOrder);
        return orderResponseDTO;
    }

    /**
     * Method that create order.
     *
     * @param orderId of existing order.
     * @return existing order.
     */
    public CustomerOrderResponseDTO getOrderById(Long orderId) {
        CustomerOrder order = findOrderById(orderId);
        CustomerOrderResponseDTO orderDTO = new CustomerOrderResponseDTO(order);
        return orderDTO;
    }

    /**
     * Method that create order.
     *
     * @param startDate for order date.
     * @param endDate for order date.
     * @return existing list of orders.
     */
    public List<CustomerOrderResponseDTO> listOrdersByStartDateAndEndDate(Instant startDate, Instant endDate) {
        List<CustomerOrderResponseDTO> customerOrderList = orderRepository.findByOrderDateBetween(startDate, endDate)
                        .stream().map(customerOrder -> {
                            return new CustomerOrderResponseDTO(customerOrder);
                        }).collect(Collectors.toList());
        return customerOrderList;
    }

    public CustomerOrder findOrderById(Long orderId){
        Optional<CustomerOrder> order = orderRepository.findById(orderId);
        if (order.isPresent()){
            return order.get();
        } else {
            throw new CustomValidationException("error.orderNotFound");
        }
    }

    public List<CustomerOrder> findAll(){
        return orderRepository.findAll();
    }

    private List<OrderBook> getOrderBooks(List<OrderBookRequestDTO> orderBooks, CustomerOrder newOrder) {
        List<OrderBook> orderBookList = orderBooks
                .stream()
                .map(orderBook -> {
                    Book book = bookService.findBookById(orderBook.getBookId());
                    checkAndUpdateBookStock(book.getBookStock(), orderBook.getQuantity());
                    return new OrderBook(newOrder, book, orderBook.getQuantity());
                }).collect(Collectors.toList());
        return orderBookList;
    }

    private void checkAndUpdateBookStock(BookStock bookStock, int quantity) {
        if (bookStock.getStock().intValue() < quantity){
            throw new CustomValidationException("error.bookQuantityIsNotEnough." + bookStock.getBook().getName());
        }
        Long newStock = bookStock.getStock() - quantity;
        bookStock.setStock(newStock);
        bookService.updateStock(bookStock);
    }
}
