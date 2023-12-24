package org.example;

import org.example.API_Endpoints.DroneTypes;
import org.example.API_Endpoints.Drones;
import org.example.GUI.GUI;

import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) {
        new GUI();

        /*creating new thread which runs asynchronously, which calls the Drones class and outputs its data
        to the screen. When the separate thread finishes it joins the main thread and display the data to the screen*/
        CompletableFuture<Void> futureDrones = CompletableFuture.runAsync(() -> {
            System.out.println("Drones Data processing...");
            Drones drones = new Drones();
            drones.APIDronesAsync().thenAccept(droneData -> {
                for (int i = 0; i < droneData.getDroneID().size(); i++) {
                    System.out.println("Drone ID: " + droneData.getDroneID().get(i));
                    System.out.println("DroneType: " + droneData.getDroneTypeURL().get(i));
                    System.out.println("Drone Creation: " + droneData.getDroneCreate().get(i));
                    System.out.println("Drone Serialnumber: " + droneData.getDroneSerialnumber().get(i));
                    System.out.println("Drone Carriage Weight: " + droneData.getDroneCarriageWeight().get(i));
                    System.out.println("Drone Carriage Type: " + droneData.getDroneCarriageType().get(i));
                    System.out.println();
                }
            }).exceptionally(ex -> {
                System.err.println("Error fetching API data: " + ex.getMessage());
                return null;
            });
        });

        CompletableFuture<Void> futureDroneTypes = CompletableFuture.runAsync(() -> {
            System.out.println("DroneTypes Data processing...");
            DroneTypes droneTypes = new DroneTypes();
            droneTypes.APIDroneTypesAsync().thenAccept(droneTypeData -> {
                for (int i = 0; i < droneTypeData.getDroneManufacturer().size(); i++) {
                    System.out.println("Drone Manufacturer: " + droneTypeData.getDroneManufacturer().get(i));
                    System.out.println("Drone TypeName: " + droneTypeData.getDroneTypeName().get(i));
                    System.out.println("Drone TypeWeight: " + droneTypeData.getDroneWeight().get(i));
                    System.out.println("Drone MaxSpeed: " + droneTypeData.getDroneMaxSpeed().get(i));
                    System.out.println("Drone BatteryCapacity: " + droneTypeData.getDroneBatteryCapacity().get(i));
                    System.out.println("Drone Control Range: " + droneTypeData.getDroneControlRange().get(i));
                    System.out.println("Drone MaxCarriage: " + droneTypeData.getDroneMaxCarriage().get(i));
                    System.out.println();
                }
            }).exceptionally(ex -> {
                System.err.println("Error fetching API data: " + ex.getMessage());
                return null;
            });
        });

        CompletableFuture<Void> combine = CompletableFuture.allOf(futureDrones, futureDroneTypes);

        combine.thenRun(() -> {
            System.out.println("All data outputted");
        }).exceptionally(ex -> {
            System.err.println("Error occurred: " + ex.getMessage());
            return null;
        });
    }
}
