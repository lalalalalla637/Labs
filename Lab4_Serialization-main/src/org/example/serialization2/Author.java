package org.example.serialization2;

public class Author extends Human {

    public Author(String name, String surname){
        setSurname(surname);
        setName(name);
    }

    public Author(){
        this("Zhenya", "Crimson");
    }

    public String toString(){
        return super.toString();
    }

}
