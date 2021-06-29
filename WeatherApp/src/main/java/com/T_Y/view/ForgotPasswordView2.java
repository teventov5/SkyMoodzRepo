package com.T_Y.view;

import com.T_Y.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ForgotPasswordView2 extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField secretAnswerText;

    /**
     public static void main(String[] args) {
     try {
     forgotPasswordView2 dialog = new forgotPasswordView2();
     dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
     dialog.setVisible(true);
     } catch (Exception e) {
     e.printStackTrace();
     }
     }     */


    /**
     * Create the dialog.
     */
    public ForgotPasswordView2(User tempUser) {
        setVisible(true);
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Secret Question:");
        lblNewLabel.setBounds(10, 86, 160, 14);
        contentPanel.add(lblNewLabel);

        secretAnswerText = new JTextField();
        secretAnswerText.setBounds(139, 133, 166, 30);
        contentPanel.add(secretAnswerText);
        secretAnswerText.setColumns(10);

        JLabel lblSecretQuestionText = new JLabel(tempUser.getSecretQuestion());
        lblSecretQuestionText.setBounds(119, 86, 160, 30);
        contentPanel.add(lblSecretQuestionText);

        JLabel lblSecretAnswer = new JLabel("Secret answer:");
        lblSecretAnswer.setBounds(10, 136, 130, 14);
        contentPanel.add(lblSecretAnswer);

        JLabel lblUsername = new JLabel("hello " + tempUser.getUsername() + " please answer your security question");
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblUsername.setBounds(10, 11, 416, 48);
        contentPanel.add(lblUsername);
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton submitButton = new JButton("Submit");
                submitButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        String secretAnswerInput = secretAnswerText.getText();
                        try {
                            if (secretAnswerInput.equalsIgnoreCase(tempUser.getSecretAnswer())) {
                                dispose();
                                new NewPasswordSelectionView(tempUser);
                            } else {
                                JOptionPane.showMessageDialog(new JFrame(), "Wrong secret answer!", "Dialog", JOptionPane.ERROR_MESSAGE);
                            }

                        } catch (ArithmeticException e1) {
                           e1.printStackTrace();;

                        }
                    }
                });
                submitButton.setActionCommand("OK");
                buttonPane.add(submitButton);
                getRootPane().setDefaultButton(submitButton);
            }
            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                });
            }
        }
    }
}
