package ru.spanferov.library.dao.impl;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import ru.spanferov.library.dao.BookDAO;
import ru.spanferov.library.domain.Author;
import ru.spanferov.library.domain.Book;
import ru.spanferov.library.domain.BookInstance;
import ru.spanferov.library.domain.Order;
import ru.spanferov.library.util.BookSearchCriteria;

public class BookDAOImpl implements BookDAO {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Book> getBooks() {
        Criteria crit = getSessionFactory().getCurrentSession().createCriteria(Book.class);
        return crit.list();
    }

    @Override
    public List<Book> getBooksByCriteria(BookSearchCriteria bookSearchCriteria) {
        Criteria crit = getSessionFactory().getCurrentSession().createCriteria(Book.class, "b");
        crit.createAlias("b.authors", "a");
        if (bookSearchCriteria.getTitle() != null) {
            crit.add(Restrictions.ilike("b.title", bookSearchCriteria.getTitle(), MatchMode.ANYWHERE));
        }
        if (bookSearchCriteria.getAuthor() != null) {
            crit.add(Restrictions.ilike("a.name", bookSearchCriteria.getAuthor(), MatchMode.ANYWHERE));
        }
        crit.addOrder(org.hibernate.criterion.Order.asc("b.title"));
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Book> books = crit.list();
        return books;
    }

    @Override
    public Book getBookById(int book_id) {
        Criteria crit = getSessionFactory().getCurrentSession().createCriteria(Book.class);
        crit.add(Restrictions.eq("id", book_id));
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        Book book = (Book) crit.uniqueResult();
        return book;
    }

    @Override
    public void saveBook(Book book) {
        getSessionFactory().getCurrentSession().save(book);
    }

    @Override
    public void saveAuthor(Author author) {
        getSessionFactory().getCurrentSession().save(author);
    }

    @Override
    public void saveBookInstance(BookInstance bi) {
        getSessionFactory().getCurrentSession().save(bi);
    }

    @Override
    public void updateBook(Book book) {
        getSessionFactory().getCurrentSession().update(book);
    }

    @Override
    public void deleteBook(Book book) {
        getSessionFactory().getCurrentSession().delete(book);
    }

    @Override
    public List<Author> getAuthorsList() {
        Criteria crit = getSessionFactory().getCurrentSession().createCriteria(Author.class);
        return crit.list();
    }

    @Override
    public Author getAuthorById(int author_id) {
        Criteria crit = getSessionFactory().getCurrentSession().createCriteria(Author.class);
        crit.add(Restrictions.eq("id", author_id));
        return (Author) crit.uniqueResult();
    }

    @Override
    public int getNumberOfLockedBookInstances(int bookId) {

        Criteria crit = getSessionFactory().getCurrentSession().createCriteria(BookInstance.class);
        crit.add(Restrictions.eq("book.id", bookId));
        crit.add(Restrictions.eq("lock", true));
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return crit.list().size();
    }

    @Override
    public int getNumberOfFreeBookInstances(int bookId) {

        Criteria crit = getSessionFactory().getCurrentSession().createCriteria(BookInstance.class);
        crit.add(Restrictions.eq("book.id", bookId));
        crit.add(Restrictions.eq("lock", false));
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return crit.list().size();
    }

    @Override
    public int getNumberOfAllBookInstances(int bookId) {

        Criteria crit = getSessionFactory().getCurrentSession().createCriteria(BookInstance.class);
        crit.add(Restrictions.eq("book.id", bookId));
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return crit.list().size();
    }

    public BookInstance getFreeInstance(int bookId) {
        Criteria crit = getSessionFactory().getCurrentSession().createCriteria(BookInstance.class);
        crit.add(Restrictions.eq("book.id", bookId));
        crit.add(Restrictions.eq("lock", false));
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        List<BookInstance> biList = crit.list();

        BookInstance bi = (BookInstance) crit.list().get(0);
        return bi;
    }

    @Override
    public List<Book> getBooksTakenByMember(int user_id) {
        Query query = getSessionFactory().getCurrentSession().createQuery("select b from Order o, BookInstance bi, Book b WHERE o.status = 1 AND o.user=:user_id AND o.bookInstance = bi.id AND bi.book = b.id");
        query.setInteger("user_id", user_id);
        return query.list();
    }

    @Override
    public List<Book> getBooksLockedByMember(int user_id) {
        Query query = getSessionFactory().getCurrentSession().createQuery("select b from Order o, BookInstance bi, Book b WHERE o.status = 0 AND o.user=:user_id AND o.bookInstance = bi.id AND bi.book = b.id");
        query.setInteger("user_id", user_id);
        return query.list();
    }

    @Override
    public long getCountOfLockedBookInstancesOfThisBookForUser(int bookId, int userId) {

        Criteria crit2 = getSessionFactory().getCurrentSession().createCriteria(Order.class, "o");
        crit2.createAlias("o.bookInstance", "bi");
        crit2.createAlias("bi.book", "b");
        crit2.add(Restrictions.eq("o.user.id", userId));
        crit2.add(Restrictions.eq("o.status", 0));
        crit2.add(Restrictions.eq("b.id", bookId));
        crit2.setProjection(Projections.rowCount());
        return (Long) crit2.uniqueResult();
    }

    @Override
    public long getCountOfCollectedBookInstancesOfThisBookForUser(int bookId, int userId) {

        Criteria crit3 = getSessionFactory().getCurrentSession().createCriteria(Order.class, "o");
        crit3.createAlias("o.bookInstance", "bi");
        crit3.createAlias("bi.book", "b");
        crit3.add(Restrictions.eq("o.user.id", userId));
        crit3.add(Restrictions.eq("o.status", 1));
        crit3.add(Restrictions.eq("b.id", bookId));
        crit3.setProjection(Projections.rowCount());
        return (Long) crit3.uniqueResult();

    }

}
