package org.example.GUI;

import org.example.GUI.components.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;



public class GUI extends Abs_GUIComponents{
JFrame frame = new JFrame();

    public GUI(){
        initUI();
        windowSettings();
    }

    public void initUI() {
        frame.add(navbar.getNavbar(), BorderLayout.NORTH);
        frame.add(mainscreen.getMainScreen(),BorderLayout.CENTER);
        frame.add(infobar.getInfobar(), BorderLayout.SOUTH);
    }

    public static void createDashboard(Color mainScreenColor, JPanel topPanel, JPanel bottomPanel) {
        CallAPIData dashboard = new CallAPIData();
        dashboard.fetchAPIData(mainScreenColor, topPanel, bottomPanel);
    }


    //function to set font, font color and background color
    protected static void quickSet(Font font, Color color1, Color color2, JComponent... components){
        for (JComponent component : components) {
            component.setFont(font);
            component.setForeground(color1);
            component.setBackground(color2);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}
