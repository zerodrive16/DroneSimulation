package org.example.API_Endpoints;

import org.example.API_Properties.ReturnDroneDynamicData;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class DroneDynamics {
    /*
    public ReturnDroneDynamicData APIDroneDynamics(){
        try{
            final String token = "Token 64f0e472cd96156e94da3c3e066c8d89e8b88f72";
            URL url = new URL("https://dronesim.facets-labs.com/api/dronedynamic/?format=json");
            HttpURLConnection con;
            con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Authorization", token);
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "XYZ");

            int responseCode = con.getResponseCode();
            System.out.println("ResponseCode: " + responseCode);

        } catch(MalformedURLException ex1){
            System.err.println("MalformedURLException: "  ex1.getMessage());
            ex1.printStackTrace();
        } catch(ProtocolException ex2){
            System.err.println("ProtocolException: " + ex2.getMessage());
            ex2.printStackTrace();
        } catch(IOException ex3){
            System.err.println("IOException: " + ex3.getMessage());
            ex3.printStackTrace();
        }

        return new ReturnDroneDynamicData();
    }
    */

}
