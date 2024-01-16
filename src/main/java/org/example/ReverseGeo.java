package org.example;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import org.example.API_Endpoints.DroneDynamics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.exit;


public class ReverseGeo {
    private static final Map<Integer, ArrayList<String>> resultLocation = new HashMap<>();

    public static void main(String[] args) {

        // Replace "YOUR_API_KEY" with your actual API key
        String apiKey = "AIzaSyA0IA-lTzhcjrSk_SfKwlxT_eGx7CtzVf4";

        // Create a GeoApiContext with the API key
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();

        new DroneDynamics().APIBuildAsync().thenAccept(response ->{
            for (Integer droneId : response.getDroneLongitude().keySet()) {
                System.out.println("Drone ID: " + droneId);
                ArrayList<Double> longitude = response.getDroneLongitude().get(droneId);
                ArrayList<Double> latitude = response.getDroneLatitude().get(droneId);
                System.out.println();

                for (int i = 0; i < longitude.size(); i++) {

                    Double ReverseLongitude = longitude.get(i);
                    Double ReverseLatitude = latitude.get(i);

                    LatLng location = new LatLng(ReverseLatitude, ReverseLongitude);

                    try {
                        // Perform reverse geocoding
                        GeocodingResult[] results = GeocodingApi.reverseGeocode(context, location).await();

                        // Print the formatted address
                        if (results.length > 0) {
                            String formattedAddress = results[0].formattedAddress;
                            resultLocation.computeIfAbsent(droneId, k -> new ArrayList<>()).add(formattedAddress);
                            System.out.println("Reverse Geocoding Result: " + resultLocation.get(droneId));
                            //clear the context of resultLocation
                            resultLocation.computeIfPresent(droneId, (k, v) -> {
                                v.clear();
                                return v;
                            });
                        } else {
                            System.out.println("No results found");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

            exit(1);
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