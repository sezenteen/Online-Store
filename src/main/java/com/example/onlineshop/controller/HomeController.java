package com.example.onlineshop.controller;

import com.example.onlineshop.service.CategoryService;
import com.example.onlineshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class HomeController {

    private final CategoryService categoryService;
    private final ProductService productService;

    public HomeController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/")
    public String home(Model model, @RequestParam(name="id", defaultValue ="1") Long id) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("products", productService.getAllProducts());
        return "index";
    }
//    @GetMapping("/test")
//    public ModelAndView test() {
//        ModelAndView modelAndView = new ModelAndView("test");
//        modelAndView.addObject("mydate", new Date());
//        return modelAndView;
//    }
}
