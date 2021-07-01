package com.hit.dao;


import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class DaoFileImpl2<V> implements IDao<V>, Serializable {


    public void save(String source, V obj) throws IOException {
        FileOutputStream f = new FileOutputStream(new File(source));
        ObjectOutputStream o = new ObjectOutputStream(f);


        // Write objects to file
        o.writeObject(obj);
        o.close();
        f.close();
/// Done writing into File//////////////

    }

    public V read(String source) throws IOException, ClassNotFoundException {

        V tempObject;

        FileInputStream fi = new FileInputStream(new File(source));
        ObjectInputStream oi = new ObjectInputStream(fi);

        tempObject = (V) oi.readObject();
        oi.close();
        fi.close();
        return tempObject;
    }

}

