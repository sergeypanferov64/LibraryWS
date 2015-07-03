package ru.spanferov.library.service;

import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import ru.spanferov.library.dao.BookDAO;
import ru.spanferov.library.dao.OrderDAO;
import ru.spanferov.library.dao.UserDAO;
import ru.spanferov.library.domain.Author;
import ru.spanferov.library.domain.Book;
import ru.spanferov.library.domain.BookInstance;
import ru.spanferov.library.domain.Order;
import ru.spanferov.library.domain.OrderEntry;
import ru.spanferov.library.domain.User;
import ru.spanferov.library.util.BookSearchCriteria;

public class BookService {

    private BookDAO bookDAO;
    private UserDAO userDAO;
    private OrderDAO orderDAO;

    public void setBookDAO(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Transactional
    public List<Book> list(BookSearchCriteria bsc) {
        return bookDAO.getBooksByCriteria(bsc);
    }

    @Transactional
    public Book getBookById(int id) {
        return bookDAO.getBookById(id);
    }

    @Transactional
    public void saveBook(Book book) {
        bookDAO.saveBook(book);
    }

    @Transactional
    public void updateBook(Book book) {
        bookDAO.updateBook(book);
    }

    @Transactional
    public void saveAuthor(Author author) {
        bookDAO.saveAuthor(author);
    }

    @Transactional
    public void deleteBookById(int id) {
        bookDAO.deleteBook(bookDAO.getBookById(id));
    }

    @Transactional
    public List<Author> getAuthorsList() {
        return bookDAO.getAuthorsList();
    }
    
    @Transactional
    public Author getAuthorById(int authorId) {
        return bookDAO.getAuthorById(authorId);
    }
    
    @Transactional
    public int getNumberOfLockedBookInstances(int bookId) {
        return bookDAO.getNumberOfLockedBookInstances(bookId);
    }
    
    @Transactional
    public int getNumberOfFreeBookInstances(int bookId) {
        return bookDAO.getNumberOfFreeBookInstances(bookId);
    }

    @Transactional
    public int getNumberOfAllBookInstances(int bookId) {
        return bookDAO.getNumberOfAllBookInstances(bookId);
    }

    @Transactional
    public void createNewBookInstanve(int bookId) {
        BookInstance bi = new BookInstance();
        bi.setBook(bookDAO.getBookById(bookId));
        bookDAO.saveBookInstance(bi);
    }

    @Transactional
    public List<Book> getBooksTakenByMember(String email) {
        User user = userDAO.getUserByEmail(email);
        return bookDAO.getBooksTakenByMember(user.getId());
    }

    @Transactional
    public List<Book> getBooksLockedByMember(String email) {
        User user = userDAO.getUserByEmail(email);
        return bookDAO.getBooksLockedByMember(user.getId());
    }

    @Transactional
    public int getBookAvaliableCodeForUser(int bookId, String email) {

        User user = userDAO.getUserByEmail(email);

        if (bookDAO.getCountOfLockedBookInstancesOfThisBookForUser(bookId, user.getId()) > 0) {
            return 2;
        } else if (bookDAO.getCountOfCollectedBookInstancesOfThisBookForUser(bookId, user.getId()) > 0) {
            return 3;
        } else if (bookDAO.getNumberOfFreeBookInstances(bookId) == 0) {
            return 4;
        } else {
            return 0;
        }

    }

    @Transactional
    public void lockBock(int bockId, String email) {

        BookInstance bi = bookDAO.getFreeInstance(bockId);
        bi.setLock(true);
        User user = userDAO.getUserByEmail(email);

        Order order = new Order();
        order.setBookInstance(bi);
        order.setUser(user);
        order.setStatus(Order.BOOK_LOCKED);

        OrderEntry oe = new OrderEntry();
        oe.setOrder(order);
        oe.setNewStatus(Order.BOOK_LOCKED);
        oe.setDate(new Date());

        orderDAO.saveOrder(order);
        orderDAO.saveOrderEntry(oe);

    }

}
