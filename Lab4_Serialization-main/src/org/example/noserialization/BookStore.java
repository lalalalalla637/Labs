package org.example.noserialization;

import java.util.ArrayList;

public class BookStore {

    private ArrayList<Book> books;
    private String subject;

    public BookStore(String subject){
        this.subject = subject;
        this.books = new ArrayList<>();
    }

    public void addBook(Book book){
        books.add(book);
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
