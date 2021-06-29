package com.T_Y.driver;
import com.T_Y.view.LookAndFeel;
import com.T_Y.view.WelcomeWindow;

public class Main {
    public static void main(String[] args) {
        try {
            LookAndFeel.tweakPLAF();
            new WelcomeWindow();

        } catch (Exception e) {
           e.printStackTrace();;
        }


    }
}
