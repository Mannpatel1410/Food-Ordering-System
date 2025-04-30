package com.csye6220.foodorderingsystem.DAO;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.csye6220.foodorderingsystem.model.Restaurant;

@Repository
public class RestaurantDAO extends DAO{

    public List<Restaurant> getAllRestaurants() {
        try {
            begin();
            List<Restaurant> restaurants = getSession()
                .createQuery("FROM Restaurant", Restaurant.class)
                .list();
            commit();
            return restaurants;
        } catch (Exception e) {
            rollback();
            throw new RuntimeException("Failed to fetch restaurants", e);
        }
    }
    
    public Restaurant getRestaurantWithMenu(Long restaurantId) {
        try {
            begin();
            Restaurant restaurant = getSession()
                .createQuery(
                    "SELECT r FROM Restaurant r LEFT JOIN FETCH r.menuItems WHERE r.id = :id", 
                    Restaurant.class)
                .setParameter("id", restaurantId)
                .uniqueResult();
            commit();
            return restaurant;
        } catch (Exception e) {
            rollback();
            throw new RuntimeException("Failed to load restaurant with menu", e);
        }
    }
    
    public List<Restaurant> searchRestaurants(String keyword) {
        try {
        	begin();
            String searchTerm = "%" + keyword.toLowerCase() + "%";
            List<Restaurant> results = getSession()
                    .createQuery("FROM Restaurant r WHERE LOWER(r.name) LIKE :keyword " +
                                 "OR LOWER(r.description) LIKE :keyword " +
                                 "OR LOWER(r.location) LIKE :keyword", Restaurant.class)
                    .setParameter("keyword", searchTerm)
                    .list();
            commit();
            return results;
            
        } catch (Exception e) {
        	rollback();
            throw new RuntimeException("Failed to search restaurants", e);
        }
    }
}
