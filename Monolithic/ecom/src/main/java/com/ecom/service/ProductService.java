package com.ecom.service;

import com.ecom.dto.ProductRequest;
import com.ecom.dto.ProductResponse;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.model.Product;
import com.ecom.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.ReadOnlyFileSystemException;
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
