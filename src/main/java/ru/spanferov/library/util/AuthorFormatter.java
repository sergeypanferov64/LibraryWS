package ru.spanferov.library.util;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import ru.spanferov.library.domain.Author;
import ru.spanferov.library.service.BookService;

@Component
public class AuthorFormatter implements Formatter<Author> {

    @Autowired
    private BookService bookService;

    @Override
    public String print(Author author, Locale arg1) {
        return author.getName();
    }

    @Override
    public Author parse(String authorId, Locale arg1) {
        return bookService.getAuthorById(Integer.parseInt(authorId));
    }
}
