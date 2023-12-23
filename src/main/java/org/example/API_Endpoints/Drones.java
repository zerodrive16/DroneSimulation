package org.example.API_Endpoints;


import com.google.gson.Gson;
import org.example.API_Properties.DronesData;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.IOException;
import java.util.ArrayList;
import static org.example.Config.token;

public class Drones{
    public DronesData.ReturnDroneData APIDrones(){
        // declaring ArrayLists to store the data
        ArrayList<String> droneID = new ArrayList<>();
        ArrayList<String> droneTypeURL = new ArrayList<>();
        ArrayList<String> droneCreate = new ArrayList<>();
        ArrayList<String> droneSerialnumber = new ArrayList<>();
        ArrayList<String> droneCarriageWeight = new ArrayList<>();
        ArrayList<String> droneCarriageType = new ArrayList<>();

        // Declaring the pagination URL and Gson
        String paginationURL = "http://dronesim.facets-labs.com/api/drones/?format=json";
        Gson gson = new Gson();

        try{
            while(paginationURL != null) {
                String response = APIRequest(paginationURL);

                // Returns the list that is encapsulated inside the result array (JSON)
                DronesData.DroneResult apiResponse = gson.fromJson(response, DronesData.DroneResult.class);

                storeAPIResponse(apiResponse, droneID, droneTypeURL, droneCreate, droneSerialnumber, droneCarriageWeight, droneCarriageType);
                paginationURL = apiResponse != null ? apiResponse.getNext() : null;
            } // Catching the Exceptions
        } catch(IOException ex1){
            System.err.println("IOException error: " + ex1.getMessage());
            ex1.printStackTrace();
        }
        // create new return instance to send the data to the constructor and store them temporarily inside the ArrayList
        return new DronesData.ReturnDroneData(droneID, droneTypeURL, droneCreate, droneSerialnumber, droneCarriageWeight, droneCarriageType);
    }


    // Retrieving token and also check if null or empty and throw exception
    private String retrieveToken() {
        if(token == null || token.isEmpty()){
            throw new IllegalStateException("Token is either null or empty");
        }
        return token;
    }

    // Preparing the REST API request to the webserver with GET (URL, token, CRUD operation etc...)
    private String APIRequest(String url) throws IOException{
        // REST API request to the webserver
        HttpURLConnection con;
        con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestProperty("Authorization", retrieveToken());
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "XYZ");


        // Read and constructs the API response and format to String
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return String.valueOf(response);
    }


    // Fetching the data from the apiResponse and store them in the ArrayList
    private void storeAPIResponse(DronesData.DroneResult apiResponse, ArrayList<String> droneID, ArrayList<String> droneTypeURL, ArrayList<String> droneCreate, ArrayList<String> droneSerialnumber,
                                  ArrayList<String> droneCarriageWeight, ArrayList<String> droneCarriageType) {
        if (apiResponse != null && apiResponse.getDroneResults() != null) {
            for (DronesData.Drone drone : apiResponse.getDroneResults()) {
                droneID.add(drone.getId());
                droneTypeURL.add(drone.getDronetype());
                droneCreate.add(drone.getCreated());
                droneSerialnumber.add(drone.getSerialnumber());
                droneCarriageWeight.add(drone.getCarriage_weight());
                droneCarriageType.add(drone.getCarriage_type());
            }
        } else {
            System.err.println("Result error / Null");
        }
    }
}