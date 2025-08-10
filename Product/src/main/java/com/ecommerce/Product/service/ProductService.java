package com.ecommerce.Product.service;


import com.ecommerce.Product.dto.ProductRequest;
import com.ecommerce.Product.dto.ProductResponse;
import com.ecommerce.Product.exception.ResourceNotFoundException;
import com.ecommerce.Product.model.Product;
import com.ecommerce.Product.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    public ProductResponse updateProduct(Long id, ProductRequest productRequest){
        Product existingProduct =  productRepository.findById(id)
                                                    .orElseThrow(()-> new ResourceNotFoundException("Product", "Product id", String.valueOf(id)));
        modelMapper.map(productRequest, existingProduct);
        Product updatedProduct =  productRepository.save(existingProduct);
        return  modelMapper.map(updatedProduct, ProductResponse.class);
    }

//    Get all products
    public List<ProductResponse> getAllProducts(){
        List<Product> products = productRepository.findByActiveTrue();
        return products.stream().map(product -> modelMapper.map(product, ProductResponse.class)).toList();
    }

    public boolean  deleteProduct(Long id){
        return productRepository.findById(id)
                                .map(product -> {
                                    product.setActive(false);
                                    productRepository.save(product);
                                    return true;
                                }).orElse(false);
    }

    public List<ProductResponse> searchProducts(String keyword){
            List<Product> products =  productRepository.searchProducts(keyword);
        return products.stream().map(product -> modelMapper.map(product, ProductResponse.class)).toList();
    }

}
