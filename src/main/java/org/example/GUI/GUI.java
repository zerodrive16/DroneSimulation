package org.example.GUI;

import org.example.GUI.components.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;



public class GUI extends Abs_GUIComponents{

    JFrame frame = new JFrame();
    static MainScreen mainscreen = new MainScreen();
    InfoBar infobar = new InfoBar(frame);
    NavBar navbar = new NavBar();

    public void windowSettings(){
        frame.setTitle("Drone Simulation");
        frame.setLocation(100,100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width,height);
        frame.setLocationRelativeTo(null);
        frame.setBackground(mainScreenColor);
        frame.setMinimumSize(new Dimension(1024,720));
        frame.setVisible(true);

        frame.addComponentListener(new ComponentAdapter() {
            //----------------------------code for resizing the window-------------------------------
            @Override
            public void componentResized(ComponentEvent e) {
                width = frame.getWidth();
                height = frame.getHeight();
            }
        });
    }

    public GUI(){
        initUI();
        windowSettings();
    }

    private void initUI() {
        frame.add(navbar.getNavbar(), BorderLayout.NORTH);
        frame.add(mainscreen.getMainScreen(),BorderLayout.CENTER);
        frame.add(infobar.getInfobar(), BorderLayout.SOUTH);
    }

    public static void createDashboard(Color mainScreenColor, JPanel topPanel, JPanel bottomPanel) {
        CallAPIData dashboard = new CallAPIData();
        dashboard.fetchAPIData(mainScreenColor, topPanel, bottomPanel);
    }

    //getter
    static public JPanel getTopPanel(){
        return mainscreen.getTopPanel();
    }

    static public JPanel getBottomPanel(){
        return mainscreen.getBottomPanel();
    }

    //function to set font, font color and background color
    public static void quickSet(Font font, Color color1, Color color2, JComponent... components){
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
