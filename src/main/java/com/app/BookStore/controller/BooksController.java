package com.app.BookStore.controller;

import com.app.BookStore.model.Book;
import com.app.BookStore.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class BooksController
{
    @Autowired
    private BooksService booksService;

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
}
