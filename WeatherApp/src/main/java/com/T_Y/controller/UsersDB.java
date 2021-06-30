package com.T_Y.controller;

import com.T_Y.controller.TcpClient;
import com.T_Y.model.City;
import com.T_Y.model.ToServerObject;
import com.T_Y.model.User;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;
import java.util.List;

public class UsersDB {
    private JFrame errorMessage;
    private ToServerObject response;

    public UsersDB() {
    }

    public boolean registerUserToDB(User tempUser) throws ClassNotFoundException, SQLException, IOException {

        ToServerObject object=new ToServerObject("Register",tempUser);
        response=  new TcpClient().sendToServerSql(object);
        if (response.getServerResponse().equals("Registration succeeded")) {
            return true;
        } else if(response.equals("Username already exist")){
            JOptionPane.showMessageDialog(errorMessage, "Username already exist", "Dialog", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else
        {
            JOptionPane.showMessageDialog(errorMessage, "Registration failed for unknown reason", "Dialog", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean loginUserToDB(User tempUser) throws ClassNotFoundException, SQLException, ArithmeticException, IOException {
        ToServerObject object=new ToServerObject("User_Login",tempUser);
        response=  new TcpClient().sendToServerSql(object);
        if (response.getServerResponse().equals("Login succeeded"))
            return true;
        else
            return false;

    }

    public boolean loginAdminToDB(User tempUser) throws SQLException, ArithmeticException, IOException, ClassNotFoundException {
        ToServerObject object=new ToServerObject("Admin_Login",tempUser);
        response=  new TcpClient().sendToServerSql(object);
        if (response.getServerResponse().equals("Login succeeded"))
            return true;
        else
            return false;

    }

    public List<Object[]> showUsersTable() throws ClassNotFoundException, SQLException, ArithmeticException, IOException {

        Socket clientSocket = new Socket("192.168.1.50", 8010);
        System.out.println("New operational socket was created");
        ObjectOutputStream toServer = new ObjectOutputStream(clientSocket.getOutputStream());

        ObjectInputStream fromServer = new ObjectInputStream(clientSocket.getInputStream());

        toServer.writeObject("Show_Users_Table");

        if (fromServer.readObject().toString().equals("Users table imported successfully")) {
            List<Object[]> records = (List<Object[]>) fromServer.readObject();
            toServer.writeObject("stop");
            clientSocket.getInputStream().close();
            clientSocket.close();
            System.out.println("Operational socket is closed");
            return records;
        } else {
            toServer.writeObject("stop");
            clientSocket.getInputStream().close();
            clientSocket.close();
            System.out.println("Operational socket is closed");
            return null;
        }
    }

    public boolean deleteUserFromDB(User tempUser) throws ClassNotFoundException, SQLException, IOException {

        ToServerObject object=new ToServerObject("Delete_User",tempUser);
        response=  new TcpClient().sendToServerSql(object);
        if (response.getServerResponse().equals("Deletion succeeded"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public String[] showUserDbFavorites(User tempUser) throws ClassNotFoundException, SQLException, IOException {
//        Socket clientSocket=new Socket("192.168.1.50",8010);
//        System.out.println("New operational socket was created");
//        ObjectOutputStream toServer=new ObjectOutputStream(clientSocket.getOutputStream());
//
//        ObjectInputStream fromServer=new ObjectInputStream(clientSocket.getInputStream());
//
//        toServer.writeObject("Show_User_Favorites");
//        toServer.writeObject(tempUser);

        ToServerObject object=new ToServerObject("Show_User_Favorites",tempUser);
        response=  new TcpClient().sendToServerSql(object);


//        String [] recFavArr=(String[])fromServer.readObject();
        String [] recFavArr=(String[]) response.getResponseObject();

        //close the socket
//        toServer.writeObject("stop");
//        clientSocket.getInputStream().close();
//        clientSocket.close();
//        System.out.println("Operational socket is closed");
        return recFavArr;

    }

    public boolean editUserDbFavorites(User tempUser, char index, City tempCT) throws ClassNotFoundException, SQLException, ArithmeticException, IOException {
//        Socket clientSocket=new Socket("192.168.1.50",8010);
//        System.out.println("New operational socket was created");
//        ObjectOutputStream toServer=new ObjectOutputStream(clientSocket.getOutputStream());
//
//        ObjectInputStream fromServer=new ObjectInputStream(clientSocket.getInputStream());
//
//        toServer.writeObject("Edit_User_Favorites");
//        toServer.writeObject(tempUser);
//        toServer.writeObject(index);
//        toServer.writeObject(tempCT.getCityName());
        ToServerObject object=new ToServerObject("Edit_User_Favorites",tempUser,index,tempCT.getCityName());
        response=  new TcpClient().sendToServerSql(object);
        if (response.getServerResponse().equals("Favorites update succeeded")){

//        if(fromServer.readObject().toString().equals("Favorites update succeeded")) {
            JOptionPane.showMessageDialog(errorMessage, "Favorites update succeeded", "Dialog", JOptionPane.INFORMATION_MESSAGE);
            //close the socket
//            toServer.writeObject("stop");
//            clientSocket.getInputStream().close();
//            clientSocket.close();
//            System.out.println("Operational socket is closed");
            return true;
        }
        else {
            JOptionPane.showMessageDialog(errorMessage, "Favorites update failed", "Dialog", JOptionPane.ERROR_MESSAGE);
            //close the socket
//            toServer.writeObject("stop");
//            clientSocket.getInputStream().close();
//            clientSocket.close();
//            System.out.println("Operational socket is closed");
        }
        return false;

    }

    public User getUserSecretInfo(User tempUser) throws ClassNotFoundException, SQLException, ArithmeticException, IOException {


        Socket clientSocket=new Socket("192.168.1.50",8010);
        System.out.println("New operational socket was created");
        ObjectOutputStream toServer=new ObjectOutputStream(clientSocket.getOutputStream());

        ObjectInputStream fromServer=new ObjectInputStream(clientSocket.getInputStream());

        toServer.writeObject("Get_Secret_info");

        toServer.writeObject(tempUser);
            tempUser=(User)fromServer.readObject();
        //close the socket
        toServer.writeObject("stop");
        clientSocket.getInputStream().close();
        clientSocket.close();
        System.out.println("Operational socket is closed");
        return tempUser;
    }

    public boolean usernameSearch(User tempUser) throws ClassNotFoundException, SQLException, ArithmeticException, IOException {
//        String response= (String) new TcpClient().sendToServer("Search_User",tempUser);

        ToServerObject object=new ToServerObject("Search_User",tempUser);
        response=  new TcpClient().sendToServerSql(object);
        if (response.getServerResponse().equals("User found"))
            return true;
        else
            return false;

    }

    public boolean updateUserPasswordToDB(User tempUser, String password) throws ClassNotFoundException, SQLException, IOException {

//
//        Socket clientSocket = new Socket("192.168.1.50", 8010);
//        System.out.println("New operational socket was created");
//        ObjectOutputStream toServer = new ObjectOutputStream(clientSocket.getOutputStream());
//
//        ObjectInputStream fromServer = new ObjectInputStream(clientSocket.getInputStream());
//
//        toServer.writeObject("Update_Password");
//
//        toServer.writeObject(tempUser);
//        toServer.writeObject(password);

        ToServerObject object=new ToServerObject("Update_Password",tempUser,password);
        response=  new TcpClient().sendToServerSql(object);


//        if (fromServer.readObject().toString().equals("Password was updated successfully")) {
        if (response.getServerResponse().equals("Password was updated successfully"))
        {
            //close the socket
//            toServer.writeObject("stop");
//            clientSocket.getInputStream().close();
//            clientSocket.close();
//            System.out.println("Operational socket is closed");
            return true;
        }
        else {
            //close the socket
//            toServer.writeObject("stop");
//            clientSocket.getInputStream().close();
//            clientSocket.close();
//            System.out.println("Operational socket is closed");
            return false;
        }
    }
}
