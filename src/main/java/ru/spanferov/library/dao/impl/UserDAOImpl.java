package ru.spanferov.library.dao.impl;

import java.util.List;
import org.hibernate.Criteria;
import ru.spanferov.library.dao.UserDAO;
import ru.spanferov.library.domain.User;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class UserDAOImpl implements UserDAO {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> getUsers() {
        Criteria crit = getSessionFactory().getCurrentSession().createCriteria(User.class, "u");
        crit.addOrder(org.hibernate.criterion.Order.asc("u.lastName"));
        return crit.list();
    }

    @Override
    public User getUserById(int userId) {
        Criteria crit = getSessionFactory().getCurrentSession().createCriteria(User.class);
        crit.add(Restrictions.eq("id", userId));
        User user = (User) crit.uniqueResult();
        return user;
    }

    @Override
    public void saveUser(User user) {
        getSessionFactory().getCurrentSession().save(user);
    }
    
    @Override
    public void updateUser(User user) {
        getSessionFactory().getCurrentSession().update(user);
    }

    @Override
    public void deleteUser(User user) {
        getSessionFactory().getCurrentSession().delete(user);
    }

    @Override
    public User getUserByEmail(String email) {
        Criteria crit = getSessionFactory().getCurrentSession().createCriteria(User.class);
        crit.add(Restrictions.eq("email", email));
        User user = (User) crit.uniqueResult();
        return user;
    }
}
