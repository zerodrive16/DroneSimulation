package org.example;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import org.example.API_Endpoints.DroneDynamics;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;


public class ReverseGeo {
    public static final ArrayList<String> resultLocation = new ArrayList<>();

    public CompletableFuture<ArrayList<String>> performReverseGeo() {
        // Replace "YOUR_API_KEY" with your actual API key
        String apiKey = "AIzaSyA0IA-lTzhcjrSk_SfKwlxT_eGx7CtzVf4";

        // Create a GeoApiContext with the API key
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();

        CompletableFuture<Void> geocodingFuture = new CompletableFuture<>();

        new DroneDynamics().APIBuildAsync().thenAccept(response -> {
            for (int i = 0; i < response.getDroneLongitude().size(); i++) {
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
                    } else {
                        System.err.println("No results found");
                        throw new RuntimeException();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            geocodingFuture.complete(null); // Signal that geocoding is complete
        });

        // Return a CompletableFuture that signals when geocoding is complete
        return geocodingFuture.thenApply(ignored -> resultLocation);
    }
}