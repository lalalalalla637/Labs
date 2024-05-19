package org.example.serialization1;

import java.io.Serializable;
import java.util.ArrayList;

public class Book implements Serializable {

    private static final long serialVersionUID = 1;

    private ArrayList <Author> authors;
    private String name;
    private int yearOfWriting, series;

    public Book(String name, ArrayList<Author> authors, int yearOfWriting, int series){
        this.name = name;
        this.authors = authors;
        this.yearOfWriting = yearOfWriting;
        this.series = series;
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

}
