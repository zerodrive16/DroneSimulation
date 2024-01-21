package org.example.GUI;

import org.example.API_Endpoints.DroneDynamics;
import org.example.API_Endpoints.DroneTypes;
import org.example.API_Endpoints.Drones;
import org.example.GUI.components.Abs_GUIComponents;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.time.format.DateTimeFormatter;
import java.time.*;
import java.util.Objects;


public class GUI extends Abs_GUIComponents implements ActionListener{
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

        mainScreen.setPreferredSize(mainscreenSize);
        mainScreen.setLayout(cardLayout);
        mainScreen.add(card1, "Card1");
        configureCard1(mainScreenColor, card1);

        card1.setBackground(mainScreenColor);

    }


    public void configureCard1(Color mainScreenColor, JPanel card1) {
        Card1 card = new Card1();
        card.configureCard1(mainScreenColor, card1);
    }

    private void navbarSettings() {
        navbar.setBackground(backgroundColor);
        navbar.setPreferredSize(navbarSize);

//----------------------------tabsPanel and the tabs-------------------------------
        JPanel tabsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tabsPanel.setBackground(backgroundColor);
        tabsPanel.setPreferredSize(new Dimension(2* navbarButtonSize.width,navbarButtonSize.height));

        tab1.setPreferredSize(navbarButtonSize);
        tab1.setBackground(mainScreenColor);

        tabsPanel.add(tab1);

        navbar.add(tabsPanel,BorderLayout.SOUTH);

//----------------------------time and date-------------------------------

        JTextArea time = new JTextArea("HH:mm"+"      "+"dd.MM.yyyy");
        quickSet(titleFont,Color.white,backgroundColor,time);

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
            }
        });

        timeAndDate.start();
    }

    //----------------------------info bar and refresh button-------------------------------
    //The infobar has a refresh button to refresh the data
    private void infobarSettings() {
        JLabel creatorText = new JLabel(">>this program was made by group15<< ");
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
            }
        });
        refreshTimer.start();

        infobar.setLayout(new BoxLayout(infobar, BoxLayout.X_AXIS));
        infobar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        infobar.setBackground(backgroundColor);
        infobar.setPreferredSize(infobarSize);
        refreshButton.setPreferredSize(new Dimension(infobarSize.width/16,infobarSize.height-8));
        refreshButton.setBackground(Color.GREEN);
        refreshButton.setBorderPainted(false);
        refreshButton.setFocusPainted(false);

        refreshButton.addActionListener(e->{
            refreshTimer.restart();
            refreshText.setText("since Last Update: 0 Seconds     ");

            /*



            UPDATE FUNKTION



             */

        });

        quickSet(new Font("Roboto",Font.PLAIN,12),Color.WHITE,backgroundColor,refreshText,creatorText);
        infobar.add(Box.createRigidArea(new Dimension((width/5)*2,infobarSize.height)));
        infobar.add(creatorText);
        infobar.add(Box.createHorizontalGlue());
        infobar.add(refreshText);
        infobar.add(refreshButton);
    }
//----------------------------add components-------------------------------

    private void addingComponents() {
        frame.add(navbar, BorderLayout.NORTH);
        frame.add(mainScreen,BorderLayout.CENTER);
        frame.add(infobar, BorderLayout.SOUTH);
    }

    //----------------------------windowsettings-------------------------------
    private void windowSettings(){
        frame.setTitle("Drone Simulation");
        frame.setLocation(100,100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width,height);
        frame.setVisible(true);
        frame.setBackground(mainScreenColor);
        frame.setMinimumSize(new Dimension(1024,576));

        frame.addComponentListener(new ComponentAdapter() {
            //----------------------------code for resizing the window-------------------------------
            @Override
            public void componentResized(ComponentEvent e) {
                if(width!=frame.getWidth()) {
                    int newWidth = frame.getWidth();
                    int newHeight = (int) (frame.getWidth() / aspectRatio);
                    Dimension newSize = new Dimension(newWidth,newHeight);
                    frame.setSize(newSize);
                }
                else if(height!=frame.getHeight()) {
                    int newHeight = frame.getHeight();
                    int newWidth = (int) (newHeight * aspectRatio);
                    Dimension newSize = new Dimension(newWidth,newHeight);
                    frame.setSize(newSize);
                }
                else{
                    int newWidth = frame.getWidth();
                    int newHeight = frame.getHeight();
                    Dimension newSize = new Dimension(newWidth,newHeight);
                    frame.setSize(newSize);
                }
                width = frame.getHeight();
                height = frame.getWidth();

//----------------------------code for resizing components-------------------------------
/*
navbar.setPreferredSize(new Dimension(navbarSize.width,Math.max((navbarSize.height),30)));
navbarButtonSize.height = Math.max(navbarButtonSize.height,20);
tab1.setPreferredSize(navbarButtonSize);
Dimension size = getSize();
int newSize = (int) (size.width / 50);
newSize = Math.max(newSize, 12);
b1.setFont(new Font(b1.getFont().getName(), Font.PLAIN, newSize));
 */
            }
        });
    }




    @Override
    public void actionPerformed(ActionEvent e) {

    }


    //quickSet is a function that quickly changes Font, Fontcolor and Backgroundcolor of the given components
    //it's used to change either of these properties for alot of components in one line
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
