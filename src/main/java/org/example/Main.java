package org.example;

import org.example.API_Endpoints.DroneTypes;
import org.example.API_Endpoints.Drones;
import org.example.GUI.GUI;

import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) {
        new GUI();

        /* The CompletableFuture with runAsync runs the function in a different thread asynchronously. It then proceeds with
        the initialization of Drones and call its class + function to get the desired output data. It then uses the thenAccept to run
        after the completion of drones.APIDronesAsync(). It takes a lambda function and iterates to all Drones ID's and output the data.
        Exceptionally is used to handle the error that might occur inside the asynchronous programming. */
        CompletableFuture<Void> futureDrones = CompletableFuture.runAsync(() -> {
            System.out.println("Drones Data processing...");
            Drones drones = new Drones();
            drones.APIBuildAsync().thenAccept(droneData -> {
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

        /* The same concept follows in this function as well which runs a different thread asynchronously. It initializes
        the DroneTypes class and runs through the functions and output its data. */
        CompletableFuture<Void> futureDroneTypes = CompletableFuture.runAsync(() -> {
            System.out.println("DroneTypes Data processing...");
            DroneTypes droneTypes = new DroneTypes();
            droneTypes.APIBuildAsync().thenAccept(droneTypeData -> {
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

        // This is used to expect that both different threads are finished and combines them together
        CompletableFuture<Void> combine = CompletableFuture.allOf(futureDrones, futureDroneTypes);

        /* it thenRun the lambda function once the code above is finished (callback) and prints a message that the output
        was successful or not. (Exception handling) */
        combine.thenRun(() -> {
            System.out.println("All data outputted");
        }).exceptionally(ex -> {
            System.err.println("Error occurred: " + ex.getMessage());
            return null;
        });
    }
}
