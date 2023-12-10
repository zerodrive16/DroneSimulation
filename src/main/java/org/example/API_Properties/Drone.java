package org.example.API_Properties;

// File is used to access the JSON objects within the result array
public class Drone {
    private String id;
    private String dronetype;
    private String created;
    private String serialnumber;
    private String carriage_weight;
    private String carriage_type;

    public String getId(){
        return id;
    }
    public String getDronetype(){
        return dronetype;
    }
    public String getCreated(){
        return created;
    }
    public String getSerialnumber(){
        return serialnumber;
    }
    public String getCarriage_weight(){
        return carriage_weight;
    }
    public String getCarriage_type(){
        return carriage_type;
    }
}
