package org.example.GUI.components;

import javax.swing.*;
import java.awt.*;
import static org.example.GUI.GUI.createDashboard;

public class MainScreen extends Abs_GUIComponents{

    private final JPanel mainScreen = new JPanel();
    private final JPanel bottomPanel = new JPanel();
    private final JPanel topPanel = new JPanel();

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

    public JPanel getMainScreen(){
        return mainScreen;
    }

    public JPanel getTopPanel(){
        return topPanel;
    }

    public JPanel getBottomPanel(){
        return bottomPanel;
    }
}
