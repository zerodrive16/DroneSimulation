package org.example.API_Endpoints;

import org.example.API_Properties.ReturnDroneTypeData;

import java.util.ArrayList;

public class DroneTypes {
    public ReturnDroneTypeData APIDroneTypes(){
        ArrayList<String> droneManufacturer = new ArrayList<>();
        ArrayList<String> droneTypeName = new ArrayList<>();
        ArrayList<String> droneWeight = new ArrayList<>();
        ArrayList<String> droneMaxSpeed = new ArrayList<>();
        ArrayList<String> droneBatteryCapacity = new ArrayList<>();
        ArrayList<String> droneControlRange = new ArrayList<>();
        ArrayList<String> droneMaxCarriage = new ArrayList<>();

        return new ReturnDroneTypeData(droneManufacturer, droneTypeName, droneWeight, droneMaxSpeed,
                droneBatteryCapacity, droneControlRange, droneMaxCarriage);
    }
}
