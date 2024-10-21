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
        Optional<Category> category = categoryService.getCategoryById(id);
        model.addAttribute("select_category", category);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("products", productService.findByCategoryId(id));
        return "/category/category";
    }

//    // Show form to create a new product
//    @GetMapping("/product/new")
//    public ModelAndView createProductForm(ModelAndView modelAndView) {
//        Product product = new Product();
//        modelAndView.addObject("name", product);
//        modelAndView.addObject("categories", categoryService.getAllCategories());
//        modelAndView.setViewName("product/product-form");
//        return modelAndView;
//    }

    @GetMapping("/products/new")
    public String createProductForm(Model model) {
        if (model.getAttribute("product") == null) {
            model.addAttribute("product", new Product());
        }
        return "product/product_form";
    }

    // bindiing result gedeg ni - hereglegcees irsen ugugdld ugugduluu zuv shivsen esehd hariu ugdug spring iin san (standard class)
    // RedirectAttributes - ymar attribute damjij baigaag hynah zoriulalttai
    @PostMapping("/product")
    public String saveProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("product", product)
                    .addFlashAttribute("org.springframework.validation.BindingResult.product", bindingResult);
            return "redirect:/products/new";
        }
        productService.createProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/product/edit/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {
        Optional<Product> product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product/product_edit";
    }
}
