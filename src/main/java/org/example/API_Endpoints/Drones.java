package org.example.API_Endpoints;

import com.google.gson.Gson;
import org.example.API_Properties.DronesData;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static org.example.Config.token;

public class Drones{
    // declaring ArrayLists which store the API data temporarily
    private final ArrayList<Integer> droneID = new ArrayList<>();
    private final ArrayList<String> droneTypeURL = new ArrayList<>();
    private final ArrayList<String> droneCreate = new ArrayList<>();
    private final ArrayList<String> droneSerialnumber = new ArrayList<>();
    private final ArrayList<Integer> droneCarriageWeight = new ArrayList<>();
    private final ArrayList<String> droneCarriageType = new ArrayList<>();

    // Define the amount of threads the program uses (work Async)
    private static final ExecutorService EHexecutor = Executors.newFixedThreadPool(10);

    // writing Async code and store resultFuture inside CompletableFuture
    public CompletableFuture<DronesData.ReturnDroneData> APIDronesAsync() {
        CompletableFuture<DronesData.ReturnDroneData> resultFuture = new CompletableFuture<>();

        // calling processAsync which takes Pagination and Recursion and prepare Asynchronous Programming
        String paginationUrl= "http://dronesim.facets-labs.com/api/drones/?format=json";
        processAsync(paginationUrl, resultFuture);
        return resultFuture;
    }

    // Pagination and Recursion
    private void processAsync(String paginationUrl, CompletableFuture<DronesData.ReturnDroneData> resultFuture){
        // when no more entities are left we leave the program and transfer its data to the constructor
        if(paginationUrl == null){
            resultFuture.complete(new DronesData.ReturnDroneData(droneID, droneTypeURL, droneCreate, droneSerialnumber, droneCarriageWeight, droneCarriageType));
            return;
        }
        // If data is fetched, immediately continue with the rest of the code
        APIRequestAsync(paginationUrl).thenAccept(response -> {
            Gson gson = new Gson();
            DronesData.DroneResult apiResponse = gson.fromJson(response, DronesData.DroneResult.class);

            // storing data to the constructor
            storeAPIResponse(apiResponse);

            // proceeds next page if entity reached end
            processAsync(apiResponse.getNext(), resultFuture);
            // Error handling the Asynchronous programming
        }).exceptionally(ex -> {
            resultFuture.completeExceptionally(ex);
            return null;
        });
    }

    // Creating a separate thread which runs the Asynchronous HTTP request
    private CompletableFuture<String> APIRequestAsync(String url) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                HttpURLConnection con;
                con = (HttpURLConnection) new URL(url).openConnection();
                con.setRequestProperty("Authorization", retrieveToken());
                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", "XYZ");

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return String.valueOf(response);
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        }, EHexecutor);
    }

    // function for storing the data to the constructor in DronesData.DroneResult
    private void storeAPIResponse(DronesData.DroneResult apiResponse) {
        if (apiResponse != null && apiResponse.getDroneResults() != null) {
            for (DronesData.Drone drone : apiResponse.getDroneResults()) {
                droneID.add(drone.getId());
                droneTypeURL.add(drone.getDronetype());
                droneCreate.add(drone.getCreated());
                droneSerialnumber.add(drone.getSerialnumber());
                droneCarriageWeight.add(drone.getCarriage_weight());
                droneCarriageType.add(drone.getCarriage_type());
            }
        } else {
            System.err.println("Result error / Null");
        }
    }

    // get token from Config.java and check for possible errors
    private String retrieveToken() {
        if(token == null || token.isEmpty()){
            throw new IllegalStateException("Token is either null or empty");
        }
        return token;
    }
}