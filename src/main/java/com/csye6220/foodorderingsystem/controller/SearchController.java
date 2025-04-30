package com.csye6220.foodorderingsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.csye6220.foodorderingsystem.DAO.MenuItemDAO;
import com.csye6220.foodorderingsystem.DAO.RestaurantDAO;
import com.csye6220.foodorderingsystem.model.MenuItem;
import com.csye6220.foodorderingsystem.model.Restaurant;

@Controller
@RequestMapping("/search")
public class SearchController {
	
    @Autowired
    private RestaurantDAO restaurantDAO;
    
    @Autowired
    private MenuItemDAO menuItemDAO;
    
    @GetMapping
    public String search(@RequestParam String keyword, Model model) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return "redirect:/dashboard";
        }
        
        List<Restaurant> restaurants = restaurantDAO.searchRestaurants(keyword);
        List<MenuItem> menuItems = menuItemDAO.searchMenuItems(keyword);
        
        model.addAttribute("keyword", keyword);
        model.addAttribute("restaurants", restaurants);
        model.addAttribute("menuItems", menuItems);
        
        return "search-results";
    }
}
