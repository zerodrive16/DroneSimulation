package org.example.GUI.components;

import org.example.API_Properties.DroneDynamicsData;
import org.example.API_Properties.DroneTypesData;
import org.example.API_Properties.DronesData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static org.example.GUI.components.Dashboard.createDronePanel;

public class pagination {

    private static int currentPage = 1;
    private static final int ITEMS_PER_PAGE = 10;

    static protected void addPaginationControls(JPanel card,
                                       JPanel card2,
                                       DronesData.ReturnDroneData droneData,
                                       DroneTypesData.ReturnDroneTypesData droneTypeData,
                                       DroneDynamicsData.ReturnDroneDynamicData droneDynamicData,
                                       Color primaryColor,
                                       ArrayList<String> geoData,
                                       ArrayList<String> convertCreateData,
                                       ArrayList<String> convertLastSeenData) {
        int totalItems = droneData.getDroneID().size();
        int totalPages = (int) Math.ceil((double) totalItems / ITEMS_PER_PAGE);

        JButton prevButton = new JButton("Previous");
        prevButton.setFont(new Font("Arial", Font.BOLD, 12));
        prevButton.setForeground(Color.WHITE);
        prevButton.setBackground(Color.DARK_GRAY);
        prevButton.setOpaque(true);
        prevButton.setBorderPainted(false);
        prevButton.setFocusPainted(false);

        JButton nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 12));
        nextButton.setForeground(Color.WHITE);
        nextButton.setBackground(Color.DARK_GRAY);
        nextButton.setOpaque(true);
        nextButton.setBorderPainted(false);
        nextButton.setFocusPainted(false);

        JPanel paginationPanel = new JPanel();
        paginationPanel.setBackground(Color.WHITE);
        paginationPanel.add(prevButton);
        JLabel paginationLabel = new JLabel("Page " + currentPage + " of " + totalPages);
        paginationPanel.add(paginationLabel);
        paginationPanel.add(nextButton);

        prevButton.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                displayPage(card, droneData, droneTypeData, droneDynamicData, primaryColor, geoData, convertCreateData, convertLastSeenData);
                paginationLabel.setText("Page " + currentPage + " of " + totalPages);
                card2.add(paginationPanel,BorderLayout.SOUTH);
            }
        });
        nextButton.addActionListener(e -> {
            if (currentPage < totalPages) {
                currentPage++;
                displayPage(card, droneData, droneTypeData, droneDynamicData, primaryColor, geoData, convertCreateData, convertLastSeenData);
                paginationLabel.setText("Page " + currentPage + " of " + totalPages);
                card2.add(paginationPanel,BorderLayout.SOUTH);
            }
        });
        card2.add(paginationPanel,BorderLayout.SOUTH);
    }

    static protected void displayPage(JPanel card,
                             DronesData.ReturnDroneData droneData,
                             DroneTypesData.ReturnDroneTypesData droneTypeData,
                             DroneDynamicsData.ReturnDroneDynamicData droneDynamicData,
                             Color primaryColor,
                             ArrayList<String> geoData,
                             ArrayList<String> convertCreateData,
                             ArrayList<String> convertLastSeenData) {
        int startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, droneData.getDroneID().size());

        card.removeAll();
        for (int i = startIndex; i < endIndex; i++) {
            JPanel dronePanel = createDronePanel(droneData, droneTypeData, droneDynamicData, i, primaryColor, geoData, convertCreateData, convertLastSeenData);
            card.add(dronePanel);
        }
        card.revalidate();
        card.repaint();
    }
}
