package ru.spanferov.library.service;

import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import ru.spanferov.library.dao.OrderDAO;
import ru.spanferov.library.domain.Order;
import ru.spanferov.library.domain.OrderEntry;
import ru.spanferov.library.util.OrderSearchCriteria;

public class OrderService {

    private OrderDAO orderDAO;

    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public List<Order> list(OrderSearchCriteria osc) {
        return orderDAO.getOrders(osc);
    }

    @Transactional
    public Order getOrderById(int id) {
        return orderDAO.getOrderById(id);
    }

    @Transactional
    public void nextStatus(int orderId) {

        int nextStatus = 0;
        OrderEntry oe = new OrderEntry();

        Order order = orderDAO.getOrderById(orderId);

        if (order.getStatus() == Order.BOOK_LOCKED) {
            nextStatus = Order.BOOK_ISSUED;
            oe.setDate(new Date());
            oe.setNewStatus(nextStatus);
            oe.setOrder(order);
        } else if (order.getStatus() == Order.BOOK_ISSUED) {
            nextStatus = Order.BOOK_RETURNED;
            oe.setDate(new Date());
            oe.setNewStatus(nextStatus);
            oe.setOrder(order);
            order.getBookInstance().setLock(false);
        }

        order.setStatus(nextStatus);

        orderDAO.saveOrderEntry(oe);
        orderDAO.updateOrder(order);
    }

}
