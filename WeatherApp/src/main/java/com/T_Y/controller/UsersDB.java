package com.T_Y.controller;

import com.T_Y.model.ToServerObject;
import com.T_Y.model.User;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.*;

public class UsersDB {
    private JFrame errorMessage;
    private ToServerObject response;
    public static TcpClient tcpClient=new TcpClient();

    public UsersDB() {
    }

    public boolean registerUserToDB(JSONObject jsonUser) throws ClassNotFoundException, SQLException, IOException {


        String httpsURL = "https://localhost:8443/user/signup";



        URL myurl = new URL(httpsURL);
        HttpsURLConnection con = (HttpsURLConnection)myurl.openConnection();
        con.setRequestMethod("POST");

        con.setRequestProperty("Content-length", String.valueOf(jsonUser.toString().length()));
        con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0;Windows98;DigExt)");
        con.setDoOutput(true);
        con.setDoInput(true);

        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        DataOutputStream output = new DataOutputStream(con.getOutputStream());


        output.writeBytes(jsonUser.toString());

        output.close();

        DataInputStream input = new DataInputStream( con.getInputStream() );



        for( int c = input.read(); c != -1; c = input.read() )
            System.out.print( (char)c );
        input.close();

        System.out.println("Resp Code:"+con .getResponseCode());
        System.out.println("Resp Message:"+ con .getResponseMessage());
        return true;





//
//        ToServerObject object=new ToServerObject("Register",tempUser);
//        response=  tcpClient.sendToServerSql(object);
//        if (response.getServerResponse().equals("Registration succeeded")) {
//            return true;
//        } else if(response.equals("Username already exist")){
//            JOptionPane.showMessageDialog(errorMessage, "Username already exist", "Dialog", JOptionPane.ERROR_MESSAGE);
//            return false;
//        }
//        else
//        {
//            JOptionPane.showMessageDialog(errorMessage, "Registration failed for unknown reason", "Dialog", JOptionPane.ERROR_MESSAGE);
//            return false;
//        }
    }

    public boolean loginUserToDB(User tempUser) throws ClassNotFoundException, SQLException, ArithmeticException, IOException {
        ToServerObject object=new ToServerObject("User_Login",tempUser);
        response=  tcpClient.sendToServerSql(object);
        if (response.getServerResponse().equals("Login succeeded"))
            return true;
        else
            return false;

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
