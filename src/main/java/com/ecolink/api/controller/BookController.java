package com.ecolink.api.controller;

import com.ecolink.api.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ecolink.api.model.Book;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping() //Her er finder man alle books: /api/books
    public List<Book> getAllBooks(@RequestParam (required = false) String author) {
        if (author == null){
            return bookService.findAll();
        }
        else{
            return bookService.findByAuthorIgnoreCase(author);
        }

    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable String id){
        return bookService.findById(id);
    }

    @PostMapping() //ResponseEntity is a change made by AI, as i struglled with finding a way to send the 201 response.
    //Here is a link exaplaining for use for myself later on in project: https://www.baeldung.com/spring-response-entity
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book){
        Book created = bookService.create(book);
        return ResponseEntity.status(201).body(created);
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable String id){
        bookService.delete(id);
        return "Removed Successfully";
    }

    @PutMapping("/{id}")
    public Book replaceBook(@RequestBody Book newBook, @PathVariable String id){
        return bookService.update(id, newBook);
    }


}



/* OLD Put just for own notes: before learning how @autowired worked:
@PutMapping("/{id}")
    public Book replaceBook(@RequestBody Book newBook, @PathVariable String id){
        return repo.findById(id)
                .map(book -> {
                    book.setId(newBook.getId());
                    book.setIsbn(newBook.getIsbn());
                    book.setTitle(newBook.getTitle());
                    book.setPublishedYear(newBook.getPublishedYear());
                    book.setAuthor(newBook.getAuthor());
                    return repo.save(book);
                })
                .orElseGet(() -> {
                    return repo.save(newBook);
                        });


    }
*/