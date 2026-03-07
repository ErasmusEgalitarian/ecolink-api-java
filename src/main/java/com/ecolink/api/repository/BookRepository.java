package com.ecolink.api.repository;
import com.ecolink.api.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findByAuthorIgnoreCase(String author);
}
