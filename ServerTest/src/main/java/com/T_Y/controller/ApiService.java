package com.T_Y.controller;

import com.T_Y.model.City;
import com.T_Y.model.ToServerObject;

import java.io.*;
import java.sql.SQLException;

public class ApiService implements Iservice {
    private ToServerObject obj;
    private static Repository repository=new Repository();

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
                case("Get_Forecast"): {
                    System.out.println("Server Received a forecast request of a city from the client");
                    City tempCt=(City)obj.getObj1();
                    if (repository.cityCodeSearch(tempCt)) {
                        if (( repository.getCityForecast(tempCt) != null) && (repository.getHangouts(tempCt) != null)) {
                            obj.setServerResponse("Forecast result updated");
                            obj.setResponseObject(tempCt);
                        }
                    }
                            else
                            {
                                obj.setServerResponse("Forecast result update failed");
                            }
                            objectOutputStream.writeObject(obj);
                            break;
                }

                case("Get_City_Code"): {
                    System.out.println("Server Received a City Code request from the client");
                    City tempCt=(City)obj.getObj1();
                    if (repository.cityCodeSearch(tempCt)) {
                         {
                             obj.setServerResponse("City code updated");
                             obj.setResponseObject(tempCt);
                        }
                    }
                    else
                    {
                        obj.setServerResponse("City code update failed");
                    }
                    objectOutputStream.writeObject(obj);
                    break;
                }

                case("stop"): {
                    isActive = false;
                    break;
                }
            }
        }
    }

}
