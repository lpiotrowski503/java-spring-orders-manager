package com.example.order.controller;

import com.example.order.dto.OrderRequest;
import com.example.order.dto.OrderResponse;
import com.example.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("removal")
@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateOrder() throws Exception {
        OrderRequest request = new OrderRequest();
        request.setName("Test");
        request.setSymbol("SYM");
        request.setQuantity(10);
        request.setSide("BUY");
        request.setExchange("NYSE");
        request.setPrice(new BigDecimal("100.0"));
        request.setType("LIMIT");

        OrderResponse response = new OrderResponse(1L, "Test", "SYM", 10, "BUY", "NYSE", new BigDecimal("100.0"), "LIMIT", null);

        Mockito.when(orderService.createOrder(any(OrderRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test"));
    }

    @Test
    void shouldGetOrderById() throws Exception {
        OrderResponse response = new OrderResponse(1L, "Test", "SYM", 10, "BUY", "NYSE", new BigDecimal("100.0"), "LIMIT", null);

        Mockito.when(orderService.getOrderById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test"));
    }

    @Test
    void shouldUpdateOrder() throws Exception {
        OrderRequest request = new OrderRequest();
        request.setName("Test");
        request.setSymbol("SYM");
        request.setQuantity(20);
        request.setSide("SELL");
        request.setExchange("NASDAQ");
        request.setPrice(new BigDecimal("200.0"));
        request.setType("MARKET");

        OrderResponse response = new OrderResponse(1L, "Updated", "SYM", 20, "SELL", "NASDAQ", new BigDecimal("200.0"), "MARKET", null);

        Mockito.when(orderService.updateOrder(eq(1L), any(OrderRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"));
    }

    @Test
    void shouldDeleteOrder() throws Exception {
        mockMvc.perform(delete("/api/orders/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(orderService).deleteOrder(1L);
    }

    @Test
    void shouldFindAllOrders() throws Exception {
        OrderResponse response = new OrderResponse(1L, "SearchTest", "SYM", 10, "BUY", "NYSE", new BigDecimal("100.0"), "LIMIT", null);
        Page<OrderResponse> page = new PageImpl<>(List.of(response));
        Mockito.when(orderService.searchOrders(any(), any()))
                .thenReturn(page);

        mockMvc.perform(get("/api/orders")
                        .param("name", "SearchTest")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("SearchTest"));
    }
}
