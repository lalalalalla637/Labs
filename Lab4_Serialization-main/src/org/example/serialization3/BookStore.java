package org.example.serialization3;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;

public class BookStore implements Externalizable {
    private ArrayList<Book> books;
    private String subject;

    public BookStore(String subject){
        this.subject = subject;
        this.books = new ArrayList<>();
    }

    public BookStore(){
        this("No subject");
    }

    public void addBook(Book book){
        books.add(book);
    }

    public Book getBook(int i){
        return books.get(i);
    }

    public String getSubject() {
        return subject;
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

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(subject);

        out.writeInt(books.size());
        for(Book book : books)
            book.writeExternal(out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        subject = (String)in.readObject();

        int booksCount = in.readInt();
        books = new ArrayList<>();
        for (int i = 0; i < booksCount; i++) {
            Book book = new Book();
            book.readExternal(in);
            books.add(book);
        }
    }
}
