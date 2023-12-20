package org.example.API_Endpoints;

import org.example.API_Properties.DroneDynamicResult;
import org.example.API_Properties.ReturnDroneDynamicData;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.example.API_Properties.DroneResult;
import org.example.API_Properties.DroneDynamic;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;

import static org.example.Config.token;


public class DroneDynamics {
    public ReturnDroneDynamicData APIDroneDynamics(){
        ArrayList<String> droneURL = new ArrayList<>();
        ArrayList<String> droneTimestamp = new ArrayList<>();
        ArrayList<String> droneSpeed = new ArrayList<>();
        ArrayList<String> droneAlignRoll = new ArrayList<>();
        ArrayList<String> droneAlignPitch = new ArrayList<>();
        ArrayList<String> droneAlignYaw = new ArrayList<>();
        ArrayList<String> droneLongitude = new ArrayList<>();
        ArrayList<String> droneLatitude = new ArrayList<>();
        ArrayList<String> droneBatteryStatus = new ArrayList<>();
        ArrayList<String> droneLastSeen = new ArrayList<>();
        ArrayList<String> droneStatus = new ArrayList<>();

        try {
            URL url = new URL("https://dronesim.facets-labs.com/api/dronedynamics/?format=json");
            HttpURLConnection con;
            con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Authorization", token);
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "XYZ");
            int responseCode = con.getResponseCode();
            System.out.println(responseCode);
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String in;
            StringBuilder response = new StringBuilder();

            while ((in = br.readLine()) != null) {
                response.append(in);
            }
            br.close();

            Gson g = new Gson();
            DroneDynamicResult api = g.fromJson(response.toString(), DroneDynamicResult.class);
            DroneDynamic drone = new DroneDynamic();
            if (api != null && api.getDroneDynamicsResults() != null){
                for (drone : api.getDroneDynamicsResults()){
                    droneURL.add(drone.getDroneURL());
                    droneTimestamp.add(drone.getDroneTimestamp());
                    droneSpeed.add(drone.getDroneSpeed());
                    droneAlignRoll.add(drone.getDroneAlignRoll());
                    droneAlignPitch.add(drone.getDroneAlignPitch());
                    droneAlignYaw.add(drone.getDroneYaw());
                    droneLongitude.add(drone.getDroneLongitude())
                }
            }

        } catch(MalformedURLException e1){
            System.err.println("MalformedURLException: " + e1.getMessage());
            e1.printStackTrace();
        }catch (ProtocolException e2){
            System.err.println("ProtocolException: " + e2.getMessage());
        }catch (IOException e3) {
            System.err.println("IOExeption: " + e3.getMessage());
            e3.printStackTrace();
        }










        return new ReturnDroneDynamicData(droneURL, droneTimestamp, droneSpeed, droneAlignRoll, droneAlignPitch, droneAlignYaw,
                droneLongitude, droneLatitude, droneBatteryStatus, droneLastSeen, droneStatus);
    }
}
