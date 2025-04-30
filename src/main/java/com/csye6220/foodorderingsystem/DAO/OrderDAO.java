package com.csye6220.foodorderingsystem.DAO;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.csye6220.foodorderingsystem.model.Order;
import com.csye6220.foodorderingsystem.model.User;

@Repository
public class OrderDAO extends DAO{
    public void save(Order order) {
        try {
            begin();
            getSession().persist(order);
            commit();
        } catch (Exception e) {
            rollback();
            throw new RuntimeException("Failed to save order", e);
        }
    }
    
    public Order findById(Long id) {
        try {
            begin();
            Order order = getSession()
                .createQuery("FROM Order o LEFT JOIN FETCH o.items LEFT JOIN FETCH o.items.menuItem WHERE o.id = :id", Order.class)
                .setParameter("id", id)
                .uniqueResult();
            commit();
            return order;
        } catch (Exception e) {
            rollback();
            throw new RuntimeException("Failed to find order by ID: " + id, e);
        }
    }
    
    public Order findByOrderNumber(String orderNumber) {
        try {
            begin();
            Order order = getSession()
                .createQuery("FROM Order o WHERE o.orderNumber = :orderNumber", Order.class)
                .setParameter("orderNumber", orderNumber)
                .uniqueResult();
            commit();
            return order;
        } catch (Exception e) {
            rollback();
            throw new RuntimeException("Failed to find order by number: " + orderNumber, e);
        }
    }
    
    public List<Order> findByUser(User user) {
        try {
            begin();
            List<Order> orders = getSession()
                .createQuery("FROM Order o LEFT JOIN FETCH o.items WHERE o.user = :user ORDER BY o.orderDate DESC", Order.class)
                .setParameter("user", user)
                .list();
            commit();
            return orders;
        } catch (Exception e) {
            rollback();
            throw new RuntimeException("Failed to find orders by user", e);
        }
    }
    
    public void updateStatus(Long orderId, String newStatus) {
        try {
            begin();
            getSession()
                .createQuery("UPDATE Order o SET o.status = :status WHERE o.id = :id")
                .setParameter("status", newStatus)
                .setParameter("id", orderId)
                .executeUpdate();
            commit();
        } catch (Exception e) {
            rollback();
            throw new RuntimeException("Failed to update order status", e);
        }
    }
}
