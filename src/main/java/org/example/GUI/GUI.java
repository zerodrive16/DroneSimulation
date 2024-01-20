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


public class GUI extends JFrame implements ActionListener {
    //First we initialize the components of the GUI globally, so they can be used in every function when needed

    //We use a monotone palette of grey tones to mimic a dark mode style. Our font of choice is Roboto.
    //We believe to have achieved a sleek modern look with these settings
    Color mainScreenColor = new Color(44,44,44); //dark Gray
    Color backgroundColor = new Color(22,22,22); //even darker Gray
    Color panelColor = new Color(66,66,66); //light Gray
    Color greytabColor = new Color(32,32,32); // darkest Gray
    Font textFont = new Font("Roboto", Font.PLAIN,18);
    Font titleFont = new Font("Roboto", Font.PLAIN,26);

    //Icons are initialized
    //These icons will be used to make our dashboard more appealing
    ImageIcon menuIcon = new ImageIcon("src/main/java/org/example/GUI/img/menuIcon.png");
    ImageIcon infoIcon = new ImageIcon("src/main/java/org/example/GUI/img/infoIcon.png");

    //Main Components are initialized
    //We chose to have a navbar, a main screen and an infobar
    //We initialize our navbar in BorderLayout.
    JPanel navbar = new JPanel(new BorderLayout());
    JPanel mainScreen = new JPanel();
    JPanel infobar = new JPanel();

    //We implemented the main screen as a cardLayout, so our program is able
    //to switch between two different screens easily
    CardLayout cardLayout = new CardLayout();

    //The first card contains the dashboard with drone data, drone type and drone dynamics
    JPanel card1 = new JPanel();
    JPanel droneData = new JPanel();

    JPanel droneType = new JPanel();

    JPanel droneDynamics = new JPanel();

    //The second card contains...
    JPanel card2 = new JPanel();

    //Buttons are initialized
    //We will have 4 buttons, 2 of which are for switching between the previous mentioned screens
    //1 more for refreshing the data and 1 for opening up a list of drones to access
    JButton tab1 = new JButton();
    JButton tab2 = new JButton();
    JButton refreshButton = new JButton("REFRESH");
    JButton dropdownButton = new JButton();

    //Variables are initialized
    //These variables are kept for convenience in arithmetic operations
    //We mainly used these to change the size of the components according to the window size
    private int width = 1600;
    private int height = 900;
    private double aspectRatio = (double) width /height;
    //sizes
    private Dimension navbarSize = new Dimension(width,(height/12));
    private Dimension navbarButtonSize = new Dimension((width/4)-10,(height/18)-10);
    private Dimension mainscreenSize = new Dimension(width,height-(navbarSize.height));
    private Dimension infobarSize = new Dimension(width,height/32);

    //Here is the GUI function. We made it abstract, so it's very easy to read what happens
    //First we initialize the UI components, and then we configure the window settings
    public GUI(){
        initUI();
        windowSettings();
    }

    //The initialization is a two-step process. First we prepare the main components, then we add them to the frame.
    //We split the settings for each main component for readability
    private void initUI() {
        mainScreenSettings();
        navbarSettings();
        infobarSettings();
        addingComponents();
    }


//----------------------------main screen settings-------------------------------

    private void mainScreenSettings() {

        mainScreen.setPreferredSize(mainscreenSize);
        mainScreen.setLayout(cardLayout);
        mainScreen.add(card1, "Card1");
        configureCard1(mainScreenColor, card1);

        card1.setBackground(mainScreenColor);

    }

    //----------------------------configureCard1-------------------------------
    //Recursive function to create the DroneList
    public void configureCard1(Color mainScreenColor, JPanel card1) {
        Card1 card = new Card1();
        card.configureCard1(mainScreenColor, card1);
    }


    //----------------------------navbar-------------------------------
    //The navbar contains the dropdownButton Menu, the Tabs and the Time
    private void navbarSettings() {
        navbar.setBackground(backgroundColor);
        navbar.setPreferredSize(navbarSize);

//----------------------------tabsPanel and the tabs-------------------------------
        JPanel tabsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tabsPanel.setBackground(backgroundColor);
        tabsPanel.setPreferredSize(new Dimension(2* navbarButtonSize.width,navbarButtonSize.height));

        tab1.setPreferredSize(navbarButtonSize);
        tab1.setBorderPainted(false);
        tab1.setBackground(Color.GREEN);
        tab1.addActionListener(e-> {
            cardLayout.show(mainScreen,"Card1");
            tab2.setBackground(Color.RED);
            tab1.setBackground(Color.GREEN);
        });

        tab2.setPreferredSize(navbarButtonSize);
        tab2.setBorderPainted(false);
        tab2.setBackground(Color.RED);
        tab2.addActionListener(e-> {
            cardLayout.show(mainScreen,"Card2");
            tab1.setBackground(Color.RED);
            tab2.setBackground(Color.GREEN);
        });

        tabsPanel.add(tab1);
        tabsPanel.add(tab2);


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
        add(navbar, BorderLayout.NORTH);
        add(mainScreen,BorderLayout.CENTER);
        add(infobar, BorderLayout.SOUTH);
    }

    //----------------------------windowsettings-------------------------------
    private void windowSettings(){
        setTitle("Drone Simulation");
        setLocation(100,100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width,height);
        setVisible(true);
        setBackground(mainScreenColor);
        setMinimumSize(new Dimension(1024,576));

        addComponentListener(new ComponentAdapter() {
            //----------------------------code for resizing the window-------------------------------
            @Override
            public void componentResized(ComponentEvent e) {
                if(width!=getWidth()) {
                    int newWidth = getWidth();
                    int newHeight = (int) (getWidth() / aspectRatio);
                    Dimension newSize = new Dimension(newWidth,newHeight);
                    setSize(newSize);
                }
                else if(height!=getHeight()) {
                    int newHeight = getHeight();
                    int newWidth = (int) (newHeight * aspectRatio);
                    Dimension newSize = new Dimension(newWidth,newHeight);
                    setSize(newSize);
                }
                else{
                    int newWidth = getWidth();
                    int newHeight = getHeight();
                    Dimension newSize = new Dimension(newWidth,newHeight);
                    setSize(newSize);
                }
                width = getHeight();
                height = getWidth();

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
