package org.example;

import org.example.API_Properties.DroneTypesData;
import org.example.API_Properties.DronesData;
import org.example.API_Endpoints.DroneTypes;
import org.example.API_Endpoints.Drones;
import org.example.GUI.GUI;

import java.util.ArrayList;

public class Main{
    public static void main(String[] args){
         GUI gui = new GUI();
        // creating new Drones and access the Data from the function
        Drones dronesAPI = new Drones();
        DronesData.ReturnDroneData droneData = dronesAPI.APIDrones();

        // creating variables that take the data from the encapsulation
        ArrayList<String> droneId = droneData.getDroneID();
        ArrayList<String> droneType = droneData.getDroneTypeURL();
        ArrayList<String> droneCreate = droneData.getDroneCreate();
        ArrayList<String> droneSerialnumber = droneData.getDroneSerialnumber();
        ArrayList<String> droneCarriageWeight = droneData.getDroneCarriageWeight();
        ArrayList<String> droneCarriageType = droneData.getDroneCarriageType();

        // output the data
        for(int x = 0; x < droneId.size(); x++){
            System.out.println("Drone ID: " + droneId.get(x));
            System.out.println("DroneType: " + droneType.get(x));
            System.out.println("Drone Creation: " + droneCreate.get(x));
            System.out.println("Drone Serialnumber: " + droneSerialnumber.get(x));
            System.out.println("Drone Carriage Weight: " + droneCarriageWeight.get(x));
            System.out.println("Drone Carriage Type: " + droneCarriageType.get(x));
            System.out.println();
        }

        DroneTypes droneTypeAPI = new DroneTypes();
        DroneTypesData.ReturnDroneTypeData droneTypeData = droneTypeAPI.APIDroneTypes();

        ArrayList<String> droneTypeManufacturer = droneTypeData.getDroneManufacturer();
        ArrayList<String> droneTypeTypeName = droneTypeData.getDroneTypeName();
        ArrayList<String> droneTypeWeight = droneTypeData.getDroneWeight();
        ArrayList<String> droneTypeMaxSpeed = droneTypeData.getDroneMaxSpeed();
        ArrayList<String> droneTypeBatteryCapacity = droneTypeData.getDroneBatteryCapacity();
        ArrayList<String> droneTypeControlRange = droneTypeData.getDroneControlRange();
        ArrayList<String> droneTypeMaxCarriage = droneTypeData.getDroneMaxCarriage();

        for(int i = 0; i < droneId.size(); i++) {
            System.out.println("Drone Manufacturer: " + droneTypeManufacturer.get(i));
            System.out.println("Drone TypeName: " + droneTypeTypeName.get(i));
            System.out.println("Drone TypeWeight: " + droneTypeWeight.get(i));
            System.out.println("Drone MaxSpeed: " + droneTypeMaxSpeed.get(i));
            System.out.println("Drone BatteryCapacity: " + droneTypeBatteryCapacity.get(i));
            System.out.println("Drone Control Range: " + droneTypeControlRange.get(i));
            System.out.println("Drone MaxCarriage: " + droneTypeMaxCarriage.get(i));
        }
    }
}