package org.example.GUI;

import org.example.API_Endpoints.Drones;
import org.example.API_Properties.DronesData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class GUI extends JFrame implements ActionListener {
    //Zuerst Komponente der GUI erstellen
    
    //Farben und Fonts werden in Variablen eingespeichert für einfachere Nutzung
    Color primaryColor = new Color(44,44,44); //dark Gray
    Color secondaryColor = new Color(22,22,22); //even darker Gray
    Color thirdColor = new Color(66,66,66);
    Font primaryFont = new Font("Roboto", Font.PLAIN,12);

    //Bilder werden in Variablen eingespeichert für einfachere Nutzung
    ImageIcon menuIcon = new ImageIcon("src/main/java/org/example/GUI/img/menuIcon.png");
    ImageIcon refreshIcon = new ImageIcon("src/main/java/org/example/GUI/img/refreshIcon.png");
    ImageIcon infoIcon = new ImageIcon("src/main/java/org/example/GUI/img/infoIcon.png");
    
    //Hauptkomponente werden erstellt.
    JPanel navbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel mainScreen = new JPanel(new CardLayout());

    // main screens
    JPanel card1 = new JPanel();


    //navbar buttons
    JButton menuButton = new JButton(menuIcon);
    JButton b1 = new JButton();
    JButton infoButton = new JButton(infoIcon);
    JButton refreshButton = new JButton(refreshIcon);
    JLabel test = new JLabel();


    //important variables
    private int width = 1600;
    private int height = 900;
    private double aspectRatio = (double) width /height;


    public GUI(){
        initUI();
        windowSettings();
    }

    private void initUI() {
        addingComponents();
        mainScreenSettings();
        navbarSettings();
    }

    private void addingComponents() {
        add(navbar, BorderLayout.NORTH);
        add(mainScreen,BorderLayout.CENTER);
    }
    //-----------------------------------------------------------------
    //mainScreen, card1
    private void mainScreenSettings() {
        //quickSet for font, font color and background color
        quickSet(primaryFont,Color.WHITE,primaryColor,card1);

        mainScreen.setPreferredSize(new Dimension(width,height));
        mainScreen.add(card1, "Card1");

        configureCard1(primaryColor, card1);
    }
    public void configureCard1(Color primaryColor, JPanel card1) {
        Card1 card = new Card1();
        card.configureCard1(primaryColor, card1);
    }
    //-----------------------------------------------------------------
    //navbar, menuButton, b1, infoButton, refreshButton
    private void navbarSettings() {
        quickSet(primaryFont,Color.WHITE,secondaryColor, menuButton,b1, infoButton, refreshButton);
        navbar.setBackground(secondaryColor);
        navbar.setPreferredSize(new Dimension(width,(height/18)));
        navbarButtonSettings();
    }
    private void navbarButtonSettings() {
        Dimension navbarButtonSize = new Dimension((width/4)-10,(height/18)-10);

        menuButton.setPreferredSize(navbarButtonSize);
        //set Button Size
        b1.setPreferredSize(navbarButtonSize);
        infoButton.setPreferredSize(navbarButtonSize);
        refreshButton.setPreferredSize(navbarButtonSize);

        //add Buttons
        navbar.add(menuButton);
        navbar.add(b1);
        navbar.add(infoButton);
        navbar.add(refreshButton);

        b1.add(test);
    }
    //-----------------------------------------------------------------

    //general Window settings
    private void windowSettings(){
        setTitle("Drone Simulation");
        setLocation(100,100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width,height);
        setVisible(true);
        setBackground(primaryColor);

        addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                if(width!=getWidth()) {
                    int newWidth = getWidth();
                    int newHeight = (int) (getWidth() / aspectRatio);
                    Dimension newSize = new Dimension(newWidth,newHeight);
                    setSize(newSize);
                    test.setText("width " + newWidth + "height" + newHeight);

                    width = newWidth;
                    height = newHeight;
                }
                else {
                    int newHeight = getHeight();
                    int newWidth = (int) (newHeight * aspectRatio);
                    Dimension newSize = new Dimension(newWidth,newHeight);
                    setSize(newSize);
                    test.setText("width " + newWidth + "height" + newHeight);

                    width = newWidth;
                    height = newHeight;
                }

                navbar.setPreferredSize(new Dimension(width,Math.max((height/18),30)));
                Dimension navbarButtonSize = new Dimension((width/4)-10,(height/18)-10);
                navbarButtonSize.height = Math.max(navbarButtonSize.height,20);

                menuButton.setPreferredSize(navbarButtonSize);
                b1.setPreferredSize(navbarButtonSize);
                infoButton.setPreferredSize(navbarButtonSize);
                refreshButton.setPreferredSize(navbarButtonSize);

                Dimension size = getSize();
                int newSize = (int) (size.width / 50);
                newSize = Math.max(newSize, 12);
                b1.setFont(new Font(b1.getFont().getName(), Font.PLAIN, newSize));

            }
        });
    }




    @Override
    public void actionPerformed(ActionEvent e) {

    }


    //quickSet ist eine Funktion die schnell Farben und Fonts ändert. Sie wird benutzt um den Code übersichtlicher zu gestalten.
    //Mann gibt Font, Schriftfarbe und Hintergrundfarbe ein, danach all Komponente die man ändern möchte
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
