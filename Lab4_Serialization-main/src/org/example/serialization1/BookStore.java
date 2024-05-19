package org.example.serialization1;

import java.io.Serializable;
import java.util.ArrayList;

public class BookStore implements Serializable {

    private static final long serialVersionUID = 1;

    private ArrayList<Book> books;
    private String subject;

    public BookStore(String subject){
        this.subject = subject;
        this.books = new ArrayList<>();
    }

    public void addBook(Book book){
        books.add(book);
    }

    public Book getBook(){
        Book book = books.get(books.size() - 1);
        books.remove(books.size() - 1);

        return book;
    }

    public String getSubject() {
        return subject;
    }

    public Book getBook(int i){
        return books.get(i);
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String toString(){
        StringBuilder str = new StringBuilder("Subject: " + subject + "\n");
        for (Book book : books){
            str.append(book.toString()).append("\n");
        }

        return str.toString();
    }

}
