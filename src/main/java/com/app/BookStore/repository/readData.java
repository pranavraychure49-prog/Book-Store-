package com.app.BookStore.repository;

import com.app.BookStore.model.Book;
import com.app.BookStore.model.LibraryStatistics;
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

    public LibraryStatistics getLibraryStatistics()
    {
        LibraryStatistics libraryStatistics = new LibraryStatistics();

        libraryStatistics.setTotalBooks((long) books.size());
        libraryStatistics.setTotalInventory(books.stream().mapToLong(Book::getQuantity).sum());
        libraryStatistics.setTotalInventoryValue(books.stream().mapToDouble(book -> book.getPrice() * book.getQuantity()).sum());
        libraryStatistics.setAverageBookPrice(books.stream().mapToDouble(Book::getPrice).average().orElse(0.0));

        libraryStatistics.setHighestPricedBook(
            books.stream().max((b1, b2) -> Double.compare(b1.getPrice(), b2.getPrice())).map(Book::getBookName).orElse("N/A")
        );
        libraryStatistics.setLowestPricedBook(
            books.stream().min((b1, b2) -> Double.compare(b1.getPrice(), b2.getPrice())).map(Book::getBookName).orElse("N/A")
        );

        // Books count per category
        libraryStatistics.setCategoryWiseBooks(
            books.stream().collect(java.util.stream.Collectors.groupingBy(Book::getCategory, java.util.stream.Collectors.counting()))
        );

        // Books count per author
        libraryStatistics.setAuthorWiseBooks(
            books.stream().collect(java.util.stream.Collectors.groupingBy(Book::getAuthorName, java.util.stream.Collectors.counting()))
        );

        // Average price per category
        libraryStatistics.setAveragePriceByCategory(
            books.stream().collect(java.util.stream.Collectors.groupingBy(Book::getCategory, java.util.stream.Collectors.averagingDouble(Book::getPrice)))
        );

        // Books count per publisher
        libraryStatistics.setPublisherWiseBooks(
            books.stream().collect(java.util.stream.Collectors.groupingBy(Book::getPublisher, java.util.stream.Collectors.counting()))
        );

        return libraryStatistics;
    }
}
