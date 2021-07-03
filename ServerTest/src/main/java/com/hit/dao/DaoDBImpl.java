package com.hit.dao;

import com.T_Y.model.User;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

        String[] tableNameAndId = source.split("\\.");
        String tableName = tableNameAndId[0];
        String id = tableNameAndId[1];

        //We only have three possible quarries- select * from users where...||select * from usersfavorite where...
        //therefore we only need one if/else to determine which "try" to use.
        if(tableName.equals("users"))
            try {
                User tempUser=(User)v;
                Connection conn = DriverManager.getConnection(SqlConnector.getSqlConnectorUrl(), SqlConnector.getSqlConnectorUsername(), SqlConnector.getSqlConnectorPassword());
                PreparedStatement ps = conn.prepareStatement("insert into users (username,password,secretQuestion,secretAnswer)  values (?,?,?,?)");
                ps.setString(1, tempUser.getUsername());
                ps.setString(2, tempUser.getPassword());
                ps.setString(3, tempUser.getSecretQuestion());
                ps.setString(4, tempUser.getSecretAnswer());
                ps.executeUpdate();

            }
            catch (Exception e)
            {
                throw new IOException("Failed reading from DB", e);
            }
        else if(tableName.equals("usersDelete"))
        {
            try {
                User tempUser=(User)v;

                Connection conn = DriverManager.getConnection(SqlConnector.getSqlConnectorUrl(), SqlConnector.getSqlConnectorUsername(), SqlConnector.getSqlConnectorPassword());
                PreparedStatement ps = conn.prepareStatement("delete from users where username=?");
                PreparedStatement ps2 = conn.prepareStatement("delete from usersfavorites where username=?");
                ps.setString(1, tempUser.getUsername());
                ps2.setString(1, tempUser.getUsername());
                ps.executeUpdate();
                ps2.executeUpdate();
            }
            catch (Exception e)
            {
                throw new IOException("Failed reading from DB", e);
            }
        }
        else if(tableName.equals("updateUserPassword"))
        {
            try {
                User tempUser=(User)v;


                Connection conn = DriverManager.getConnection(SqlConnector.getSqlConnectorUrl(), SqlConnector.getSqlConnectorUsername(), SqlConnector.getSqlConnectorPassword());
                PreparedStatement ps = conn.prepareStatement("update users set password=? where username=?");
                ps.setString(1, tempUser.getPassword());
                ps.setString(2, tempUser.getUsername());
                ps.executeUpdate();
            }
            catch (Exception e)
            {
                throw new IOException("Failed reading from DB", e);
            }
        }
        else//init favorites
            try {
                String[] favArr=(String[])v;


                int i = 1;
                Connection conn = DriverManager.getConnection(SqlConnector.getSqlConnectorUrl(), SqlConnector.getSqlConnectorUsername(), SqlConnector.getSqlConnectorPassword());
                PreparedStatement ps = conn.prepareStatement("insert into usersFavorites (username,city1,city2,city3,city4,city5,city6)  values (?,?,?,?,?,?,?)");
                ps.setString(1, id);
                ps.setString(2, (favArr[i++]));
                ps.setString(3, (favArr[i++]));
                ps.setString(4, (favArr[i++]));
                ps.setString(5, (favArr[i++]));
                ps.setString(6, (favArr[i++]));
                ps.setString(7, (favArr[i++]));
                int updateFlag = ps.executeUpdate();

//                Connection conn = DriverManager.getConnection(SqlConnector.getSqlConnectorUrl(), SqlConnector.getSqlConnectorUsername(), SqlConnector.getSqlConnectorPassword());
//                PreparedStatement ps = conn.prepareStatement("insert into users (username,password,secretQuestion,secretAnswer)  values (?,?,?,?)");
//                ps.setString(1, tempUser.getUsername());
//                ps.setString(2, tempUser.getPassword());
//                ps.setString(3, tempUser.getSecretQuestion());
//                ps.setString(4, tempUser.getSecretAnswer());
//                ps.executeUpdate();

            }
            catch (Exception e)
            {
                throw new IOException("Failed reading from DB", e);
            }

    }

    @Override
    public V read(String source) throws IOException {
        V result;

        String[] tableNameAndId = source.split("\\.");
        String tableName = tableNameAndId[0];
        String id = tableNameAndId[1];
        //We only have two possible quarries- select * from users where...||select * from usersfavorite where...
        //therefore we only need one if/else to determine which "try" to use.
        if(tableName.equals("users")||tableName.equals("admins"))
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
        }
        catch (Exception e)
        {
            throw new IOException("Failed reading from DB", e);
        }
        else if(tableName.equals("usersfavorites"))
        {
            try
            {
                Connection conn = DriverManager.getConnection(SqlConnector.getSqlConnectorUrl(), SqlConnector.getSqlConnectorUsername(), SqlConnector.getSqlConnectorPassword());
                PreparedStatement sel = conn.prepareStatement("select * from " + tableName + " where username=?");

                ResultSet rs = null;
                sel.setString(1, id);
                rs = sel.executeQuery();
                if (rs.next()) {
                    User user = new User(rs.getString("username"), "0000");
                    String[] favoritesArr = new String[7];
                    for (int i = 1; i < 7; i++) {
                        favoritesArr[i] = rs.getString("city"+i);
                    }
                    user.setFavoritesArr(favoritesArr);

                    return (V) user;

                } else {
                    return null;
                }
            }
            catch (Exception e)
            {
                throw new IOException("Failed reading from DB", e);
            }

        }
        else
        {
            try
            {
                Connection conn = DriverManager.getConnection(SqlConnector.getSqlConnectorUrl(), SqlConnector.getSqlConnectorUsername(), SqlConnector.getSqlConnectorPassword());
                PreparedStatement sel = conn.prepareStatement("select * from users");
                ResultSet rs = null;
                rs = sel.executeQuery();
                List<Object[]> records=new ArrayList<Object[]>();
                while(rs.next())
                {
                    int cols = rs.getMetaData().getColumnCount();
                    Object[] arr = new Object[cols];
                    for(int i=0; i<cols; i++){
                        arr[i] = rs.getObject(i+1);
                    }
                    records.add(arr);
                }

                    return (V) records;
            }
            catch (Exception e)
            {
                throw new IOException("Failed reading from DB", e);
            }
        }

    }
}


