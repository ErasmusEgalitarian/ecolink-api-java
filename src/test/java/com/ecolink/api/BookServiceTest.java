package com.ecolink.api;

import com.ecolink.api.model.Book;
import com.ecolink.api.repository.BookRepository;
import com.ecolink.api.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


// Using this as a source https://medium.com/@alxkm/mocking-with-mockito-simplifying-unit-testing-in-java-1cc50d78d2c0
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock //Creates a fake instance of the BookRepository
    BookRepository repo;

    @InjectMocks //Injects the fake BookRepository into the Bookservice, as the bookservice is dependent on BookRepository
    BookService bookService;

    @Test
    public void findById_existingId_returnsBook(){
        //Arrange = Create an id with a book object
        Book mockBook = new Book("123", "Alice in wonderland", "Emil Bartels", 2005, "1111111111111111");
        when(repo.findById("123")).thenReturn(Optional.of(mockBook));
        //Act
        Book result = bookService.findById("123");
        //Assert
        assertEquals("Alice in wonderland", result.getTitle());
        verify(repo).findById("123");
    }


    //bruger denne kilde: https://www.baeldung.com/junit-assert-exception
    @Test
    void findById_notFound_throwsException(){
        //Arrange
        when(repo.findById("1234")).thenReturn(Optional.empty());
        //Act
        Exception exception = assertThrows(ResponseStatusException.class, () -> {bookService.findById("1234");});

        String expectedMessage = "Book with id 1234 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    //Using this soruce: https://www.baeldung.com/mockito-verify
    @Test
    void delete_existingId_callsRepository(){
        // Act
        bookService.delete("123");

        // Assert
        verify(repo).deleteById("123");
    }
}
