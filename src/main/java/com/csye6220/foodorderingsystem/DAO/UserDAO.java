package com.csye6220.foodorderingsystem.DAO;

import org.springframework.stereotype.Repository;

import com.csye6220.foodorderingsystem.model.User;

@Repository
public class UserDAO extends DAO{

    public User findByEmail(String email) {
        try {
            begin();
            String hql = "FROM User u WHERE u.email = :email";
            User user = getSession().createQuery(hql, User.class)
                .setParameter("email", email)
                .uniqueResult();
            commit();
            return user;
        } catch (Exception e) {
            rollback();
            throw new RuntimeException("Error finding user by email: " + email, e);
        }
    }
    
    public void saveUser(User user) {
        try {
            begin();
            if (user.getId() == null) {
                getSession().persist(user);
            } else {
                getSession().merge(user);
            }
            commit();
        } catch (Exception e) {
            rollback();
            throw new RuntimeException("Error saving user", e);
        }
    }
}
