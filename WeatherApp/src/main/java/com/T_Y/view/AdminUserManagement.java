package com.T_Y.view;

import com.T_Y.controller.UsersDB;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class AdminUserManagement extends JFrame {

    private JPanel contentPane;

    /**
     * Create the frame.
     */
    public AdminUserManagement() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 157, 249);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnViewUsers = new JButton("View users");
        btnViewUsers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    JOptionPane.showMessageDialog(new JFrame(), "Users Table available in CLI after pressing OK", "Dialog", JOptionPane.PLAIN_MESSAGE);
                    List<Object[]> records =new UsersDB().showUsersTable();
                    if(records!=null)
                        records.forEach(y -> System.out.println(Arrays.toString(y) + "\n"));
                    else
                        JOptionPane.showMessageDialog(new JFrame(), "Problem getting users table data from server", "Dialog", JOptionPane.PLAIN_MESSAGE);

                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
           } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        btnViewUsers.setBounds(26, 28, 112, 62);
        contentPane.add(btnViewUsers);

        JButton btnDeleteUser = new JButton("Delete user");
        btnDeleteUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DeleteUserFromDbView deleteUserFromDbView = new DeleteUserFromDbView();
            }
        });
        btnDeleteUser.setBounds(26, 125, 112, 62);
        contentPane.add(btnDeleteUser);
        setLocationRelativeTo(null);
    }

}
