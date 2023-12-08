package org.example.API_Properties;

import java.util.ArrayList;

public class ReturnDroneData {
    final private ArrayList<String> data_droneID;
    final private ArrayList<String> data_droneCreate;
    final private ArrayList<String> data_droneSerialnumber;
    final private ArrayList<String> data_droneCarriageWeight;
    final private ArrayList<String> data_droneCarriageType;

    public ReturnDroneData(ArrayList<String> data_droneID, ArrayList<String> data_droneCreate, ArrayList<String> data_droneSerialnumber,
                                ArrayList<String> data_droneCarriageWeight, ArrayList<String> data_droneCarriageType){
        this.data_droneID = data_droneID;
        this.data_droneCreate = data_droneCreate;
        this.data_droneSerialnumber = data_droneSerialnumber;
        this.data_droneCarriageWeight = data_droneCarriageWeight;
        this.data_droneCarriageType = data_droneCarriageType;
    }

    public ArrayList<String> getDroneID(){
        return data_droneID;
    }
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
