package ru.spanferov.library.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "ORDERS")
public class Order implements Serializable {

    public static final int BOOK_LOCKED = 0;
    public static final int BOOK_ISSUED = 1;
    public static final int BOOK_RETURNED = 2;

    private static final String BOOK_LOCKED_STR = "LOCKED";
    private static final String BOOK_ISSUED_STR = "ISSUED";
    private static final String BOOK_RETURNED_STR = "RETURNED";

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    private BookInstance bookInstance;

    @Column(nullable = false)
    private Integer status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.EAGER)
    @OrderBy("newStatus")
    private Set<OrderEntry> orderEntries;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BookInstance getBookInstance() {
        return bookInstance;
    }

    public void setBookInstance(BookInstance bookInstance) {
        this.bookInstance = bookInstance;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<OrderEntry> getOrderEntries() {
        return orderEntries;
    }

    public void setOrderEntries(Set<OrderEntry> orderEntries) {
        this.orderEntries = orderEntries;
    }

    public static String getStatusNameById(int i) {

        switch (i) {
            case 0:
                return BOOK_LOCKED_STR;
            case 1:
                return BOOK_ISSUED_STR;
            case 2:
                return BOOK_RETURNED_STR;
            default:
                return "Invalid status id";
        }
    }

    public static Map<Integer, String> getStatusMap() {
        HashMap<Integer, String> statusMap = new HashMap();
        statusMap.put(BOOK_LOCKED, BOOK_LOCKED_STR);
        statusMap.put(BOOK_ISSUED, BOOK_ISSUED_STR);
        statusMap.put(BOOK_RETURNED, BOOK_RETURNED_STR);
        return statusMap;
    }

}
