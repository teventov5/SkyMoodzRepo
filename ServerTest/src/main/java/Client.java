import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client{

    public static void main(String[] args) throws IOException, ClassNotFoundException {



        Socket clientSocket=new Socket("127.0.0.1",8010);
        System.out.println("New operational socket was created");
        ObjectOutputStream toServer=new ObjectOutputStream(clientSocket.getOutputStream());

        ObjectInputStream fromServer=new ObjectInputStream(clientSocket.getInputStream());

        toServer.writeObject("URL");
        String addressTest="Https://www.google.co.il";
        toServer.writeObject(addressTest);
        toServer.writeObject("stop");

        






    }






}
