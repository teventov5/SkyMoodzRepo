
package com.T_Y.view;

import com.T_Y.model.ForecastResult;
import com.T_Y.model.HangoutsResult;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HangoutDialogView extends JDialog {

    JLabel lblRatingText;
    private HangoutsResult[] allHangouts;
    private HangoutsResult specificHangout;
    private JLabel lblHeadlineText;
    private BufferedImage image;
    private ImageIcon icon;


    /**
     * Create the dialog.
     */
    public HangoutDialogView(ForecastResult ctRes) {
        setVisible(true);
        JLabel lblIcon = new JLabel("");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 435, 268);
        getContentPane().setLayout(null);
        allHangouts = ctRes.getHangoutsResultsArr();
        JList hangoutList = new JList();
        hangoutList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int index = hangoutList.getAnchorSelectionIndex();
                specificHangout = allHangouts[index];
                lblHeadlineText.setText(specificHangout.getHeadline());
                lblRatingText.setText(specificHangout.getRating());
                try {
                    double value;
                    value=Double.parseDouble(specificHangout.getRating());
                    if(value>=6)
                        image = ImageIO.read(new File("images\\" + "outside" + ".JPG"));

                    else
                        image = ImageIO.read(new File("images\\" + "home" + ".JPG"));
                    icon = new ImageIcon(image);
                    lblIcon.setIcon(icon);
                    lblIcon.setBounds(270, 137, 100, 86);
                    add(lblIcon);
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        });
        hangoutList.setModel(new AbstractListModel() {
            String[] values;

            {
                values = new String[] { "Flight delays", "Running", "Hiking", "Bicycle", "Golf", "Tennis", "Star gazing", "Outdoor concert", "Kite flying", "Beach and pool", "Sailing", "Ski"};

            }

            public int getSize() {
                return values.length;
            }

            public Object getElementAt(int index) {
                return values[index];
            }
        });
        hangoutList.setBounds(10, 11, 190, 219);
        getContentPane().add(hangoutList);

        JLabel lblHeadline = new JLabel("Headline:");
        lblHeadline.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        lblHeadline.setBounds(210, 35, 83, 42);
        getContentPane().add(lblHeadline);

        lblHeadlineText = new JLabel("");
        lblHeadlineText.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        lblHeadlineText.setBounds(295, 35, 116, 42);
        getContentPane().add(lblHeadlineText);

        lblRatingText = new JLabel("");
        lblRatingText.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        lblRatingText.setBounds(276, 88, 56, 42);
        getContentPane().add(lblRatingText);

        JLabel lblRatingDesc = new JLabel("Rating:");
        lblRatingDesc.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblRatingDesc.setBounds(210, 88, 62, 42);
        getContentPane().add(lblRatingDesc);

    }
}
