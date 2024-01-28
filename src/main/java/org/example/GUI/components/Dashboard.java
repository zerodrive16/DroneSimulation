package org.example.GUI.components;

import org.example.API_Properties.DroneDynamicsData;
import org.example.API_Properties.DroneTypesData;
import org.example.API_Properties.DronesData;

import org.example.GUI.Utility.StreamFiles;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


import static org.example.GUI.components.ViewMore.viewMoreInformation;


public class Dashboard extends Abs_GUIComponents {
    /**
     * Creates a JPanel representing a drone with detailed information.
     * This panel includes drone ID, manufacturer, typename, serial number, creation date, status,
     * and last update time. It also provides buttons for viewing more information
     * and exporting the data.
     *
     * @param droneData          The data object containing drone information.
     * @param droneTypesData     The data object containing drone type information.
     * @param droneDynamicData   The data object containing drone dynamic information.
     * @param droneIndex         The index of the drone in the data lists.
     * @param mainColor       The primary color used for UI components.
     * @param geocodingData      The geocoding data for location information.
     * @param convertCreateData  The converted creation date data.
     * @param convertLastSeenData The converted last seen date data.
     * @return A JPanel representing the drone with detailed information.
     */
    static public JPanel createDronePanel(DronesData.ReturnDroneData droneData, DroneTypesData.ReturnDroneTypesData droneTypesData,
                                    DroneDynamicsData.ReturnDroneDynamicData droneDynamicData, int droneIndex, Color mainColor, ArrayList<String> geocodingData,
                                    ArrayList<String> convertCreateData, ArrayList<String> convertLastSeenData) {
        // Create a new JPanel for displaying drone information
        JPanel dronePanel = new JPanel();
        dronePanel.setLayout(new BorderLayout());
        dronePanel.setBackground(mainColor);
        dronePanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        // Create a new Drone ID Jlabel
        JLabel droneIdLabel = new JLabel("Drone ID: " + droneData.getDroneID().get(droneIndex));
        droneIdLabel.setForeground(Color.WHITE);
        droneIdLabel.setHorizontalAlignment(JLabel.CENTER);
        droneIdLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Create a new viewMoreButton that calls viewMoreInformation when clicked
        JButton viewMoreButton = new JButton("View More");
        viewMoreButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewMoreButton.setFont(new Font("Arial", Font.BOLD, 12));
        viewMoreButton.setForeground(Color.WHITE);
        viewMoreButton.setBackground(Abs_GUIComponents.buttonColor);
        viewMoreButton.setOpaque(true);
        viewMoreButton.setBorderPainted(false);
        viewMoreButton.setFocusPainted(false);
        viewMoreButton.addActionListener(e -> {
            viewMoreInformation(droneIndex,droneData,droneTypesData,droneDynamicData,geocodingData, convertCreateData, convertLastSeenData);
        });

        // Create a new JPanel and JButton to add an exportButton next to the Drone ID
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box,BoxLayout.X_AXIS));
        box.setBackground(Color.DARK_GRAY);
        JButton exportButton = new JButton(clipboard);
        exportButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exportButton.setBackground(Color.WHITE);
        exportButton.setOpaque(true);
        exportButton.setBorderPainted(false);
        exportButton.setFocusPainted(false);
        exportButton.addActionListener(e -> {
            StreamFiles streamFiles = new StreamFiles();
            streamFiles.fileHandler(droneData, droneTypesData, droneDynamicData, droneIndex, geocodingData, convertCreateData, convertLastSeenData);
        });
        
        box.add(Box.createRigidArea(new Dimension(50, 50)));
        box.add(droneIdLabel);
        box.add(Box.createHorizontalGlue());
        box.add(exportButton);

        // Create a new JPanel for displaying drone information
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(mainColor);
        infoPanel.add(createWhiteLabel("Manufacturer: " + droneTypesData.getDroneManufacturer().get(droneIndex)));
        infoPanel.add(createWhiteLabel("Typename: " + droneTypesData.getDroneTypeName().get(droneIndex)));
        infoPanel.add(createWhiteLabel("Serialnumber: " + droneData.getDroneSerialnumber().get(droneIndex)));
        infoPanel.add(createWhiteLabel("Created: " + convertCreateData.get(droneIndex) + " Uhr"));
        String labelText;
        if("ON".equals(droneDynamicData.getDroneStatus().get(droneIndex))){
            labelText = "<html><font color='white'>Status: </font><font color='green'>" + droneDynamicData.getDroneStatus().get(droneIndex) + "</font></html>";
        }
        else {
            labelText = "<html><font color='white'>Status: </font><font color='red'>" + droneDynamicData.getDroneStatus().get(droneIndex) + "</font></html>";
        }
        infoPanel.add(new JLabel(labelText));
        infoPanel.add(createWhiteLabel("Last update: " + convertLastSeenData.get(droneIndex)+ " Uhr"));

        dronePanel.add(box,BorderLayout.NORTH);
        dronePanel.add(infoPanel, BorderLayout.CENTER);
        dronePanel.add(viewMoreButton, BorderLayout.SOUTH);

        return dronePanel;
    }

    /**
     * This function creates a white label.
     * @param  text String
     * @return Text in JLabel form
     */
    static private JLabel createWhiteLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        return label;
    }
}