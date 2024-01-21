package org.example.GUI.Utility;

import org.example.API_Properties.DroneDynamicsData;
import org.example.API_Properties.DroneTypesData;
import org.example.API_Properties.DronesData;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class StreamFiles {
    public void fileHandler(DronesData.ReturnDroneData droneData, DroneTypesData.ReturnDroneTypesData droneTypesData,
                            DroneDynamicsData.ReturnDroneDynamicData droneDynamicData, int droneIndex) {
        String path = "File_Output.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {

            writer.write("Drone ID: " + droneData.getDroneID().get(droneIndex));
            writer.newLine();
            writer.write("Manufacturer: " + droneTypesData.getDroneManufacturer().get(droneIndex));
            writer.newLine();
            writer.write("TypeName: " + droneTypesData.getDroneTypeName().get(droneIndex));
            writer.newLine();
            writer.write("Serialnumber: " + droneData.getDroneSerialnumber().get(droneIndex));
            writer.newLine();
            writer.write("Created: " + droneData.getDroneCreate().get(droneIndex));
            writer.newLine();
            writer.write("Status: " + droneDynamicData.getDroneStatus().get(droneIndex));
            writer.newLine();

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
