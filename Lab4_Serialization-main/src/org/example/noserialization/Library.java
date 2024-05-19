package org.example.noserialization;

import java.util.ArrayList;
import java.util.Random;

public class Library {

    private Random random = new Random();

    private ArrayList<BookStore> bookStores;
    private ArrayList <BookReader> registeredReaders;
    private String name;

    public Library(String name){
        this.name = name;
        this.bookStores = new ArrayList<>();
        this.registeredReaders = new ArrayList<>();
    }

    public Library(String name, ArrayList <BookStore> bookStores){
        this.bookStores = bookStores;
        this.name = name;
        this.registeredReaders = new ArrayList<>();
    }

    public void addBookStore(BookStore bookStore){
        bookStores.add(bookStore);
    }

    public void registerReader(BookReader bookReader){
        registeredReaders.add(bookReader);
    }

    public String getName(){
        return name;
    }

    public String toString(){

        StringBuilder str = new StringBuilder("****** Library ******\n");
        str.append("Name: " + name + "\n\n").append("****** Book Stores: ******\n");
        for(int i = 0; i < bookStores.size(); i++){
            str.append(i+1).append(")").append(bookStores.get(i).toString()).append("\n");
        }
        str.append("\n****** Registered readers: ******\n");
        for(int i = 0; i < registeredReaders.size(); i++){
            str.append(i+1).append(")").append(registeredReaders.get(i).toString()).append("\n");
        }
        return str.toString();

    }

}
