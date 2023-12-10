package org.example.API_Properties;

import java.util.ArrayList;

public class ReturnDroneData {
    final private ArrayList<String> data_droneID;
    final private ArrayList<String> data_droneType;
    final private ArrayList<String> data_droneCreate;
    final private ArrayList<String> data_droneSerialnumber;
    final private ArrayList<String> data_droneCarriageWeight;
    final private ArrayList<String> data_droneCarriageType;

    public ReturnDroneData(ArrayList<String> droneID, ArrayList<String> droneTypeURL, ArrayList<String> droneCreate, ArrayList<String> droneSerialnumber,
                                ArrayList<String> droneCarriageWeight, ArrayList<String> droneCarriageType){
        this.data_droneID = droneID;
        this.data_droneType = droneTypeURL;
        this.data_droneCreate = droneCreate;
        this.data_droneSerialnumber = droneSerialnumber;
        this.data_droneCarriageWeight = droneCarriageWeight;
        this.data_droneCarriageType = droneCarriageWeight;
    }

    public ArrayList<String> getDroneID(){
        return data_droneID;
    }
    public ArrayList<String> getDroneTypeURL() { return data_droneType; }
    public ArrayList<String> getDroneCreate(){
        return data_droneCreate;
    }
    public ArrayList<String> getDroneSerialnumber(){
        return data_droneSerialnumber;
    }
    public ArrayList<String> getDroneCarriageWeight(){
        return data_droneCarriageWeight;
    }
    public ArrayList<String> getDroneCarriageType(){
        return data_droneCarriageType;
    }

}
