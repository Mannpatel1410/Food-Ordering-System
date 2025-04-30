package com.csye6220.foodorderingsystem.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.csye6220.foodorderingsystem.DAO.MenuItemDAO;
import com.csye6220.foodorderingsystem.DAO.UserDAO;
import com.csye6220.foodorderingsystem.model.Cart;
import com.csye6220.foodorderingsystem.model.CartItem;
import com.csye6220.foodorderingsystem.model.MenuItem;
import com.csye6220.foodorderingsystem.model.User;
import com.csye6220.foodorderingsystem.service.CartService;
import com.csye6220.foodorderingsystem.service.OrderService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {
    
    @Autowired
    private MenuItemDAO menuItemDAO;
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private UserDAO userDAO;
    
    // GET Method for viewing cart
    @GetMapping("/view")
    public String viewCart(HttpSession session, Model model) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        
        double total = cartService.totalAmount(cart);
        
        model.addAttribute("cart", cart);
        model.addAttribute("cartService", cartService);
        model.addAttribute("total", total);
        
        return "cart";
    }
    
    // POST Method for adding item to cart
    @PostMapping("/add")
    public String addToCart(@RequestParam Long menuItemId, @RequestParam(required = false) Long restaurantId, HttpSession session) {
        
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        
        MenuItem menuItem = menuItemDAO.findById(menuItemId);
        if (menuItem == null) {
            throw new RuntimeException("Menu item not found: " + menuItemId);
        }
        
        cartService.addItem(cart, menuItem);
        
        String redirectUrl = "/dashboard";
        if (restaurantId != null) {
            redirectUrl = "/dashboard/restaurant/" + restaurantId;
        }
        
        return "redirect:" + redirectUrl + "?addedItemId=" + menuItemId;
    }
    
    // POST Method for removing items from cart
    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long menuItemId, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            cartService.removeItem(cart, menuItemId);
        }
        return "redirect:/cart/view";
    }
    
    // POST Method to update items in the cart
    @PostMapping("/update")
    public String updateCartItem(@RequestParam Long menuItemId, @RequestParam(required = false, defaultValue = "1") Integer quantity, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
        	int validQuantity = (quantity != null && quantity > 0) ? quantity : 1;
            cartService.updateItem(cart, menuItemId, validQuantity);
        }
        return "redirect:/cart/view";
    }
    
    // GET Method for order checkout
    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model){
        
        if (session.getAttribute("completedOrderId") != null) {
            model.addAttribute("orderID", session.getAttribute("completedOrderId"));
            
            if (session.getAttribute("userAddress") != null) {
                model.addAttribute("userAddress", session.getAttribute("userAddress"));
            }
            
            return "checkout";
        }
        
        Cart checkout = (Cart) session.getAttribute("cart");
        if(checkout == null || checkout.getItems().isEmpty()) {
            return "redirect:/cart/view";
        }
        
        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            return "redirect:/login";
        }
        
        User currentUser = userDAO.findByEmail(userEmail);
        String userAddress = currentUser.getAddress();
        
        model.addAttribute("userAddress", currentUser.getAddress());
        session.setAttribute("userAddress", userAddress);
        
        Cart cartInvoice = new Cart();
        cartInvoice.setItems(new ArrayList<>());
        for (CartItem item : checkout.getItems()) {
            CartItem newItem = new CartItem();
            newItem.setMenuItem(item.getMenuItem());
            newItem.setQuantity(item.getQuantity());
            newItem.setCart(cartInvoice);
            cartInvoice.getItems().add(newItem);
        }
        
        int orderID = new java.util.Random().nextInt(9000) + 1000;
        
        orderService.createOrderFromCart(currentUser, checkout, orderID);
        
        
        session.setAttribute("completedOrderId", orderID);
        session.setAttribute("cartInvoice", cartInvoice);
        session.removeAttribute("cart");
        
        model.addAttribute("orderID", orderID);
        
        return "checkout";
    }
    
    @GetMapping("/complete")
    public String completeOrder(HttpSession session) {
        session.removeAttribute("completedOrderId");
        session.removeAttribute("cartInvoice");
        return "redirect:/dashboard";
    }
}
