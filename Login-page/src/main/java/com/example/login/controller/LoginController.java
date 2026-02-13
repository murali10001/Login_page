package com.example.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.login.entity.User;
import com.example.login.repositary.UserRepository;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, Model model) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return "dashboard"; 
        }
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    @GetMapping("/signup")
    public String showSignupPage(Model model) {
        model.addAttribute("user", new User()); 
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(@ModelAttribute User user) {
        userRepository.save(user); 
        return "redirect:/login?success";
    }

    @GetMapping("/forgot")
    public String showForgotPage() {
        return "forgot";
    }

    @PostMapping("/forgot")
    public String resetPassword(@RequestParam String email, 
                                @RequestParam String answer, 
                                @RequestParam String newPassword, 
                                Model model) {
        User user = userRepository.findByEmail(email);
        
        if (user != null && user.getSecurityAnswer().equalsIgnoreCase(answer)) {
            user.setPassword(newPassword); 
            userRepository.save(user);
            return "redirect:/login?resetSuccess";
        }
        
        model.addAttribute("error", "Email not found or Security Answer is wrong!");
        return "forgot";
    }
    
    @GetMapping("/dashboard")
    public String showDashboard() {
        return "dashboard";
    }
}