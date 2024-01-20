package org.example.GUI.components;

import javax.swing.*;
import java.awt.*;

public abstract class Abs_GUIComponents {
    public Color mainScreenColor = new Color(44,44,44); //dark Gray
    public Color backgroundColor = new Color(22,22,22); //even darker Gray
    public Color panelColor = new Color(66,66,66); //light Gray
    public Color greytabColor = new Color(32,32,32); // darkest Gray
    public Font textFont = new Font("Roboto", Font.PLAIN,18);
    public Font titleFont = new Font("Roboto", Font.PLAIN,26);


    public JPanel navbar = new JPanel(new BorderLayout());
    public JPanel mainScreen = new JPanel();
    public JPanel infobar = new JPanel();


    public CardLayout cardLayout = new CardLayout();

    public JPanel card1 = new JPanel();

    public JPanel tab1 = new JPanel();

    public JButton refreshButton = new JButton("REFRESH");
    public JButton dropdownButton = new JButton();


    public int width = 1600;
    public int height = 900;
    public double aspectRatio = (double) width /height;

    public Dimension navbarSize = new Dimension(width,(height/12));
    public Dimension navbarButtonSize = new Dimension((width/4)-10,(height/18)-10);
    public Dimension mainscreenSize = new Dimension(width,height-(navbarSize.height));
    public Dimension infobarSize = new Dimension(width,height/32);
}
