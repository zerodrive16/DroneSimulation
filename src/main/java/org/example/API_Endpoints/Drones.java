package org.example.API_Endpoints;

// HTTP Request properties
import com.google.gson.Gson;
import org.example.API_Properties.ApiResult;
import org.example.API_Properties.Drone;
import org.example.API_Properties.ReturnDroneData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;

// Exception Library
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;

public class Drones{
    public ReturnDroneData APIDrones(){
        ArrayList<String> droneID = new ArrayList<>();
        ArrayList<String> droneCreate = new ArrayList<>();
        ArrayList<String> droneSerialnumber = new ArrayList<>();
        ArrayList<String> droneCarriageWeight = new ArrayList<>();
        ArrayList<String> droneCarriageType = new ArrayList<>();
        try{
            // REST API request to the webserver
            final String token = "Token 64f0e472cd96156e94da3c3e066c8d89e8b88f72";
            URL url = new URL("https://dronesim.facets-labs.com/api/drones/?format=json");
            HttpURLConnection con;
            con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Authorization", token);
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "XYZ");

            // Output from responseCode
            int responseCode = con.getResponseCode();
            System.out.println(responseCode);

            // Read and constructs the API response and format to String
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
            in.close();

            // Gson is used to convert the String to an object and vise versa
            Gson gson = new Gson();
            ApiResult apiResponse = gson.fromJson(response.toString(), ApiResult.class);

            // We use the ApiResponse and Drone files to tell Gson how the Json output format is formatted, so we can get the specific elements
            if(apiResponse != null && apiResponse.getResults() != null){
                for(Drone drone : apiResponse.getResults()){
                    droneID.add(drone.getId());
                    droneCreate.add(drone.getCreated());
                    droneSerialnumber.add(drone.getSerialnumber());
                    droneCarriageWeight.add(drone.getCarriage_weight());
                    droneCarriageType.add(drone.getCarriage_type());
                }
            } else {
                System.err.println("Result error / Null");
            }

            // Catching the Exceptions
        } catch(MalformedURLException ex1){
            System.err.println("MalformedURLException: " + ex1.getMessage());
            ex1.printStackTrace();
        } catch(ProtocolException ex2) {
            System.err.println("ProtocolException: " + ex2.getMessage());
            ex2.printStackTrace();
        } catch(IOException ex3){
            System.err.println("IOException: " + ex3.getMessage());
            ex3.printStackTrace();
        } finally{
            System.out.println("Process completed");
        }
        // bundling ArrayList inside constructor
        return new ReturnDroneData(droneID, droneCreate, droneSerialnumber, droneCarriageWeight, droneCarriageType);
    }
}