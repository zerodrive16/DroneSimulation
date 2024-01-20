package org.example;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import org.example.API_Properties.DroneDynamicsData;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class ReverseGeo {
    private static final ArrayList<String> resultLocation = new ArrayList<>();

    public CompletableFuture<ArrayList<String>> performReverseGeoAsync(DroneDynamicsData.ReturnDroneDynamicData droneDynamicData) {
        String apiKey = "AIzaSyA0IA-lTzhcjrSk_SfKwlxT_eGx7CtzVf4";

        // Create a GeoApiContext with the API key
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();

        return CompletableFuture.supplyAsync(() -> {
            for (int i = 0; i < droneDynamicData.getDroneLongitude().size(); i++) {
                Double reverseLongitude = droneDynamicData.getDroneLongitude().get(i);
                Double reverseLatitude = droneDynamicData.getDroneLatitude().get(i);
                LatLng location = new LatLng(reverseLatitude, reverseLongitude);

                try {
                    // Perform reverse geocoding
                    GeocodingResult[] results = GeocodingApi.reverseGeocode(context, location).await();

                    // Check if data are saved in results
                    if (results.length > 0) {
                        String formattedAddress = results[0].formattedAddress;
                        resultLocation.add(formattedAddress);
                    } else {
                        System.err.println("No results found");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return resultLocation;
        });
    }
}


