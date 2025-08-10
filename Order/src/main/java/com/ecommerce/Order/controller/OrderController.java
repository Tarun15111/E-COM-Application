package com.ecommerce.Order.controller;


import com.ecommerce.Order.dto.OrderResponse;
import com.ecommerce.Order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestHeader("X-User-Id") String userId){
        OrderResponse orderResponse = orderService.createOrder(userId);
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }

}
