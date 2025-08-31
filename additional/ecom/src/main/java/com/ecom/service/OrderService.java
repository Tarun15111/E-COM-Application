package com.ecom.service;

import com.ecom.dto.OrderItemDTO;
import com.ecom.dto.OrderResponse;
import com.ecom.dto.UserRequest;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.model.*;
import com.ecom.repository.CartItemRepository;
import com.ecom.repository.OrderRepository;
import com.ecom.repository.UserRepository;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public OrderResponse createOrder(String userId){
//        Validating the user
        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()->new ResourceNotFoundException("User", "User Id", userId));
//        Validating the cart items.
        List<CartItem> cartItems = cartItemRepository.findAllByUser(user);
//        Calculate the price
        BigDecimal totalPrice = cartItems.stream().map(CartItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
//        Create order
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);
        List<OrderItems> orderItemsList = cartItems.stream().map(cartItem -> new OrderItems(
                null,
                cartItem.getProduct(),
                cartItem.getQuantity(),
                cartItem.getPrice(),
                order
        )).toList();
        order.setItems(orderItemsList);
        Order savedOrder = orderRepository.save(order);
//        Clear the cart
        cartItemService.clearCart(userId);
        return mapToOrderResponse(order);
    }
    private  OrderResponse mapToOrderResponse(Order order){
        return  new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getItems().stream().map(orderItems ->
                    new OrderItemDTO(
                            orderItems.getId(),
                            orderItems.getProduct().getId(),
                            orderItems.getQuantity(),
                            orderItems.getPrice(),
                            orderItems.getPrice().multiply(new BigDecimal(orderItems.getQuantity()))
                    )).toList(),
                order.getCreatedAt()
        );
    }
}
