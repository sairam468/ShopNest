package com.shopnest.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.shopnest.entities.User;
import com.shopnest.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserAuthController {

    private final UserService userService;

    public UserAuthController(UserService userService) {
        this.userService = userService;
    }

    // -------- USER LOGIN --------
    @GetMapping("/login")
    public String userLoginPage() {
        return "userLogin";
    }

    @PostMapping("/login")
    public String processUserLogin(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        Optional<User> user = userService.login(email, password);

        if (user.isPresent()) {
            session.setAttribute("loggedInUser", user.get());
            return "redirect:/dashboard";
        }

        model.addAttribute("error", true);
        return "userLogin";
    }

    // -------- USER REGISTER --------
    @GetMapping("/register")
    public String registerPage() {
        return "createUserAcc";
    }

    @PostMapping("/user/register")
    public String processRegistration(
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            Model model) {

        if (!password.equals(confirmPassword)) {
            model.addAttribute("passwordError", true);
            return "createUserAcc";
        }

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password); // plain text for now

        boolean registered = userService.register(user);

        if (!registered) {
            model.addAttribute("emailExists", true);
            return "createUserAcc";
        }

        model.addAttribute("success", true);
        return "createUserAcc";
    }

    // -------- LOGOUT --------
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
