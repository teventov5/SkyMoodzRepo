package com.T_Y.controller;

import com.T_Y.model.*;


import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class CitySearch {
    private JFrame errorMessage;
    private ToServerObject response;


    public CitySearch() {
    }

    public ForecastResult searchForCityResult(String cityUserInput) {
            try {
                City ct = new City(cityUserInput, "0000");
                if(ct.getCityName().contains("empty city slot#")) {
                    JOptionPane.showMessageDialog(null, "Change list default value to an actual city", "Dialog", JOptionPane.INFORMATION_MESSAGE);
                    return null;
                }

                ToServerObject object=new ToServerObject("Get_Forecast",ct);
                response=  new TcpClient().sendToServerApi(object);
                City cityUpdated=new City();
                if (response.getServerResponse().equals("Forecast result updated")) {
                    cityUpdated=(City)response.getResponseObject();

                    return cityUpdated.getResult();
                } else {
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

    public City FavoriteCityCodeSearch(City ct) throws IOException, ClassNotFoundException {
        ToServerObject object=new ToServerObject("Get_City_Code",ct);
        response=  new TcpClient().sendToServerApi(object);
        City cityUpdated;
        if (response.getServerResponse().equals("City code updated")) {
            cityUpdated=(City) response.getResponseObject();
            return cityUpdated;
        } else {
            return null;
        }

    }





}
