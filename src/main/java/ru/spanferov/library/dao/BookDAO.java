package ru.spanferov.library.dao;

import java.util.List;
import ru.spanferov.library.domain.Author;
import ru.spanferov.library.domain.Book;
import ru.spanferov.library.domain.BookInstance;
import ru.spanferov.library.util.BookSearchCriteria;

public interface BookDAO {

    public List<Book> getBooks();

    public List<Book> getBooksByCriteria(BookSearchCriteria bookSearchCriteria);

    public Book getBookById(int id);
    
    public void saveBook(Book book);
    
    public void saveAuthor(Author author);
    
    public void saveBookInstance(BookInstance bi);
    
    public void updateBook(Book book);
    
    public void deleteBook(Book book);
    
    public List<Author> getAuthorsList();
    
    public Author getAuthorById(int authorId);
    
    public int getNumberOfLockedBookInstances(int bookId);
    
    public int getNumberOfFreeBookInstances(int bookId);
    
    public int getNumberOfAllBookInstances(int bookId);
    
    public BookInstance getFreeInstance(int bookId);
    
    public List<Book> getBooksTakenByMember(int user_id);

     public List<Book> getBooksLockedByMember(int user_id);
     
     public long getCountOfLockedBookInstancesOfThisBookForUser(int bookId, int userId);
     
     public long getCountOfCollectedBookInstancesOfThisBookForUser(int bookId, int userId);

}
