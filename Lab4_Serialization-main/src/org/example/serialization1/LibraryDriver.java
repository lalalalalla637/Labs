package org.example.serialization1;

import java.io.*;

public class LibraryDriver {

    private static final String filename = "serialization.txt";

    public static void serializeObject(Object object){
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(object);
            out.close();

            System.out.println("Serialization complete!");
        } catch (IOException e){
            System.out.println("Serialization failed!");
        }
    }

    public static Object deserializeObject(){
        Object object = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
            object = in.readObject();
            in.close();
            System.out.println("Deserialization complete!");
        } catch (Exception e){
            System.out.println("Deserialization failed!");
        }

        return object;
    }

}
