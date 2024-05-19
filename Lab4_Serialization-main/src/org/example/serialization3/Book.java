package org.example.serialization3;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;

public class Book implements Externalizable {
    private ArrayList <Author> authors;
    private String name;
    private int yearOfWriting, series;

    public Book(String name, ArrayList<Author> authors, int yearOfWriting, int series){
        this.name = name;
        this.authors = authors;
        this.yearOfWriting = yearOfWriting;
        this.series = series;
    }

    public Book(){
        this("Untitled", new ArrayList<Author>(), 0, 0);
    }

    public Book(String name, Author author, int yearOfWriting, int series){
        ArrayList <Author> authors = new ArrayList();
        authors.add(author);

        this.name = name;
        this.authors = authors;
        this.yearOfWriting = yearOfWriting;
        this.series = series;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<Author> authors) {
        this.authors = authors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfWriting() {
        return yearOfWriting;
    }

    public void setYearOfWriting(int yearOfWriting) {
        this.yearOfWriting = yearOfWriting;
    }

    public int getSeries() {
        return series;
    }

    public String toString(){
        StringBuilder str = new StringBuilder("Book name: " + name + "\n\tAuthors: ");
        for(Author author : authors){
            str.append(author.toString()).append("\n");
        }
        str.append("\tYear of writing: ").append(yearOfWriting).append("\n");
        str.append("\tSeries: ").append(series).append("\n");

        return str.toString();
    }


    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeInt(yearOfWriting);
        out.writeInt(series);

        out.writeInt(authors.size());
        for(Author author : authors)
            author.writeExternal(out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String)in.readObject();
        yearOfWriting = in.readInt();
        series = in.readInt();
        authors = new ArrayList<>();

        int authorsCount = in.readInt();
        for (int i = 0; i < authorsCount; i++) {
            Author author = new Author();
            author.readExternal(in);
            authors.add(author);
        }
    }
}

