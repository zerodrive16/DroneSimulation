package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {
    //colors and fonts
    Color primaryColor = new Color(44,44,44); //dark Gray
    Color secondaryColor = new Color(22,22,22); //even darker Gray
    Color thirdColor = new Color(66,66,66);
    Font primaryFont = new Font("Roboto", Font.PLAIN,12);

    //images for icons and more
    ImageIcon menuIcon = new ImageIcon("src/main/java/org/example/menuIcon.png");
    ImageIcon refreshIcon = new ImageIcon("src/main/java/org/example/refreshIcon.png");
    ImageIcon infoIcon = new ImageIcon("src/main/java/org/example/infoIcon.png");

    //main components
    JPanel mainP = new JPanel(new BorderLayout());
    JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel sideBar = new JPanel();
    JPanel mainScreen = new JPanel(new CardLayout());

    // main screens
    JPanel card1 = new JPanel();
    JPanel card2 = new JPanel();
    JPanel card3 = new JPanel();

    //topbar buttons
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


    GUI(){
        initUI();
        windowSettings();
    }

    private void initUI() {
        addingComponents();
        mainScreenSettings();
        topBarSettings();
        sideBarSettings();
    }



    private void addingComponents() {
        this.add(mainP);
        mainP.add(topBar, BorderLayout.NORTH);
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

        configureCard1();
        configureCard2();
        configureCard3();
    }
    private void configureCard1() {

    }
    private void configureCard2() {
    }
    private void configureCard3() {
    }
    //topBar, menuButton, b1, infoButton, refreshButton
    private void topBarSettings() {
        quickSet(primaryFont,Color.WHITE,secondaryColor, menuButton,b1, infoButton, refreshButton);
        topBar.setBackground(secondaryColor);
        topBar.setPreferredSize(new Dimension(Short.MAX_VALUE,80));
        topButtonSettings();
    }
    private void topButtonSettings() {
        Dimension topBarButtonSize = new Dimension(70,70);

        menuButton.setPreferredSize(topBarButtonSize);
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleMenu();
            }
        });

        //set Button Size
        b1.setPreferredSize(topBarButtonSize);
        infoButton.setPreferredSize(topBarButtonSize);
        refreshButton.setPreferredSize(topBarButtonSize);

        //add Buttons
        topBar.add(menuButton);
        topBar.add(b1);
        topBar.add(infoButton);
        topBar.add(refreshButton);
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
