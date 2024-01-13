package org.example.API_StoreData;

import org.example.API_Properties.DronesData;

import java.util.ArrayList;
import java.util.List;

public class DronesDataStore {
    // ArrayLists to store the API fetch data temporarily
    private final ArrayList<Integer> droneID = new ArrayList<>();
    private final ArrayList<String> droneTypeURL = new ArrayList<>();
    private final ArrayList<String> droneCreate = new ArrayList<>();
    private final ArrayList<String> droneSerialnumber = new ArrayList<>();
    private final ArrayList<Integer> droneCarriageWeight = new ArrayList<>();
    private final ArrayList<String> droneCarriageType = new ArrayList<>();

    public void addDrones(DronesData.Drone drone) {
        droneID.add(drone.getId());
        droneTypeURL.add(drone.getDronetype());
        droneCreate.add(drone.getCreated());
        droneSerialnumber.add(drone.getSerialnumber());
        droneCarriageWeight.add(drone.getCarriage_weight());
        droneCarriageType.add(drone.getCarriage_type());
    }

    public List<Integer> getDroneID() {
        return droneID;
    }
    public List<String> getDroneTypeURL() {
        return droneTypeURL;
    }
    public List<String> getDroneCreate() {
        return droneCreate;
    }
    public List<String> getDroneSerialnumber() {
        return droneSerialnumber;
    }
    public List<Integer> getDroneCarriageWeight() {
        return droneCarriageWeight;
    }
    public List<String> getDroneCarriageType() {
        return droneCarriageType;
    }
}
