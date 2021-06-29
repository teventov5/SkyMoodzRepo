package com.T_Y.view;

import com.T_Y.controller.FavoriteCitySearch;
import com.T_Y.controller.UserManagement;
import com.T_Y.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class UserFavoritesView extends JFrame {
    private static String[] currentFavoritesArr = new String[6];
    private FavoriteCitySearch dialog;
    private boolean successFlag = false;

    /**
     * Create the panel.
     */
    public UserFavoritesView(User tempUser) {
        this.setCurrentFavoritesArr(new UserManagement().showFavorites(tempUser));
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(new Dimension(800, 600));
        getContentPane().setLayout(null);

        Component verticalStrut = Box.createVerticalStrut(20);
        verticalStrut.setBounds(215, 0, 12, 600);
        getContentPane().add(verticalStrut);

        Component verticalStrut_1 = Box.createVerticalStrut(20);
        verticalStrut_1.setBounds(528, 0, 12, 600);
        getContentPane().add(verticalStrut_1);

        Component horizontalStrut = Box.createHorizontalStrut(20);
        horizontalStrut.setBounds(10, 273, 780, 0);
        getContentPane().add(horizontalStrut);
        currentFavoritesArr = tempUser.getFavoritesArr();

        JLabel lblCity1 = new JLabel(currentFavoritesArr[1]);
        lblCity1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblCity1.setBounds(71, 105, 164, 48);
        getContentPane().add(lblCity1);

        JLabel lblCity2 = new JLabel(currentFavoritesArr[2]);
        lblCity2.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblCity2.setBounds(334, 105, 164, 48);
        getContentPane().add(lblCity2);

        JLabel lblCity3 = new JLabel(currentFavoritesArr[3]);
        lblCity3.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblCity3.setBounds(612, 105, 164, 48);
        getContentPane().add(lblCity3);

        JLabel lblCity4 = new JLabel(currentFavoritesArr[4]);
        lblCity4.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblCity4.setBounds(71, 386, 164, 48);
        getContentPane().add(lblCity4);

        JLabel lblCity5 = new JLabel(currentFavoritesArr[5]);
        lblCity5.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblCity5.setBounds(334, 386, 164, 48);
        getContentPane().add(lblCity5);

        JLabel lblCity6 = new JLabel(currentFavoritesArr[6]);
        lblCity6.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblCity6.setBounds(612, 386, 164, 48);
        getContentPane().add(lblCity6);

        JButton btnEditCity1 = new JButton("Edit City");
        btnEditCity1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    successFlag = new UserManagement().editFavorites(tempUser, '1');
                    if (successFlag) {
                        new UserCustomizedScreen(tempUser);
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(new JFrame(), exception.getMessage(), "Dialog", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnEditCity1.setBounds(34, 181, 164, 55);
        getContentPane().add(btnEditCity1);

        JButton btnEditCity2 = new JButton("Edit City");
        btnEditCity2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    successFlag = new UserManagement().editFavorites(tempUser, '2');
                    if (successFlag) {
                        new UserCustomizedScreen(tempUser);
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

        });
        btnEditCity2.setBounds(297, 181, 164, 55);
        getContentPane().add(btnEditCity2);

        JButton btnEditCity3 = new JButton("Edit City");
        btnEditCity3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    successFlag = new UserManagement().editFavorites(tempUser, '3');
                    if (successFlag) {
                        new UserCustomizedScreen(tempUser);
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        btnEditCity3.setBounds(575, 181, 164, 55);
        getContentPane().add(btnEditCity3);

        JButton btnEditCity6 = new JButton("Edit City");
        btnEditCity6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    successFlag = new UserManagement().editFavorites(tempUser, '6');
                    if (successFlag) {
                        new UserCustomizedScreen(tempUser);
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

        });
        btnEditCity6.setBounds(575, 467, 164, 55);
        getContentPane().add(btnEditCity6);

        JButton btnEditCity5 = new JButton("Edit City");
        btnEditCity5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    successFlag = new UserManagement().editFavorites(tempUser, '5');
                    if (successFlag) {
                        new UserCustomizedScreen(tempUser);
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        btnEditCity5.setBounds(284, 467, 164, 55);
        getContentPane().add(btnEditCity5);

        JButton btnEditCity4 = new JButton("Edit City");
        btnEditCity4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    successFlag = new UserManagement().editFavorites(tempUser, '4');
                    if (successFlag) {
                        new UserCustomizedScreen(tempUser);
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        btnEditCity4.setBounds(34, 467, 164, 55);
        getContentPane().add(btnEditCity4);

        JLabel lblWelcomeUsername = new JLabel("Welcome " + tempUser.getUsername());
        lblWelcomeUsername.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        lblWelcomeUsername.setBounds(281, 0, 197, 63);
        getContentPane().add(lblWelcomeUsername);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
                new UserFavoritesView(tempUser);
            }
        });
        btnRefresh.setBounds(537, 0, 239, 78);
        getContentPane().add(btnRefresh);
        this.setVisible(true);


    }

    public void setCurrentFavoritesArr(String[] currentFavoritesArr) {
        this.currentFavoritesArr = currentFavoritesArr;
    }
}
