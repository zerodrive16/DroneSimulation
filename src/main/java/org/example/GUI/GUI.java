package org.example.GUI;

import org.example.GUI.components.Abs_GUIComponents;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.time.format.DateTimeFormatter;
import java.time.*;
import java.util.Objects;


public class GUI extends Abs_GUIComponents{
JFrame frame = new JFrame();

    public GUI(){
        initUI();
        windowSettings();
    }

    private void initUI() {
        mainScreenSettings();
        navbarSettings();
        infobarSettings();
        addingComponents();
    }

    private void mainScreenSettings() {
        //mainScreen.setPreferredSize(new Dimension(width,height-(height/12)));
        mainScreen.setLayout(cardLayout);
        configureCard1(mainScreenColor, card1);
        card1.setBackground(mainScreenColor);

        JScrollPane scrollPane = new JScrollPane(card1);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        mainScreen.add(scrollPane, "Dashboard");
    }

    public void configureCard1(Color mainScreenColor, JPanel card1) {
        Dashboard card = new Dashboard();
        card.configureCard1(mainScreenColor, card1);
    }

    private void navbarSettings() {
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

    private void infobarSettings() {
        JLabel refreshText = new JLabel("since Last Update: X Seconds     ");
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
        refreshButton.setBackground(Color.WHITE);
        refreshButton.setOpaque(true);
        refreshButton.setPreferredSize(new Dimension(width/16,(height/32)-8));
        refreshButton.setBorderPainted(false);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e->{
            refreshTimer.restart();
            refreshText.setText("since Last Update: 0 Seconds     ");
            /*
            UPDATE FUNKTION
             */
        });

        quickSet(textFont,Color.WHITE,backgroundColor,refreshText);
        infobar.add(Box.createHorizontalGlue()); //align right side
        infobar.add(refreshText);
        infobar.add(refreshButton);
    }

    private void addingComponents() {
        frame.add(navbar, BorderLayout.NORTH);
        frame.add(mainScreen,BorderLayout.CENTER);
        frame.add(infobar, BorderLayout.SOUTH);
    }

    private void windowSettings(){
        frame.setTitle("Drone Simulation");
        frame.setLocation(100,100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width,height);
        frame.setVisible(true);
        frame.setExtendedState(frame.getExtendedState() | Frame.ICONIFIED); // minimize window
        frame.setBackground(mainScreenColor);
        frame.setMinimumSize(new Dimension(1280,720));

        frame.addComponentListener(new ComponentAdapter() {
            //----------------------------code for resizing the window-------------------------------
            @Override
            public void componentResized(ComponentEvent e) {
            width = frame.getWidth();
            height = frame.getHeight();
            card1.setPreferredSize(new Dimension(width,height*(1600/width)));
            }
        });

    }


    //function to set font, font color and background color
    protected static void quickSet(Font font, Color color1, Color color2, JComponent... components){
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
