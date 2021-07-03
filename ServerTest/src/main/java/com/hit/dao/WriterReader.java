//package com.hit.dao;
//
//import com.T_Y.model.User;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//
//public class WriterReader {
//
//    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        IDao<Object> file;
//        file = DaoImpl.createAccess(DaoImpl.File);
//        User tom = new User("Tom", "Aa1234");
//        User yakir = new User("yakir", "Aa1234");
//        User haimon = new User("haimon", "Aa1234");
//        ArrayList<User> temp = new ArrayList<User>();
//        temp.add(yakir);
//        temp.add(tom);
//        temp.add(haimon);
//        file.save("myObjects4.txt", temp);
//
//        ArrayList tempObject = (ArrayList) file.read("myObjects4.txt");
////        System.out.println(tempObject);
//        tempObject.forEach(y -> System.out.println(Arrays.toString(y) + "\n"));
//
//    }
//}