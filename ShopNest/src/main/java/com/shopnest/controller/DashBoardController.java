package com.shopnest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shopnest.dto.DashboardData;
import com.shopnest.service.DashboardService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashBoardController {

    private final DashboardService dashboardService;

    public DashBoardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {

        DashboardData data = dashboardService.getDashboardData();

        model.addAttribute("products", data.getProducts());
        model.addAttribute("categories", data.getCategories());
        model.addAttribute("cartCount",
                session.getAttribute("cartCount") != null
                ? session.getAttribute("cartCount") : 0);

        return "dashboard";
    }
}
