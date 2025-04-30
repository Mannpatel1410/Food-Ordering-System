package com.csye6220.foodorderingsystem.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csye6220.foodorderingsystem.DAO.OrderDAO;
import com.csye6220.foodorderingsystem.model.Cart;
import com.csye6220.foodorderingsystem.model.CartItem;
import com.csye6220.foodorderingsystem.model.Order;
import com.csye6220.foodorderingsystem.model.OrderItem;
import com.csye6220.foodorderingsystem.model.User;

@Service
public class OrderService {
	
    @Autowired
    private OrderDAO orderDAO;
    
    public Order createOrderFromCart(User user, Cart cart, int orderNumber) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderNumber(String.valueOf(orderNumber));
        order.setTotalAmount(cart.getTotalPrice());
        order.setOrderDate(new Date());
        order.setStatus("PLACED");
        
        // Method to convert cart items to order items
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setMenuItem(cartItem.getMenuItem());
            orderItem.setItemName(cartItem.getMenuItem().getName());
            orderItem.setItemPrice(cartItem.getMenuItem().getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            order.addItem(orderItem);
        }
        
        orderDAO.save(order);
        return order;
    }
    
    public List<Order> getUserOrderHistory(User user) {
        return orderDAO.findByUser(user);
    }
    
    public Order getOrderDetails(Long orderId) {
        return orderDAO.findById(orderId);
    }
    
    public Order getOrderByNumber(String orderNumber) {
        return orderDAO.findByOrderNumber(orderNumber);
    }
    
    public void updateOrderStatus(Long orderId, String newStatus) {
        orderDAO.updateStatus(orderId, newStatus);
    }
}
