package com.T_Y.controller;

import com.T_Y.model.ToServerObject;
import com.T_Y.model.User;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class UsersDB {
    private JFrame errorMessage;
    private ToServerObject response;
    public static TcpClient tcpClient = new TcpClient();

    public UsersDB() {
    }

    public boolean registerUserToDB(JSONObject jsonUser) throws ClassNotFoundException, SQLException, IOException, KeyManagementException, NoSuchAlgorithmException {

        new Example();

        HttpPut httpPut = new HttpPut("https://localhost:8443/user/signup");
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");
        StringEntity stringEntity = new StringEntity(jsonUser.toString());
        httpPut.setEntity(stringEntity);
        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpResponse response = httpclient.execute(httpPut);

        BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

        //Throw runtime exception if status code isn't 200
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
        }

        //Create the StringBuffer object and store the response into it.
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = br.readLine()) != null) {
            System.out.println("Response : \n" + result.append(line));
        }

        return true;
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
