package com.ecommerce.Order.controller;

import com.ecommerce.Order.dto.CartItemRequest;
import com.ecommerce.Order.dto.CartItemResponse;
import com.ecommerce.Order.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<CartItemResponse> addToCart(
            @RequestHeader("X-User-ID") String userId,
            @RequestBody CartItemRequest cartItemRequest
    ){
        CartItemResponse cartItemResponse =  cartItemService.addToCart(userId, cartItemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemResponse);
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(
        @RequestHeader("X-User-ID") String userId,
        @PathVariable String productId
    ){
            boolean isDeleted =  cartItemService.removeFromCart(userId, productId);
            return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCartItems(@RequestHeader("X-User-ID") String userId){
        List<CartItemResponse> cartItemResponses =  cartItemService.getCartItems(userId);
        return ResponseEntity.ok(cartItemResponses);
    }



}
