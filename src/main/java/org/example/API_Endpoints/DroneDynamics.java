package org.example.API_Endpoints;

import org.example.API_Properties.DroneDynamicsData;
import com.google.gson.Gson;
import org.example.API_Properties.DroneTypesData;
import org.example.API_Properties.DronesData;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class DroneDynamics extends Abs_APIBuilding<DroneDynamicsData.ReturnDroneDynamicData> {
   private final Map<Integer, ArrayList<String>> droneURL = new HashMap<>();
   private final Map<Integer, ArrayList<String>> droneTimestamp = new HashMap<>();
   private final Map<Integer, ArrayList<String>> droneSpeed = new HashMap<>();
   private final Map<Integer, ArrayList<String>> droneAlignRoll = new HashMap<>();
   private final Map<Integer, ArrayList<String>> droneAlignPitch = new HashMap<>();
   private final Map<Integer, ArrayList<String>> droneAlignYaw = new HashMap<>();
   private final Map<Integer, ArrayList<String>> droneLongitude = new HashMap<>();
   private final Map<Integer, ArrayList<String>> droneLatitude = new HashMap<>();
   private final Map<Integer, ArrayList<String>> droneBatteryStatus = new HashMap<>();
   private final Map<Integer, ArrayList<String>> droneLastSeen = new HashMap<>();
   private final Map<Integer, ArrayList<String>> droneStatus = new HashMap<>();

    @Override
    public CompletableFuture<DroneDynamicsData.ReturnDroneDynamicData> APIBuildAsync() {
        CompletableFuture<DroneDynamicsData.ReturnDroneDynamicData> resultFuture = new CompletableFuture<>();

        new Drones().APIBuildAsync().thenAccept(response -> {
            ArrayList<Integer> droneID = response.getDroneID();

            if(droneID != null && !droneID.isEmpty()) {
                String url = "http://dronesim.facets-labs.com/api/" + droneID.get(0) + "/dynamics/?format=json";
                processAsync(url, resultFuture);
            } else {
                resultFuture.completeExceptionally(new IllegalStateException("Drone ID null or empty"));
            }
        }).exceptionally(ex -> {
            resultFuture.completeExceptionally(ex);
            return null;
        });

        return resultFuture;
    }

    @Override
    protected void processAsync(String paginationUrl, CompletableFuture<DroneDynamicsData.ReturnDroneDynamicData> resultFuture) {

        APIRequestAsync(paginationUrl).thenAccept(response -> {
            Gson gson = new Gson();
            DroneDynamicsData.DroneDynamicResult apiResponse = gson.fromJson(response, DroneDynamicsData.DroneDynamicResult.class);

            storeAPIResponse(apiResponse, resultFuture);

        }).exceptionally(ex -> {
            resultFuture.completeExceptionally(ex);
            return null;
        });
    }
    private void storeAPIResponse(DroneDynamicsData.DroneDynamicResult apiResponse, CompletableFuture<DroneDynamicsData.ReturnDroneDynamicData> resultFuture) {
        if (apiResponse != null && apiResponse.getResults() != null) {
            for (DroneDynamicsData.DroneDynamic dronedynamic : apiResponse.getResults()) {
                if(droneURL.getOrDefault(71, new ArrayList<>()).size() >= 3) {
                    resultFuture.complete(new DroneDynamicsData.ReturnDroneDynamicData(droneURL, droneTimestamp, droneSpeed, droneAlignRoll, droneAlignPitch,
                            droneAlignYaw, droneLongitude, droneLatitude, droneBatteryStatus, droneLastSeen, droneStatus));
                    return;
                }
                droneURL.computeIfAbsent(71, k -> new ArrayList<>()).add(dronedynamic.getDrone());
                droneTimestamp.computeIfAbsent(71, k -> new ArrayList<>()).add(dronedynamic.getTimestamp());
                droneSpeed.computeIfAbsent(71, k -> new ArrayList<>()).add(dronedynamic.getSpeed());
                droneAlignRoll.computeIfAbsent(71, k -> new ArrayList<>()).add(dronedynamic.getAlignRoll());
                droneAlignPitch.computeIfAbsent(71, k -> new ArrayList<>()).add(dronedynamic.getAlignPitch());
                droneAlignYaw.computeIfAbsent(71, k -> new ArrayList<>()).add(dronedynamic.getAlignYaw());
                droneLongitude.computeIfAbsent(71, k -> new ArrayList<>()).add(dronedynamic.getLongitude());
                droneLatitude.computeIfAbsent(71, k -> new ArrayList<>()).add(dronedynamic.getLatitude());
                droneBatteryStatus.computeIfAbsent(71, k -> new ArrayList<>()).add(dronedynamic.getBatteryStatus());
                droneLastSeen.computeIfAbsent(71, k -> new ArrayList<>()).add(dronedynamic.getLastSeen());
                droneStatus.computeIfAbsent(71, k -> new ArrayList<>()).add(dronedynamic.getStatus());
            }
        } else {
            // notifies the user if response is null or result into no results
            System.err.println("Result error / Null");
            resultFuture.completeExceptionally(new RuntimeException("Result error / Null"));
        }
    }
}