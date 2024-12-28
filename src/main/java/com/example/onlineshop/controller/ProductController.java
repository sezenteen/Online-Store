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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;

@Controller
class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;


    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/products")
    public ModelAndView listProducts() {
        ModelAndView modelAndView = new ModelAndView("/product/products");
        modelAndView.addObject("products", productService.getAllProducts());
        return modelAndView;
    }

    @GetMapping("/products/{id}")
    public String productsByCategory(@PathVariable("id") Long id, Model model) {
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isEmpty()) {
            System.out.println("Category not found for ID: " + id);
            return "redirect:/products?error=CategoryNotFound";
        }
        System.out.println("Category found: " + category.get().getName());
        model.addAttribute("select_category", category.get());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("products", productService.findByCategoryId(id));
        return "/category/products_by_category";
    }

    @GetMapping("/products/new")
    public String createProductForm(Model model) {
        if (!model.containsAttribute("product")) {
            model.addAttribute("product", new Product());
        }
        model.addAttribute("categories", categoryService.getAllCategories());
        return "/product/product_form";
    }

    @PostMapping("/product")
    public String saveProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        System.out.println("Product received: " + product);
        if (bindingResult.hasErrors()) {
            System.out.println("Validation errors: " + bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("product", product)
                    .addFlashAttribute("org.springframework.validation.BindingResult.product", bindingResult);
            return "redirect:/products/new";
        }
        productService.createProduct(product);
        redirectAttributes.addFlashAttribute("success", "Product created successfully!");
        return "redirect:/products";
    }



    @GetMapping("/products/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isEmpty()) {
            return "redirect:/products?error=ProductNotFound";
        }
        model.addAttribute("product", product.get());
        model.addAttribute("categories", categoryService.getAllCategories()); // Pass list of categories
        return "/product/product_edit";
    }


    @PostMapping("/product/{id}")
    public String updateProduct(@PathVariable Long id,
                                @Valid @ModelAttribute("product") Product product,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("product", product)
                    .addFlashAttribute("org.springframework.validation.BindingResult.product", bindingResult);
            return "redirect:/products/edit/" + id;
        }
        Optional<Product> existingProduct = productService.getProductById(id);
        if (existingProduct.isEmpty()) {
            return "redirect:/products?error=ProductNotFound";
        }

        // Update existing product
        Product updatedProduct = existingProduct.get();
        updatedProduct.setName(product.getName());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setDescription(product.getDescription());
        updatedProduct.setCategory(product.getCategory());

        productService.createProduct(updatedProduct); // Save updated product
        redirectAttributes.addFlashAttribute("success", "Product updated successfully!");
        return "redirect:/products";
    }




    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (productService.getProductById(id).isPresent()) {
            productService.deleteProductById(id);
            redirectAttributes.addFlashAttribute("success", "Product deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Product not found!");
        }
        return "redirect:/product/products";
    }


}