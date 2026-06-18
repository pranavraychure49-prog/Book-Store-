package com.app.BookStore.repository;

import com.app.BookStore.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.File;
import java.util.List;

@Getter
@Component
public class readData
{
    private List<Book> books;
    public void convertCsvToJson() throws Exception {

        String csvPath = "src/main/java/com/app/BookStore/data/books_catalog.csv";
        String jsonPath = "src/main/java/com/app/BookStore/data/books_catalog.json";

        books = new CsvToBeanBuilder<Book>(new FileReader(csvPath))
                .withType(Book.class)
                .build()
                .parse();

        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter()
              .writeValue(new File(jsonPath), books);

        System.out.println("Done! JSON saved to: " + jsonPath);
        System.out.println("Total books converted: " + books.size());
    }

    public Book getBookByName(String name)
    {
        if (books != null) {
            for (Book book : books) {
                if (book.getBookName().equalsIgnoreCase(name)) {
                    return book;
                }
            }
        }
        return null;
    }

    public Book addBooks(Book newBook)
    {
        if (books != null) {
            books.add(newBook);
            return newBook;
        }
        return null;
    }
}
