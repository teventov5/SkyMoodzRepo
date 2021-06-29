import com.T_Y.model.User;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UrlIhandler   implements Ihandler{
    @Override
    public void handle(InputStream fromClient, OutputStream toClient) throws IOException, ClassNotFoundException, SQLException {
        ObjectInputStream objectInputStream=new ObjectInputStream(fromClient);
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(toClient);
        boolean isActive=true;
        while(isActive)
        {

            switch (objectInputStream.readObject().toString())
            {
                case("Register"): {
                    System.out.println("Server Received a register query from the client");
                    User tempUser = (User) objectInputStream.readObject();
                    try {
                        if (new DbAccess().registerUserToDB(tempUser)) {
                            try {
                                if (new DbAccess().initFavoriteToDB(tempUser)) {
                                    objectOutputStream.writeObject("Registration succeeded");
                                    break;
                                }
                                else
                                {
                                    objectOutputStream.writeObject("Registration Failed");
                                    break;
                                }
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                        objectOutputStream.writeObject("Username already exist");
                        break;
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }

                case("User_Login"):{
                    System.out.println("Server Received a Login query from the client");
                    User tempUser= (User) objectInputStream.readObject();
                    try {
                        if(new DbAccess().loginUserToDB(tempUser)) {
                            objectOutputStream.writeObject("Login succeeded");
                        }
                        else
                        {
                            objectOutputStream.writeObject("Login Failed");
                        }
                        break;
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }

                case("Admin_Login"):{
                    System.out.println("Server Received an Admin Login query from the client");
                    User tempUser= (User) objectInputStream.readObject();
                    try {
                        if(new DbAccess().loginAdminToDB(tempUser)) {
                            objectOutputStream.writeObject("Login succeeded");
                        }
                        else
                        {
                            objectOutputStream.writeObject("Login Failed");
                        }
                        break;
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }

                case("Show_User_Favorites"): {
                    System.out.println("Server Received a favorites query from the client");
                    User tempUser = (User) objectInputStream.readObject();
                    try {
                        objectOutputStream.writeObject(new DbAccess().getFavoritesArr(tempUser));
                        break;
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }

                case("Edit_User_Favorites"): {
                    System.out.println("Server Received a request to change favorites from the client");
                    User tempUser = (User) objectInputStream.readObject();
                    char index = (char) objectInputStream.readObject();
                    String newCity = (String) objectInputStream.readObject();
                    if(new DbAccess().editUserDbFavorites(tempUser,index,newCity))
                        objectOutputStream.writeObject("Favorites update succeeded");
                    else
                        objectOutputStream.writeObject("Problem updating the city");


                }

                case("Delete_User"):{
                    System.out.println("Server Received a delete request of a user from the client");
                    User tempUser= (User) objectInputStream.readObject();
                    try {
                        if(new DbAccess().deleteUserFromDB(tempUser)) {
                            objectOutputStream.writeObject("Deletion succeeded");
                        }
                        else
                        {
                            objectOutputStream.writeObject("Deletion Failed");
                        }
                        break;
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }

                case("Search_User"):{
                    System.out.println("Server Received a search request of a specific user");
                    User tempUser= (User) objectInputStream.readObject();
                    try {
                        if(new DbAccess().usernameSearch(tempUser)) {
                            objectOutputStream.writeObject("User found");
                        }
                        else
                        {
                            objectOutputStream.writeObject("No such user");
                        }
                        break;
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }

                case("Get_Secret_info"):{
                    System.out.println("Server Received a secret info request from the client");
                    User tempUser= (User) objectInputStream.readObject();
                    try {
                        if(new DbAccess().getUserSecretInfo(tempUser)) {
                            System.out.println("Secret info import succeeded");
                        }
                        else
                        {
                           System.out.println("Secret info import failed");
                            tempUser.setSecretQuestion(null);
                            tempUser.setSecretAnswer(null);

                        }
                        objectOutputStream.writeObject(tempUser);
                        break;
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }

                case("Update_Password"):{
                    System.out.println("Server Received an update password request from the client");
                    User tempUser= (User) objectInputStream.readObject();
                    String password=(String) objectInputStream.readObject();
                    try {
                        if(new DbAccess().updateUserPasswordToDB(tempUser,password)) {
                            objectOutputStream.writeObject("Password was updated successfully");
                        }
                        else
                        {
                            objectOutputStream.writeObject("Password update failed");
                        }
                        break;
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }

                case("Show_Users_Table"):{
                    System.out.println("Server Received a show users table request from the client");
                    try {
                        List<Object[]> records=new DbAccess().showUsersTable();
                        objectOutputStream.writeObject("Users table imported successfully");
                        objectOutputStream.writeObject(records);
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
