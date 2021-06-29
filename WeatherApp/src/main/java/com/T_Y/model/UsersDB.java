package com.T_Y.model;

import com.T_Y.controller.DBTablePrinter;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;
import java.util.List;

public class UsersDB {
    private JFrame errorMessage;

    public UsersDB() {
    }

    //    public boolean initFavoriteToDB(User tempUser) throws ClassNotFoundException, SQLException, IOException {
//        Socket clientSocket = new Socket("192.168.1.50", 8010);
//        System.out.println("New operational socket was created");
//        ObjectOutputStream toServer = new ObjectOutputStream(clientSocket.getOutputStream());
//
//        ObjectInputStream fromServer = new ObjectInputStream(clientSocket.getInputStream());
//
//        toServer.writeObject("Init_Favorites");
//
//        toServer.writeObject(tempUser);
//        if (fromServer.readObject().toString().equals("Favorites table initialization succeeded")) {
//            toServer.writeObject("stop");
//            return true;
//        } else {
//            toServer.writeObject("stop");
//            return false;
//        }
//
//
//    }
    public boolean registerUserToDB(User tempUser) throws ClassNotFoundException, SQLException, IOException {
        Socket clientSocket = new Socket("192.168.1.50", 8010);
        System.out.println("New operational socket was created");
        ObjectOutputStream toServer = new ObjectOutputStream(clientSocket.getOutputStream());

        ObjectInputStream fromServer = new ObjectInputStream(clientSocket.getInputStream());

        toServer.writeObject("Register");

        toServer.writeObject(tempUser);
        String response=fromServer.readObject().toString();
        if (response.equals("Registration succeeded")) {
            toServer.writeObject("stop");
            return true;
        } else if(response.equals("Username already exist")){
            JOptionPane.showMessageDialog(errorMessage, "Username already exist", "Dialog", JOptionPane.ERROR_MESSAGE);
            toServer.writeObject("stop");
            return false;
        }
        else
        {
            toServer.writeObject("stop");
            JOptionPane.showMessageDialog(errorMessage, "Registraion failed for unknown reason", "Dialog", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean loginUserToDB(User tempUser) throws ClassNotFoundException, SQLException, ArithmeticException, IOException {

        Socket clientSocket = new Socket("192.168.1.50", 8010);
        System.out.println("New operational socket was created");
        ObjectOutputStream toServer = new ObjectOutputStream(clientSocket.getOutputStream());

        ObjectInputStream fromServer = new ObjectInputStream(clientSocket.getInputStream());

        toServer.writeObject("User_Login");

        toServer.writeObject(tempUser);
        if (fromServer.readObject().toString().equals("Login succeeded")) {
            toServer.writeObject("stop");
            return true;
        } else {
            toServer.writeObject("stop");
            return false;
        }
    }

    public boolean loginAdminToDB(User tempUser) throws SQLException, ArithmeticException, IOException, ClassNotFoundException {

        Socket clientSocket = new Socket("192.168.1.50", 8010);
        System.out.println("New operational socket was created");
        ObjectOutputStream toServer = new ObjectOutputStream(clientSocket.getOutputStream());

        ObjectInputStream fromServer = new ObjectInputStream(clientSocket.getInputStream());

        toServer.writeObject("Admin_Login");

        toServer.writeObject(tempUser);
        if (fromServer.readObject().toString().equals("Login succeeded")) {
            toServer.writeObject("stop");
            return true;
        } else {
            toServer.writeObject("stop");
            return false;
        }
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
            return records;
        } else {
            toServer.writeObject("stop");
            return null;
        }
    }

    public boolean deleteUserFromDB(User tempUser) throws ClassNotFoundException, SQLException, IOException {


        Socket clientSocket=new Socket("192.168.1.50",8010);
        System.out.println("New operational socket was created");
        ObjectOutputStream toServer=new ObjectOutputStream(clientSocket.getOutputStream());

        ObjectInputStream fromServer=new ObjectInputStream(clientSocket.getInputStream());

        toServer.writeObject("Delete_User");

        toServer.writeObject(tempUser);
        if(fromServer.readObject().toString().equals("Deletion succeeded"))
        {
            toServer.writeObject("stop");
            return true;
        }
        else
        {
            toServer.writeObject("stop");
            return false;
        }
    }

    public String[] showUserDbFavorites(User tempUser) throws ClassNotFoundException, SQLException, IOException {

        Socket clientSocket=new Socket("192.168.1.50",8010);
        System.out.println("New operational socket was created");
        ObjectOutputStream toServer=new ObjectOutputStream(clientSocket.getOutputStream());

        ObjectInputStream fromServer=new ObjectInputStream(clientSocket.getInputStream());

        toServer.writeObject("Show_User_Favorites");

        toServer.writeObject(tempUser);
        String [] recFavArr=(String[])fromServer.readObject();
        toServer.writeObject("stop");
        return recFavArr;

    }

    public boolean editUserDbFavorites(User tempUser, char index, City tempCT) throws ClassNotFoundException, SQLException, ArithmeticException, IOException {
        Socket clientSocket=new Socket("192.168.1.50",8010);
        System.out.println("New operational socket was created");
        ObjectOutputStream toServer=new ObjectOutputStream(clientSocket.getOutputStream());

        ObjectInputStream fromServer=new ObjectInputStream(clientSocket.getInputStream());

        toServer.writeObject("Edit_User_Favorites");
        toServer.writeObject(tempUser);
        toServer.writeObject(index);
        toServer.writeObject(tempCT.getCityName());

        if(fromServer.readObject().toString().equals("Favorites update succeeded")) {
            JOptionPane.showMessageDialog(errorMessage, "Favorites update succeeded", "Dialog", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        else
            JOptionPane.showMessageDialog(errorMessage, "Favorites update failed", "Dialog", JOptionPane.ERROR_MESSAGE);

        toServer.writeObject("stop");
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
            toServer.writeObject("stop");
            return tempUser;
    }

    public boolean usernameSearch(User tempUser) throws ClassNotFoundException, SQLException, ArithmeticException, IOException {


        Socket clientSocket=new Socket("192.168.1.50",8010);
        System.out.println("New operational socket was created");
        ObjectOutputStream toServer=new ObjectOutputStream(clientSocket.getOutputStream());

        ObjectInputStream fromServer=new ObjectInputStream(clientSocket.getInputStream());

        toServer.writeObject("Search_User");
        toServer.writeObject(tempUser);
        if(fromServer.readObject().toString().equals("User found")) {
            return true;
        }
        else
        toServer.writeObject("stop");
        return false;

    }

    public boolean updateUserPasswordToDB(User tempUser, String password) throws ClassNotFoundException, SQLException, IOException {


        Socket clientSocket = new Socket("192.168.1.50", 8010);
        System.out.println("New operational socket was created");
        ObjectOutputStream toServer = new ObjectOutputStream(clientSocket.getOutputStream());

        ObjectInputStream fromServer = new ObjectInputStream(clientSocket.getInputStream());

        toServer.writeObject("Update_Password");

        toServer.writeObject(tempUser);
        toServer.writeObject(password);

        if (fromServer.readObject().toString().equals("Password was updated successfully")) {
            toServer.writeObject("stop");
            return true;
        } else {
            toServer.writeObject("stop");
            return false;
        }


    }
}
