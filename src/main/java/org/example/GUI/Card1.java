package org.example.GUI;

import org.example.API_Endpoints.Drones;
import org.example.API_Properties.DronesData;
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CompletableFuture;

public class Card1 {
    public void configureCard1(Color primaryColor, JPanel card1) {
        // calling the Drones class
        Drones dronesAPI = new Drones();
        CompletableFuture<DronesData.ReturnDroneData> futureDronesData = dronesAPI.APIBuildAsync();

        futureDronesData.thenAccept(droneData -> {
            // ensuring that it's thread safe
            SwingUtilities.invokeLater(() -> {
                card1.setLayout(new BorderLayout());
                card1.removeAll();

                // Panel for the clickable drones
                JPanel buttonsPanel = new JPanel();
                buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
                buttonsPanel.setBackground(primaryColor);

                // Panel for the output of the drones
                JPanel outputPanel = new JPanel(new CardLayout());
                outputPanel.setBackground(primaryColor);

                // Iterate through every droneID and create a button and output for every ID
                for (int droneIndex = 0; droneIndex < droneData.getDroneID().size(); droneIndex++) {
                    // function for outputting the data
                    JTextArea outputArea = createDroneOutput(droneData, droneIndex, primaryColor);

                    // output the ID of drones for the list
                    String cardName = "Drone " + droneData.getDroneID().get(droneIndex);
                    outputPanel.add(outputArea, cardName);
                    JButton button = createDroneButton(cardName, primaryColor, outputPanel);
                    buttonsPanel.add(button);
                }

                // Add a scrollPane to the buttonsPanel if data exceeds screen size
                JScrollPane buttonsScrollPane = customizeScrollPane(buttonsPanel);
                card1.add(buttonsScrollPane, BorderLayout.WEST);

                // The outputPanel will contain the outputAreas and will be added to the center
                JScrollPane outputScrollPane = customizeScrollPane(outputPanel);
                card1.add(outputScrollPane, BorderLayout.CENTER);

                // revalidate and repaint are needed if there are new updates
                card1.revalidate();
                card1.repaint();
            });
        }).exceptionally(ex -> {
            SwingUtilities.invokeLater(() -> {
                ex.printStackTrace();
            });
            return null;
        });
    }

    private JButton createDroneButton(String cardName, Color primaryColor, JPanel outputPanel) {
        JButton button = new JButton(cardName);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setForeground(Color.WHITE);
        button.setBackground(primaryColor);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        button.addActionListener(e -> {
            CardLayout cardLayout = (CardLayout) (outputPanel.getLayout());
            cardLayout.show(outputPanel, cardName);
        });

        return button;
    }

    private JScrollPane customizeScrollPane(JPanel cardsPanel) {
        JScrollPane scrollPane = new JScrollPane(cardsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setBackground(Color.BLACK);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setBackground(Color.BLACK);
        scrollPane.setBorder(null);
        return scrollPane;
    }

    private JTextArea createDroneOutput(DronesData.ReturnDroneData droneData, int droneIndex, Color primaryColor) {
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Arial", Font.BOLD, 15));
        outputArea.setForeground(Color.WHITE);
        outputArea.setBackground(primaryColor);

        // Add the drone data to the JTextArea
        outputArea.append("Drone ID: " + droneData.getDroneID().get(droneIndex) + "\n");
        outputArea.append("DroneType: " + droneData.getDroneTypeURL().get(droneIndex) + "\n");
        outputArea.append("Drone Creation: " + droneData.getDroneCreate().get(droneIndex) + "\n");
        outputArea.append("Drone Serialnumber: " + droneData.getDroneSerialnumber().get(droneIndex) + "\n");
        outputArea.append("Drone Carriage Weight: " + droneData.getDroneCarriageWeight().get(droneIndex) + "\n");
        outputArea.append("Drone Carriage Type: " + droneData.getDroneCarriageType().get(droneIndex) + "\n");

        // Initially, we hide the outputArea, it will be made visible when the corresponding button is clicked
        outputArea.setVisible(false);

        return outputArea;
    }
}
