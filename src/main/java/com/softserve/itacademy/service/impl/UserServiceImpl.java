package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.UserRepository;
import com.softserve.itacademy.service.UserService;
import com.softserve.itacademy.util.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();

    @Override
    public User create(User user) {

        Session session = sessionFactory.openSession();
        User result = null;
        try {
            session.getTransaction().begin();
            session.persist(user);
            result = session.get(User.class, user.getEmail());
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            System.err.printf("Error saving account %s. \n Transaction has been rollback", user);
        } finally {
            session.close();
        }

        return result;
    }

    @Override
    public User readById(long id) {

        Session session = sessionFactory.openSession();
        User result = null;
        try {
            session.getTransaction().begin();
            result = session.get(User.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.printf("There is no such User with id: %s", id);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public User update(User user) {

        Session session = sessionFactory.openSession();
        User result = null;
        session.getTransaction().begin();
        try {
            session.saveOrUpdate(user);
            result = session.get(User.class, user.getId());
            session.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            session.getTransaction().rollback();
            System.err.printf("Unable to update user with id: %s", user.getId());
        } finally {
            session.close();
        }

        return result;
    }

    @Override
    public void delete(long id) {
        Session session = sessionFactory.openSession();

        try {
            session.getTransaction().begin();
            session.delete(id);
            session.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            System.err.printf("Unable to remove user with id: %s", id);
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAll() {
        Session session = sessionFactory.openSession();
        List<User> users = new ArrayList<>();

        try {
            session.getTransaction().begin();
            users = session.createQuery("from User").list();
            session.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            System.err.println("Can't fetch all users.");
        } finally {
            session.close();
        }
        return users;
    }
}
