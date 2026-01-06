package com.shopnest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.shopnest.repositories.ProductRepository;
import com.shopnest.repositories.ProductCategoryRepositorty;

@Controller
public class SearchController {

    private final ProductRepository productRepo;
    private final ProductCategoryRepositorty categoryRepo;

    public SearchController(ProductRepository productRepo,
                            ProductCategoryRepositorty categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    @GetMapping("/search")
    public String search(@RequestParam String q, Model model) {

        model.addAttribute("products",
            productRepo.findAll().stream()
                .filter(p -> p.getName().toLowerCase()
                .contains(q.toLowerCase()))
                .toList());

        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("q", q);

        return "dashboard";
    }
}
