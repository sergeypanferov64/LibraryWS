package ru.spanferov.library.dao;

import java.util.List;
import ru.spanferov.library.domain.Order;
import ru.spanferov.library.domain.OrderEntry;
import ru.spanferov.library.util.OrderSearchCriteria;

public interface OrderDAO {

    public List<Order> getOrders(OrderSearchCriteria osc);
    
    public void saveOrder(Order order);
    
    public void updateOrder(Order order);
    
    public void saveOrderEntry(OrderEntry oe);
    
    public Order getOrderById(int id);

}
