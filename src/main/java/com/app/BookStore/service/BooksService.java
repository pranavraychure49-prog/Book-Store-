package com.app.BookStore.service;

import com.app.BookStore.model.Book;
import com.app.BookStore.repository.readData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BooksService
{
    @Autowired
    private readData data;

    public List<Book> getAllBooks() {
        return data.getBooks();
    }

    public Book getBookByName(String name) {
        return data.getBookByName(name);
    }

    public Book addBooks(Book newBook) {
        return data.addBooks(newBook);
    }
}
