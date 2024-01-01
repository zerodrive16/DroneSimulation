package org.example.GUI;

import org.example.API_Endpoints.Drones;
import org.example.API_Properties.DronesData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class GUI extends JFrame implements ActionListener {
    //colors and fonts
    Color primaryColor = new Color(44,44,44); //dark Gray
    Color secondaryColor = new Color(22,22,22); //even darker Gray
    Color thirdColor = new Color(66,66,66);
    Font primaryFont = new Font("Roboto", Font.PLAIN,12);

    //images for icons and more
    ImageIcon menuIcon = new ImageIcon("src/main/java/org/example/GUI/img/menuIcon.png");
    ImageIcon refreshIcon = new ImageIcon("src/main/java/org/example/GUI/img/refreshIcon.png");
    ImageIcon infoIcon = new ImageIcon("src/main/java/org/example/GUI/img/infoIcon.png");

    //main components
    JPanel mainP = new JPanel(new BorderLayout());
    JPanel navbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel sideBar = new JPanel();
    JPanel mainScreen = new JPanel(new CardLayout());

    // main screens
    JPanel card1 = new JPanel();
    JPanel card2 = new JPanel();
    JPanel card3 = new JPanel();

    //navbar buttons
    JButton menuButton = new JButton(menuIcon);
    JButton b1 = new JButton();
    JButton infoButton = new JButton(infoIcon);
    JButton refreshButton = new JButton(refreshIcon);

    //Sidebar Buttons
    JButton a1 = new JButton("Drone Catalog");
    JButton a2 = new JButton("Flight Dynamics");
    JButton a3 = new JButton("Drone Log");


    //important variables
    private static int status = 1; //status of MainScreen
    private int width = 1024;
    private int height = 768;


    public GUI(){
        initUI();
        windowSettings();
    }

    private void initUI() {
        addingComponents();
        mainScreenSettings();
        navbarSettings();
        sideBarSettings();
    }



    private void addingComponents() {
        this.add(mainP);
        mainP.add(navbar, BorderLayout.NORTH);
        mainP.add(sideBar, BorderLayout.WEST);
        mainP.add(mainScreen,BorderLayout.EAST);
    }
    
    //mainScreen, card1, card2 ,card3
    private void mainScreenSettings() {
        //quickSet for font, font color and background color
        quickSet(primaryFont,Color.WHITE,primaryColor,card1,card2,card3);

        mainScreen.setPreferredSize(new Dimension(width,Short.MAX_VALUE));
        mainScreen.add(card1, "Card1");
        mainScreen.add(card2, "Card2");
        mainScreen.add(card3, "Card3");

        configureCard1(primaryColor, card1);
        configureCard2(primaryColor, card2);
        configureCard3(primaryColor, card3);
    }
    public void configureCard1(Color primaryColor, JPanel card1) {
        Card1 card = new Card1();
        card.configureCard1(primaryColor, card1);
    }
    public void configureCard2(Color primaryColor, JPanel card2) {
        Card2 card = new Card2();
        card.configureCard2(primaryColor, card2);
    }
    public void configureCard3(Color primaryColor, JPanel card3) {
        Card3 card = new Card3();
        card.configureCard3(primaryColor, card3);
    }
    //navbar, menuButton, b1, infoButton, refreshButton
    private void navbarSettings() {
        quickSet(primaryFont,Color.WHITE,secondaryColor, menuButton,b1, infoButton, refreshButton);
        navbar.setBackground(secondaryColor);
        navbar.setPreferredSize(new Dimension(Short.MAX_VALUE,80));
        topButtonSettings();
    }
    private void topButtonSettings() {
        Dimension navbarButtonSize = new Dimension(70,70);

        menuButton.setPreferredSize(navbarButtonSize);
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleMenu();
            }
        });

        //set Button Size
        b1.setPreferredSize(navbarButtonSize);
        infoButton.setPreferredSize(navbarButtonSize);
        refreshButton.setPreferredSize(navbarButtonSize);

        //add Buttons
        navbar.add(menuButton);
        navbar.add(b1);
        navbar.add(infoButton);
        navbar.add(refreshButton);
    }

    //sideBar, a1, a2, a3
    private void sideBarSettings() {
        //quickSet for font, font color and background color
        quickSet(primaryFont,Color.WHITE,Color.BLUE,a1,a2,a3);

        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.setBackground(thirdColor);
        sideBar.setPreferredSize(new Dimension(300,Short.MAX_VALUE));
        sideBar.setVisible(false);
        addSideBarButtons();

    }

    private void addSideBarButtons() {
        Dimension sideBarButtonSize = new Dimension(300,70);

        //set Button Size
        a1.setPreferredSize(sideBarButtonSize);
        a2.setPreferredSize(sideBarButtonSize);
        a3.setPreferredSize(sideBarButtonSize);
        a1.setMaximumSize(sideBarButtonSize);
        a2.setMaximumSize(sideBarButtonSize);
        a3.setMaximumSize(sideBarButtonSize);

        //add Button Functionality
        a1.addActionListener(createMainScreenListener(mainScreen, "Card1"));
        a2.addActionListener(createMainScreenListener(mainScreen, "Card2"));
        a3.addActionListener(createMainScreenListener(mainScreen, "Card3"));

        //add Buttons
        sideBar.add(a1);
        sideBar.add(a2);
        sideBar.add(a3);
    }



    //general Window settings
    private void windowSettings(){
        this.setTitle("Drone Simulation");
        this.setLocation(300,100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width,height);
        this.setVisible(true);
        this.setResizable(false);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    //ACTION LISTENER FUNCTIONS
    private void toggleMenu() {
        sideBar.setVisible(!sideBar.isVisible());
    }

    private ActionListener createMainScreenListener(JPanel cardPanel, String cardName) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
                cardLayout.show(cardPanel, cardName);
                sideBar.setVisible(false);
                switch(cardName) {
                    case "Card1":
                        status = 1;
                        break;
                    case "Card2":
                        status = 2;
                        break;
                    case "Card3":
                        status = 3;
                        break;
                }
            }
        };
    }

    //quickSet functions
    private static void quickSet(Font font, Color color1, Color color2, JComponent... components){
        for (JComponent component : components) {
            component.setFont(font);
            component.setForeground(color1);
            component.setBackground(color2);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI());
    }

}
