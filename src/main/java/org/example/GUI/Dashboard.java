package org.example.GUI;

import org.example.API_Endpoints.DroneDynamics;
import org.example.API_Endpoints.DroneTypes;
import org.example.API_Endpoints.Drones;
import org.example.API_Properties.DroneDynamicsData;
import org.example.API_Properties.DroneTypesData;
import org.example.API_Properties.DronesData;
import org.example.GUI.Utility.ConvertDate;
import org.example.GUI.Utility.ReverseGeo;
import org.example.GUI.components.Abs_GUIComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Dashboard extends Abs_GUIComponents {
    private int currentPage = 1;
    private static final int ITEMS_PER_PAGE = 10;
    public void configureCard1(Color primaryColor, JPanel card) {
        Drones dronesAPI = new Drones();
        CompletableFuture<DronesData.ReturnDroneData> futureDronesData = dronesAPI.APIBuildAsync();

        DroneTypes droneTypesAPI = new DroneTypes();
        CompletableFuture<DroneTypesData.ReturnDroneTypesData> futureDroneTypesData = droneTypesAPI.APIBuildAsync();

        DroneDynamics droneDynamicsAPI = new DroneDynamics();
        CompletableFuture<DroneDynamicsData.ReturnDroneDynamicData> futureDroneDynamicsData = droneDynamicsAPI.APIBuildAsync();

        CompletableFuture<Void> combineFuture = CompletableFuture.allOf(
                futureDronesData, futureDroneTypesData, futureDroneDynamicsData
        );

        combineFuture.thenAccept(voidResult -> {
            try {
                DronesData.ReturnDroneData droneData = futureDronesData.get();
                DroneTypesData.ReturnDroneTypesData droneTypeData = futureDroneTypesData.get();
                DroneDynamicsData.ReturnDroneDynamicData droneDynamicData = futureDroneDynamicsData.get();

                ReverseGeo reverseGeo = new ReverseGeo();
                CompletableFuture<ArrayList<String>> geocodingFuture = reverseGeo.performReverseGeoAsync(droneDynamicData);

                ConvertDate convertDate = new ConvertDate();
                CompletableFuture<ArrayList<String>> futureConvertCreate = convertDate.performConvertCreateAsync(droneData);
                CompletableFuture<ArrayList<String>> futureConvertLastSeen = convertDate.performConvertLastSeenAsync(droneDynamicData);

                CompletableFuture<Void> combineConvertDate = CompletableFuture.allOf(futureConvertCreate, futureConvertLastSeen, geocodingFuture);

                combineConvertDate.thenAccept(convertResult -> SwingUtilities.invokeLater(() -> {
                    try {
                        ArrayList<String> geoData = geocodingFuture.get();
                        ArrayList<String> convertCreateData = futureConvertCreate.get();
                        ArrayList<String> convertLastSeenData = futureConvertLastSeen.get();

                        card.setLayout(new FlowLayout(FlowLayout.LEFT));
                        card.removeAll();

                        for (int droneIndex = 0; droneIndex < droneData.getDroneID().size(); droneIndex++) {
                            JPanel dronePanel = createDronePanel(droneData, droneTypeData, droneDynamicData, droneIndex, primaryColor, geoData, convertCreateData, convertLastSeenData);
                            card.add(dronePanel);
                        }

                        card.revalidate();
                        card.repaint();

                        displayPage(card, droneData, droneTypeData, droneDynamicData, primaryColor, geoData, convertCreateData, convertLastSeenData);
                        addPaginationControls(card, droneData, droneTypeData, droneDynamicData, primaryColor, geoData, convertCreateData, convertLastSeenData);

                    } catch(InterruptedException | ExecutionException ex) {
                        ex.printStackTrace();
                    }
                }));

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).exceptionally(ex -> {
            SwingUtilities.invokeLater(() -> ex.printStackTrace());
            return null;
        });
    }
    private void addPaginationControls(JPanel card,
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
                card.add(paginationPanel, BorderLayout.SOUTH);
            }
        });
        nextButton.addActionListener(e -> {
            if (currentPage < totalPages) {
                currentPage++;
                displayPage(card, droneData, droneTypeData, droneDynamicData, primaryColor, geoData, convertCreateData, convertLastSeenData);
                paginationLabel.setText("Page " + currentPage + " of " + totalPages);
                card.add(paginationPanel, BorderLayout.SOUTH);
            }
        });
        card.add(paginationPanel, BorderLayout.SOUTH);
    }

    private void displayPage(JPanel card,
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


    //----------------------------------

    private JPanel createDronePanel(DronesData.ReturnDroneData droneData, DroneTypesData.ReturnDroneTypesData droneTypesData,
                                    DroneDynamicsData.ReturnDroneDynamicData droneDynamicData, int droneIndex, Color primaryColor, ArrayList<String> geocodingData,
                                    ArrayList<String> convertCreateData, ArrayList<String> convertLastSeenData) {
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
        viewMoreButton.setOpaque(true);
        viewMoreButton.setBorderPainted(false);
        viewMoreButton.setFocusPainted(false);
        viewMoreButton.addActionListener(e -> {
            // view more button addition
        });

        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box,BoxLayout.X_AXIS));
        box.setBackground(Color.DARK_GRAY);
        JButton exportButton = new JButton(clipboard);
        exportButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exportButton.setForeground(Color.WHITE);
        exportButton.setBackground(Color.GRAY);
        exportButton.setOpaque(true);
        exportButton.setBorderPainted(false);
        exportButton.setFocusPainted(false);
        exportButton.addActionListener(e -> {
            // export button addition
            // here stream and files
        });

        box.add(Box.createRigidArea(new Dimension(50, 50)));
        box.add(droneIdLabel);
        box.add(Box.createHorizontalGlue());
        box.add(exportButton);


        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(primaryColor);
        infoPanel.add(createWhiteLabel("Manufacturer: " + droneTypesData.getDroneManufacturer().get(droneIndex)));
        infoPanel.add(createWhiteLabel("Typename: " + droneTypesData.getDroneTypeName().get(droneIndex)));
        infoPanel.add(createWhiteLabel("Serialnumber: " + droneData.getDroneSerialnumber().get(droneIndex)));
        infoPanel.add(createWhiteLabel("Created: " + convertCreateData.get(droneIndex) + " o'clock"));
        String labelText;
        if("ON".equals(droneDynamicData.getDroneStatus().get(droneIndex))){
            labelText = "<html><font color='white'>Status: </font><font color='green'>" + droneDynamicData.getDroneStatus().get(droneIndex) + "</font></html>";
        }
        else {
            labelText = "<html><font color='white'>Status: </font><font color='red'>" + droneDynamicData.getDroneStatus().get(droneIndex) + "</font></html>";
        }
        infoPanel.add(new JLabel(labelText));
        infoPanel.add(createWhiteLabel("Last update: " + convertLastSeenData.get(droneIndex)+ " o'clock"));
        infoPanel.add(createWhiteLabel("Location: " + geocodingData.get(droneIndex)));

        dronePanel.add(box,BorderLayout.NORTH);
        dronePanel.add(infoPanel, BorderLayout.CENTER);
        dronePanel.add(viewMoreButton, BorderLayout.SOUTH);

        return dronePanel;
    }

    private JLabel createWhiteLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        return label;
    }
}