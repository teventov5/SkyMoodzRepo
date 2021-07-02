package com.hit.dao;

import com.T_Y.model.User;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DaoDBImpl<V> implements IDao<V> {

    public DaoDBImpl() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(String source, V v) throws IOException {

    }

    @Override
    public V read(String source) throws IOException {
        V result;

        String[] tableNameAndId = source.split("\\.");
        String tableName = tableNameAndId[0];
        String id = tableNameAndId[1];

        try {
            Connection conn = DriverManager.getConnection(SqlConnector.getSqlConnectorUrl(), SqlConnector.getSqlConnectorUsername(), SqlConnector.getSqlConnectorPassword());
            PreparedStatement sel = conn.prepareStatement("select * from " + tableName + " where username=?");
            sel.setString(1, id);
            ResultSet rs = sel.executeQuery();

            if (rs.next()) {
                User user = new User(rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("secretQuestion"),
                    rs.getString("secretAnswer"));
                return (V)user;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new IOException("Failed reading from DB", e);
        }
    }
}


