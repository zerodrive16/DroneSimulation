package org.example.GUI;

import org.example.API_Endpoints.DroneDynamics;
import org.example.API_Endpoints.DroneTypes;
import org.example.API_Endpoints.Drones;
import org.example.API_Properties.DroneDynamicsData;
import org.example.API_Properties.DroneTypesData;
import org.example.API_Properties.DronesData;
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

        CompletableFuture<Void> combineFuture = CompletableFuture.allOf(
                futureDronesData, futureDroneTypesData, futureDroneDynamicsData, geocodingFuture
        );

        combineFuture.thenAccept(voidResult -> {
            try {
                DronesData.ReturnDroneData droneData = futureDronesData.get();
                DroneTypesData.ReturnDroneTypeData droneTypesData = futureDroneTypesData.get();
                DroneDynamicsData.ReturnDroneDynamicData droneDynamicData = futureDroneDynamicsData.get();
                ArrayList<String> geocodingData = geocodingFuture.get();

                SwingUtilities.invokeLater(() -> {
                    card.setLayout(new FlowLayout(FlowLayout.LEFT));
                    card.removeAll();

                    int startIndex = currentPage * ITEMS_PER_PAGE;
                    int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, droneData.getDroneID().size());

                    for (int droneIndex = startIndex; droneIndex < endIndex; droneIndex++) {
                        JPanel dronePanel = createDronePanel(droneData, droneTypesData, droneDynamicData, droneIndex, primaryColor, geocodingData);
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

    private void showMoreInformation(int droneIndex, DronesData.ReturnDroneData droneData, DroneTypesData.ReturnDroneTypeData droneTypesData,
                                     DroneDynamicsData.ReturnDroneDynamicData droneDynamicData, ArrayList<String> geocodingData) {
        // Create a new JFrame for displaying additional information
        JFrame additionalInfoFrame = new JFrame("Drone Details");
        additionalInfoFrame.setLocation(500,300);
        additionalInfoFrame.setSize(600, 500);
        additionalInfoFrame.setLayout(new BorderLayout());

        // Create a JTextArea to show additional information
        JTextArea additionalInfoTextArea = new JTextArea();
        additionalInfoTextArea.setEditable(false);

        Font font = new Font("Arial", Font.BOLD, 16);
        additionalInfoTextArea.setFont(font);

        // retrieve display additional information for the selected drone
        String additionalInfo = "Manufacturer: " + droneTypesData.getDroneManufacturer().get(droneIndex) + "\n"
                + "Typename: " + droneTypesData.getDroneTypeName().get(droneIndex) + "\n"
                + "Serialnumber: " + droneData.getDroneSerialnumber().get(droneIndex) + "\n"
                + "Weight: "+ droneTypesData.getDroneWeight().get(droneIndex) + "\n"
                + "Speed: " + droneDynamicData.getDroneSpeed().get(droneIndex) + "\n"
                + "Max. Speed: " + droneTypesData.getDroneMaxCarriage().get(droneIndex) + "\n"
                + "Battery Capacity: " + droneTypesData.getDroneBatteryCapacity().get(droneIndex) + "\n"
                + "Control Range: " + droneTypesData.getDroneControlRange().get(droneIndex) + "\n"
                + "Max. Carriage: " + droneTypesData.getDroneMaxCarriage().get(droneIndex) + "\n"
                + "Created: " + droneData.getDroneCreate().get(droneIndex).substring(0,10) +"  "+ droneData.getDroneCreate().get(droneIndex).substring(12,19)+  "\n"
                + "Status: " + droneDynamicData.getDroneStatus().get(droneIndex) + "\n"
                + "Last update: " + droneDynamicData.getDroneLastSeen().get(droneIndex).substring(0,10)+ "  " +droneDynamicData.getDroneLastSeen().get(droneIndex).substring(12,19) +"\n"
                + "Location: " + geocodingData.get(droneIndex);

        additionalInfoTextArea.setText(additionalInfo);

        // Add the JTextArea to the frame
        additionalInfoFrame.add(new JScrollPane(additionalInfoTextArea), BorderLayout.CENTER);

        // Set the frame visibility
        additionalInfoFrame.setVisible(true);
    }


    protected JLabel createDroneIdLabel(Integer droneId) {
        JLabel droneIdLabel = new JLabel("Drone ID: " + droneId);
        droneIdLabel.setForeground(Color.WHITE);
        droneIdLabel.setHorizontalAlignment(JLabel.CENTER);
        droneIdLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        return droneIdLabel;
    }

    protected JPanel createInfoPanel(DroneTypesData.ReturnDroneTypeData droneTypesData,
                                   DronesData.ReturnDroneData droneData,
                                   DroneDynamicsData.ReturnDroneDynamicData droneDynamicData,
                                   int droneIndex, ArrayList<String> geocodingData) {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
       //need to set primaryColor from GUI
        infoPanel.setBackground(new Color(44,44,44));
        infoPanel.add(createWhiteLabel("Manufacturer: " + droneTypesData.getDroneManufacturer().get(droneIndex)));
        infoPanel.add(createWhiteLabel("Typename: " + droneTypesData.getDroneTypeName().get(droneIndex)));
        infoPanel.add(createWhiteLabel("Serialnumber: " + droneData.getDroneSerialnumber().get(droneIndex)));
        infoPanel.add(createWhiteLabel("Created: " + droneData.getDroneCreate().get(droneIndex)));
        infoPanel.add(createWhiteLabel("Status: " + droneDynamicData.getDroneStatus().get(droneIndex)));
        infoPanel.add(createWhiteLabel("Last update: " + droneDynamicData.getDroneLastSeen().get(droneIndex)));
        infoPanel.add(createWhiteLabel("Location: " + geocodingData.get(droneIndex)));
        return infoPanel;
    }


    protected JPanel createDronePanel(DronesData.ReturnDroneData droneData, DroneTypesData.ReturnDroneTypeData droneTypesData,
                                    DroneDynamicsData.ReturnDroneDynamicData droneDynamicData, int droneIndex, Color primaryColor, ArrayList<String> geocodingData) {
        JPanel dronePanel = new JPanel();
        dronePanel.setLayout(new BorderLayout());
        dronePanel.setBackground(primaryColor);
        dronePanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        JLabel droneIdLabel = new JLabel("Drone ID: " + droneData.getDroneID().get(droneIndex));
        droneIdLabel.setForeground(Color.WHITE);
        droneIdLabel.setHorizontalAlignment(JLabel.CENTER);
        droneIdLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));


        JButton viewDetailsButton = new JButton("View Details");
        viewDetailsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewDetailsButton.setFont(new Font("Arial", Font.BOLD, 12));
        viewDetailsButton.setForeground(Color.WHITE);
        viewDetailsButton.setBackground(Color.DARK_GRAY);
        viewDetailsButton.setBorderPainted(false);
        viewDetailsButton.setFocusPainted(false);
        viewDetailsButton.addActionListener(e -> showMoreInformation(droneIndex, droneData, droneTypesData, droneDynamicData, geocodingData));


        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(primaryColor);
        infoPanel.add(createWhiteLabel("Manufacturer: " + droneTypesData.getDroneManufacturer().get(droneIndex)));
        infoPanel.add(createWhiteLabel("Typename: " + droneTypesData.getDroneTypeName().get(droneIndex)));
        infoPanel.add(createWhiteLabel("Serialnumber: " + droneData.getDroneSerialnumber().get(droneIndex)));
        infoPanel.add(createWhiteLabel("Created: " + droneData.getDroneCreate().get(droneIndex)));
        infoPanel.add(createWhiteLabel("Status: " + droneDynamicData.getDroneStatus().get(droneIndex)));
        infoPanel.add(createWhiteLabel("Last update: " + droneDynamicData.getDroneLastSeen().get(droneIndex)));
        infoPanel.add(createWhiteLabel("Location: " + geocodingData.get(droneIndex)));

        dronePanel.add(droneIdLabel, BorderLayout.NORTH);
        dronePanel.add(infoPanel, BorderLayout.CENTER);
        dronePanel.add(viewDetailsButton, BorderLayout.SOUTH);




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

