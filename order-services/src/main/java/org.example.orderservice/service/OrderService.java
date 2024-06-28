package org.example.orderservice.service;

import org.example.orderservice.dto.request.OrderRequest;

public interface OrderService {
    String placeOrder(OrderRequest orderRequest);
}
