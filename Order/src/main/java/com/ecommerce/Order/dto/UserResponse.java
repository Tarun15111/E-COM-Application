package com.ecommerce.Order.dto;

import com.ecommerce.Order.model.UserRole;
import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private  String firstName;
    private  String lastName;
    private String email;
    private String phone;
    private UserRole userRole = UserRole.CUSTOMER;
    private AddressDTO address;
}
