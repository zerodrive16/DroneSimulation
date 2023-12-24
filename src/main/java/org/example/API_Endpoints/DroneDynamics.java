package org.example.API_Endpoints;

import org.example.API_Properties.DroneDynamicsData;

import java.io.IOException;
import java.util.ArrayList;


public class DroneDynamics {
    public DroneDynamicsData.ReturnDroneDynamicData APIDroneDynamics(){
        ArrayList<String> droneURL = new ArrayList<>();
        ArrayList<String> droneTimestamp = new ArrayList<>();
        ArrayList<Integer> droneSpeed = new ArrayList<>();
        ArrayList<Float> droneAlignRoll = new ArrayList<>();
        ArrayList<Float> droneAlignPitch = new ArrayList<>();
        ArrayList<Float> droneAlignYaw = new ArrayList<>();
        ArrayList<Double> droneLongitude = new ArrayList<>();
        ArrayList<Double> droneLatitude = new ArrayList<>();
        ArrayList<Integer> droneBatteryStatus = new ArrayList<>();
        ArrayList<String> droneLastSeen = new ArrayList<>();
        ArrayList<String> droneStatus = new ArrayList<>();

        return new DroneDynamicsData.ReturnDroneDynamicData(droneURL, droneTimestamp, droneSpeed, droneAlignRoll, droneAlignPitch, droneAlignYaw,
                droneLongitude, droneLatitude, droneBatteryStatus, droneLastSeen, droneStatus);
    }
}
