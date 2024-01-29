package org.example.GUI.Utility;

import org.example.API_Properties.DroneDynamicsData;
import org.example.API_Properties.DroneTypesData;
import org.example.API_Properties.DronesData;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class StreamFiles {
    /**
     * Writes data to a file and displays a success or error message using JOptionPane.
     *
     * @param droneData           The drone data object containing information about drones.
     * @param droneTypesData      The drone types data object containing information about drone types.
     * @param droneDynamicData    The drone dynamics data object containing dynamic information about drones.
     * @param droneIndex          The index of the drone for which data is being exported.
     * @param geocodingData       The list of geocoding data for drones.
     * @param convertCreateData   The list of converted creation timestamps for drones.
     * @param convertLastSeenData The list of converted last seen timestamps for drones.
     */

    public void fileHandler(DronesData.ReturnDroneData droneData, DroneTypesData.ReturnDroneTypesData droneTypesData,
                            DroneDynamicsData.ReturnDroneDynamicData droneDynamicData, int droneIndex, ArrayList<String> geocodingData,
                            ArrayList<String> convertCreateData, ArrayList<String> convertLastSeenData) {
        String path = "File_Output.txt";
        // Set default file path
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {

            // Calculate battery percentage
            int batteryPercentage = (int) (100 * (Double.parseDouble(droneDynamicData.getDroneBatteryStatus().get(droneIndex)) / droneTypesData.getDroneBatteryCapacity().get(droneIndex)));

            // Write down data to the file
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

            // Display data exported successfully message
            JOptionPane.showMessageDialog(null, "Data exported successfully to " + path, "Export Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            // Handle IO exception and display data failed to export message
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to export data", "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

