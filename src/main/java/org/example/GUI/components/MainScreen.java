package org.example.GUI.components;

import javax.swing.*;
import java.awt.*;
import static org.example.GUI.GUI.createDashboard;
/**
 * The MainScreen class represents the main screen of the GUI.
 * It contains panels for displaying the dashboard and bottom controls.
 */
public class MainScreen extends Abs_GUIComponents{

    private final JPanel mainScreen = new JPanel();
    private final JPanel bottomPanel = new JPanel();
    private final JPanel topPanel = new JPanel();

    /**
     * Constructs a MainScreen object.
     */
    public MainScreen() {
        CardLayout cardLayout = new CardLayout();
        mainScreen.setLayout(cardLayout);

        JPanel splitScreen = new JPanel();
        splitScreen.setLayout(new BoxLayout(splitScreen, BoxLayout.Y_AXIS));
        mainScreen.add(splitScreen, "Dashboard");

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        createDashboard(mainScreenColor, topPanel, bottomPanel);

        topPanel.setPreferredSize(new Dimension(Short.MAX_VALUE, (int) (height * 0.96)));
        topPanel.setBackground(mainScreenColor);
        splitScreen.add(topPanel);

        bottomPanel.setPreferredSize(new Dimension(Short.MAX_VALUE, (int) (height * 0.04)));
        bottomPanel.setBackground(mainScreenColor);
        splitScreen.add(bottomPanel);

    }

    /**
     * Gets the JPanel representing the main screen.
     *
     * @return The JPanel representing the main screen.
     */
    public JPanel getMainScreen(){
        return mainScreen;
    }

    /**
     * Gets the JPanel representing the top panel of the main screen.
     *
     * @return The JPanel representing the top panel of the main screen.
     */
    public JPanel getTopPanel(){
        return topPanel;
    }

    /**
     * Gets the JPanel representing the bottom panel of the main screen.
     *
     * @return The JPanel representing the bottom panel of the main screen.
     */
    public JPanel getBottomPanel(){
        return bottomPanel;
    }
}
