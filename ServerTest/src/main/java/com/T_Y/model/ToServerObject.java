package com.T_Y.model;

import java.io.Serializable;

public class ToServerObject implements Serializable {
    private String commandToServer;
    private String ServerResponse;
    private Object obj1=null;
    private Object obj2=null;
    private Object obj3=null;
    private Object responseObject;


    public ToServerObject(String commandToServer)
    {
        this.commandToServer=commandToServer;
    }
    public ToServerObject(String commandToServer,Object obj1)
    {
        this.commandToServer=commandToServer;
        this.obj1=obj1;
    }

    public ToServerObject(String commandToServer,Object obj1,Object obj2)
    {
        this.commandToServer=commandToServer;
        this.obj1=obj1;
        this.obj2=obj2;
    }

    public ToServerObject(String commandToServer,Object obj1,Object obj2,Object obj3)
    {
        this.commandToServer=commandToServer;
        this.obj1=obj1;
        this.obj2=obj2;
        this.obj3=obj3;
    }

    public String getCommandToServer() {
        return commandToServer;
    }

    public Object getObj1() {
        return obj1;
    }

    public Object getObj2() {
        return obj2;
    }

    public Object getObj3() {
        return obj3;
    }

    public void setCommandToServer(String commandToServer) {
        this.commandToServer = commandToServer;
    }

    public void setObj1(Object obj1) {
        this.obj1 = obj1;
    }

    public void setObj2(Object obj2) {
        this.obj2 = obj2;
    }

    public void setObj3(Object obj3) {
        this.obj3 = obj3;
    }

    public String getServerResponse() {
        return ServerResponse;
    }

    public void setServerResponse(String serverResponse) {
        ServerResponse = serverResponse;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }

    public Object getResponseObject() {
        return responseObject;
    }
}
