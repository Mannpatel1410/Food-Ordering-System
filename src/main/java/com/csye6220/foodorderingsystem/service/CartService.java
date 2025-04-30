package com.csye6220.foodorderingsystem.service;

import java.util.Iterator;

import org.springframework.stereotype.Service;

import com.csye6220.foodorderingsystem.model.Cart;
import com.csye6220.foodorderingsystem.model.CartItem;
import com.csye6220.foodorderingsystem.model.MenuItem;

@Service
public class CartService {
    
    public void addItem(Cart cart, MenuItem menuItem) {
        for (CartItem item : cart.getItems()) {
            if (item.getMenuItem().getId().equals(menuItem.getId())) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        
        CartItem newItem = new CartItem();
        newItem.setMenuItem(menuItem);
        newItem.setQuantity(1);
        newItem.setCart(cart);
        cart.getItems().add(newItem);
    }
    
    public void removeItem(Cart cart, Long menuItemId) {
        Iterator<CartItem> iterator = cart.getItems().iterator();
        while (iterator.hasNext()) {
            CartItem item = iterator.next();
            if (item.getMenuItem().getId().equals(menuItemId)) {
                iterator.remove();
                break;
            }
        }
    }
    
    public void updateItem(Cart cart, Long menuItemId, int quantity) {
        cart.getItems().stream()
            .filter(item -> item.getMenuItem().getId().equals(menuItemId))
            .findFirst()
            .ifPresent(item -> item.setQuantity(quantity));
    }
    
    public double totalAmount(Cart cart) {
        return cart.getItems().stream()
            .mapToDouble(item -> item.getMenuItem().getPrice() * item.getQuantity())
            .sum();
    }
}
