package com.getir.readingisgood.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.readingisgood.model.Customer;
import com.getir.readingisgood.model.CustomerOrder;
import com.getir.readingisgood.model.OrderBook;
import com.getir.readingisgood.model.enums.OrderStatus;
import com.getir.readingisgood.service.CustomerService;
import com.getir.readingisgood.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase
public class StatisticsControllerTest {

    @Autowired
    private MockMvc mock;

    @MockBean
    private OrderService orderService;

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

    private static CustomerOrder getOrder() {
        CustomerOrder order = new CustomerOrder();

        OrderBook orderBook = new OrderBook();
        orderBook.setQuantity(5);

        order.setOrderBooks(Arrays.asList(orderBook));
        order.setStatus(OrderStatus.CREATED);
        order.setTotalPrice(78.0);
        order.setOrderDate(Instant.now());
        return order;
    }

    @Test
    public void getMonthlyStatistics() throws Exception {
        given(orderService.findAll()).willReturn(Arrays.asList(getOrder()));
        this.mock.perform(get("/api/statistics/monthly"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].totalBookCount", is(5)));
    }

}
