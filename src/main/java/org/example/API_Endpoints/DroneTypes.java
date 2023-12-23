package org.example.API_Endpoints;

import com.google.gson.Gson;
import org.example.API_Properties.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import static org.example.Config.token;

public class DroneTypes {
    public DroneTypesData.ReturnDroneTypeData APIDroneTypes(){

        ArrayList<String> droneManufacturer = new ArrayList<>();
        ArrayList<String> droneTypeName = new ArrayList<>();
        ArrayList<String> droneWeight = new ArrayList<>();
        ArrayList<String> droneMaxSpeed = new ArrayList<>();
        ArrayList<String> droneBatteryCapacity = new ArrayList<>();
        ArrayList<String> droneControlRange = new ArrayList<>();
        ArrayList<String> droneMaxCarriage = new ArrayList<>();

        Gson gson = new Gson();
        Drones droneAPI = new Drones();
        DronesData.ReturnDroneData returnData = droneAPI.APIDrones();
        ArrayList<String> droneID = returnData.getDroneID();

        try{
            for (int x = 0; x < droneID.size(); x++) {
                String response = APIRequest(x, droneID);

                DroneTypesData.DroneType apiResponse = gson.fromJson(response, DroneTypesData.DroneType.class);

                storeAPIResponse(apiResponse, droneManufacturer, droneTypeName, droneWeight, droneMaxSpeed, droneBatteryCapacity, droneControlRange, droneMaxCarriage);
            }

        } catch(MalformedURLException ex1){
            System.err.println("MalformedURLException: " + ex1.getMessage());
            ex1.printStackTrace();
        } catch(ProtocolException ex2){
            System.err.println("ProtocolException: " + ex2.getMessage());
            ex2.printStackTrace();
        } catch(IOException ex3){
            System.err.println("IOException: " + ex3.getMessage());
            ex3.printStackTrace();
        } finally {
            System.out.println("Process Completed!");
        }

        return new DroneTypesData.ReturnDroneTypeData(droneManufacturer, droneTypeName, droneWeight, droneMaxSpeed,
                droneBatteryCapacity, droneControlRange, droneMaxCarriage);
    }

    // checking if token is either null or empty
    private String retrieveToken() {
        if(token == null || token.isEmpty()){
            throw new IllegalStateException("Token is either null or empty");
        }
        return token;
    }

    private String APIRequest(Integer x, ArrayList<String> droneID) throws IOException {
        URL url = new URL("http://dronesim.facets-labs.com/api/dronetypes/" + droneID.get(x) + "/?format=json");
        HttpURLConnection con;
        con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Authorization", retrieveToken());
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "XYZ");


        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return String.valueOf(response);
    }

    private void storeAPIResponse(DroneTypesData.DroneType apiResponse, ArrayList<String> droneManufacturer, ArrayList<String> droneTypeName, ArrayList<String> droneWeight, ArrayList<String> droneMaxSpeed, ArrayList<String> droneBatteryCapacity, ArrayList<String> droneControlRange, ArrayList<String> droneMaxCarriage) {
        if (apiResponse != null) {
            droneManufacturer.add(apiResponse.getManufacturer());
            droneTypeName.add(apiResponse.getTypename());
            droneWeight.add(apiResponse.getWeight());
            droneMaxSpeed.add(apiResponse.getMax_Speed());
            droneBatteryCapacity.add(apiResponse.getBattery_Capacity());
            droneControlRange.add(apiResponse.getControl_Range());
            droneMaxCarriage.add(apiResponse.getMax_Carriage());
        } else {
            System.err.println("Result error / Null");
        }
    }
}
