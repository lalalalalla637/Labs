package org.example.serialization2;

import java.util.Random;

public class Main {

    private static final String [] names = {"Harry", "Alex", "Max", "Anton"};
    private static final String [] surnames = {"Potter", "Spivyk", "Verstappen", "Magislavov"};


    public static Random random = new Random();

    public static void main(String[] args) {
        Library library = new Library("Kyiv national library");

        Author author1 = new Author("Harper", "Morgan");
        Author author2 = new Author("Denislav", "Denislavovich");

        BookStore bookStore1 = new BookStore("Adventure");
        bookStore1.addBook(new Book("Spivyk Adventure", author2, 2021, random.nextInt(100)));
        bookStore1.addBook(new Book("Tale of F1", author1, 2019, random.nextInt(100)));
        bookStore1.addBook(new Book("Yevhenii of the Northern lands", author1, 2020, random.nextInt(100)));

        BookStore bookStore2 = new BookStore("History");
        bookStore2.addBook(new Book("The Great War", author2, 2019, random.nextInt(100)));

        BookStore bookStore3 = new BookStore("Music");
        bookStore3.addBook(new Book("Pop Mix", author2, 2022, random.nextInt(100)));
        bookStore3.addBook(new Book("Rick roll", author1, 2021, random.nextInt(100)));

        library.addBookStore(bookStore1);
        library.addBookStore(bookStore2);
        library.addBookStore(bookStore3);

        BookReader[] reader = new BookReader[names.length];

        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            String surname = surnames[i];
            int readerID = random.nextInt(100000);
            reader[i] = new BookReader(name, surname, readerID);
        }

        reader[0].takeBook(bookStore1.getBook(1));
        reader[1].takeBook(bookStore1.getBook(0));
        reader[1].takeBook(bookStore2.getBook(0));
        reader[1].takeBook(bookStore3.getBook(0));
        reader[2].takeBook(bookStore1.getBook(2));
        reader[3].takeBook(bookStore3.getBook(1));

        for(int i = 0; i < reader.length; i++) {
            library.registerReader(reader[i]);
        }

        System.out.println("******************* Before: ******************* ");
        System.out.println(library);

        System.out.println("******************* After: ******************* ");
        LibraryDriver.serializeObject(library);
        System.out.println(LibraryDriver.deserializeObject());
    }
}
