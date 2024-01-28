package org.example.GUI;

import org.example.GUI.components.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

/**
 * The GUI class represents the graphical user interface of the drone simulation application.
 * It initializes and manages the main components of the GUI, such as the main screen, navigation bar
 * and information bar. It also provides methods for setting up the window settings, creating the dashboard,
 * and performing quick settings for UI components.
 */

public class GUI extends Abs_GUIComponents{

    private final JFrame frame = new JFrame();
    private static final MainScreen mainscreen = new MainScreen();
    private final InfoBar infobar = new InfoBar(frame);
    private final NavBar navbar = new NavBar();

    /**
     * Initializes the user interface by setting up the main components and window settings.
     */
    public GUI(){
        initUI();
        windowSettings();
    }

    /**
     * Initializes the user interface by adding main components to the frame.
     */
    private void initUI() {
        frame.add(navbar.getNavbar(), BorderLayout.NORTH);
        frame.add(mainscreen.getMainScreen(),BorderLayout.CENTER);
        frame.add(infobar.getInfobar(), BorderLayout.SOUTH);
    }

    /**
     * Sets up the window settings, such as title, location, size, and visibility.
     */
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

    /**
     * Creates the dashboard by fetching data from APIs and updating the main screen.
     *
     * @param mainScreenColor The background color for the main screen.
     * @param topPanel        The JPanel representing the top panel of the main screen.
     * @param bottomPanel     The JPanel representing the bottom panel of the main screen.
     */
    public static void createDashboard(Color mainScreenColor, JPanel topPanel, JPanel bottomPanel) {
        CallAPIData dashboard = new CallAPIData();
        dashboard.fetchAPIData(mainScreenColor, topPanel, bottomPanel);
    }

    /**
     * Retrieves the top panel of the main screen.
     *
     * @return The JPanel representing the top panel of the main screen.
     */
    static public JPanel getTopPanel(){
        return mainscreen.getTopPanel();
    }

    /**
     * Retrieves the bottom panel of the main screen.
     *
     * @return The JPanel representing the bottom panel of the main screen.
     */
    static public JPanel getBottomPanel(){
        return mainscreen.getBottomPanel();
    }

    /**
     * Sets the font, font color, and background color for the specified components.
     *
     * @param font       The font to be set for the components.
     * @param color1     The font color to be set for the components.
     * @param color2     The background color to be set for the components.
     * @param components The components to apply the settings to.
     */
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
