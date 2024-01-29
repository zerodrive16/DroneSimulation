package org.example.GUI.Utility;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import org.example.API_Properties.DroneDynamicsData;
import org.example.Config;


import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ReverseGeo {

    private static final Logger logger = Logger.getLogger(ReverseGeo.class.getName());

    /**
     *  Performs reverse geocoding for drone locations.
     *
     * @param droneDynamicData The drone dynamic data containing latitude and longitude information.
     * @return A CompletableFuture that resolves to an ArrayList of formatted location results.
     */
    public CompletableFuture<ArrayList<String>> performReverseGeoAsync(DroneDynamicsData.ReturnDroneDynamicData droneDynamicData) {
        // Get the API key for Google Maps
        String apiKey = Config.geoToken;

        return CompletableFuture.supplyAsync(() -> {
            // Create the list to store location results
            ArrayList<String> localResultLocation = new ArrayList<>();

            try {
                // Create a GeoApiContext with the API key
                GeoApiContext context = new GeoApiContext.Builder()
                        .apiKey(apiKey)
                        .build();

                // Iterate through all drone location
                for (int i = 0; i < droneDynamicData.getDroneLongitude().size(); i++) {
                    try {
                        // Get latitude and longitude of the drone location
                        Double reverseLongitude = droneDynamicData.getDroneLongitude().get(i);
                        Double reverseLatitude = droneDynamicData.getDroneLatitude().get(i);
                        LatLng location = new LatLng(reverseLatitude, reverseLongitude);

                        // Perform reverse geocoding
                        GeocodingResult[] results = GeocodingApi.reverseGeocode(context, location).await();

                        // Check if any data are saves in results
                        if (results.length > 0) {
                            // Extract postal code and city from geocoding results
                            String postalCode = "";
                            String city = "";
                            for (AddressComponent component : results[0].addressComponents) {
                                if (component.types[0] == AddressComponentType.POSTAL_CODE) {
                                    postalCode = component.longName;
                                } else if (component.types[0] == AddressComponentType.LOCALITY) {
                                    city = component.longName;
                                }
                            }
                            localResultLocation.add(postalCode + ", " + city);
                        } else {
                            logger.warning("No results found for location at index " + i);
                        }
                    } catch (Exception e) {
                        // Log the exception during reverse geocoding and show the index
                        logger.log(Level.SEVERE, "Exception during reverse geocoding for location at index " + i, e);
                    }
                }
            } catch (Exception e) {
                // Log the exception when creating GeoApiContext
                logger.log(Level.SEVERE, "Exception during GeoApiContext creation", e);
            }
            return localResultLocation;
        });
    }
}