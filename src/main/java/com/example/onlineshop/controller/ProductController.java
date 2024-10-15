package com.example.onlineshop.controller;

import com.example.onlineshop.model.Category;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.service.CategoryService;
import com.example.onlineshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    // List all products
    @GetMapping("/products")
    public ModelAndView listProducts(ModelAndView modelAndView) {
        List<Product> products = productService.getAllProducts();
        modelAndView.addObject("products", products);
        modelAndView.setViewName("product/products");
        return modelAndView;
    }

    // Show product by ID
    @GetMapping("/product/{id}")
    public String product(Model model, @PathVariable("id") Long id) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
        } else {
            model.addAttribute("error", "Product not found");
        }
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/product-details";
    }

    // Show form to create a new product
    @GetMapping("/product/new")
    public ModelAndView createProductForm(ModelAndView modelAndView) {
        Product product = new Product();
        modelAndView.addObject("product", product);
        modelAndView.addObject("categories", categoryService.getAllCategories());
        modelAndView.setViewName("product/product-form");
        return modelAndView;
    }

    // Handle form submission for creating a product
    @PostMapping("/product")
    public String saveProduct(@Valid @ModelAttribute("product") Product product,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "product/product-form";
        }
        productService.createProduct(product);
        return "redirect:/products";
    }

    // Show form to edit an existing product
    @GetMapping("/product/edit/{id}")
    public ModelAndView editProductForm(@PathVariable("id") Long id, ModelAndView modelAndView) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            modelAndView.addObject("product", product.get());
            modelAndView.addObject("categories", categoryService.getAllCategories());
            modelAndView.setViewName("product/product-form");
        } else {
            modelAndView.setViewName("error/404");
        }
        return modelAndView;
    }

    // Handle form submission for updating a product
    @PostMapping("/product/edit/{id}")
    public String updateProduct(@PathVariable("id") Long id,
                                @Valid @ModelAttribute("product") Product product,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "product/product-form";
        }
        product.setId(id); // Ensure that the ID is set for the updated product
        productService.updateProduct(product);
        return "redirect:/products";
    }

    // Delete product by ID
    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProductById(id);
        return "redirect:/products";
    }

    // Show products by category
    @GetMapping("/category/{id}/products")
    public String getProductsByCategory(@PathVariable("id") Long id, Model model) {
        List<Product> products = (List<Product>) productService.findByCategoryId(id);
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isPresent()) {
            model.addAttribute("category", category.get());
            model.addAttribute("products", products);
        } else {
            model.addAttribute("error", "Category not found");
        }
        return "product/products-by-category";
    }
}
