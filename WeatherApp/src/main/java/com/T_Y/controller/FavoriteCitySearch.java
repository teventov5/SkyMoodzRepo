package com.T_Y.controller;

import com.T_Y.model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class FavoriteCitySearch extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField cityTextInput;
    private City tempCT;
    private JLabel lblSuccessFlag;
    private boolean DBsuccessFlag;
    private boolean ctAlreadyExist=false;

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
    public FavoriteCitySearch(User tempUser, char index) throws ArithmeticException {

        setBounds(100, 100, 456, 213);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        cityTextInput = new JTextField();
        cityTextInput.setBounds(171, 23, 144, 46);
        contentPanel.add(cityTextInput);
        cityTextInput.setColumns(10);
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setBounds(0, 130, 436, 33);
            contentPanel.add(buttonPane);
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            {
                JButton applyButton = new JButton("Apply");
                applyButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) throws ArithmeticException {
                        try {
                            if (lblSuccessFlag.getForeground() == Color.red) {
                                throw new ArithmeticException("cant apply without pressing the search button first");
                            }
                        } catch (ArithmeticException e1) {
                            JOptionPane.showMessageDialog(new JFrame(), e1.getMessage(), "Dialog", JOptionPane.ERROR_MESSAGE);
                            return;

                        }
                        String[] currFavoriteArr = tempUser.getFavoritesArr();
                        for (int i = 1; i < currFavoriteArr.length; i++) {
                            if (currFavoriteArr[i].equalsIgnoreCase(tempCT.getCityName())) {
                                JOptionPane.showMessageDialog(null, "city already exists for username " + tempUser.getUsername(), "Dialog", JOptionPane.ERROR_MESSAGE);
                                ctAlreadyExist=true;
                                break;
                            }
                        }
                        if(ctAlreadyExist==false)
                        try {
                            if(new UsersDB().editUserDbFavorites(tempUser, index, tempCT));
                            JOptionPane.showMessageDialog(new JFrame(), "successful favorite city update", "Dialog", JOptionPane.INFORMATION_MESSAGE);

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
            }
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
        }

        JLabel lblCity = new JLabel("type the new city here");
        lblCity.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblCity.setBounds(10, 19, 161, 50);
        contentPanel.add(lblCity);

        lblSuccessFlag = new JLabel("city not found");
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
                    lblSuccessFlag.setText("city not found");
                    tempCT = new City(cityTextInput.getText(), "0000");


                    City cityUpdated=new CitySearch().FavoriteCityCodeSearch(tempCT);
                        if (cityUpdated!=null)
                            {
                            lblSuccessFlag.setText("city found!");
                            lblSuccessFlag.setForeground(Color.blue);
                            }
                        else
                            JOptionPane.showMessageDialog(null, "Problem finding your city", "Dialog", JOptionPane.ERROR_MESSAGE);

                } catch (Exception el) {
                    el.printStackTrace();
                }
            }
        });
        btnSearch.setBounds(326, 29, 93, 34);
        contentPanel.add(btnSearch);
    }
}
