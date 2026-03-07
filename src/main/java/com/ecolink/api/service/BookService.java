package com.ecolink.api.service;

import com.ecolink.api.model.Book;
import com.ecolink.api.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private final BookRepository repo;
    public BookService(BookRepository repo) { this.repo = repo; }
    public Book create(Book book) { return repo.save(book); }
    public List<Book> findAll() { return repo.findAll(); }

    public Book findById(String id)
    {Optional<Book> e = repo.findById(id);
        if (e.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Book with id %s not found", id));
        };
    return e.get();}


    public Book update(String id, Book book) { book.setId(id); return repo.save(book); }
    public void delete(String id) { repo.deleteById(id); }

    public List<Book> findByAuthorIgnoreCase(String author) {return repo.findByAuthorIgnoreCase(author);}
    // findByAuthor(String author)


}


/*   OLD CODE FOR THROW 404:
    public Optional<Book> findById(String id)
        { try {return repo.findById(id);} catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }}
        */