package org.example.serialization2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class BookStore implements Serializable {

    private static final long serialVersionUID = 1;
    private transient ArrayList<Book> books;
    private String subject;

    public BookStore(String subject) {
        this.subject = subject;
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
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
    private void writeObject(ObjectOutputStream out) throws IOException {

        out.defaultWriteObject();

        out.writeInt(books.size());
        for (Book book : books) {
            out.writeObject(book.getName());

            out.writeInt(book.getAuthors().size());
            for (Author author : book.getAuthors()) {
                out.writeObject(author.getName());
                out.writeObject(author.getSurname());
            }

            out.writeInt(book.getYearOfWriting());
            out.writeInt(book.getSeries());
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{

        in.defaultReadObject();
        books = new ArrayList<Book>();

        int bookCount = in.readInt();
        for (int i = 0; i < bookCount; i++) {
            String name = (String)in.readObject();
            int authorsCount = in.readInt();

            ArrayList <Author> authors = new ArrayList<>();
            for (int j = 0; j < authorsCount; j++) {
                String authorName = (String)in.readObject();
                String authorSurname = (String)in.readObject();

                Author author = new Author(authorName, authorSurname);
                authors.add(author);
            }

            int yearOfWriting = in.readInt();
            int series = in.readInt();

            Book book = new Book(name, authors, yearOfWriting, series);
            books.add(book);
        }
    }
}
