package com.T_Y.view;

import com.T_Y.model.ForecastResult;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

////////////NOT IN USE ATM//////////////////

public class ForcastResultView extends JFrame {

    /**
     * @wbp.nonvisual location=128,149
     */
    private final ImageIcon imageIcon = new ImageIcon();
    private JPanel contentPane;

    /**
     * Launch the application.
     */

    public ForcastResultView(ForecastResult result) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 799, 596);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
        rigidArea.setBounds(0, 0, 785, 559);
        contentPane.add(rigidArea);

        JList featuresList = new JList();
        featuresList.setModel(new AbstractListModel() {
            String[] values = new String[] { "Waves Height", "Humidity Level", "Wind Speed" };

            public int getSize() {
                return values.length;
            }

            public Object getElementAt(int index) {
                return values[index];
            }
        });
        featuresList.setFont(new Font("Tahoma", Font.PLAIN, 27));
        featuresList.setBounds(10, 297, 271, 184);
        contentPane.add(featuresList);

        JLabel lblHeadline = new JLabel("Headline:");
        lblHeadline.setFont(new Font("Tahoma", Font.PLAIN, 24));
        lblHeadline.setBounds(509, 11, 152, 66);
        contentPane.add(lblHeadline);

        JLabel lblHeadlineText = new JLabel("");
        lblHeadlineText.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblHeadlineText.setBounds(407, 68, 340, 152);
        contentPane.add(lblHeadlineText);

        JLabel lblMinTemperature = new JLabel("MIN:");
        lblMinTemperature.setFont(new Font("Tahoma", Font.PLAIN, 24));
        lblMinTemperature.setBounds(427, 297, 64, 95);
        contentPane.add(lblMinTemperature);

        JLabel lblMaxTemperature = new JLabel("Max");
        lblMaxTemperature.setFont(new Font("Tahoma", Font.PLAIN, 24));
        lblMaxTemperature.setBounds(654, 311, 53, 66);
        contentPane.add(lblMaxTemperature);

        JLabel lblMinTemperaturetext = new JLabel(result.getTemperature());
        lblMinTemperaturetext.setFont(new Font("Tahoma", Font.PLAIN, 24));
        lblMinTemperaturetext.setBounds(407, 426, 109, 66);
        contentPane.add(lblMinTemperaturetext);

    }


}
