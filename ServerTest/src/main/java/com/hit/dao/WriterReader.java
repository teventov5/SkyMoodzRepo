//package com.hit.dao;
//
//import com.hit.dm.Ingredient;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//
//public class WriterReader {
//
//    public static void main(String[] args) {
//
//        Ingredient potato=new Ingredient("potato",1000);
//        Ingredient gamba=new Ingredient("gamba",2000);
//
//        try {
//            FileOutputStream f = new FileOutputStream(new File("myObjects.txt"));
//            ObjectOutputStream o = new ObjectOutputStream(f);
//
//            // Write objects to file
//            o.writeObject(potato);
//            o.writeObject(gamba);
//
//            o.close();
//            f.close();
///// Done writing into File//////////////
//
//
//            FileInputStream fi = new FileInputStream(new File("myObjects.txt"));
//            ObjectInputStream oi = new ObjectInputStream(fi);
//
//            // Read objects
//            Ingredient ing1 = (Ingredient) oi.readObject();
//            Ingredient ing2 = (Ingredient) oi.readObject();
//
//            System.out.println(ing1.toString());
//            System.out.println(ing2.toString());
//
//            oi.close();
//            fi.close();
//
//        } catch (FileNotFoundException e) {
//            System.out.println("File not found");
//        } catch (IOException e) {
//            System.out.println("Error initializing stream");
//        } catch (ClassNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//    }
//
//}