package ru.spanferov.library.dao.impl;

import java.util.List;
import org.hibernate.Criteria;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import ru.spanferov.library.dao.OrderDAO;
import ru.spanferov.library.domain.Order;
import ru.spanferov.library.domain.OrderEntry;
import ru.spanferov.library.util.OrderSearchCriteria;

public class OrderDAOImpl implements OrderDAO {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Order> getOrders(OrderSearchCriteria osc) {
        Criteria crit = getSessionFactory().getCurrentSession().createCriteria(Order.class, "o");

        if (osc.getId() != null && osc.getId().length() > 0) {
            crit.add(Restrictions.eq("id", Integer.parseInt(osc.getId())));
        }
        if (osc.getStatus() > -1) {
            crit.add(Restrictions.eq("o.status", osc.getStatus()));
        }
        crit.addOrder(org.hibernate.criterion.Order.asc("o.status"));
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return crit.list();
    }

    @Override
    public void saveOrder(Order order) {
        getSessionFactory().getCurrentSession().save(order);
    }

    @Override
    public void updateOrder(Order order) {
        getSessionFactory().getCurrentSession().update(order);
    }

    @Override
    public void saveOrderEntry(OrderEntry oe) {
        getSessionFactory().getCurrentSession().save(oe);
    }

    @Override
    public Order getOrderById(int book_id) {
        Criteria crit = getSessionFactory().getCurrentSession().createCriteria(Order.class);
        crit.add(Restrictions.eq("id", book_id));
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        Order order = (Order) crit.uniqueResult();
        return order;
    }

}
