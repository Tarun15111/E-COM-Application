package com.ecommerce.Order.service;

//import com.ecom.exception.InSufficientStockException;
//import com.ecom.exception.ResourceNotFoundException;
//import com.ecom.model.Product;
//import com.ecom.model.User;
//import com.ecom.repository.ProductRepository;
//import com.ecom.repository.UserRepository;
import com.ecommerce.Order.dto.CartItemRequest;
import com.ecommerce.Order.dto.CartItemResponse;
import com.ecommerce.Order.model.CartItem;
import com.ecommerce.Order.repository.CartItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartItemService {

        @Autowired
        private CartItemRepository cartItemRepository;
//
//        @Autowired
//        private UserRepository userRepository;
//
//        @Autowired
//        private ProductRepository productRepository;

        @Autowired
        private ModelMapper modelMapper;

        public CartItemResponse addToCart(String userId, CartItemRequest cartItemRequest){
//            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()-> new ResourceNotFoundException("User", "User Id", userId));
//            Product product = productRepository.findById(cartItemRequest.getProductId()).orElseThrow(()-> new ResourceNotFoundException("Product", "Product Id", String.valueOf(cartItemRequest.getProductId())));
//            if(product.getStockQuantity() < cartItemRequest.getQuantity()){
//                throw new InSufficientStockException("Out of stock");
//            }
            CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId, String.valueOf(cartItemRequest.getProductId()));
//            There is item present for user with this product
            if(cartItem != null){
//                Update the quantity
                cartItem.setQuantity(cartItem.getQuantity() + cartItemRequest.getQuantity());
                cartItem.setPrice(BigDecimal.valueOf(1000.00));
            }else {
//                Persist the data in to Cart Repo
                cartItem = new CartItem();
                cartItem.setUserId(userId);
                cartItem.setProductId(String.valueOf(cartItemRequest.getProductId()));
                cartItem.setQuantity(cartItemRequest.getQuantity());
                cartItem.setPrice(BigDecimal.valueOf(1000.00));
            }
            CartItem savedCartItem =  cartItemRepository.save(cartItem);
            CartItemResponse response = new CartItemResponse();
            response.setId(savedCartItem.getId());
            response.setProductId(cartItemRequest.getProductId());
//            response.setProductName("Dummy");
            response.setQuantity(savedCartItem.getQuantity());
            response.setPrice(savedCartItem.getPrice());
            response.setUserId(Long.valueOf(userId));

            return response;

        }

        @Transactional
        public  boolean removeFromCart(String userId, String productId){
//            Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "Product Id", String.valueOf(productId)));
//            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()-> new ResourceNotFoundException("User","User id", userId));
//
            CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId);
            if(cartItem != null){
                cartItemRepository.delete(cartItem);
                return true;
            }
            return false;
        }

        public List<CartItemResponse> getCartItems(String userId){
//            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()->new ResourceNotFoundException("User", "User Id", userId));
            List<CartItem> cartItems = cartItemRepository.findAllByUserId(userId);
            return cartItems.stream().map(cartItem -> modelMapper.map(cartItem, CartItemResponse.class)).toList();
        }

        public void clearCart(String userId){
//            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()-> new ResourceNotFoundException("User", "User Id", userId));
            cartItemRepository.deleteByUserId(userId);
        }

}
