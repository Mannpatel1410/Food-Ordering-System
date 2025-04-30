package com.csye6220.foodorderingsystem.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.csye6220.foodorderingsystem.DAO.UserDAO;
import com.csye6220.foodorderingsystem.model.Cart;
import com.csye6220.foodorderingsystem.model.CartItem;
import com.csye6220.foodorderingsystem.model.Order;
import com.csye6220.foodorderingsystem.model.User;
import com.csye6220.foodorderingsystem.service.OrderService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private UserDAO userDAO;
    
    @GetMapping("/history")
    public String viewOrderHistory(HttpSession session, Model model) {

        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            return "redirect:/login";
        }
        
        User currentUser = userDAO.findByEmail(userEmail);
        if (currentUser == null) {
            return "redirect:/login";
        }
        
        List<Order> orderHistory = orderService.getUserOrderHistory(currentUser);
        model.addAttribute("orders", orderHistory);
        
        return "order-history";
    }
    
    @GetMapping("/details/{orderId}")
    public String viewOrderDetails(@PathVariable Long orderId, HttpSession session, Model model) {

        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            return "redirect:/login";
        }
        
        User currentUser = userDAO.findByEmail(userEmail);
        if (currentUser == null) {
            return "redirect:/login";
        }
        
        Order order = orderService.getOrderDetails(orderId);
        
        if (order == null || !order.getUser().getId().equals(currentUser.getId())) {
            return "redirect:/orders/history";
        }
        
        model.addAttribute("order", order);
        
        return "order-details";
    }
    
    @PostMapping("/reorder/{orderId}")
    public String reorderItems(@PathVariable Long orderId, HttpSession session) {

        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            return "redirect:/login";
        }
        
        User currentUser = userDAO.findByEmail(userEmail);
        if (currentUser == null) {
            return "redirect:/login";
        }
        
        Order order = orderService.getOrderDetails(orderId);

        if (order == null || !order.getUser().getId().equals(currentUser.getId())) {
            return "redirect:/orders/history";
        }
        
        Cart cart = new Cart();
        cart.setItems(new ArrayList<>());
        
        order.getItems().forEach(orderItem -> {
            CartItem cartItem = new CartItem();
            cartItem.setMenuItem(orderItem.getMenuItem());
            cartItem.setQuantity(orderItem.getQuantity());
            cartItem.setCart(cart);
            cart.getItems().add(cartItem);
        });
        
        session.setAttribute("cart", cart);
        
        return "redirect:/cart/view";
    }
}
