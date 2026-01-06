package com.shopnest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.shopnest.repositories.ProductRepository;
import com.shopnest.repositories.ProductCategoryRepositorty;

@Controller
public class CategoryController {

    private final ProductRepository productRepo;
    private final ProductCategoryRepositorty categoryRepo;

    public CategoryController(ProductRepository productRepo,
                              ProductCategoryRepositorty categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    @GetMapping("/category/{id}")
    public String byCategory(@PathVariable Long id, Model model) {

        model.addAttribute("products",
            productRepo.findAll().stream()
                .filter(p -> p.getCategory().getId().equals(id))
                .toList());

        model.addAttribute("categories", categoryRepo.findAll());

        return "dashboard";
    }
}
