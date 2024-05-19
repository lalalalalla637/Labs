package org.example.serialization3;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;

public class BookReader extends Human {

    private int bookReaderNumber;
    private ArrayList<Book> takenBooks;

    public BookReader(String name, String surname, int bookReaderNumber){
        setName(name);
        setSurname(surname);
        this.bookReaderNumber = bookReaderNumber;
        this.takenBooks = new ArrayList<>();
    }

    public BookReader(){
        this("No name", "No surname", 0);
    }

    public void takeBook(Book book){
        takenBooks.add(book);
    }

    public ArrayList<Book> getTakenBooks(){
        return takenBooks;
    }

    public int getBookReaderNumber() {
        return bookReaderNumber;
    }

    public void setBookReaderNumber(int bookReaderNumber) {
        this.bookReaderNumber = bookReaderNumber;
    }

    public String toString(){
        StringBuilder str = new StringBuilder("Reader number: " + bookReaderNumber + "\n");
        str.append(super.toString()).append("\n").append("Taken books:\n");
        for(Book book: takenBooks){
            str.append(book.toString());
        }

        return str.toString();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        out.writeInt(bookReaderNumber);

        out.writeInt(takenBooks.size());
        for (Book book : takenBooks)
            book.writeExternal(out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        bookReaderNumber = in.readInt();

        int booksCount = in.readInt();
        takenBooks = new ArrayList<>();
        for (int i = 0; i < booksCount; i++) {
            Book takenBook = new Book();
            takenBook.readExternal(in);

            takenBooks.add(takenBook);
        }
    }

}
