package org.example;

import java.util.ArrayList;

public class Config {
    public static String token = System.getenv("Secure_Token");

    public static ArrayList<String> Drone_Mask;

    static {
        Drone_Mask = new ArrayList<>();
        Drone_Mask.add("ID: ");
        Drone_Mask.add("Created: ");
        Drone_Mask.add("Serialnumber: ");
        Drone_Mask.add("Carriage Weight: ");
        Drone_Mask.add("Carriage Type: ");
    }

    public static ArrayList<String> DroneDynamics_Mask;

    static{
        DroneDynamics_Mask = new ArrayList<>();
        Drone_Mask.add("Timestamp: ");
        Drone_Mask.add("Speed: ");
        Drone_Mask.add("Align Roll: ");
        Drone_Mask.add("Align Pitch: ");
        Drone_Mask.add("Align Yaw: ");
        Drone_Mask.add("Longitude: ");
        Drone_Mask.add("Latitude: ");
        Drone_Mask.add("Battery Status: ");
        Drone_Mask.add("Last Seen: ");
    }

    public static ArrayList<String> DroneTypes_Mask;

    static{
        DroneTypes_Mask = new ArrayList<>();
        DroneTypes_Mask.add("Manufacturer: ");
        DroneTypes_Mask.add("Typename: ");
        DroneTypes_Mask.add("Weight: ");
        DroneTypes_Mask.add("Max Speed: ");
        DroneTypes_Mask.add("Battery Capacity: ");
        DroneTypes_Mask.add("Control Range: ");
        DroneTypes_Mask.add("Max Carriage: ");
    }
}
