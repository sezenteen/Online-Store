package com.example.onlineshop.service.Implement;

import com.example.onlineshop.model.OrderedProduct;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.repository.OrderedProductRepository;
import com.example.onlineshop.repository.ProductRepository;
import com.example.onlineshop.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;

    private final OrderedProductRepository orderedProductRepository;


    public ProductServiceImpl(ProductRepository productRepository, OrderedProductRepository orderedProductRepository) {
        this.productRepository = productRepository;
        this.orderedProductRepository = orderedProductRepository;
    }

    @Override
    public List<Product> findByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }


    @Override
    public List<Product> findByProductDescription(String name) {
        return productRepository.findAllByDescription(name);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public List<Product> createProducts(List<Product> products) {
        return productRepository.saveAll(products);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product updateProduct(Product product) {
        Product findProduct= productRepository.findById(product.getId()).orElse(null);
        findProduct.setName(product.getName());
        findProduct.setPrice(product.getPrice());
        findProduct.setDescription(product.getDescription());
        findProduct.setLast_update(product.getLast_update());
        findProduct.setCategory(product.getCategory());

        return productRepository.save(findProduct);
    }

//    @Override
//    public String deleteProductById(Long id) {
//        productRepository.deleteById(id);
//        return id+"-тай Хэрэглэгчийн мэдээлэл устлаа.";
//    }

    public String deleteProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            // Delete associated OrderedProduct records
            orderedProductRepository.deleteByProductId(id);
            productRepository.deleteById(id);
            return "Product with ID " + id + " deleted successfully.";
        } else {
            return "Product with ID " + id + " not found.";
        }
    }

}
