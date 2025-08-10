package com.ecommerce.Order.service;

import com.ecommerce.Order.dto.OrderItemDTO;
import com.ecommerce.Order.dto.OrderResponse;
import com.ecommerce.Order.exception.ResourceNotFoundException;
import com.ecommerce.Order.model.CartItem;
import com.ecommerce.Order.model.Order;
import com.ecommerce.Order.model.OrderItems;
import com.ecommerce.Order.model.OrderStatus;
import com.ecommerce.Order.repository.CartItemRepository;
import com.ecommerce.Order.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {
//
//    @Autowired
//    private UserRepository userRepository;

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
//        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()->new ResourceNotFoundException("User", "User Id", userId));
//        Validating the cart items.
        List<CartItem> cartItems = cartItemRepository.findAllByUserId(userId);
//        Calculate the price
        BigDecimal totalPrice = cartItems.stream().map(CartItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
//        Create order
        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);
        List<OrderItems> orderItemsList = cartItems.stream().map(cartItem -> new OrderItems(
                null,
                cartItem.getProductId(),
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
    private OrderResponse mapToOrderResponse(Order order){
        return  new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getItems().stream().map(orderItems ->
                    new OrderItemDTO(
                            orderItems.getId(),
                            Long.valueOf(orderItems.getProductId()),
                            orderItems.getQuantity(),
                            orderItems.getPrice(),
                            orderItems.getPrice().multiply(new BigDecimal(orderItems.getQuantity()))
                    )).toList(),
                order.getCreatedAt()
        );
    }
}
