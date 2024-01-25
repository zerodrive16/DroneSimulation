package org.example.API_StoreData;

import org.example.API_Properties.DroneTypesData;

import java.util.ArrayList;

/**
 *  class represents the fetched API data
 *  it stores the data temporarily for the Drone types
 */
public class DroneTypesStore {
    // storing the data inside the List
    private final ArrayList<String> droneManufacturer = new ArrayList<>();
    private final ArrayList<String> droneTypeName = new ArrayList<>();
    private final ArrayList<Integer> droneWeight = new ArrayList<>();
    private final ArrayList<Integer> droneMaxSpeed = new ArrayList<>();
    private final ArrayList<Integer> droneBatteryCapacity = new ArrayList<>();
    private final ArrayList<Integer> droneControlRange = new ArrayList<>();
    private final ArrayList<Integer> droneMaxCarriage = new ArrayList<>();

    /**
     * calling the function to store the drones data
     *
     * @param droneType it takes the parameter as access point to the getters
     */
    public void addDroneTypes(DroneTypesData.DroneType droneType) {
        droneManufacturer.add(droneType.getManufacturer());
        droneTypeName.add(droneType.getTypename());
        droneWeight.add(droneType.getWeight());
        droneMaxSpeed.add(droneType.getMax_Speed());
        droneBatteryCapacity.add(droneType.getBattery_Capacity());
        droneControlRange.add(droneType.getControl_Range());
        droneMaxCarriage.add(droneType.getMax_Carriage());
    }

    // getters to get the fetched API data
    public ArrayList<String> getDroneManufacturer() { return droneManufacturer; }
    public ArrayList<String> getDroneTypeName() { return droneTypeName; }
    public ArrayList<Integer> getDroneWeight() {
        return droneWeight;
    }
    public ArrayList<Integer> getDroneMaxSpeed() {
        return droneMaxSpeed;
    }
    public ArrayList<Integer> getDroneBatteryCapacity() {
        return droneBatteryCapacity;
    }
    public ArrayList<Integer> getDroneControlRange() {
        return droneControlRange;
    }
    public ArrayList<Integer> getDroneMaxCarriage() {
        return droneMaxCarriage;
    }
}
