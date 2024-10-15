package com.example.onlineshop.controller;

import com.example.onlineshop.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class HomeController {

    private final CategoryService categoryService;

    public HomeController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String home(Model model, @RequestParam(name="id", defaultValue ="1") Long id) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "index";
    }
//    @GetMapping("/test")
//    public ModelAndView test() {
//        ModelAndView modelAndView = new ModelAndView("test");
//        modelAndView.addObject("mydate", new Date());
//        return modelAndView;
//    }
}
