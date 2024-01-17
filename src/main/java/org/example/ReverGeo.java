package org.example;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import org.example.API_Endpoints.DroneDynamics;

import java.util.ArrayList;


public class ReverseGeo {
    public static final ArrayList<String> resultLocation = new ArrayList<>();

    public static void main(String[] args) {


        // Replace "YOUR_API_KEY" with your actual API key
        String apiKey = "AIzaSyA0IA-lTzhcjrSk_SfKwlxT_eGx7CtzVf4";

        // Create a GeoApiContext with the API key
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();

        new DroneDynamics().APIBuildAsync().thenAccept(response ->{

            for(int i = 0; i< response.getDroneLongitude().size(); i++){
                Double ReverseLongitude = response.getDroneLongitude().get(i);
                Double ReverseLatitude = response.getDroneLatitude().get(i);
                LatLng location = new LatLng(ReverseLatitude, ReverseLongitude);
                try {
                    // Perform reverse geocoding
                    GeocodingResult[] results = GeocodingApi.reverseGeocode(context, location).await();

                    // Print the formatted address
                    if (results.length > 0) {
                        String formattedAddress = results[0].formattedAddress;
                        resultLocation.add(formattedAddress);
                        System.out.println("Reverse Geocoding Result: " + resultLocation.get(i));
                    } else {
                        System.out.println("No results found");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        // Define the latitude and longitude
        //double latitude = 37.7749; // Replace with your desired latitude
        //double longitude = -122.4194; // Replace with your desired longitude

        // Create a LatLng object
        /*
        LatLng location = new LatLng(latitude, longitude);

        try {
            // Perform reverse geocoding
            GeocodingResult[] results = GeocodingApi.reverseGeocode(context, location).await();

            // Print the formatted address
            if (results.length > 0) {
                String formattedAddress = results[0].formattedAddress;
                System.out.println("Reverse Geocoding Result: " + formattedAddress);
            } else {
                System.out.println("No results found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } */
    }
}