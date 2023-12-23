package org.example.API_Endpoints;

import org.example.API_Properties.DroneDynamicsData;

import java.util.ArrayList;


public class DroneDynamics {
    public DroneDynamicsData.ReturnDroneDynamicData APIDroneDynamics(){
        ArrayList<String> droneURL = new ArrayList<>();
        ArrayList<String> droneTimestamp = new ArrayList<>();
        ArrayList<String> droneSpeed = new ArrayList<>();
        ArrayList<String> droneAlignRoll = new ArrayList<>();
        ArrayList<String> droneAlignPitch = new ArrayList<>();
        ArrayList<String> droneAlignYaw = new ArrayList<>();
        ArrayList<String> droneLongitude = new ArrayList<>();
        ArrayList<String> droneLatitude = new ArrayList<>();
        ArrayList<String> droneBatteryStatus = new ArrayList<>();
        ArrayList<String> droneLastSeen = new ArrayList<>();
        ArrayList<String> droneStatus = new ArrayList<>();

        return new DroneDynamicsData.ReturnDroneDynamicData(droneURL, droneTimestamp, droneSpeed, droneAlignRoll, droneAlignPitch, droneAlignYaw,
                droneLongitude, droneLatitude, droneBatteryStatus, droneLastSeen, droneStatus);
    }
}
