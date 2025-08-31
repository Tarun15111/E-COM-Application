package com.ecommerce.Order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemResponse {
    private Long id;
    private Long productId;
//    private String productName;
    private int quantity;
    private BigDecimal price;
    private String userId;
}
