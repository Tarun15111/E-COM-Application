package com.ecommerce.Order.repository;

//import com.ecom.model.Product;
//import com.ecom.model.User;
import com.ecommerce.Order.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByUserIdAndProductId(String userId, String productId);
    List<CartItem> findAllByUserId(String  userId);
    void deleteByUserId(String userId);
}
