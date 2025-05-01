package com.example.order.service;

import com.example.order.dto.OrderRequest;
import com.example.order.dto.OrderResponse;
import com.example.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.example.order.repository.OrderRepository;
import org.springframework.beans.BeanUtils;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderResponse createOrder(OrderRequest request) {
        Order order = mapToEntity(request);
        return mapToResponse(orderRepository.save(order));
    }

    public OrderResponse updateOrder(Long id, OrderRequest request) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        BeanUtils.copyProperties(request, existing, "id");
        return mapToResponse(orderRepository.save(existing));
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public OrderResponse getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Page<OrderResponse> searchOrders(String name, Pageable pageable) {
        Page<Order> orders;

        if (name != null && !name.isEmpty()) {
            orders = orderRepository.findByNameContainingIgnoreCase(name, pageable);
        } else {
            orders = orderRepository.findAll(pageable);
        }

        return orders.map(this::mapToResponse);
    }

    private Order mapToEntity(OrderRequest request) {
        return Order.builder()
                .name(request.getName())
                .symbol(request.getSymbol())
                .quantity(request.getQuantity())
                .side(request.getSide())
                .exchange(request.getExchange())
                .price(request.getPrice())
                .type(request.getType())
                .attachment(request.getAttachment())
                .build();
    }

    private OrderResponse mapToResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .name(order.getName())
                .symbol(order.getSymbol())
                .quantity(order.getQuantity())
                .side(order.getSide())
                .exchange(order.getExchange())
                .price(order.getPrice())
                .type(order.getType())
                .attachment(order.getAttachment())
                .build();
    }
}


