package org.example.API_StoreData;

import org.example.API_Properties.DroneDynamicsData;

import java.util.ArrayList;


public class DroneDynamicsStore {
    private final ArrayList<String> droneURL = new ArrayList<>();
    private final ArrayList<String> droneTimestamp = new ArrayList<>();
    private final ArrayList<String> droneSpeed = new ArrayList<>();
    private final ArrayList<String> droneAlignRoll = new ArrayList<>();
    private final ArrayList<String> droneAlignPitch = new ArrayList<>();
    private final ArrayList<String> droneAlignYaw = new ArrayList<>();
    private final ArrayList<Double> droneLongitude = new ArrayList<>();
    private final ArrayList<Double> droneLatitude = new ArrayList<>();
    private final ArrayList<String> droneBatteryStatus = new ArrayList<>();
    private final ArrayList<String> droneLastSeen = new ArrayList<>();
    private final ArrayList<String> droneStatus = new ArrayList<>();

    public void addDroneDynamics(DroneDynamicsData.DroneDynamic droneDynamic) {
        droneURL.add(droneDynamic.getDrone());
        droneTimestamp.add(droneDynamic.getTimestamp());
        droneSpeed.add(droneDynamic.getSpeed());
        droneAlignRoll.add(droneDynamic.getAlignRoll());
        droneAlignPitch.add(droneDynamic.getAlignPitch());
        droneAlignYaw.add(droneDynamic.getAlignYaw());
        droneLongitude.add(droneDynamic.getLongitude());
        droneLatitude.add(droneDynamic.getLatitude());
        droneBatteryStatus.add(droneDynamic.getBatteryStatus());
        droneLastSeen.add(droneDynamic.getLastSeen());
        droneStatus.add(droneDynamic.getStatus());
    }

    public ArrayList<String> getDroneURL() {
        return droneURL;
    }
    public ArrayList<String> getDroneTimestamp() {
        return droneTimestamp;
    }
    public ArrayList<String> getDroneSpeed() {
        return droneSpeed;
    }
    public ArrayList<String> getDroneAlignRoll() {
        return droneAlignRoll;
    }
    public ArrayList<String> getDroneAlignPitch() {
        return droneAlignPitch;
    }
    public ArrayList<String> getDroneAlignYaw() {
        return droneAlignYaw;
    }
    public ArrayList<Double> getDroneLongitude() {
        return droneLongitude;
    }
    public ArrayList<Double> getDroneLatitude() {
        return droneLatitude;
    }
    public ArrayList<String> getDroneBatteryStatus() {
        return droneBatteryStatus;
    }
    public ArrayList<String> getDroneLastSeen() {
        return droneLastSeen;
    }
    public ArrayList<String> getDroneStatus() {
        return droneStatus;
    }
}
