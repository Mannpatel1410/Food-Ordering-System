package com.csye6220.foodorderingsystem.DAO;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.csye6220.foodorderingsystem.model.MenuItem;
import com.csye6220.foodorderingsystem.model.Restaurant;

@Repository
public class MenuItemDAO extends DAO{

    public MenuItem findById(Long id) {
        try {
            begin();
            MenuItem item = getSession()
                .createQuery("FROM MenuItem WHERE id = :id", MenuItem.class)
                .setParameter("id", id)
                .uniqueResult();
            commit();
            return item;
        } catch (Exception e) {
            rollback();
            throw new RuntimeException("Failed to find menu item by ID: " + id, e);
        }
    }

    public void save(MenuItem item) {
        try {
            begin();
            getSession().persist(item);
            commit();
        } catch (Exception e) {
            rollback();
            throw new RuntimeException("Failed to save menu item", e);
        }
    }
    
    public void update(MenuItem item) {
        try {
            begin();
            getSession().merge(item);
            commit();
        } catch (Exception e) {
            rollback();
            throw new RuntimeException("Failed to update menu item", e);
        }
    }
    
    public void delete(MenuItem item) {
        try {
            begin();
            getSession().remove(item);
            commit();
        } catch (Exception e) {
            rollback();
            throw new RuntimeException("Failed to delete menu item", e);
        }
    }
    
    public List<MenuItem> findByRestaurant(Restaurant restaurant) {
        try {
            begin();
            List<MenuItem> items = getSession()
                .createQuery("FROM MenuItem WHERE restaurant = :restaurant", MenuItem.class)
                .setParameter("restaurant", restaurant)
                .list();
            commit();
            return items;
        } catch (Exception e) {
            rollback();
            throw new RuntimeException("Failed to find menu items by restaurant", e);
        }
    }
    
    public List<MenuItem> searchMenuItems(String keyword) {
        try {
        	begin();
            String searchTerm = "%" + keyword.toLowerCase() + "%";
            List<MenuItem> results = getSession()
                    .createQuery("FROM MenuItem m LEFT JOIN FETCH m.restaurant WHERE LOWER(m.name) LIKE :keyword " +
                                 "OR LOWER(m.description) LIKE :keyword " +
                                 "OR LOWER(m.category) LIKE :keyword", MenuItem.class)
                    .setParameter("keyword", searchTerm)
                    .list();
            commit();
            return results;
            
        } catch (Exception e) {
        	rollback();
            throw new RuntimeException("Failed to search menu items", e);
        }
    }
}
