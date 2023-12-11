package org.example.API_Endpoints;

import org.example.API_Properties.ReturnDroneTypeData;

import java.io.IOException;
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
            URL url = new URL("http://dronesim.facets-labs.com/api/dronetypes/60/?format=json");
            HttpURLConnection con;
            con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Authorization", token);
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "XYZ");

            int responseCode = con.getResponseCode();
            System.out.println(responseCode);

        } catch(MalformedURLException ex1){
            System.err.println("MalformedURLException: " + ex1.getMessage());
            ex1.printStackTrace();
        } catch(ProtocolException ex2){
            System.err.println("ProtocolException: " + ex2.getMessage());
            ex2.printStackTrace();
        } catch(IOException ex3){
            System.err.println("IOException: " + ex3.getMessage());
            ex3.printStackTrace();
        } catch(Exception ex4){
            System.err.println("Exception: " + ex4.getMessage());
            ex4.printStackTrace();
        }

        return new ReturnDroneTypeData(droneManufacturer, droneTypeName, droneWeight, droneMaxSpeed,
                droneBatteryCapacity, droneControlRange, droneMaxCarriage);
    }
}
