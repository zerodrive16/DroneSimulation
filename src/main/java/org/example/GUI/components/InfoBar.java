package org.example.GUI.components;

import org.example.GUI.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import static org.example.GUI.GUI.createDashboard;
import static org.example.GUI.GUI.quickSet;
/**
 * The InfoBar class represents the information bar displayed at the top of the GUI.
 * It includes a refresh timer, a label showing time since last update, and a group information label.
 */
public class InfoBar extends Abs_GUIComponents{

    private JPanel infobar = new JPanel();

    /**
     * Constructs an InfoBar object.
     *
     * @param frame The main JFrame of the application.
     */
    public InfoBar(JFrame frame) {
        JLabel refreshText = new JLabel("since Last Update: X Seconds     ");
        JLabel groupText = new JLabel(" This program was made by Group15");

        //Timer keeps track of last click on update button.
        Timer refreshTimer = new Timer(1000, new ActionListener() {
            int seconds = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Objects.equals(refreshText.getText(), "since Last Update: 0 Seconds     ")) {
                    seconds = 0;
                }
                seconds++;
                refreshText.setText("since Last Update: "+seconds+" Seconds     ");
            }}
        );
        refreshTimer.start();

        infobar.setLayout(new BoxLayout(infobar, BoxLayout.X_AXIS));
        infobar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        infobar.setBackground(backgroundColor);
        infobar.setPreferredSize(new Dimension(width,height/32));
        JLabel refreshLabel = new JLabel(" REFRESH");
        JButton refreshButton = new JButton();
        refreshButton.add(refreshLabel);
        refreshButton.setBackground(buttonColor);
        refreshButton.setOpaque(true);
        refreshButton.setPreferredSize(new Dimension(width/16,(height/32)-8));
        refreshButton.setBorderPainted(false);
        refreshButton.setFocusPainted(false);

        //Function to update the data.
        refreshButton.addActionListener(e->{
            SwingUtilities.updateComponentTreeUI(frame);
            refreshTimer.stop();
            GUI.getTopPanel().removeAll();
            GUI.getBottomPanel().removeAll();
            createDashboard(mainColor, GUI.getTopPanel(), GUI.getBottomPanel());
            refreshText.setText("since Last Update: 0 Seconds     ");
            refreshTimer.start();
            frame.revalidate();
            frame.repaint();
        });

        quickSet(textFont,Color.WHITE,backgroundColor,refreshText,refreshLabel,groupText);
        infobar.add(groupText);
        infobar.add(Box.createHorizontalGlue()); //align right side
        infobar.add(refreshText);
        infobar.add(refreshButton);
    }

    /**
     * Gets the JPanel representing the infobar.
     *
     * @return The JPanel representing the infobar.
     */
    public JPanel getInfobar(){
        return infobar;
    }
}
