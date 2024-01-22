package org.example.GUI.Utility;

import org.example.API_Properties.DroneDynamicsData;
import org.example.API_Properties.DroneTypesData;
import org.example.API_Properties.DronesData;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class StreamFiles {
    public void fileHandler(DronesData.ReturnDroneData droneData, DroneTypesData.ReturnDroneTypesData droneTypesData,
                            DroneDynamicsData.ReturnDroneDynamicData droneDynamicData, int droneIndex, ArrayList<String> geocodingData,
                            ArrayList<String> convertCreateData, ArrayList<String> convertLastSeenData) {
        String path = "File_Output.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {

            // calculate battery percentage
            int batteryPercentage = (int) (100 * (Double.parseDouble(droneDynamicData.getDroneBatteryStatus().get(droneIndex)) / droneTypesData.getDroneBatteryCapacity().get(droneIndex)));

            writer.write("Drone ID: \t\t\t" + droneData.getDroneID().get(droneIndex));
            writer.newLine();
            writer.write("Manufacturer: \t\t" + droneTypesData.getDroneManufacturer().get(droneIndex));
            writer.newLine();
            writer.write("TypeName: \t\t\t" + droneTypesData.getDroneTypeName().get(droneIndex));
            writer.newLine();
            writer.write("Serialnumber: \t\t" + droneData.getDroneSerialnumber().get(droneIndex));
            writer.newLine();
            writer.write("Created: \t\t\t" + convertCreateData.get(droneIndex) + " Uhr");
            writer.newLine();
            writer.write("Carriage Type: \t\t" + droneData.getDroneCarriageType().get(droneIndex));
            writer.newLine();
            writer.write("Location: \t\t\t" + geocodingData.get(droneIndex));
            writer.newLine();
            writer.write("Battery: \t\t\t" + batteryPercentage + "%");
            writer.newLine();
            writer.write("Battery Capacity: \t" + droneTypesData.getDroneBatteryCapacity().get(droneIndex) + "kW");
            writer.newLine();
            writer.write("Weight: \t\t\t" + droneTypesData.getDroneWeight().get(droneIndex) + "g");
            writer.newLine();
            writer.write("Max. Carriage: \t\t" + droneTypesData.getDroneMaxCarriage().get(droneIndex) + "g");
            writer.newLine();
            writer.write("Carriage Weight: \t" + droneData.getDroneCarriageWeight().get(droneIndex) + "g");
            writer.newLine();
            writer.write("Speed: \t\t\t\t" + droneDynamicData.getDroneSpeed().get(droneIndex) + "km/h");
            writer.newLine();
            writer.write("Max. Speed: \t\t" + droneTypesData.getDroneMaxSpeed().get(droneIndex) + "km/h");
            writer.newLine();
            writer.write("Control Range: \t\t" + droneTypesData.getDroneControlRange().get(droneIndex) + "m");
            writer.newLine();
            writer.write("Status: \t\t\t" + droneDynamicData.getDroneStatus().get(droneIndex));
            writer.newLine();
            writer.write("Last update: \t\t" + convertLastSeenData.get(droneIndex) + "Uhr");

            JOptionPane.showMessageDialog(null, "Data exported successfully to " + path, "Export Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to export data", "Export Error", JOptionPane.ERROR_MESSAGE);
        }

        /*
        File file = new File(path);
        boolean result = file.setReadOnly();
        if(result) {
            JOptionPane.showMessageDialog(null, "Data exported successfully to " + path, "Export Successful", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Failed to export data", "Export Error", JOptionPane.ERROR_MESSAGE);
        }
        */
    }
}
