package com.shopnest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shopnest.entities.User;
import com.shopnest.repositories.OrderRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {
	
	private final OrderRepository orderRepo;

    public OrderController(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @GetMapping("/order-success")
    public String orderSuccess() {
        return "order-success";
    }
    
    @GetMapping("/orders")
    public String myOrders(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }


        model.addAttribute("user", user);
        model.addAttribute("orders", orderRepo.findByUser(user));

        return "userProfile";
    }
    
    
}
