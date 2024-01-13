package org.example.API_StoreData;

import org.example.API_Properties.DroneTypesData;
import org.example.API_Properties.DronesData;

import java.util.ArrayList;
import java.util.List;

public class DroneTypesStore {
    // declaring ArrayLists which store the REST API data temporarily
    private final ArrayList<String> droneManufacturer = new ArrayList<>();
    private final ArrayList<String> droneTypeName = new ArrayList<>();
    private final ArrayList<Integer> droneWeight = new ArrayList<>();
    private final ArrayList<Integer> droneMaxSpeed = new ArrayList<>();
    private final ArrayList<Integer> droneBatteryCapacity = new ArrayList<>();
    private final ArrayList<Integer> droneControlRange = new ArrayList<>();
    private final ArrayList<Integer> droneMaxCarriage = new ArrayList<>();

    public void addDroneTypes(DroneTypesData.DroneType droneType) {
        droneManufacturer.add(droneType.getManufacturer());
        droneTypeName.add(droneType.getTypename());
        droneWeight.add(droneType.getWeight());
        droneMaxSpeed.add(droneType.getMax_Speed());
        droneBatteryCapacity.add(droneType.getBattery_Capacity());
        droneControlRange.add(droneType.getControl_Range());
        droneMaxCarriage.add(droneType.getMax_Carriage());
    }

    public List<String> getDroneManufacturer() {
        return droneManufacturer;
    }
    public List<String> getDroneTypeName() {
        return droneTypeName;
    }
    public List<Integer> getDroneWeight() {
        return droneWeight;
    }
    public List<Integer> getDroneMaxSpeed() {
        return droneMaxSpeed;
    }
    public List<Integer> getDroneBatteryCapacity() {
        return droneBatteryCapacity;
    }
    public List<Integer> getDroneControlRange() {
        return droneControlRange;
    }
    public List<Integer> getDroneMaxCarriage() {
        return droneMaxCarriage;
    }
}
