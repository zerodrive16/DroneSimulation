package org.example;

import org.example.API_Endpoints.Drones;
import org.example.API_Properties.ReturnDroneData;

import java.util.ArrayList;

public class Main{
    public static void main(String[] args){
        GUI gui = new GUI();
        // creating new Drones and access the Data from the function
        Drones dronesAPI = new Drones();
        ReturnDroneData droneData = dronesAPI.APIDrones();

        // creating variables that take the data from the encapsulation
        ArrayList<String> droneId = droneData.getDroneID();
        ArrayList<String> droneCreate = droneData.getDroneCreate();
        ArrayList<String> droneSerialnumber = droneData.getDroneSerialnumber();
        ArrayList<String> droneCarriageWeight = droneData.getDroneCarriageWeight();
        ArrayList<String> droneCarriageType = droneData.getDroneCarriageType();

        // output the data
        for(int x = 0; x < droneId.size(); x++){
            System.out.println("Drone ID: " + droneId.get(x));
            System.out.println("Drone Creation: " + droneCreate.get(x));
            System.out.println("Drone Serialnumber: " + droneSerialnumber.get(x));
            System.out.println("Drone Carriage Weight: " + droneCarriageWeight.get(x));
            System.out.println("Drone Carriage Type: " + droneCarriageType.get(x));
            System.out.println();
        }
    }
}