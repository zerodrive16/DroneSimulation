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

public class ReverseGeo {

    /**
     * @param droneDynamicData from {@link DroneDynamicsData.ReturnDroneDynamicData} which takes the fetched API data
     * @return localResultLocation It returns the conversion of longitude and latitude to addresses in lists
     * */

    public CompletableFuture<ArrayList<String>> performReverseGeoAsync(DroneDynamicsData.ReturnDroneDynamicData droneDynamicData) {
        String apiKey = Config.geoToken;

        // Create a GeoApiContext with the API key
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();

        return CompletableFuture.supplyAsync(() -> {
            ArrayList<String> localResultLocation = new ArrayList<>();

            for (int i = 0; i < droneDynamicData.getDroneLongitude().size(); i++) {
                Double reverseLongitude = droneDynamicData.getDroneLongitude().get(i);
                Double reverseLatitude = droneDynamicData.getDroneLatitude().get(i);
                LatLng location = new LatLng(reverseLatitude, reverseLongitude);

                try {
                    // Perform reverse geocoding
                    GeocodingResult[] results = GeocodingApi.reverseGeocode(context, location).await();

                    // Check if data are saved in results
                    if (results.length > 0) {
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
                        System.err.println("No results found");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return localResultLocation;
        });
    }
}
