package org.example.GUI.components;

import org.example.GUI.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import static org.example.GUI.GUI.createDashboard;
import static org.example.GUI.GUI.quickSet;

public class InfoBar extends Abs_GUIComponents{

    private JPanel infobar = new JPanel();

    public InfoBar(JFrame frame) {
        JLabel refreshText = new JLabel("since Last Update: X Seconds     ");
        JLabel groupText = new JLabel(" This program was made by Group15");

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
        refreshButton.setBackground(panelColor);
        refreshButton.setOpaque(true);
        refreshButton.setPreferredSize(new Dimension(width/16,(height/32)-8));
        refreshButton.setBorderPainted(false);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e->{
            SwingUtilities.updateComponentTreeUI(frame);
            refreshTimer.stop();
            GUI.getTopPanel().removeAll();
            GUI.getBottomPanel().removeAll();
            createDashboard(mainScreenColor, GUI.getTopPanel(), GUI.getBottomPanel());
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
    public JPanel getInfobar(){
        return infobar;
    }
}
