package com.T_Y.controller;

import com.T_Y.model.User;
import com.T_Y.view.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.function.IntPredicate;
import java.util.regex.Pattern;

public class UserManagement {
    public final Pattern textPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$");
    private FavoriteCitySearch dialog;
    private DeleteUserFromDbView dialog2;
    private boolean isRealUser = false;
    private JFrame errorMessage;

    public UserManagement() {
    }

    public boolean isPasswordValid(String value) {
        return (   containsNumber(value) && (containsLowerCase(value) && containsUpperCase(value)) &&  ((value.length()>3)&& value.length()<9) );

    }

    private boolean containsLowerCase(String value) {
        return contains(value, i -> Character.isLetter(i) && Character.isLowerCase(i));
    }

    private boolean containsUpperCase(String value) {
        return contains(value, i -> Character.isLetter(i) && Character.isUpperCase(i));
    }

    private boolean containsNumber(String value) {
        return contains(value, Character::isDigit);
    }

    private boolean contains(String value, IntPredicate predicate) {
        return value.chars().anyMatch(predicate);
    }

    private boolean passwordCheck(User tempUser) {
        String tempPassword = tempUser.getPassword();
        if (tempPassword.length() > 8 || tempPassword.length() < 4) {
            JOptionPane.showMessageDialog(null, "Password has to be between 4-8 chars ", "Dialog", JOptionPane.INFORMATION_MESSAGE);
            return false;

        }
        if (!isPasswordValid(tempPassword)) {
            JOptionPane.showMessageDialog(null, "Password has to include at least one digit and one letter ", "Dialog", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean registerUser(User tempUser) throws SQLException, ClassNotFoundException, IOException {
        try {
            if(passwordCheck(tempUser))
            System.out.println("user password meets requirements");
            else return false;
            if (new UsersDB().registerUserToDB(tempUser)) {
                JOptionPane.showMessageDialog(null, "Registration Succeeded", "Dialog", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }

            } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (HeadlessException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean loginUser(User tempUser) throws SQLException, ClassNotFoundException, IOException {
        try {
            if(new UsersDB().loginUserToDB(tempUser)) {
                JOptionPane.showMessageDialog(errorMessage, "Login Allowed", "Dialog", JOptionPane.INFORMATION_MESSAGE);
                new UserCustomizedScreen(tempUser);
                return true;
            }
            else
            {
                JOptionPane.showMessageDialog(errorMessage, "Login failed", "Dialog", JOptionPane.ERROR_MESSAGE);
                //return false comes later
            }
        } catch (Exception e1) {
           e1.printStackTrace();
        }
        return false;
    }

    public boolean loginAdmin(User tempUser) throws SQLException, ClassNotFoundException, IOException {
        try {
           boolean loginSuccess= new UsersDB().loginAdminToDB(tempUser);
           return loginSuccess;
        }
        catch (ArithmeticException e1) {
            e1.printStackTrace();
            }
        return false;
    }

    public String[] showFavorites(User tempUser) {
        try {
            try {
                return (new UsersDB().showUserDbFavorites(tempUser));
            } catch (Exception e2) {
               e2.printStackTrace();;
            }
        } catch (Exception e1) {
           e1.printStackTrace();;
        }
        throw new ArithmeticException("problem getting favorites");
    }

    public boolean editFavorites(User tempUser, char index) throws SQLException, ClassNotFoundException, IOException, ArithmeticException {
        try {
            dialog = new FavoriteCitySearch(tempUser, index);
            dialog.setVisible(true);
        } catch (Exception e1) {
            return true;
        }
        return false;
    }

    public User resetUserPassword(User tempUser) throws SQLException, ClassNotFoundException, IOException {
        try {
            User tempUser2 = new UsersDB().getUserSecretInfo(tempUser);
            if (tempUser2!=null) {
                return tempUser2;
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return null;
    }

}






