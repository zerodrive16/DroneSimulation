package org.example;

import org.example.API_Properties.DroneTypesData;
import org.example.API_Properties.DronesData;
import org.example.API_Endpoints.DroneTypes;
import org.example.API_Endpoints.Drones;
import org.example.GUI.GUI;

public class Main {
    public static void main(String[] args) {
        GUI gui = new GUI();

        // Fetching drones data
        DronesData.ReturnDroneData droneData = new Drones().APIDrones();
        printDroneData(droneData);

        // Fetching drone types data
        DroneTypesData.ReturnDroneTypeData droneTypeData = new DroneTypes().APIDroneTypes();
        printDroneTypeData(droneTypeData);
    }

    private static void printDroneData(DronesData.ReturnDroneData data) {
        if (data == null) {
            System.out.println("No drone data available.");
            return;
        }

        for (int i = 0; i < data.getDroneID().size(); i++) {
            System.out.println("Drone ID: " + data.getDroneID().get(i));
            System.out.println("DroneType: " + data.getDroneTypeURL().get(i));
            System.out.println("Drone Creation: " + data.getDroneCreate().get(i));
            System.out.println("Drone Serialnumber: " + data.getDroneSerialnumber().get(i));
            System.out.println("Drone Carriage Weight: " + data.getDroneCarriageWeight().get(i));
            System.out.println("Drone Carriage Type: " + data.getDroneCarriageType().get(i));
            System.out.println();
        }
    }

    private static void printDroneTypeData(DroneTypesData.ReturnDroneTypeData data) {
        if (data == null) {
            System.out.println("No drone type data available.");
            return;
        }

        for (int i = 0; i < data.getDroneManufacturer().size(); i++) {
            System.out.println("Drone Manufacturer: " + data.getDroneManufacturer().get(i));
            System.out.println("Drone TypeName: " + data.getDroneTypeName().get(i));
            System.out.println("Drone TypeWeight: " + data.getDroneWeight().get(i));
            System.out.println("Drone MaxSpeed: " + data.getDroneMaxSpeed().get(i));
            System.out.println("Drone BatteryCapacity: " + data.getDroneBatteryCapacity().get(i));
            System.out.println("Drone Control Range: " + data.getDroneControlRange().get(i));
            System.out.println("Drone MaxCarriage: " + data.getDroneMaxCarriage().get(i));
            System.out.println();
        }
    }
}
