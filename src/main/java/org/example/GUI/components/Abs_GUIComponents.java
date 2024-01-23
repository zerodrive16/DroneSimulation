package org.example.GUI.components;

import javax.swing.*;
import java.awt.*;

public abstract class Abs_GUIComponents {
    static protected Color mainScreenColor = new Color(44,44,44); //dark Gray
    protected Color backgroundColor = new Color(22,22,22); //even darker Gray
    protected Color panelColor = new Color(66,66,66); //light Gray
    protected Font textFont = new Font("Roboto", Font.PLAIN,12);
    protected Font titleFont = new Font("Roboto", Font.PLAIN,26);
    static protected ImageIcon clipboard = new ImageIcon("src/main/java/org/example/GUI/img/CopyClipboard.png");
    protected int width = 1600;
    protected int height = 900;
}
