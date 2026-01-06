package com.shopnest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shopnest.entities.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserProfileController {

    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", loggedInUser);
        return "userProfile";
    }
}
