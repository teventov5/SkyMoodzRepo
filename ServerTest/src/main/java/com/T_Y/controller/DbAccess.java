package com.T_Y.controller;

import com.T_Y.model.User;
import com.hit.dao.DaoImpl;
import com.hit.dao.IDao;
import com.hit.dao.SqlConnector;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbAccess {
    private IDao<User> db;

    public DbAccess() {
        this.db = DaoImpl.createAccess(DaoImpl.DB, "");
    }

    public boolean loginUserToDB(User tempUser) throws IOException, ClassNotFoundException {
        User user = db.read("users." + tempUser.getUsername());
        if (user == null) {
            return false;
        }

        return user.getPassword().equals(tempUser.getPassword());
    }

    public boolean loginAdminToDB(User tempUser) throws IOException, ClassNotFoundException {
        User user = db.read("admins." + tempUser.getUsername());
        if (user == null) {
            return false;
        }

        return user.getPassword().equals(tempUser.getPassword());
    }

    public boolean registerUserToDB(User tempUser) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(SqlConnector.getSqlConnectorUrl(), SqlConnector.getSqlConnectorUsername(), SqlConnector.getSqlConnectorPassword());
        PreparedStatement sel = conn.prepareStatement("select username from users where username=?");
        ResultSet rs = null;
        sel.setString(1, tempUser.getUsername());
        rs = sel.executeQuery();
        if (rs.next()) {
            return false;
        }
        PreparedStatement ps = conn.prepareStatement("insert into users (username,password,secretQuestion,secretAnswer)  values (?,?,?,?)");
        ps.setString(1, tempUser.getUsername());
        ps.setString(2, tempUser.getPassword());
        ps.setString(3, tempUser.getSecretQuestion());
        ps.setString(4, tempUser.getSecretAnswer());
        int updateFlag = ps.executeUpdate();
        if (updateFlag > 0) {

            return true;

        } else {
            return false;
        }
    }

    public boolean deleteUserFromDB(User tempUser) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(SqlConnector.getSqlConnectorUrl(), SqlConnector.getSqlConnectorUsername(), SqlConnector.getSqlConnectorPassword());
        PreparedStatement ps = conn.prepareStatement("delete from users where username=?");
        PreparedStatement ps2 = conn.prepareStatement("delete from usersfavorites where username=?");
        ps.setString(1, tempUser.getUsername());
        ps2.setString(1, tempUser.getUsername());
        int updateFlag = ps.executeUpdate();
        int updateFlag2=ps2.executeUpdate();
        if (  (updateFlag > 0) && (updateFlag2>0)  )
            return true;
        else
            return false;

    }

    public boolean initFavoriteToDB(User tempUser) throws ClassNotFoundException, SQLException, IOException {
        String[] favArr = {"null", "empty city slot#1", "empty city slot#2", "empty city slot#3", "empty city slot#4", "empty city slot#5", "empty city slot#6"};
        Class.forName("com.mysql.cj.jdbc.Driver");
        int i = 1;
        Connection conn = DriverManager.getConnection(SqlConnector.getSqlConnectorUrl(), SqlConnector.getSqlConnectorUsername(), SqlConnector.getSqlConnectorPassword());
        PreparedStatement ps = conn.prepareStatement("insert into usersFavorites (username,city1,city2,city3,city4,city5,city6)  values (?,?,?,?,?,?,?)");
        ps.setString(1, tempUser.getUsername());
        ps.setString(2, (favArr[i++]));
        ps.setString(3, (favArr[i++]));
        ps.setString(4, (favArr[i++]));
        ps.setString(5, (favArr[i++]));
        ps.setString(6, (favArr[i++]));
        ps.setString(7, (favArr[i++]));
        int updateFlag = ps.executeUpdate();
        if (updateFlag > 0) {
            return true;
        } else {
            return false;
        }
    }

    public String[] getFavoritesArr(User tempUser) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(SqlConnector.getSqlConnectorUrl(), SqlConnector.getSqlConnectorUsername(), SqlConnector.getSqlConnectorPassword());
        PreparedStatement sel = conn.prepareStatement("select city1,city2,city3,city4,city5,city6 from usersfavorites where username=?");
        ResultSet rs = null;
        sel.setString(1, tempUser.getUsername());
        rs = sel.executeQuery();
        if (rs.next()) {
            String[] favoritesArr = new String[7];
            for (int i = 1; i < 7; i++) {
                favoritesArr[i] = rs.getString(i);
            }
            return favoritesArr;
        } else {
            return null;
        }


    }

    public boolean editUserDbFavorites(User tempUser, char index, String newCity) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(SqlConnector.getSqlConnectorUrl(), SqlConnector.getSqlConnectorUsername(), SqlConnector.getSqlConnectorPassword());
        String updateString = new String("update usersFavorites set city");
        updateString = updateString + index + "='" + newCity + "' ";
        PreparedStatement upd = conn.prepareStatement(updateString + "where username=?");
        upd.setString(1, tempUser.getUsername());
        int updateFlag = upd.executeUpdate();
        if (updateFlag > 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean usernameSearch(User tempUser) throws ClassNotFoundException, SQLException, ArithmeticException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(SqlConnector.getSqlConnectorUrl(), SqlConnector.getSqlConnectorUsername(), SqlConnector.getSqlConnectorPassword());
        PreparedStatement sel = conn.prepareStatement("select username from users where username=?");
        ResultSet rs = null;
        sel.setString(1, tempUser.getUsername());


        rs = sel.executeQuery();
        if (rs.next()) {
            return true;
        }
        return false;
    }

    public boolean getUserSecretInfo(User tempUser) throws ClassNotFoundException, SQLException, ArithmeticException, IOException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(SqlConnector.getSqlConnectorUrl(), SqlConnector.getSqlConnectorUsername(), SqlConnector.getSqlConnectorPassword());
        PreparedStatement sel = conn.prepareStatement("select secretQuestion,secretAnswer from users where username=?");
        ResultSet rs = null;
        sel.setString(1, tempUser.getUsername());


        rs = sel.executeQuery();
        if (rs.next()) {
            tempUser.setSecretQuestion(rs.getString(1));
            tempUser.setSecretAnswer(rs.getString(2));
            return true;
        }
        return false;

    }

    public boolean updateUserPasswordToDB(User tempUser, String password) throws ClassNotFoundException, SQLException, IOException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(SqlConnector.getSqlConnectorUrl(), SqlConnector.getSqlConnectorUsername(), SqlConnector.getSqlConnectorPassword());
        PreparedStatement ps = conn.prepareStatement("update users set password=? where username=?");
        ps.setString(1, password);
        ps.setString(2, tempUser.getUsername());
        int updateFlag = ps.executeUpdate();
        if (updateFlag > 0) {
            return true;
        } else {
            return false;

        }

    }

    public List<Object[]> showUsersTable() throws ClassNotFoundException, SQLException, ArithmeticException, IOException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(SqlConnector.getSqlConnectorUrl(), SqlConnector.getSqlConnectorUsername(), SqlConnector.getSqlConnectorPassword());
        PreparedStatement sel = conn.prepareStatement("select * from users");
        ResultSet rs = null;
        rs = sel.executeQuery();


        List<Object[]> records=new ArrayList<Object[]>();
        while(rs.next()){
            int cols = rs.getMetaData().getColumnCount();
            Object[] arr = new Object[cols];
            for(int i=0; i<cols; i++){
                arr[i] = rs.getObject(i+1);
            }
            records.add(arr);
        }
        return records;
    }



}
