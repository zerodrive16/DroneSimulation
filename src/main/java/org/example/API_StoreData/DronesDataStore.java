package org.example.API_StoreData;

import org.example.API_Properties.DronesData;

import java.util.ArrayList;

/**
 *  class represents the fetched API data
 *  it stores the data temporarily for the Drones
 */
public class DronesDataStore {
    // storing the data inside the List
    private final ArrayList<Integer> droneID = new ArrayList<>();
    private final ArrayList<String> droneTypeURL = new ArrayList<>();
    private final ArrayList<String> droneCreate = new ArrayList<>();
    private final ArrayList<String> droneSerialnumber = new ArrayList<>();
    private final ArrayList<Integer> droneCarriageWeight = new ArrayList<>();
    private final ArrayList<String> droneCarriageType = new ArrayList<>();

    /**
     * calling the function to store the drones data
     *
     * @param drone it takes the parameter as access point to the getters
     */
    public void addDrones(DronesData.Drone drone) {
        droneID.add(drone.getId());
        droneTypeURL.add(drone.getDroneType());
        droneCreate.add(drone.getCreated());
        droneSerialnumber.add(drone.getSerialnumber());
        droneCarriageWeight.add(drone.getCarriage_weight());
        droneCarriageType.add(drone.getCarriage_type());
    }

    // getters to get the fetched API data
    public ArrayList<Integer> getDroneID() {
        return droneID;
    }
    public ArrayList<String> getDroneTypeURL() { return droneTypeURL; }
    public ArrayList<String> getDroneCreate() {
        return droneCreate;
    }
    public ArrayList<String> getDroneSerialnumber() {
        return droneSerialnumber;
    }
    public ArrayList<Integer> getDroneCarriageWeight() {
        return droneCarriageWeight;
    }
    public ArrayList<String> getDroneCarriageType() {
        return droneCarriageType;
    }
}
