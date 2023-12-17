package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {

    Color primaryColor = new Color(44,44,44); //dark Gray
    Color secondaryColor = new Color(22,22,22); //even darker Gray
    Color thirdColor = new Color(66,66,66);

    Font primaryFont = new Font("Roboto", Font.PLAIN,12);
    JPanel mainP = new JPanel(new BorderLayout());
    JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel sideBar = new JPanel();
    JPanel sideBarExtension = new JPanel();
    ImageIcon menuIMG = new ImageIcon("src/main/java/org/example/test.png");
    ImageIcon menuIMG2 = new ImageIcon("src/main/java/org/example/test2.png");
    JButton menu = new JButton(menuIMG);
    JLabel test = new JLabel("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");


    GUI(){
        initUI();
        windowSettings();
    }

    private void initUI() {

        mainP.setBackground(primaryColor);
        this.add(mainP);

        mainP.add(topBar, BorderLayout.NORTH);
        mainP.add(sideBar, BorderLayout.WEST);
        mainP.add(sideBarExtension, BorderLayout.EAST);
        mainP.add(test, BorderLayout.EAST);

        topBar.setBackground(secondaryColor);
        topBar.setPreferredSize(new Dimension(Short.MAX_VALUE,80));
        topBar.add(menu);

        sideBar.setBackground(thirdColor);
        sideBar.setPreferredSize(new Dimension(300,Short.MAX_VALUE));
        sideBar.setVisible(false);


        menu.setPreferredSize(new Dimension(70,70));
        menu.setRolloverIcon(menuIMG2);
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleMenu();
            }
        });

    }

    private void windowSettings(){
        this.setTitle("Drone Simulation");
        this.setLocation(300,100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1024,768);
        this.setVisible(true);
        this.setResizable(false);
        this.setFont(primaryFont);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private void toggleMenu() {
        sideBar.setVisible(!sideBar.isVisible());
        sideBarExtension.setVisible(sideBar.isVisible());
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI());
    }


}
