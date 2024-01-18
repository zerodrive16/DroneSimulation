package org.example.GUI;

import org.example.API_Endpoints.DroneDynamics;
import org.example.API_Endpoints.DroneTypes;
import org.example.API_Endpoints.Drones;
import org.example.API_Properties.DroneDynamicsData;
import org.example.API_Properties.DroneTypesData;
import org.example.API_Properties.DronesData;
import org.example.ConvertDate;
import org.example.ReverseGeo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class Card1 {
    private static final int ITEMS_PER_PAGE = 10;
    private int currentPage = 0;

    public void configureCard1(Color primaryColor, JPanel card) {
        Drones dronesAPI = new Drones();
        CompletableFuture<DronesData.ReturnDroneData> futureDronesData = dronesAPI.APIBuildAsync();

        DroneTypes droneTypesAPI = new DroneTypes();
        CompletableFuture<DroneTypesData.ReturnDroneTypeData> futureDroneTypesData = droneTypesAPI.APIBuildAsync();

        DroneDynamics droneDynamicsAPI = new DroneDynamics();
        CompletableFuture<DroneDynamicsData.ReturnDroneDynamicData> futureDroneDynamicsData = droneDynamicsAPI.APIBuildAsync();

        ReverseGeo reverseGeo = new ReverseGeo();
        CompletableFuture<ArrayList<String>> geocodingFuture = reverseGeo.performReverseGeoAsync();

        ConvertDate convertDate = new ConvertDate();
        CompletableFuture<ArrayList<String>> convertDateFuture = convertDate.performConvertDateAsync();
        CompletableFuture<ArrayList<String>> convertLastSeenFuture = convertDate.performConvertLastSeenAsync();


        CompletableFuture<Void> combineFuture = CompletableFuture.allOf(
                futureDronesData, futureDroneTypesData, futureDroneDynamicsData, geocodingFuture, convertDateFuture
        );

        combineFuture.thenAccept(voidResult -> {
            try {
                DronesData.ReturnDroneData droneData = futureDronesData.get();
                DroneTypesData.ReturnDroneTypeData droneTypeData = futureDroneTypesData.get();
                DroneDynamicsData.ReturnDroneDynamicData droneDynamicData = futureDroneDynamicsData.get();
                ArrayList<String> geocodingData = geocodingFuture.get();
                ArrayList<String> convertDateData = convertDateFuture.get();
                ArrayList<String> convertLastSeenData = convertLastSeenFuture.get();
                ArrayList<String> convertCreateData = convertDateFuture.get();


                SwingUtilities.invokeLater(() -> {
                    card.setLayout(new FlowLayout(FlowLayout.LEFT));
                    card.removeAll();

                    int startIndex = currentPage * ITEMS_PER_PAGE;
                    int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, droneData.getDroneID().size());

                    for (int droneIndex = startIndex; droneIndex < endIndex; droneIndex++) {
                        JPanel dronePanel = createDronePanel(droneData, droneTypeData, droneDynamicData, droneIndex, primaryColor, geocodingData,convertDateData,convertLastSeenData, convertCreateData);
                        card.add(dronePanel);
                    }

                    addPaginationControls(card, droneData.getDroneID().size());
                    card.revalidate();
                    card.repaint();
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).exceptionally(ex -> {
            SwingUtilities.invokeLater(() -> {
                ex.printStackTrace();
            });
            return null;
        });
    }

    private JPanel createDronePanel(DronesData.ReturnDroneData droneData, DroneTypesData.ReturnDroneTypeData droneTypesData,
                                    DroneDynamicsData.ReturnDroneDynamicData droneDynamicData, int droneIndex, Color primaryColor, ArrayList<String> geocodingData,
                                    ArrayList<String> convertDateData,ArrayList<String> convertLastSeenData, ArrayList<String> convertCreateData ) {
        JPanel dronePanel = new JPanel();
        dronePanel.setLayout(new BorderLayout());
        dronePanel.setBackground(primaryColor);
        dronePanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        JLabel droneIdLabel = new JLabel("Drone ID: " + droneData.getDroneID().get(droneIndex));
        droneIdLabel.setForeground(Color.WHITE);
        droneIdLabel.setHorizontalAlignment(JLabel.CENTER);
        droneIdLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton viewMoreButton = new JButton("View More");
        viewMoreButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewMoreButton.setFont(new Font("Arial", Font.BOLD, 12));
        viewMoreButton.setForeground(Color.WHITE);
        viewMoreButton.setBackground(Color.DARK_GRAY);
        viewMoreButton.setBorderPainted(false);
        viewMoreButton.setFocusPainted(false);
        viewMoreButton.addActionListener(e -> {
            // view more button addition
        });

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(primaryColor);
        infoPanel.add(createWhiteLabel("Manufacturer: " + droneTypesData.getDroneManufacturer().get(droneIndex)));
        infoPanel.add(createWhiteLabel("Typename: " + droneTypesData.getDroneTypeName().get(droneIndex)));
        infoPanel.add(createWhiteLabel("Serialnumber: " + droneData.getDroneSerialnumber().get(droneIndex)));
        infoPanel.add(createWhiteLabel("Created: " + convertCreateData.get(droneIndex)));
        infoPanel.add(createWhiteLabel("Status: " + droneDynamicData.getDroneStatus().get(droneIndex)));
        infoPanel.add(createWhiteLabel("Last update: " + convertLastSeenData.get(droneIndex)));
        infoPanel.add(createWhiteLabel("Location: " + geocodingData.get(droneIndex)));
        infoPanel.add(createWhiteLabel("Time Stamp: " + convertDateData.get(droneIndex)));


        dronePanel.add(droneIdLabel, BorderLayout.NORTH);
        dronePanel.add(infoPanel, BorderLayout.CENTER);
        dronePanel.add(viewMoreButton, BorderLayout.SOUTH);

        return dronePanel;
    }

    private void addPaginationControls(JPanel card, int totalItems) {
        int totalPages = (int) Math.ceil((double) totalItems / ITEMS_PER_PAGE);

        JButton prevButton = new JButton("Previous");
        prevButton.addActionListener(e -> {
            if (currentPage > 0) {
                currentPage--;
                configureCard1(card.getBackground(), card);
            }
        });

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            if (currentPage < totalPages - 1) {
                currentPage++;
                configureCard1(card.getBackground(), card);
            }
        });

        JPanel paginationPanel = new JPanel();
        paginationPanel.setLayout(new BoxLayout(paginationPanel, BoxLayout.X_AXIS));
        paginationPanel.add(Box.createHorizontalGlue());
        paginationPanel.add(prevButton);
        paginationPanel.add(Box.createHorizontalStrut(10));
        paginationPanel.add(nextButton);
        paginationPanel.add(Box.createHorizontalGlue());

        card.add(paginationPanel);
    }

    private JLabel createWhiteLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        return label;
    }
}