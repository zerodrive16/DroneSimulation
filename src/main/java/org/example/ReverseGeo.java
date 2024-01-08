package org.example;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

public class ReverseGeo {

    public static void main(String[] args) {
        // Replace "YOUR_API_KEY" with your actual API key
        String apiKey = "AIzaSyA0IA-lTzhcjrSk_SfKwlxT_eGx7CtzVf4";

        // Create a GeoApiContext with the API key
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();

        // Define the latitude and longitude
        double latitude = 37.7749; // Replace with your desired latitude
        double longitude = -122.4194; // Replace with your desired longitude

        // Create a LatLng object
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
        }
    }
}