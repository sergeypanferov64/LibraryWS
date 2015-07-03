package ru.spanferov.library.domain;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "BOOK_INSTANCES")
public class BookInstance implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    
    @Column(nullable = false)
    private boolean lock;
    
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="book_id")
    private Book book;
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy="bookInstance")
    private Set<Order> orders;

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
    
    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
    
}