package com.app.BookStore.controller;

import com.app.BookStore.model.Book;
import com.app.BookStore.model.LibraryStatistics;
import com.app.BookStore.service.BooksService;
import com.app.BookStore.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class BooksController
{
    @Autowired
    private BooksService booksService;

    @Autowired
    private ReportService reportService;

    @GetMapping("/books/all")
    public List<Book> getAllBooks ()
    {
        return booksService.getAllBooks();
    }

    @GetMapping("/books/{name}")
    public Book getBooksByName (@PathVariable String name)
    {
        return booksService.getBookByName(name);
    }

    @PostMapping("/books/add")
    public Book addBook(@RequestBody Book newBook)
    {
        return booksService.addBooks(newBook);
    }

    @GetMapping("/admin")
    public LibraryStatistics getLibraryStatistics()
    {
        return booksService.getLibraryStatistics();
    }
    @GetMapping(value = "/report", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getReport() {
        String report = reportService.generateReport();
        return ResponseEntity.ok(report);
    }
}
