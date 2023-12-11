package org.example.API_Properties;

import java.util.ArrayList;

public class ReturnDroneTypeData {
    final private ArrayList<String> data_droneManufacturer;
    final private ArrayList<String> data_droneTypeName;
    final private ArrayList<String> data_droneWeight;
    final private ArrayList<String> data_droneMaxSpeed;
    final private ArrayList<String> data_droneBatteryCapacity;
    final private ArrayList<String> data_droneControlRange;
    final private ArrayList<String> data_droneMaxCarriage;

    public ReturnDroneTypeData(ArrayList<String> droneManufacturer, ArrayList<String> droneTypeName, ArrayList<String> droneWeight,
                               ArrayList<String> droneMaxSpeed, ArrayList<String> droneBatteryCapacity, ArrayList<String> droneControlRange,
                               ArrayList<String> droneMaxCarriage){
        this.data_droneManufacturer = droneManufacturer;
        this.data_droneTypeName = droneTypeName;
        this.data_droneWeight = droneWeight;
        this.data_droneMaxSpeed = droneMaxSpeed;
        this.data_droneBatteryCapacity = droneBatteryCapacity;
        this.data_droneControlRange = droneControlRange;
        this.data_droneMaxCarriage = droneMaxCarriage;
    }

    public ArrayList<String> getDroneManufacturer(){
        return data_droneManufacturer;
    }
    public ArrayList<String> getDroneTypeName(){
        return data_droneTypeName;
    }
    public ArrayList<String> getDroneWeight(){
        return data_droneWeight;
    }
    public ArrayList<String> getDroneMaxSpeed(){
        return data_droneMaxSpeed;
    }
    public ArrayList<String> getDroneBatteryCapacity(){
        return data_droneBatteryCapacity;
    }
    public ArrayList<String> getDroneControlRange(){
        return data_droneControlRange;
    }
    public ArrayList<String> getDroneMaxCarriage(){
        return data_droneMaxCarriage;
    }
}
