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
    public void configureCard1(Color primaryColor, JPanel card) {
        Drones dronesAPI = new Drones();
        CompletableFuture<DronesData.ReturnDroneData> futureDronesData = dronesAPI.APIBuildAsync();

        DroneTypes droneTypesAPI = new DroneTypes();
        CompletableFuture<DroneTypesData.ReturnDroneTypesData> futureDroneTypesData = droneTypesAPI.APIBuildAsync();

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
                DroneTypesData.ReturnDroneTypesData droneTypeData = futureDroneTypesData.get();
                DroneDynamicsData.ReturnDroneDynamicData droneDynamicData = futureDroneDynamicsData.get();
                ArrayList<String> geocodingData = geocodingFuture.get();
                ArrayList<String> convertDateData = convertDateFuture.get();
                ArrayList<String> convertLastSeenData = convertLastSeenFuture.get();
                ArrayList<String> convertCreateData = convertDateFuture.get();


                SwingUtilities.invokeLater(() -> {
                    card.setLayout(new FlowLayout(FlowLayout.LEFT));
                    card.removeAll();

                    for (int droneIndex = 0; droneIndex < droneData.getDroneID().size(); droneIndex++) {
                        JPanel dronePanel = createDronePanel(droneData, droneTypeData, droneDynamicData, droneIndex, primaryColor, geocodingData,convertDateData,convertLastSeenData, convertCreateData);
                        card.add(dronePanel);
                    }

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

    private JPanel createDronePanel(DronesData.ReturnDroneData droneData, DroneTypesData.ReturnDroneTypesData droneTypesData,
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
        infoPanel.add(createWhiteLabel("Created: " + convertCreateData.get(droneIndex) + " o'clock"));
        infoPanel.add(createWhiteLabel("Status: " + droneDynamicData.getDroneStatus().get(droneIndex)));
        infoPanel.add(createWhiteLabel("Last update: " + convertLastSeenData.get(droneIndex)+ " o'clock"));
        infoPanel.add(createWhiteLabel("Location: " + geocodingData.get(droneIndex)));
        infoPanel.add(createWhiteLabel("Time Stamp: " + convertDateData.get(droneIndex) + " o'clock"));


        dronePanel.add(droneIdLabel, BorderLayout.NORTH);
        dronePanel.add(infoPanel, BorderLayout.CENTER);
        dronePanel.add(viewMoreButton, BorderLayout.SOUTH);

        return dronePanel;
    }

    private JLabel createWhiteLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        return label;
    }
    private JLabel createRedLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.RED);
        return label;
    }
    private JLabel createGreenLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.GREEN);
        return label;
    }
}