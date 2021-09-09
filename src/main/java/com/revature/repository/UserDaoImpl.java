package com.revature.repository;

import com.revature.models.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("userDao")
@Transactional
public class UserDaoImpl implements UserDao {
    private SessionFactory sessionFactory;

    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void insert(User user) {
        this.sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public User selectByUsername(String username) {
        return this.sessionFactory.getCurrentSession().createQuery(
                "From User where username = '" + username + "'",
                User.class).getSingleResult();
    }

    @Override
    public User selectByEmail(String email) {
        return this.sessionFactory.getCurrentSession().createQuery(
                "From User where email = '" + email + "'",
                User.class).getSingleResult();
    }

    @Override
    public void updateUserInfo(User user) {
        this.sessionFactory.getCurrentSession().update(user);
    }
}
