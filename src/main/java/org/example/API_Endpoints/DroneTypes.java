package org.example.API_Endpoints;

import com.google.gson.Gson;
import org.example.API_Properties.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.example.Config.token;

public class DroneTypes {

    private final ArrayList<String> droneManufacturer = new ArrayList<>();
    private final ArrayList<String> droneTypeName = new ArrayList<>();
    private final ArrayList<Integer> droneWeight = new ArrayList<>();
    private final ArrayList<Integer> droneMaxSpeed = new ArrayList<>();
    private final ArrayList<Integer> droneBatteryCapacity = new ArrayList<>();
    private final ArrayList<Integer> droneControlRange = new ArrayList<>();
    private final ArrayList<Integer> droneMaxCarriage = new ArrayList<>();

    private static final ExecutorService EHexecutor = Executors.newFixedThreadPool(10);

    public CompletableFuture<DroneTypesData.ReturnDroneTypeData> APIDroneTypesAsync() {
        CompletableFuture<DroneTypesData.ReturnDroneTypeData> resultFuture = new CompletableFuture<>();

        new Drones().APIDronesAsync().thenAccept(returnData -> {
            ArrayList<Integer> droneID = returnData.getDroneID();
            ArrayList<CompletableFuture<Void>> futures = new ArrayList<>();

            for (int temp_id : droneID) {
                futures.add(APIRequestAsync(temp_id).thenAccept(response -> {
                    Gson gson = new Gson();
                    DroneTypesData.DroneType apiResponse = gson.fromJson(response, DroneTypesData.DroneType.class);

                    storeAPIResponse(apiResponse);
                }));
            }

            CompletableFuture<Void> chainFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            chainFutures.thenRun(() -> {
                resultFuture.complete(new DroneTypesData.ReturnDroneTypeData(droneManufacturer, droneTypeName, droneWeight, droneMaxSpeed,
                        droneBatteryCapacity, droneControlRange, droneMaxCarriage));
            }).exceptionally(ex -> {
                resultFuture.completeExceptionally(ex);
                return null;
            });
        });

        return resultFuture;
    }

    private CompletableFuture<String> APIRequestAsync(Integer x){
        return CompletableFuture.supplyAsync(() -> {
            try {
                URL url = new URL("http://dronesim.facets-labs.com/api/dronetypes/" + x + "/?format=json");
                HttpURLConnection con;
                con = (HttpURLConnection) url.openConnection();
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

                return response.toString();
            } catch(IOException e){
                throw new RuntimeException(e);
            }
        }, EHexecutor);
    }

    private void storeAPIResponse(DroneTypesData.DroneType apiResponse) {
        if (apiResponse != null) {
            droneManufacturer.add(apiResponse.getManufacturer());
            droneTypeName.add(apiResponse.getTypename());
            droneWeight.add(apiResponse.getWeight());
            droneMaxSpeed.add(apiResponse.getMax_Speed());
            droneBatteryCapacity.add(apiResponse.getBattery_Capacity());
            droneControlRange.add(apiResponse.getControl_Range());
            droneMaxCarriage.add(apiResponse.getMax_Carriage());
        } else {
            System.err.println("Result error / Null");
        }
    }

    // checking if token is either null or empty
    private String retrieveToken() {
        if(token == null || token.isEmpty()){
            throw new IllegalStateException("Token is either null or empty");
        }
        return token;
    }
}
