package com.T_Y.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

public interface Iservice {
public abstract void handle(InputStream fromClient, OutputStream toClient) throws IOException, ClassNotFoundException, SQLException;
}
