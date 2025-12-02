package com.tasktrackr.tasktrackr.controller;

import com.tasktrackr.tasktrackr.model.*;
import com.tasktrackr.tasktrackr.repository.*;
import com.tasktrackr.tasktrackr.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepo;

    public AuthController(UserService userService, UserRepository userRepo) {
        this.userService = userService;
        this.userRepo = userRepo;
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("userForm", new User());
        return "signup"; // templates/signup.html
    }

    @PostMapping("/signup")
    public String doSignup(@ModelAttribute("userForm") User form,
                           Model model) {
        try {
            userService.register(form.getUsername(), form.getEmail(),
                    form.getPassword(), form.getName());
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "signup";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginError", null);
        return "login"; // templates/login.html
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam("usernameOrEmail") String usernameOrEmail,
                          @RequestParam("password") String password,
                          HttpSession session,
                          Model model) {
        User user = userService.login(usernameOrEmail, password);
        if (user == null) {
            model.addAttribute("loginError", "Invalid credentials");
            return "login";
        }
        session.setAttribute("userId", user.getUserId());
        session.setAttribute("username", user.getUsername());

        return "redirect:/tasks";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
