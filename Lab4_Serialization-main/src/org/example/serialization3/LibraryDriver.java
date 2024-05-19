package org.example.serialization3;

import java.io.*;

public class LibraryDriver {

    private static final String filename = "serialization3.txt";

    public static void serializeObject(Externalizable object){
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
            object.writeExternal(out);
            out.close();

            System.out.println("Serialization complete!");
        } catch (IOException e){
            System.out.println("Serialization failed!");
        }
    }

    public static Object deserializeLibrary(){
        Externalizable library = new Library();
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
            library.readExternal(in);
            in.close();
            System.out.println("Deserialization complete!");
        } catch (Exception e){
            System.out.println("Deserialization failed!");
            e.printStackTrace();
        }

        return library;
    }
}
