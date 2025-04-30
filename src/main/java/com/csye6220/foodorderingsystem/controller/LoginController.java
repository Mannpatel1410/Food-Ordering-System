package com.csye6220.foodorderingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.csye6220.foodorderingsystem.DAO.UserDAO;
import com.csye6220.foodorderingsystem.model.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	

    @Autowired
    private UserDAO userDAO;

    // GET Method for User Login
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("error", "");
        return "login";
    }

    // POST Method for User Login
    @PostMapping("/login")
    public String loginHandler(@RequestParam String email, @RequestParam String password, Model model, HttpSession session) {
        try {
            User user = userDAO.findByEmail(email);

            if (user != null && user.getPassword().equals(password)) {
                session.setAttribute("userEmail", user.getEmail());
                return "redirect:/dashboard";
            } else {
                model.addAttribute("message", "Invalid email or password");
                return "login";
            }
        } catch (Exception e) {
            model.addAttribute("message", "An error occurred while login");
            return "login";
        }
    }

    // GET Method for User Signup
    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("message", "");
        return "signup";
    }

    //POST Method for User Signup
    @PostMapping("/signup")
    public String signupHandler(@RequestParam String email, @RequestParam String password,
                           @RequestParam String confirmPassword, @RequestParam String address, Model model) {
        try {
            if(!password.equals(confirmPassword)) {
                model.addAttribute("message", "Password does not match.");
                return "signup";
            }

            User existingUser = userDAO.findByEmail(email);
            if(existingUser != null) {
                model.addAttribute("message", "Email already exists.");
                return "signup";
            }

            User newUser = new User(email, password, address);
            userDAO.saveUser(newUser);

            model.addAttribute("message", "User account created successfully!");
            return "login";
        } catch(Exception e) {
            e.printStackTrace();

            model.addAttribute("message", "An error occurred during signup. Please try again.");
            return "signup";
        }
    }

    // GET Method for User Logout
    @GetMapping("/logout")
    public String logoutHandler(HttpSession session) {
        session.invalidate();
        return "redirect:/login";  // Added the missing return statement
    }   
}
