package org.example.GUI.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.example.GUI.GUI.quickSet;


/**
 * The NavBar class represents the navigation bar at the top of the GUI.
 * It includes elements such as dashboard label and a clock displaying current time and date.
 */
public class NavBar extends Abs_GUIComponents{
    private JPanel navbar = new JPanel(new BorderLayout());
    private JPanel tab1 = new JPanel();

    /**
     * Constructs a NavBar object.
     */
    public NavBar() {
            navbar.setBackground(backgroundColor);
            navbar.setPreferredSize(new Dimension(width,(height/12)));
            JLabel dashboardLabel = new JLabel(" Dashboard");
            navbar.add(dashboardLabel,BorderLayout.WEST);

            JPanel tabsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            tabsPanel.setBackground(backgroundColor);
            tabsPanel.setPreferredSize(new Dimension((width/4)-10,(height/18)-10));
            tab1.setBackground(mainScreenColor);
            tab1.setPreferredSize(new Dimension((width/4)-10,(height/18)-10));
            tabsPanel.add(tab1);
            navbar.add(tabsPanel,BorderLayout.SOUTH);


            JTextArea time = new JTextArea("HH:mm"+"      "+"dd.MM.yyyy");
            time.setEditable(false);
            quickSet(titleFont,Color.white,backgroundColor,time,dashboardLabel);

            navbar.add(time,BorderLayout.EAST);

            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
            Timer timeAndDate = new Timer(1000, new ActionListener(){
                LocalDate dateNow = LocalDate.now();
                LocalTime timeNow = LocalTime.now();
                int minute = 0;
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(minute != LocalTime.now().getMinute()) {
                        timeNow = LocalTime.now();
                        dateNow = LocalDate.now();
                        String outputTime = timeNow.format(timeFormat);
                        String outputDate = dateNow.format(dateFormat);
                        time.setText(outputTime+"     "+outputDate);
                    }
                }}
            );
            timeAndDate.start();
    }

    /**
     * Getter function for accessing the navbar from other classes.
     *
     * @return The JPanel representing the navbar.
     */
    public JPanel getNavbar(){
        return navbar;
    }

}

