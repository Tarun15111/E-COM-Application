package com.ecommerce.Product.repository;

import com.ecommerce.Product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByActiveTrue();

    @Query(value = "select p from Product p where p.active = true and p.stockQuantity > 0 and LOWER(p.name) like LOWER(CONCAT('%',:keyword,'%'))")
    List<Product> searchProducts(@Param("keyword") String keyword);

    Optional<Product> findByIdAndActiveTrue(Long id);
}
