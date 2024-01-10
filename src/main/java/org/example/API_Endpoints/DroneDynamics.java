package org.example.API_Endpoints;

import org.example.API_Properties.DroneDynamicsData;
import com.google.gson.Gson;
import org.example.API_Properties.DroneTypesData;
import org.example.API_Properties.DronesData;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class DroneDynamics extends Abs_APIBuilding<DroneDynamicsData.ReturnDroneDynamicData> {
    private final Map<Integer, ArrayList<String>> droneURL = new HashMap<>();
    private final Map<Integer, ArrayList<String>> droneTimestamp = new HashMap<>();
    private final Map<Integer, ArrayList<String>> droneSpeed = new HashMap<>();
    private final Map<Integer, ArrayList<String>> droneAlignRoll = new HashMap<>();
    private final Map<Integer, ArrayList<String>> droneAlignPitch = new HashMap<>();
    private final Map<Integer, ArrayList<String>> droneAlignYaw = new HashMap<>();
    private final Map<Integer, ArrayList<Double>> droneLongitude = new HashMap<>();
    private final Map<Integer, ArrayList<Double>> droneLatitude = new HashMap<>();
    private final Map<Integer, ArrayList<String>> droneBatteryStatus = new HashMap<>();
    private final Map<Integer, ArrayList<String>> droneLastSeen = new HashMap<>();
    private final Map<Integer, ArrayList<String>> droneStatus = new HashMap<>();

    @Override
    public CompletableFuture<DroneDynamicsData.ReturnDroneDynamicData> APIBuildAsync() {
        CompletableFuture<DroneDynamicsData.ReturnDroneDynamicData> resultFuture = new CompletableFuture<>();

        List<CompletableFuture<Void>> futuresList = new ArrayList<>();

        new Drones().APIBuildAsync().thenAccept(response -> {
            ArrayList<Integer> droneIDs = response.getDroneID();

            if (droneIDs != null && !droneIDs.isEmpty()) {
                for (Integer id : droneIDs) {
                    String url = "http://dronesim.facets-labs.com/api/" + id + "/dynamics/?format=json";
                    CompletableFuture<Void> droneFuture = processAsyncs(url, id);
                    futuresList.add(droneFuture);
                }

                CompletableFuture<Void> allFutures = CompletableFuture.allOf(futuresList.toArray(new CompletableFuture[0]));
                allFutures.thenRun(() -> {
                    resultFuture.complete(new DroneDynamicsData.ReturnDroneDynamicData(
                            droneURL, droneTimestamp, droneSpeed, droneAlignRoll, droneAlignPitch,
                            droneAlignYaw, droneLongitude, droneLatitude, droneBatteryStatus,
                            droneLastSeen, droneStatus));
                });

            } else {
                resultFuture.completeExceptionally(new IllegalStateException("Drone ID list is null or empty"));
            }
        }).exceptionally(ex -> {
            resultFuture.completeExceptionally(ex);
            return null;
        });

        return resultFuture;
    }

    protected CompletableFuture<Void> processAsyncs(String paginationUrl, Integer droneID) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        APIRequestAsync(paginationUrl).thenAccept(response -> {
            Gson gson = new Gson();
            DroneDynamicsData.DroneDynamicResult apiResponse = gson.fromJson(response, DroneDynamicsData.DroneDynamicResult.class);

            storeAPIResponse(apiResponse, droneID);

            future.complete(null);
        }).exceptionally(ex -> {
            future.completeExceptionally(ex);
            return null;
        });

        return future;
    }
    private void storeAPIResponse(DroneDynamicsData.DroneDynamicResult apiResponse, Integer droneID) {
        if (apiResponse != null && apiResponse.getResults() != null) {

            // reversing the Entities that should be saved
            List<DroneDynamicsData.DroneDynamic> reversedEntities = new ArrayList<>(apiResponse.getResults());
            Collections.reverse(reversedEntities);

            ArrayList<String> urls = droneURL.computeIfAbsent(droneID, k -> new ArrayList<>());
            ArrayList<String> timestamps = droneTimestamp.computeIfAbsent(droneID, k -> new ArrayList<>());
            ArrayList<String> speed = droneSpeed.computeIfAbsent(droneID, k -> new ArrayList<>());
            ArrayList<String> alignroll= droneAlignRoll.computeIfAbsent(droneID, k -> new ArrayList<>());
            ArrayList<String> alignpitch = droneAlignPitch.computeIfAbsent(droneID, k -> new ArrayList<>());
            ArrayList<String> alignyaw = droneAlignYaw.computeIfAbsent(droneID, k -> new ArrayList<>());
            ArrayList<Double> longitude = droneLongitude.computeIfAbsent(droneID, k -> new ArrayList<>());
            ArrayList<Double> latitude = droneLatitude.computeIfAbsent(droneID, k -> new ArrayList<>());
            ArrayList<String> batterystatus = droneBatteryStatus.computeIfAbsent(droneID, k -> new ArrayList<>());
            ArrayList<String> lastseen = droneLastSeen.computeIfAbsent(droneID, k -> new ArrayList<>());
            ArrayList<String> status = droneStatus.computeIfAbsent(droneID, k -> new ArrayList<>());

            for (DroneDynamicsData.DroneDynamic dronedynamic : reversedEntities) {
                if (urls.size() >= 3) break;
                urls.add(dronedynamic.getDrone());
                timestamps.add(dronedynamic.getTimestamp());
                speed.add(dronedynamic.getSpeed());
                alignroll.add(dronedynamic.getAlignRoll());
                alignpitch.add(dronedynamic.getAlignPitch());
                alignyaw.add(dronedynamic.getAlignYaw());
                longitude.add(dronedynamic.getLongitude());
                latitude.add(dronedynamic.getLatitude());
                batterystatus.add(dronedynamic.getBatteryStatus());
                lastseen.add(dronedynamic.getLastSeen());
                status.add(dronedynamic.getStatus());
            }
        } else {
            System.err.println("Result error / Null for droneID: " + droneID);
        }
    }




    @Override
    protected void processAsync(String paginationUrl, CompletableFuture<DroneDynamicsData.ReturnDroneDynamicData> resultFuture) {

    }
}