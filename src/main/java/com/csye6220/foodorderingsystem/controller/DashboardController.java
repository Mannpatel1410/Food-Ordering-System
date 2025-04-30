package com.csye6220.foodorderingsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.csye6220.foodorderingsystem.DAO.RestaurantDAO;
import com.csye6220.foodorderingsystem.model.Restaurant;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
	
	@Autowired
    private RestaurantDAO restaurantDAO;
    
    // GET Mehtod for showing Dashboard
    @GetMapping
    public String showDashboard(HttpSession session, Model model) {
        
        List<Restaurant> restaurants = restaurantDAO.getAllRestaurants();
        model.addAttribute("restaurants", restaurants);
        return "dashboard";
    }
    
    // GET Method for showing Restaurants Menu
    @GetMapping("/restaurant/{id}")
    public String showRestaurantMenu(@PathVariable Long id, Model model, HttpSession session) {
        
        Restaurant restaurant = restaurantDAO.getRestaurantWithMenu(id);
        model.addAttribute("restaurant", restaurant);
        return "restaurant-menu";
    }
}
