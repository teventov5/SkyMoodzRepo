package com.T_Y.view;

import com.T_Y.controller.UserManagement;
import com.T_Y.model.DateLabelFormatter;
import com.T_Y.model.User;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

public class RegistrationWindow extends JFrame {

    private JPanel contentPane;
    private JTextField idField;
    private JTextField nameField;
    private JTextField dateField;
    private JPasswordField pwdField;
    private boolean registrationSucceed;
    JDatePickerImpl datePicker;


    /**
     * Create the frame.
     */
    public RegistrationWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 390, 443);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idInput,nameInput;
                char[] pwdInput;

                idInput=idField.getText();
                pwdInput = pwdField.getText().toCharArray();
                nameInput=nameField.getText();
                dateField = datePicker.getJFormattedTextField();
                try {
                    User tempUser = new User(idInput, nameInput,pwdInput,dateField.getText());

                    registrationSucceed= new UserManagement().registerUser(tempUser);
                    if(registrationSucceed) {
                        new UserCustomizedScreen(tempUser);
                        setVisible(false);
                    }

                } catch (Exception e1) {
                   e1.printStackTrace();;
                }
            }
        });
        btnSubmit.setBounds(0, 304, 376, 107);
        contentPane.add(btnSubmit);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblUsername.setBounds(10, 14, 96, 14);
        contentPane.add(lblUsername);

        idField = new JTextField();
        idField.setColumns(10);
        idField.setBounds(160, 14, 148, 30);
        contentPane.add(idField);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblPassword.setBounds(10, 54, 89, 14);
        contentPane.add(lblPassword);

        pwdField = new JPasswordField();
        pwdField.setColumns(10);
        pwdField.setBounds(160, 56, 148, 30);
        contentPane.add(pwdField);

        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblName.setBounds(10, 110, 96, 14);
        contentPane.add(lblName);

        nameField = new JTextField();
        nameField.setColumns(10);
        nameField.setBounds(160, 100, 148, 30);
        contentPane.add(nameField);


        JLabel lblDob = new JLabel("Date of birth:");
        lblDob.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblDob.setBounds(10, 150, 96, 14);
        contentPane.add(lblDob);

        //Date picker for date of birth
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");


        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.setBounds(160, 150, 148, 30);
        contentPane.add(datePicker);
        JButton btnWelcomeScreen = new JButton("Press if you want to go back to welcome screen");
        btnWelcomeScreen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new WelcomeWindow();
                dispose();

            }
        });
        btnWelcomeScreen.setBounds(30, 207, 300, 50);
        contentPane.add(btnWelcomeScreen);




    }
}
