package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.repository.RoleRepository;
import com.softserve.itacademy.service.RoleService;
import com.softserve.itacademy.util.HibernateSessionFactory;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private final SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();

    @Override
    public Role create(Role role) {
        Session session = sessionFactory.openSession();
        Role result = null;
        try {
            session.getTransaction().begin();
            session.persist(role);
            session.flush();
            session.clear();
            result = session.get(Role.class, role.getId());
            session.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            session.getTransaction().rollback();
            System.err.printf("Unable to save role : %s", role);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Role readById(long id) {
        return null;
    }

    @Override
    public Role update(Role role) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<Role> getAll() {
        return null;
    }
}
