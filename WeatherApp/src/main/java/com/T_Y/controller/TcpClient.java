package com.T_Y.controller;

import com.T_Y.model.ToServerObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TcpClient {
    public ToServerObject sendToServerSql(ToServerObject obj) throws IOException, ClassNotFoundException {
        Socket clientSocket = new Socket("192.168.1.50", 8010);
        System.out.println("New operational socket was created");
        ObjectOutputStream toServer = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream fromServer = new ObjectInputStream(clientSocket.getInputStream());
        toServer.writeObject(obj);
        ToServerObject response=(ToServerObject) fromServer.readObject();
        ToServerObject stop=new ToServerObject("stop");
        toServer.writeObject(stop);
        clientSocket.getInputStream().close();
        clientSocket.close();
        System.out.println("Operational socket is closed");
        return response;

    }

    public ToServerObject sendToServerApi(ToServerObject obj) throws IOException, ClassNotFoundException {
        Socket clientSocket = new Socket("192.168.1.50", 8011);
        System.out.println("New operational socket was created");
        ObjectOutputStream toServer = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream fromServer = new ObjectInputStream(clientSocket.getInputStream());
        toServer.writeObject(obj);
        ToServerObject response=(ToServerObject) fromServer.readObject();
        ToServerObject stop=new ToServerObject("stop");
        toServer.writeObject(stop);
        clientSocket.getInputStream().close();
        clientSocket.close();
        System.out.println("Operational socket is closed");
        return response;

    }
}
