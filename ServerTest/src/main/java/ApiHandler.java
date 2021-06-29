import com.T_Y.model.City;
import com.T_Y.controller.Repository;

import java.io.*;
import java.sql.SQLException;

public class ApiHandler implements Ihandler{
    @Override
    public void handle(InputStream fromClient, OutputStream toClient) throws IOException, ClassNotFoundException, SQLException {
        ObjectInputStream objectInputStream=new ObjectInputStream(fromClient);
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(toClient);
        boolean isActive=true;
        while(isActive)
        {
            switch (objectInputStream.readObject().toString())
            {
                case("Get_Forecast"): {
                    System.out.println("Server Received a forecast request of a city from the client");
                    City tempCt=(City)objectInputStream.readObject();
                    if (new Repository().cityCodeSearch(tempCt)) {
                        if ((new Repository().getCityForecast(tempCt) != null) && (new Repository().getHangouts(tempCt) != null)) {
                            objectOutputStream.writeObject("Forecast result updated");
                            objectOutputStream.writeObject(tempCt);
                        }
                    }
                            else
                            {
                                objectOutputStream.writeObject("Forecast result update failed");
                            }
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
