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
    public ReturnDroneTypeData APIDroneTypes(){
        ArrayList<String> droneManufacturer = new ArrayList<>();
        ArrayList<String> droneTypeName = new ArrayList<>();
        ArrayList<String> droneWeight = new ArrayList<>();
        ArrayList<String> droneMaxSpeed = new ArrayList<>();
        ArrayList<String> droneBatteryCapacity = new ArrayList<>();
        ArrayList<String> droneControlRange = new ArrayList<>();
        ArrayList<String> droneMaxCarriage = new ArrayList<>();

        try{
            URL url = new URL("http://dronesim.facets-labs.com/api/dronetypes/61/?format=json");
            HttpURLConnection con;
            con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Authorization", token);
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "XYZ");

            int responseCode = con.getResponseCode();
            System.out.println(responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
            in.close();

            Gson gson = new Gson();
            DroneTypeResult apiResponse = gson.fromJson(response.toString(), DroneTypeResult.class);

            if(apiResponse != null && apiResponse.getDroneTypeResults() != null){
                for(DroneType droneType : apiResponse.getDroneTypeResults()){
                    droneManufacturer.add(droneType.getManufacturer());
                    droneTypeName.add(droneType.getTypename());
                    droneWeight.add(droneType.getWeight());
                    droneMaxSpeed.add(droneType.getMax_Speed());
                    droneBatteryCapacity.add(droneType.getBattery_Capacity());
                    droneControlRange.add(droneType.getControl_Range());
                    droneMaxCarriage.add(droneType.getMax_Carriage());
                }
            } else {
                System.err.println("Result error / Null");
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

        return new ReturnDroneTypeData(droneManufacturer, droneTypeName, droneWeight, droneMaxSpeed,
                droneBatteryCapacity, droneControlRange, droneMaxCarriage);
    }
}
