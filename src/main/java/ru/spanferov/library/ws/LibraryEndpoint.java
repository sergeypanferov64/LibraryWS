package ru.spanferov.library.ws;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.spanferov.library.service.BookService;
import ru.spanferov.library.ws.books.Author;
import ru.spanferov.library.ws.books.Authors;
import ru.spanferov.library.ws.books.Book;
import ru.spanferov.library.ws.books.BookSearchCriteria;
import ru.spanferov.library.ws.books.GetAllBooksRequest;
import ru.spanferov.library.ws.books.GetAllBooksResponse;
import ru.spanferov.library.ws.books.GetBookInfoRequest;
import ru.spanferov.library.ws.books.GetBookInfoResponse;

@Endpoint
public class LibraryEndpoint {

    @Autowired
    private BookService bookService;

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @PayloadRoot(namespace = "http://spanferov.ru/library/ws/books", localPart = "getBookInfoRequest")
    @ResponsePayload
    public GetBookInfoResponse getBookInfo(@RequestPayload GetBookInfoRequest request) throws NonexistentBookIdException {

        ru.spanferov.library.domain.Book domainBook = bookService.getBookById(request.getId());
        
        if(domainBook == null)
            throw new NonexistentBookIdException();

        GetBookInfoResponse response = new GetBookInfoResponse();

        Book responseBook = new Book();

        responseBook.setId(domainBook.getId());
        responseBook.setTitle(domainBook.getTitle());
        responseBook.setDescription(domainBook.getDescription());
        responseBook.setImprintDate(domainBook.getImprintDate());
        responseBook.setNumOfPages(domainBook.getNumOfPages());
        responseBook.setFreeInstances(bookService.getNumberOfFreeBookInstances(request.getId()));
        responseBook.setLockedInstances(bookService.getNumberOfLockedBookInstances(request.getId()));
        responseBook.setAllInstances(bookService.getNumberOfAllBookInstances(request.getId()));

        Authors authors = new Authors();
        authors.getAuthor().addAll(getResponseAuthorsList(domainBook.getAuthors()));
        responseBook.setAuthors(authors);

        response.setBook(responseBook);

        return response;
    }

    @PayloadRoot(namespace = "http://spanferov.ru/library/ws/books", localPart = "getAllBooksRequest")
    @ResponsePayload
    public GetAllBooksResponse getAllBooks(@RequestPayload GetAllBooksRequest request) {

        BookSearchCriteria bscReq = request.getBookSearchCriteria();
        ru.spanferov.library.util.BookSearchCriteria bsc = new ru.spanferov.library.util.BookSearchCriteria();
        bsc.setAuthor(bscReq.getAuthor());
        bsc.setTitle(bscReq.getTitle());

        GetAllBooksResponse response = new GetAllBooksResponse();

        List<Book> list = getResponseBookList(bookService.list(bsc));

        response.getBook().addAll(list);

        return response;
    }

    private List<Book> getResponseBookList(List<ru.spanferov.library.domain.Book> inList) {

        List<Book> respBooks = new ArrayList<>();

        for (ru.spanferov.library.domain.Book book : inList) {
            Book next = new Book();
            next.setId(book.getId());
            next.setTitle(book.getTitle());
            next.setDescription(book.getDescription());
            next.setImprintDate(book.getImprintDate());
            next.setNumOfPages(book.getNumOfPages());
            next.setFreeInstances(bookService.getNumberOfFreeBookInstances(book.getId()));
            next.setLockedInstances(bookService.getNumberOfLockedBookInstances(book.getId()));
            next.setAllInstances(bookService.getNumberOfAllBookInstances(book.getId()));

            Authors authors = new Authors();
            authors.getAuthor().addAll(getResponseAuthorsList(book.getAuthors()));
            next.setAuthors(authors);

            respBooks.add(next);
        }

        return respBooks;

    }
    
        private List<Author> getResponseAuthorsList(List<ru.spanferov.library.domain.Author> inList) {

        List<Author> respBookAuthors = new ArrayList<>();

        for (ru.spanferov.library.domain.Author author : inList) {
            Author next = new Author();
            next.setId(author.getId());
            next.setName(author.getName());
            respBookAuthors.add(next);
        }

        return respBookAuthors;

    }

}
