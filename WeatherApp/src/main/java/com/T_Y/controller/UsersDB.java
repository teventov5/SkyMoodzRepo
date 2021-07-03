package com.T_Y.controller;

import com.T_Y.controller.TcpClient;
import com.T_Y.model.City;
import com.T_Y.model.ToServerObject;
import com.T_Y.model.User;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public class UsersDB {
    private JFrame errorMessage;
    private ToServerObject response;
    public static TcpClient tcpClient=new TcpClient();

    public UsersDB() {
    }

    public boolean registerUserToDB(User tempUser) throws ClassNotFoundException, SQLException, IOException {

        ToServerObject object=new ToServerObject("Register",tempUser);
        response=  tcpClient.sendToServerSql(object);
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
        response=  tcpClient.sendToServerSql(object);
        if (response.getServerResponse().equals("Login succeeded"))
            return true;
        else
            return false;

    }

    public boolean loginAdminToDB(User tempUser) throws SQLException, ArithmeticException, IOException, ClassNotFoundException {
        ToServerObject object=new ToServerObject("Admin_Login",tempUser);
        response=  tcpClient.sendToServerSql(object);
        if (response.getServerResponse().equals("Login succeeded"))
            return true;
        else
            return false;

    }

    public List<Object[]> showUsersTable() throws ClassNotFoundException, SQLException, ArithmeticException, IOException {

        ToServerObject object=new ToServerObject("Show_Users_Table");
        response=  tcpClient.sendToServerSql(object);

        if (response.getServerResponse().equals("Users table imported successfully")){

            List<Object[]> records = (List<Object[]>) response.getResponseObject();

            return records;
        } else {
            return null;
        }
    }

    public boolean deleteUserFromDB(User tempUser) throws ClassNotFoundException, SQLException, IOException {

        ToServerObject object=new ToServerObject("Delete_User",tempUser);
        response=  tcpClient.sendToServerSql(object);
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

        ToServerObject object=new ToServerObject("Show_User_Favorites",tempUser);
        response=  tcpClient.sendToServerSql(object);
        String [] recFavArr;
        if(response.getServerResponse().equals("Users favorites were successfully imported")) {
            recFavArr = (String[]) response.getResponseObject();
            return recFavArr;
        }
        return null;
    }

    public boolean editUserDbFavorites(User tempUser, char index, City tempCT) throws ClassNotFoundException, SQLException, ArithmeticException, IOException {

        ToServerObject object=new ToServerObject("Edit_User_Favorites",tempUser,index,tempCT.getCityName());
        response=  tcpClient.sendToServerSql(object);
        if (response.getServerResponse().equals("Favorites update succeeded")){
            return true;
        }
        else {
            JOptionPane.showMessageDialog(errorMessage, "Favorites update failed", "Dialog", JOptionPane.ERROR_MESSAGE);
        }
        return false;

    }

    public User getUserSecretInfo(User tempUser) throws ClassNotFoundException, SQLException, ArithmeticException, IOException {
        ToServerObject object=new ToServerObject("Get_Secret_info",tempUser);
        response=  tcpClient.sendToServerSql(object);
        tempUser=(User)response.getResponseObject();
        return tempUser;
    }

    public boolean usernameSearch(User tempUser) throws ClassNotFoundException, SQLException, ArithmeticException, IOException {
        ToServerObject object=new ToServerObject("Search_User",tempUser);
        response=  tcpClient.sendToServerSql(object);
        if (response.getServerResponse().equals("User found"))
            return true;
        else
            return false;
    }

    public boolean updateUserPasswordToDB(User tempUser, String password) throws ClassNotFoundException, SQLException, IOException {
        ToServerObject object=new ToServerObject("Update_Password",tempUser,password);
        response=  tcpClient.sendToServerSql(object);
        if (response.getServerResponse().equals("Password was updated successfully"))
        {
            return true;
        }
        else {
            return false;
        }
    }

}
