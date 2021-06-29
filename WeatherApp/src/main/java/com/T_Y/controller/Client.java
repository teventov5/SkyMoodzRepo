//package com.T_Y.controller;
//
//import com.T_Y.model.User;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.Socket;
//
//public class Client {
//
//    public static void main(String[] args) throws IOException, ClassNotFoundException {
//
//
//
//        Socket clientSocket=new Socket("192.168.1.50",8010);
//        System.out.println("New operational socket was created");
//        ObjectOutputStream toServer=new ObjectOutputStream(clientSocket.getOutputStream());
//
//        ObjectInputStream fromServer=new ObjectInputStream(clientSocket.getInputStream());
//
//        toServer.writeObject("SQL");
//
//        User haim=new User("haim","Aa1234","whos sharmuta?","yakir");
//        toServer.writeObject(haim);
////        toServer.writeObject("test");
//        toServer.writeObject("stop");
////        System.out.println(fromServer.readObject());
////        System.out.println(haim.getUsername()+"\n\n"+haim.getPassword());
//
//
//
//
//
//
//
//
//    }
//
//
//
//
//
//
//}
