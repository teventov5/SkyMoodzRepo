package com.T_Y.controller;

import com.T_Y.model.ToServerObject;
import com.T_Y.model.User;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

public class UrlIService implements Iservice {
    private ToServerObject obj;
    private static DbAccess DbAccess=new DbAccess();
    @Override
    public void handle(InputStream fromClient, OutputStream toClient) throws IOException, ClassNotFoundException, SQLException {
        ObjectInputStream objectInputStream=new ObjectInputStream(fromClient);
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(toClient);
        boolean isActive=true;
        while(isActive)
        {

            this.obj=(ToServerObject) objectInputStream.readObject();
            switch (obj.getCommandToServer())
            {
                case("Register"): {
                    System.out.println("Server Received a register query from the client");
                    User tempUser = (User) obj.getObj1();

                    try {
                        if (DbAccess.registerUserToDB(tempUser)) {
                            try {
                                if (DbAccess.initFavoriteToDB(tempUser)) {
                                    obj.setServerResponse("Registration succeeded");
                                }
                                else
                                {
                                    obj.setServerResponse("Registration Failed");
                                }
                                objectOutputStream.writeObject(obj);
                                break;
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                        obj.setServerResponse("Registration Failed");
                        objectOutputStream.writeObject(obj);
                        break;
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }

                case("User_Login"):{
                    System.out.println("Server Received a Login query from the client");
                    User tempUser = (User) obj.getObj1();

                    try {
                        if(DbAccess.loginUserToDB(tempUser)) {
                            obj.setServerResponse("Login succeeded");
                        }
                        else
                        {
                            obj.setServerResponse("Login Failed");
                        }
                        objectOutputStream.writeObject(obj);
                        break;
                    } catch (IOException throwables) {
                        throwables.printStackTrace();
                    }
                }

                case("Admin_Login"):{
                    System.out.println("Server Received an Admin Login query from the client");
                    User tempUser = (User) obj.getObj1();

                    try {
                        if(DbAccess.loginAdminToDB(tempUser)) {
                            obj.setServerResponse("Login succeeded");
                        }
                        else
                        {
                            obj.setServerResponse("Login Failed");

                        }
                        objectOutputStream.writeObject(obj);

                        break;
                    } catch (IOException throwables) {
                        throwables.printStackTrace();
                    }
                }

                case("Show_User_Favorites"): {
                    System.out.println("Server Received a favorites query from the client");
                    User tempUser = (User) obj.getObj1();

                    try {
                        obj.setServerResponse("Users favorites were successfully imported");
                        obj.setResponseObject(DbAccess.getFavoritesArr(tempUser));
                        objectOutputStream.writeObject(obj);
                        break;
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }

                case("Edit_User_Favorites"): {
                    System.out.println("Server Received a request to change favorites from the client");
                    User tempUser = (User) obj.getObj1();
                    char index =(char)obj.getObj2();
                    String newCity=(String)obj.getObj3();


                    if(DbAccess.editUserDbFavorites(tempUser,index,newCity))
                        obj.setServerResponse("Favorites update succeeded");
                    else
                        obj.setServerResponse("Problem updating the city");
                    objectOutputStream.writeObject(obj);
                    break;


                }

                case("Delete_User"):{
                    System.out.println("Server Received a delete request of a user from the client");
                    User tempUser = (User) obj.getObj1();
                    try {
                        if(DbAccess.deleteUserFromDB(tempUser)) {
                            obj.setServerResponse("Deletion succeeded");
                        }
                        else
                        {
                            obj.setServerResponse("Deletion Failed");
                        }
                        objectOutputStream.writeObject(obj);
                        break;
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }

                case("Search_User"):{
                    System.out.println("Server Received a search request of a specific user");
                    User tempUser = (User) obj.getObj1();

                    try {
                        if(DbAccess.usernameSearch(tempUser)) {
                            obj.setServerResponse("User found");
                        }
                        else
                        {
                            obj.setServerResponse("No such user");
                        }
                        objectOutputStream.writeObject(obj);
                        break;
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }

                case("Get_Secret_info"):{
                    System.out.println("Server Received a secret info request from the client");
                    User tempUser = (User) obj.getObj1();
                    try {
                       User updatedUser=DbAccess.getUserSecretInfo(tempUser);
                        if(updatedUser!=null) {
                            obj.setServerResponse("Secret info import succeeded");
                        }
                        else
                        {
                            obj.setServerResponse("User not found");
                            tempUser.setSecretQuestion(null);
                            tempUser.setSecretAnswer(null);

                        }
                        obj.setResponseObject(updatedUser);
                        objectOutputStream.writeObject(obj);
                        break;
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }

                case("Update_Password"):{
                    System.out.println("Server Received an update password request from the client");
                    User tempUser = (User) obj.getObj1();
                    String password=(String)obj.getObj2();
                    try {
                        if(DbAccess.updateUserPasswordToDB(tempUser,password)) {
                            obj.setServerResponse("Password was updated successfully");
                        }
                        else
                        {
                            obj.setServerResponse("Password update failed");

                        }
                        objectOutputStream.writeObject(obj);
                        break;
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }

                case("Show_Users_Table"):{
                    System.out.println("Server Received a show users table request from the client");
                    try {
                        List<Object[]> records=DbAccess.showUsersTable();
                        obj.setServerResponse("Users table imported successfully");
                        obj.setResponseObject(records);
                        objectOutputStream.writeObject(obj);
                        break;
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }

                case("stop"): {
                        isActive = false;
                        break;
                    }
            }
        }
    }

}
