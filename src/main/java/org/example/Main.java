package org.example;

import org.example.API_Endpoints.DroneTypes;
import org.example.API_Endpoints.Drones;
import org.example.API_Properties.ReturnDroneData;
import org.example.API_Properties.ReturnDroneTypeData;

import java.util.ArrayList;

import static org.example.Config.Drone_Mask;

public class Main{
    public static void main(String[] args){
        // GUI gui = new GUI();
        // creating new Drones and access the Data from the function
        Drones dronesAPI = new Drones();
        ReturnDroneData droneData = dronesAPI.APIDrones();

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
        ReturnDroneTypeData droneTypeData = droneTypeAPI.APIDroneTypes();

        ArrayList<String> droneTypeManufacturer = droneTypeData.getDroneManufacturer();
        ArrayList<String> droneTypeTypeName = droneTypeData.getDroneTypeName();
        ArrayList<String> droneTypeWeight = droneTypeData.getDroneWeight();
        ArrayList<String> droneTypeMaxSpeed = droneTypeData.getDroneMaxSpeed();
        ArrayList<String> droneTypeBatteryCapacity = droneTypeData.getDroneBatteryCapacity();
        ArrayList<String> droneTypeControlRange = droneTypeData.getDroneControlRange();
        ArrayList<String> droneTypeMaxCarriage = droneTypeData.getDroneMaxCarriage();

        System.out.println("Drone Manufacturer: " + droneTypeManufacturer);

        System.out.println("New");
        System.out.println("Hello World");
        System.out.println("Stefan");
    }
}