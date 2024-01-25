package org.example.API_StoreData;

import org.example.API_Properties.DroneDynamicsData;

import java.util.ArrayList;

/**
 *  class represents the fetched API data
 *  it stores the data temporarily for the DroneDynamics
 */
public class DroneDynamicsStore {
    // storing the data inside the List
    private final ArrayList<String> droneSpeed = new ArrayList<>();
    private final ArrayList<Double> droneLongitude = new ArrayList<>();
    private final ArrayList<Double> droneLatitude = new ArrayList<>();
    private final ArrayList<String> droneBatteryStatus = new ArrayList<>();
    private final ArrayList<String> droneLastSeen = new ArrayList<>();
    private final ArrayList<String> droneStatus = new ArrayList<>();

    /**
     * calling the function to store the drone dynamics data
     *
     * @param droneDynamic it takes the parameter as access point to the getters
     */
    public void addDroneDynamics(DroneDynamicsData.DroneDynamic droneDynamic) {
        droneSpeed.add(droneDynamic.getSpeed());
        droneLongitude.add(droneDynamic.getLongitude());
        droneLatitude.add(droneDynamic.getLatitude());
        droneBatteryStatus.add(droneDynamic.getBatteryStatus());
        droneLastSeen.add(droneDynamic.getLastSeen());
        droneStatus.add(droneDynamic.getStatus());
    }

    // getters to get the fetched API data
    public ArrayList<String> getDroneSpeed() {
        return droneSpeed;
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
