package com.T_Y.controller;

import com.T_Y.model.User;
import com.hit.dao.DaoImpl;
import com.hit.dao.IDao;
import com.hit.dao.SqlConnector;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbAccess extends com.hit.dao.SqlConnector {
    private IDao<User> db;
    private IDao<List<Object[]>> db2;
    private IDao<String[]> dbS;

    public DbAccess() {
        this.db = DaoImpl.createAccess(DaoImpl.DB, "");
        this.db2 = DaoImpl.createAccess(DaoImpl.DB, "");
        this.dbS = DaoImpl.createAccess(DaoImpl.DB, "");
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

    public boolean registerUserToDB(User tempUser) throws ClassNotFoundException, SQLException, IOException {


        User user = db.read("users." + tempUser.getUsername());
        if (user != null) {
            return false;
        }

        db.save("users." + tempUser.getUsername(), tempUser);
        return true;

    }

    public boolean deleteUserFromDB(User tempUser) throws ClassNotFoundException, SQLException, IOException {


        db.save("usersDelete." + tempUser.getUsername(), tempUser);
        User user = db.read("users." + tempUser.getUsername());
        if (user != null)
            return false;

        return true;

    }

    public boolean initFavoriteToDB(User tempUser) throws ClassNotFoundException, SQLException, IOException {

        String[] favArr = {"null", "empty city slot#1", "empty city slot#2", "empty city slot#3", "empty city slot#4", "empty city slot#5", "empty city slot#6"};
        dbS.save("usersfavorite." + tempUser.getUsername(), favArr);
        User user = db.read("users." + tempUser.getUsername());
        if (user == null)
            return false;


        return true;
    }

    public String[] getFavoritesArr(User tempUser) throws ClassNotFoundException, SQLException, IOException {

        User user = db.read("usersfavorites." + tempUser.getUsername());
        if (user == null)
        {
            return null;
        }
        return user.getFavoritesArr();

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

    public boolean usernameSearch(User tempUser) throws ClassNotFoundException, SQLException, ArithmeticException, IOException {


        User user = db.read("users." + tempUser.getUsername());
        if (user == null)
        {
            return false;
        }
        return true;




//        Class.forName("com.mysql.cj.jdbc.Driver");
//        Connection conn = DriverManager.getConnection(SqlConnector.getSqlConnectorUrl(), SqlConnector.getSqlConnectorUsername(), SqlConnector.getSqlConnectorPassword());
//        PreparedStatement sel = conn.prepareStatement("select username from users where username=?");
//        ResultSet rs = null;
//        sel.setString(1, tempUser.getUsername());
//
//
//        rs = sel.executeQuery();
//        if (rs.next()) {
//            return true;
//        }
//        return false;
    }

    public User getUserSecretInfo(User tempUser) throws ClassNotFoundException, SQLException, ArithmeticException, IOException{

        User user = db.read("users." + tempUser.getUsername());
        if (user == null) {
            return null;
        }
        return user;

    }

    public boolean updateUserPasswordToDB(User tempUser, String password) throws ClassNotFoundException, SQLException, IOException {
        tempUser.setPassword(password);
        db.save("updateUserPassword." + tempUser.getUsername(), tempUser);
        User user = db.read("users." + tempUser.getUsername());
        if (user.getPassword().equals(password))
            return true;

        return false;

    }

    public List<Object[]> showUsersTable() throws ClassNotFoundException, SQLException, ArithmeticException, IOException {
        List<Object[]> records = db2.read("allUsers. nullValue");
        if (records == null) {
            return null;
        }
        return records;
    }

}
