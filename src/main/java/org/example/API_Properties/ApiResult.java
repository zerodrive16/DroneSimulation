package org.example.API_Properties;

import java.util.List;

// File is needed for accessing the result inside the Json object
public class ApiResult {
    private List<Drone> results;

    public List<Drone> getResults(){
        return results;
    }
}
