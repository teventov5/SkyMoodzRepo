package com.T_Y.view;

import com.T_Y.controller.UserManagement;
import com.T_Y.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationWindow extends JFrame {

    private JPanel contentPane;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private boolean registrationSucceed;
    private JTextField secretQuestionField;
    private JTextField secretAnswerField;


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
                String usernameInput, passwordInput, secretQuestionInput, secretAnswerInput;
                usernameInput = usernameField.getText();
                passwordInput = passwordField.getText();
                secretQuestionInput = secretQuestionField.getText();
                secretAnswerInput = secretAnswerField.getText();
                try {
                    User tempUser = new User(usernameInput, passwordInput, secretQuestionInput, secretAnswerInput);

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

        usernameField = new JTextField();
        usernameField.setColumns(10);
        usernameField.setBounds(160, 14, 148, 30);
        contentPane.add(usernameField);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblPassword.setBounds(10, 54, 89, 14);
        contentPane.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setColumns(10);
        passwordField.setBounds(160, 56, 148, 30);
        contentPane.add(passwordField);

        JButton btnWelcomeScreen = new JButton("Press if you want to go back to welcome screen");
        btnWelcomeScreen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new WelcomeWindow();
                dispose();

            }
        });
        btnWelcomeScreen.setBounds(30, 207, 300, 50);
        contentPane.add(btnWelcomeScreen);

        secretQuestionField = new JTextField();
        secretQuestionField.setColumns(10);
        secretQuestionField.setBounds(160, 103, 148, 30);
        contentPane.add(secretQuestionField);

        JLabel lblSecretQuestion = new JLabel("Secret Question:");
        lblSecretQuestion.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblSecretQuestion.setBounds(10, 101, 118, 14);
        contentPane.add(lblSecretQuestion);

        secretAnswerField = new JTextField();
        secretAnswerField.setColumns(10);
        secretAnswerField.setBounds(160, 141, 148, 30);
        contentPane.add(secretAnswerField);

        JLabel lblSecretAnswer = new JLabel("Secret Answer:");
        lblSecretAnswer.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblSecretAnswer.setBounds(10, 142, 118, 14);
        contentPane.add(lblSecretAnswer);
    }
}
