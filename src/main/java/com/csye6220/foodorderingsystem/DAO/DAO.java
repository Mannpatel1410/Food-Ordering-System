package com.csye6220.foodorderingsystem.DAO;

import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class DAO {

private static final Logger log = Logger.getAnonymousLogger();
    
    @Autowired
    private SessionFactory sessionFactory;

    protected DAO() {}

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    protected void begin() {
        getSession().beginTransaction();
    }

    protected void commit() {
        getSession().getTransaction().commit();
    }

    protected void rollback() {
        try {
            if (getSession().getTransaction().isActive()) {
                getSession().getTransaction().rollback();
            }
        } catch (HibernateException e) {
            log.warning("Cannot rollback transaction: " + e.getMessage());
        }
    }
}
