package org.example.API_Properties;

import java.util.List;

// File is needed for accessing the result inside the Json object
public class DroneResult {
    private List<Drone> results;

    public List<Drone> getDroneResults(){
        return results;
    }
}
