package com.T_Y.controller;

import com.T_Y.model.*;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CitySearch {
    private JFrame errorMessage;

    public CitySearch() {
    }

    public ForecastResult searchForCityResult(String cityUserInput) {
            try {
                City ct = new City(cityUserInput, "0000");
                if(ct.getCityName().contains("empty city slot#")) {
                    JOptionPane.showMessageDialog(null, "Change list default value to an actual city", "Dialog", JOptionPane.INFORMATION_MESSAGE);
                    return null;
                }


//now we will send the city to the server so it will send api request

                Socket clientSocket = new Socket("192.168.1.50", 8011);
                System.out.println("New operational socket was created");
                ObjectOutputStream toServer = new ObjectOutputStream(clientSocket.getOutputStream());

                ObjectInputStream fromServer = new ObjectInputStream(clientSocket.getInputStream());

                toServer.writeObject("Get_Forecast");

                toServer.writeObject(ct);
                City cityUpdated=new City();
                if (fromServer.readObject().toString().equals("Forecast result updated")) {
                    cityUpdated=(City) fromServer.readObject();
                    toServer.writeObject("stop");
                    return cityUpdated.getResult();
                } else {
                    toServer.writeObject("stop");
                    return null;
                }

            } catch (Exception e2) {
                if(e2.getMessage().contains("The allowed number of requests has been exceeded"))
                JOptionPane.showMessageDialog(errorMessage, "The allowed number of Api requests has been exceeded", "Dialog", JOptionPane.ERROR_MESSAGE);
                else
                    JOptionPane.showMessageDialog(errorMessage, "There is a problem getting weather data for the specified city", "Dialog", JOptionPane.ERROR_MESSAGE);

            }
        return null;
    }


}
