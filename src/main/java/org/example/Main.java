package org.example;

import org.example.API_Endpoints.DroneDynamics;
import org.example.API_Endpoints.DroneTypes;
import org.example.API_Endpoints.Drones;

import org.example.GUI.GUI;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) {
        new GUI();


        /* the CompletableFuture function runs the runAsync on a sub-task also called ForkJoinPool asynchronously. It divides the
        so-called task in sub-tasks and does that for both functions (futureDrones and futureDroneTypes). In this code specifically we
         call the Drones class and run the function and get the return variables which are inside the constructor and output it*/

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

        CompletableFuture<Void> futureDroneDynamics = CompletableFuture.runAsync(() -> {
            System.out.println("DroneDynamics Data processing...");
            DroneDynamics droneDynamics = new DroneDynamics();
            droneDynamics.APIBuildAsync().thenAccept(droneDynamicData -> {
                for (Integer droneId : droneDynamicData.getDroneURL().keySet()) {
                    System.out.println("Drone ID: " + droneId);
                    ArrayList<String> urls = droneDynamicData.getDroneURL().get(droneId);
                    ArrayList<String> timestamps = droneDynamicData.getDroneTimeStamp().get(droneId);
                    ArrayList<String> speeds = droneDynamicData.getDroneSpeed().get(droneId);
                    ArrayList<String> alignRolls = droneDynamicData.getDroneAlignRoll().get(droneId);
                    ArrayList<String> alignPitchs = droneDynamicData.getDroneAlignPitch().get(droneId);
                    ArrayList<String> alignYaws = droneDynamicData.getDroneAlignYaw().get(droneId);
                    ArrayList<Double> longitude = droneDynamicData.getDroneLongitude().get(droneId);
                    ArrayList<Double> latitude = droneDynamicData.getDroneLatitude().get(droneId);
                    ArrayList<String> batteryStatus = droneDynamicData.getDroneBatteryStatus().get(droneId);
                    ArrayList<String> lastSeen = droneDynamicData.getDroneLastSeen().get(droneId);
                    ArrayList<String> status = droneDynamicData.getDroneStatus().get(droneId);

                    for (int i = 0; i < urls.size(); i++) {
                        System.out.println("Drone URL: " + urls.get(i));
                        System.out.println("Timestamp: " + timestamps.get(i));
                        System.out.println("Speed: " + speeds.get(i));
                        System.out.println("Align Roll: " + alignRolls.get(i));
                        System.out.println("Align Pitch: " + alignPitchs.get(i));
                        System.out.println("Align Yaw: " + alignYaws.get(i));
                        System.out.println("Longitude: " + longitude.get(i));
                        System.out.println("Latitude: " + latitude.get(i));
                        System.out.println("BatteryStatus: " + batteryStatus.get(i));
                        System.out.println("Last Seen: " + lastSeen.get(i));
                        System.out.println("Status: " + status.get(i));

                        System.out.println();
                    }
                }
            }).exceptionally(ex -> {
                System.err.println("Error fetching API data: " + ex.getMessage());
                return null;
            });
        });

        // When both sub-tasks are done it uses a so-called join method to bring the tasks together.
        CompletableFuture<Void> combine = CompletableFuture.allOf(futureDrones, futureDroneTypes, futureDroneDynamics);

        /* it thenRun the lambda function once the code above is finished (callback) and prints a message that the output
        was successful or not. (Exception handling) */
        combine.thenRun(() -> System.out.println("All data outputted")).exceptionally(ex -> {
            System.err.println("Error occurred: " + ex.getMessage());
            return null;
        });

        combine.join();
    }
}
