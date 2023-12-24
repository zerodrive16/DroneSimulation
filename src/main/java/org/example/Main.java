package org.example;

import org.example.API_Properties.DroneTypesData;
import org.example.API_Properties.DronesData;
import org.example.API_Endpoints.DroneTypes;
import org.example.API_Endpoints.Drones;
import org.example.GUI.GUI;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {
        GUI gui = new GUI();

        CompletableFuture<Void> futureDrones = CompletableFuture.runAsync(() -> {
            try {
                System.out.println("Data processing...");
                DronesData.ReturnDroneData droneData = new Drones().APIDrones();

                Thread.sleep(2000);
                for (int i = 0; i < droneData.getDroneID().size(); i++) {
                    System.out.println("Drone ID: " + droneData.getDroneID().get(i));
                    System.out.println("DroneType: " + droneData.getDroneTypeURL().get(i));
                    System.out.println("Drone Creation: " + droneData.getDroneCreate().get(i));
                    System.out.println("Drone Serialnumber: " + droneData.getDroneSerialnumber().get(i));
                    System.out.println("Drone Carriage Weight: " + droneData.getDroneCarriageWeight().get(i));
                    System.out.println("Drone Carriage Type: " + droneData.getDroneCarriageType().get(i));
                    System.out.println();
                }
            } catch(InterruptedException e){
                throw new IllegalStateException();
            }
        });

        CompletableFuture<Void> futureDroneTypes = CompletableFuture.runAsync(() -> {
            try {
                System.out.println("Data processing...");
                DroneTypesData.ReturnDroneTypeData droneTypeData = new DroneTypes().APIDroneTypes();

                Thread.sleep(2000);
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

            } catch(InterruptedException e){
                throw new IllegalStateException();
            }
        });
        //System.out.println(Thread.activeCount());
        CompletableFuture<Void> combineFuture = CompletableFuture.allOf(futureDrones, futureDroneTypes);

        try {
            combineFuture.get();
            System.out.println("All data processing completed!");
        } catch(InterruptedException | ExecutionException e){
            e.printStackTrace();
        }
    }
}
