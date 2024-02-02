package org.example.GUI.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Objects;

/**
 * This abstract class sets the color and font for InfoBar, MainScreen, NavBar and the frame.
 */
public abstract class Abs_GUIComponents {
    static protected Color mainColor = new Color(44,44,44); //dark Gray
    static protected Color backgroundColor = new Color(22,22,22); //even darker Gray
    static protected Color buttonColor = new Color(66,66,66); //light Gray
    protected Font textFont = new Font("Roboto", Font.PLAIN,12);
    protected Font titleFont = new Font("Roboto", Font.PLAIN,26);
    protected int width = 1600;
    protected int height = 900;
}
