package com.example.onlineshop.service;

import com.example.onlineshop.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findByProductDescription(String name);
    List<Product> getAllProducts();
    Product createProduct(Product product);
    List<Product> createProducts(List<Product> products);
    Optional<Product> getProductById(Long id);
    Product updateProduct(Product product);
    String deleteProductById(Long id);

    Object findByCategoryId(Long id);
}
