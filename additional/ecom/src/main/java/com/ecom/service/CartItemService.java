package com.ecom.service;

import com.ecom.dto.CartItemRequest;
import com.ecom.dto.CartItemResponse;
import com.ecom.exception.InSufficientStockException;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.model.CartItem;
import com.ecom.model.Product;
import com.ecom.model.User;
import com.ecom.repository.CartItemRepository;
import com.ecom.repository.ProductRepository;
import com.ecom.repository.UserRepository;
import com.ecom.utils.APIResponse;
import org.aspectj.asm.IModelFilter;
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

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private ProductRepository productRepository;

        @Autowired
        private ModelMapper modelMapper;

        public CartItemResponse addToCart(String userId, CartItemRequest cartItemRequest){
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()-> new ResourceNotFoundException("User", "User Id", userId));
            Product product = productRepository.findById(cartItemRequest.getProductId()).orElseThrow(()-> new ResourceNotFoundException("Product", "Product Id", String.valueOf(cartItemRequest.getProductId())));
            if(product.getStockQuantity() < cartItemRequest.getQuantity()){
                throw new InSufficientStockException("Out of stock");
            }
            CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product);
//            There is item present for user with this product
            if(cartItem != null){
//                Update the quantity
                cartItem.setQuantity(cartItem.getQuantity() + cartItemRequest.getQuantity());
                cartItem.setPrice(cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            }else {
//                Persist the data in to Cart Repo
                cartItem = new CartItem();
                cartItem.setUser(user);
                cartItem.setProduct(product);
                cartItem.setQuantity(cartItemRequest.getQuantity());
                cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItemRequest.getQuantity())));
            }
            CartItem savedCartItem =  cartItemRepository.save(cartItem);
            CartItemResponse response = new CartItemResponse();
            response.setId(savedCartItem.getId());
            response.setProductId(product.getId());
            response.setProductName(product.getName());
            response.setQuantity(savedCartItem.getQuantity());
            response.setPrice(savedCartItem.getPrice());
            response.setUserId(user.getId());

            return response;

        }

        @Transactional
        public  boolean removeFromCart(String userId, Long productId){
            Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "Product Id", String.valueOf(productId)));
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()-> new ResourceNotFoundException("User","User id", userId));
            CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product);
            if(cartItem != null){
                cartItemRepository.delete(cartItem);
                return true;
            }
            return false;
        }

        public List<CartItemResponse> getCartItems(String userId){
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()->new ResourceNotFoundException("User", "User Id", userId));
            List<CartItem> cartItems = cartItemRepository.findAllByUser(user);
            return cartItems.stream().map(cartItem -> modelMapper.map(cartItem, CartItemResponse.class)).toList();
        }

        public void clearCart(String userId){
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()-> new ResourceNotFoundException("User", "User Id", userId));
            cartItemRepository.deleteByUser(user);
        }

}
