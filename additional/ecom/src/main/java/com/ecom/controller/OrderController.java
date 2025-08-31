package com.ecom.controller;

import com.ecom.dto.OrderResponse;
import com.ecom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
