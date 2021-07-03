package com.T_Y.server;

import com.T_Y.controller.Iservice;

import javax.imageio.IIOException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TcpServer {

    private final int port;
    private  boolean serverListen;
    private ThreadPoolExecutor threadPool;
    private Iservice requestHandler;

    public TcpServer(int port)
    {
        this.port=port;
        //init values
        this.serverListen=true;
        this.threadPool=null;
        this.requestHandler=null;
    }

    //listens to incoming connections
    public void supportClients(Iservice concurrentHandler)
    {
        this.requestHandler=concurrentHandler;

        new Thread(
                ()->
        {
            threadPool=new ThreadPoolExecutor(3,5,10, TimeUnit.SECONDS,new LinkedBlockingDeque<>());

            try{
                ServerSocket serverSocket=new ServerSocket(port);

                while(serverListen)
                {
                    Socket serverToSpecificClientSocket=serverSocket.accept();// listen()+accept()
                    /*
                    a new socket will be created for each client;
                    server will handle each client in a separate thread
                    will make every client request as a runnable task to execute
                     */

                    Runnable clientHandling= ()->{
                        try{
                            requestHandler.handle(serverToSpecificClientSocket.getInputStream(),serverToSpecificClientSocket.getOutputStream());
                            //now that we are done handling clients we must close all open streams.
                            serverToSpecificClientSocket.getInputStream().close();
                            serverToSpecificClientSocket.getOutputStream().close();
                            serverToSpecificClientSocket.close();
                        }
                        catch(IIOException e){System.out.println(e.getMessage());}
                        catch (ClassNotFoundException | IOException e){System.out.println(e.getMessage());} catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    };
                    threadPool.execute(clientHandling);
                }
                serverSocket.close();
            }
            catch (IOException e) { e.printStackTrace();}
        }
                ).start();
    }
    public void stop(){
        if(serverListen)
            serverListen=false;
        if(threadPool!=null)threadPool.shutdown();
    }

}
