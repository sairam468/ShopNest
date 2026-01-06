package com.shopnest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.shopnest.repositories.UserRepository;

@Controller
public class ForgotPasswordController {

    private final UserRepository userRepository;

    public ForgotPasswordController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // -------- FORGOT PASSWORD PAGE --------
    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgotPwd";
    }

    // -------- PROCESS FORGOT PASSWORD --------
    @PostMapping("/forgot-password")
    public String processForgotPassword(
            @RequestParam String email,
            Model model) {

        boolean exists = userRepository.findByEmail(email).isPresent();

        if (!exists) {
            model.addAttribute("emailNotFound", true);
            return "forgotPwd";
        }

        // Later: send reset link via email
        model.addAttribute("success", true);
        return "forgotPwd";
    }
}
