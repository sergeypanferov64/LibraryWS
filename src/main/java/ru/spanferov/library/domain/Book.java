package ru.spanferov.library.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "BOOKS")
public class Book implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "author_book", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    @NotEmpty(message="book must have one author at least")
    private List<Author> authors;

    @Column(nullable = false)
    @NotBlank(message = "title can't be empty")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    @NotNull(message = "imprintDate can't be empty")
    @Range(min=800, max=3000, message="invalid imprint date")
    private Integer imprintDate;
    
    @Column(nullable = false)
    @NotNull(message = "numOfPages can't be empty")
    @Range(min=1, max=1000000, message="invalid count of pages")
    private Integer numOfPages;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
    private Set<BookInstance> bookInstances;

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return id;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getImprintDate() {
        return imprintDate;
    }

    public void setImprintDate(Integer imprintDate) {
        this.imprintDate = imprintDate;
    }

    public Integer getNumOfPages() {
        return numOfPages;
    }

    public void setNumOfPages(Integer numOfPages) {
        this.numOfPages = numOfPages;
    }

    public Set<BookInstance> getBookInstances() {
        return bookInstances;
    }

    public void setBookInstances(Set<BookInstance> bookInstances) {
        this.bookInstances = bookInstances;
    }

}
