import com.T_Y.model.ToServerObject;
import com.T_Y.model.User;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UrlIhandler   implements Ihandler{
    private ToServerObject obj;
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
                        if (new DbAccess().registerUserToDB(tempUser)) {
                            try {
                                if (new DbAccess().initFavoriteToDB(tempUser)) {
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
                        if(new DbAccess().loginUserToDB(tempUser)) {
                            obj.setServerResponse("Login succeeded");
                        }
                        else
                        {
                            obj.setServerResponse("Login Failed");
                        }
                        objectOutputStream.writeObject(obj);
                        break;
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }

                case("Admin_Login"):{
                    System.out.println("Server Received an Admin Login query from the client");
                    User tempUser = (User) obj.getObj1();

                    try {
                        if(new DbAccess().loginAdminToDB(tempUser)) {
                            obj.setServerResponse("Login succeeded");
                        }
                        else
                        {
                            obj.setServerResponse("Login Failed");

                        }
                        objectOutputStream.writeObject(obj);

                        break;
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }

                case("Show_User_Favorites"): {
                    System.out.println("Server Received a favorites query from the client");
                    User tempUser = (User) obj.getObj1();

                    try {
                        obj.setResponseObject(new DbAccess().getFavoritesArr(tempUser));
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


                    if(new DbAccess().editUserDbFavorites(tempUser,index,newCity))
                        obj.setServerResponse("Favorites update succeeded");
                    else
                        obj.setServerResponse("Problem updating the city");
                    objectOutputStream.writeObject(obj);




                }

                case("Delete_User"):{
                    System.out.println("Server Received a delete request of a user from the client");
                    User tempUser = (User) obj.getObj1();
                    try {
                        if(new DbAccess().deleteUserFromDB(tempUser)) {
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
                        if(new DbAccess().usernameSearch(tempUser)) {
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
//TODO change Get secret info case into ToServerObject on both client side and server side
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
                    User tempUser = (User) obj.getObj1();
                    String password=(String)obj.getObj2();
                    try {
                        if(new DbAccess().updateUserPasswordToDB(tempUser,password)) {
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
//TODO change Show_Users_Table case into ToServerObject on both client side and server side

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
