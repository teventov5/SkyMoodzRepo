package com.T_Y.view;

import com.T_Y.controller.UserManagement;
import com.T_Y.model.User;
import com.T_Y.model.UsersDB;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewPasswordSelectionView extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JPasswordField passwordText;
    private JPasswordField password2Text;
    private JFrame error;

    /**
     * Create the dialog.
     */
    public NewPasswordSelectionView(User tempUser) {
        setVisible(true);
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblDescription = new JLabel("please enter your new password");
        lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblDescription.setBounds(10, 11, 373, 39);
        contentPanel.add(lblDescription);

        passwordText = new JPasswordField();
        passwordText.setBounds(135, 154, 96, 30);
        contentPanel.add(passwordText);
        passwordText.setColumns(10);


        JLabel lblPassword1 = new JLabel("New password:");
        lblPassword1.setBounds(10, 154, 125, 14);
        contentPanel.add(lblPassword1);

        JLabel lblUsername = new JLabel("username: " + tempUser.getUsername());
        lblUsername.setBounds(10, 84, 159, 14);
        contentPanel.add(lblUsername);

        password2Text = new JPasswordField();
        password2Text.setColumns(10);
        password2Text.setBounds(135, 188, 96, 30);
        contentPanel.add(password2Text);

        JLabel lblConfirmPassword = new JLabel("Confirm password");
        lblConfirmPassword.setBounds(10, 188, 115, 14);
        contentPanel.add(lblConfirmPassword);
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton submitButton = new JButton("Submit");
                submitButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        try {
                            String password1 = passwordText.getText();
                            String password2 = password2Text.getText();
                            passwordCheck(password1, password2);
                           boolean updateSucceeded= new UsersDB().updateUserPasswordToDB(tempUser, password1);
                           if(updateSucceeded)
                           {
                               JOptionPane.showMessageDialog(error, "Password update succeeded", "Dialog", JOptionPane.INFORMATION_MESSAGE);
                               dispose();
                           }

                        } catch (Exception e1) {
                            JOptionPane.showMessageDialog(new JFrame(), e1.getMessage(), "Dialog", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                submitButton.setActionCommand("OK");
                buttonPane.add(submitButton);
                getRootPane().setDefaultButton(submitButton);
            }
            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        dispose();
                    }
                });
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
        }
    }


    /**
     public static void main(String[] args) {
     try {
     newPasswordSelectionView dialog = new newPasswordSelectionView();
     dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
     dialog.setVisible(true);
     } catch (Exception e) {
     e.printStackTrace();
     }
     }     */

    private void passwordCheck(String password1, String password2) {
        if (!password1.equalsIgnoreCase(password2)) {
            throw new ArithmeticException("confirmation password mismatch");
        }
        if (!new UserManagement().isPasswordValid(password1)) {
            throw new ArithmeticException("password doesn't meet security requirements");
        }

    }
}
