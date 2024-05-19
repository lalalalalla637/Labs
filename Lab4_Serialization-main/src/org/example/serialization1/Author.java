package org.example.serialization1;

import java.io.Serializable;

public class Author extends Human implements Serializable {

    private static final long serialVersionUID = 1;

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
