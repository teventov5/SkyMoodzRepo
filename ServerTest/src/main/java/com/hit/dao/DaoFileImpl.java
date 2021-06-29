//package com.hit.dao;
//
//import com.hit.dm.Ingredient;
//
//import java.io.*;
//
//public class DaoFileImpl <V, K> implements IDao<V, K>, Serializable {
//
//    public V getElement(V value, K fileName) {
//
//        try {
//            FileInputStream fi = new FileInputStream(new File((String) fileName));
//            ObjectInputStream oi = new ObjectInputStream(fi);
//
//            // Read object
//            V obj = (V) oi.readObject();
//
//
//            oi.close();
//            fi.close();
//
//            if(obj==null) {
//                System.out.println("Error-yakir sharmuta");
//                return null;
//            }
//            else {
//             System.out.println("Success-Yakir gever");
//                return (V) obj;
//            }
//
//        } catch (FileNotFoundException e) {
//            System.out.println("File not found");
//        } catch (IOException e) {
//            System.out.println("Error initializing stream");
//        } catch (ClassNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return null;
//    }
//    public void putElement(V obj, K fileName)
//    {
//
//        try {
//            FileOutputStream f = new FileOutputStream(new File((String) fileName));
//            ObjectOutputStream o = new ObjectOutputStream(f);
//
//
//            // Write objects to file
//            o.writeObject(obj);
//            o.close();
//            System.out.println(f);
//            f.close();
//
//
//        } catch (FileNotFoundException e) {
//            System.out.println("File not found");
//        } catch (IOException e) {
//            System.out.println("Error initializing stream");
//        }
//    }
//}
//
//
