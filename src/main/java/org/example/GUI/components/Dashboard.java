package org.example.GUI.components;

import org.example.API_Properties.DroneDynamicsData;
import org.example.API_Properties.DroneTypesData;
import org.example.API_Properties.DronesData;

import org.example.GUI.Utility.StreamFiles;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


import static org.example.GUI.components.ViewMore.showMoreInformation;


public class Dashboard extends Abs_GUIComponents {
    static public JPanel createDronePanel(DronesData.ReturnDroneData droneData, DroneTypesData.ReturnDroneTypesData droneTypesData,
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
            showMoreInformation(droneIndex,droneData,droneTypesData,droneDynamicData,geocodingData, convertCreateData, convertLastSeenData);
        });

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


        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(primaryColor);
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

    static private JLabel createWhiteLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        return label;
    }
}