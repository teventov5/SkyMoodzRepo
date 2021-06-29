package com.T_Y.view;

import com.T_Y.controller.UserManagement;
import com.T_Y.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ForgotPasswordView extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField usernameText;
    private boolean isRealUser = false;

    /**
     * Create the dialog.
     */
    public ForgotPasswordView() {
        setVisible(true);
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        {
            JLabel lblUsername = new JLabel("enter username here:");
            lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
            lblUsername.setBounds(10, 39, 170, 126);
            contentPanel.add(lblUsername);
        }
        {
            usernameText = new JTextField();
            usernameText.setBounds(180, 75, 170, 59);
            contentPanel.add(usernameText);
            usernameText.setColumns(10);
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton nextButton = new JButton("Next");
                nextButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String usernameInput = usernameText.getText();
                        try {
                            User tempUser = new User(usernameInput, "1234");
                            User tempUser2 = new UserManagement().resetUserPassword(tempUser);
                            if (tempUser2!=null) {
                                dispose();
                                new ForgotPasswordView2(tempUser2);
                            } else {
                                JOptionPane.showMessageDialog(new JFrame(), "No such username", "Dialog", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (Exception e1) {
                           e1.printStackTrace();;
                        }

                    }
                });
                nextButton.setActionCommand("Next");
                buttonPane.add(nextButton);
                getRootPane().setDefaultButton(nextButton);
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
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            ForgotPasswordView dialog = new ForgotPasswordView();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
