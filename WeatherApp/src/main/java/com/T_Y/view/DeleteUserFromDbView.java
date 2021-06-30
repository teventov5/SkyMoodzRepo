package com.T_Y.view;

import com.T_Y.model.User;
import com.T_Y.controller.UsersDB;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class DeleteUserFromDbView extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField userTextInput;
    private User tempUser;
    private JLabel lblSuccessFlag;
    private JLabel lblUsername;
    private boolean successFlag;
    private boolean searchFlag;

    /**
     * Launch the application.


     public static void main(String[] args) {
     try {
     favoriteCitySearch dialog = new favoriteCitySearch();
     dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
     dialog.setVisible(true);
     } catch (Exception e) {
     e.printStackTrace();
     }
     }

     */

    /**
     * Create the dialog.
     */
    public DeleteUserFromDbView() throws ArithmeticException {
        setVisible(true);
        setBounds(100, 100, 456, 213);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        userTextInput = new JTextField();
        userTextInput.setBounds(171, 23, 144, 46);
        contentPanel.add(userTextInput);
        userTextInput.setColumns(10);
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setBounds(0, 130, 436, 33);
            contentPanel.add(buttonPane);
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            {

                {
                    JButton closeButton = new JButton("close");
                    closeButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            setVisible(false);
                            dispose();
                        }
                    });
                    closeButton.setActionCommand("Cancel");
                    buttonPane.add(closeButton);
                }
                JButton applyButton = new JButton("Delete");
                applyButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) throws ArithmeticException {
                        try {
                            if (lblSuccessFlag.getForeground() == Color.red) {
                                throw new ArithmeticException("cant Delete without pressing the search button first");
                            }
                        } catch (ArithmeticException e1) {
                            JOptionPane.showMessageDialog(new JFrame(), e1.getMessage(), "Dialog", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        try {
                            successFlag = new UsersDB().deleteUserFromDB(tempUser);
                            if (successFlag) {
                                JOptionPane.showMessageDialog(new JFrame(), "User " + tempUser.getUsername() + " was deleted", "Dialog", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(new JFrame(), "There is a problem and " + tempUser.getUsername() + " was *NOT* deleted", "Dialog", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (ClassNotFoundException classNotFoundException) {
                            classNotFoundException.printStackTrace();


                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }


                    }
                });
                applyButton.setActionCommand("apply");
                buttonPane.add(applyButton);
                getRootPane().setDefaultButton(applyButton);

                lblUsername = new JLabel("type the username here");
                lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
                lblUsername.setBounds(10, 19, 161, 50);
                contentPanel.add(lblUsername);

                lblSuccessFlag = new JLabel("User not found");
                lblSuccessFlag.setForeground(Color.RED);
                lblSuccessFlag.setBackground(Color.RED);
                lblSuccessFlag.setFont(new Font("Tahoma", Font.PLAIN, 14));
                lblSuccessFlag.setBounds(181, 80, 114, 46);
                contentPanel.add(lblSuccessFlag);

                JButton btnSearch = new JButton("Search");
                btnSearch.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            lblSuccessFlag.setForeground(Color.red);
                            lblSuccessFlag.setText("User not found");
                            tempUser = new User(userTextInput.getText(), "0000");
                            searchFlag = new UsersDB().usernameSearch(tempUser);
                            if (searchFlag) {
                                lblSuccessFlag.setText("user found!");
                                lblSuccessFlag.setForeground(Color.blue);
                            } else {
                                JOptionPane.showMessageDialog(null, "Username " + tempUser.getUsername() + " does not Exist", "Dialog", JOptionPane.INFORMATION_MESSAGE);

                            }
                        } catch (Exception el) {
                            el.printStackTrace();

                        }
                    }
                });
                btnSearch.setBounds(326, 29, 93, 34);
                contentPanel.add(btnSearch);
            }
        }
    }
}
