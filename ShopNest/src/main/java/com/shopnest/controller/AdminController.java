package com.shopnest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.shopnest.repositories.ProductRepository;
import com.shopnest.repositories.ProductCategoryRepositorty;
import com.shopnest.service.AdminService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final ProductRepository productRepo;
    private final ProductCategoryRepositorty categoryRepo;

    public AdminController(AdminService adminService,
                           ProductRepository productRepo,
                           ProductCategoryRepositorty categoryRepo) {
        this.adminService = adminService;
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    // -------- ADMIN LOGIN --------
    @GetMapping("/login")
    public String adminLoginPage() {
        return "adminLogin";
    }

    @PostMapping("/login")
    public String processAdminLogin(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        boolean authenticated = adminService.authenticate(username, password);

        if (authenticated) {
            session.setAttribute("admin", username);
            return "redirect:/admin/admindashboard";
        }

        model.addAttribute("error", true);
        return "adminLogin";
    }

    // -------- ADMIN DASHBOARD --------
    @GetMapping("/admindashboard")
    public String adminDashboard(HttpSession session, Model model) {

        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }

        // ✅ LOAD DATA FOR VIEW
        model.addAttribute("products", productRepo.findAll());
        model.addAttribute("categories", categoryRepo.findAll());

        return "adminDashboard";
    }

    // -------- ADMIN LOGOUT --------
    @GetMapping("/logout")
    public String adminLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }
}
