package org.example.GUI.components;

import org.example.API_Properties.DroneDynamicsData;
import org.example.API_Properties.DroneTypesData;
import org.example.API_Properties.DronesData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ViewMore {

    static protected void showMoreInformation(int droneIndex, DronesData.ReturnDroneData droneData,
                                            DroneTypesData.ReturnDroneTypesData droneTypesData,
                                            DroneDynamicsData.ReturnDroneDynamicData droneDynamicData,
                                            ArrayList<String> geocodingData, ArrayList<String> convertCreateData,
                                            ArrayList<String> convertLastSeenData) {
        // Create a new JFrame for displaying additional information
        JDialog additionalInfoFrame = new JDialog();
        additionalInfoFrame.setTitle("Dialog");
        additionalInfoFrame.setLocation(500,300);
        additionalInfoFrame.setLocationRelativeTo(null);
        additionalInfoFrame.setSize(600, 500);
        additionalInfoFrame.setLayout(new BorderLayout());
        additionalInfoFrame.setBackground(Abs_GUIComponents.mainScreenColor);

        // Create a JTextArea to show additional information
        JTextArea additionalInfoTextArea = new JTextArea();
        additionalInfoTextArea.setEditable(false);

        Font font = new Font("Arial", Font.BOLD, 12);
        additionalInfoTextArea.setFont(font);
        additionalInfoTextArea.setForeground(Color.WHITE);
        additionalInfoTextArea.setBackground(Abs_GUIComponents.mainScreenColor);

        //additional calculations
        int batteryPercentage = (int) (100 * (Double.parseDouble(droneDynamicData.getDroneBatteryStatus().get(droneIndex)) / droneTypesData.getDroneBatteryCapacity().get(droneIndex)));

        // retrieve display additional information for the selected drone
        String additionalInfo = "Drone ID: \t\t" + droneData.getDroneID().get(droneIndex) + "\n"
                + "Manufacturer: \t\t" + droneTypesData.getDroneManufacturer().get(droneIndex) + "\n"
                + "Typename: \t\t" + droneTypesData.getDroneTypeName().get(droneIndex) + "\n"
                + "Serialnumber: \t\t" + droneData.getDroneSerialnumber().get(droneIndex) + "\n"
                + "Created: \t\t" + convertCreateData.get(droneIndex) + " Uhr\n"
                + "Carriage Type: \t\t" + droneData.getDroneCarriageType().get(droneIndex) + "\n"
                + "Location: \t\t" + geocodingData.get(droneIndex) + "\n"
                + "Battery: \t\t" + batteryPercentage + "%\n"
                + "Battery Capacity: \t" + droneTypesData.getDroneBatteryCapacity().get(droneIndex) + "kW\n"
                + "Weight: \t\t" + droneTypesData.getDroneWeight().get(droneIndex) + "g\n"
                + "Max. Carriage: \t\t" + droneTypesData.getDroneMaxCarriage().get(droneIndex) + "g\n"
                + "Carriage Weight: \t" + droneData.getDroneCarriageWeight().get(droneIndex) + "g\n"
                + "Speed: \t\t" + droneDynamicData.getDroneSpeed().get(droneIndex) + "km/h\n"
                + "Max. Speed: \t\t" + droneTypesData.getDroneMaxSpeed().get(droneIndex) + "km/h\n"
                + "Control Range: \t\t" + droneTypesData.getDroneControlRange().get(droneIndex) + "m\n"
                + "Status: \t\t" + droneDynamicData.getDroneStatus().get(droneIndex) + "\n"
                + "Last update: \t\t" + convertLastSeenData.get(droneIndex) + " Uhr\n";


        additionalInfoTextArea.setText(additionalInfo);

        // Add the JTextArea to the frame
        additionalInfoFrame.add(new JScrollPane(additionalInfoTextArea), BorderLayout.CENTER);

        // Set the frame visibility
        additionalInfoFrame.setVisible(true);
    }
}
