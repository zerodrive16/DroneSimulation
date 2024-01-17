package org.example.API_Endpoints;

import org.example.API_Properties.DroneDynamicsData;
import com.google.gson.Gson;
import org.example.API_StoreData.DroneDynamicsStore;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class DroneDynamics extends Abs_APIBuilding<DroneDynamicsData.ReturnDroneDynamicData> {

    private final DroneDynamicsStore storeDroneDynamics = new DroneDynamicsStore();

    @Override
    public CompletableFuture<DroneDynamicsData.ReturnDroneDynamicData> APIBuildAsync() {
        CompletableFuture<DroneDynamicsData.ReturnDroneDynamicData> resultFuture = new CompletableFuture<>();
        new Drones().APIBuildAsync().thenAccept(returnData -> {
            ArrayList<Integer> droneIDs = returnData.getDroneID();
            ArrayList<CompletableFuture<Void>> futures = new ArrayList<>();

            for (Integer id : droneIDs) {
                futures.add(getLastDroneDynamic(id));
            }

            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).thenRun(() -> {
                DroneDynamicsData.ReturnDroneDynamicData data = new DroneDynamicsData.ReturnDroneDynamicData(
                        storeDroneDynamics.getDroneURL(), storeDroneDynamics.getDroneTimestamp(), storeDroneDynamics.getDroneSpeed(),
                        storeDroneDynamics.getDroneAlignRoll(), storeDroneDynamics.getDroneAlignPitch(), storeDroneDynamics.getDroneAlignYaw(),
                        storeDroneDynamics.getDroneLongitude(), storeDroneDynamics.getDroneLatitude(), storeDroneDynamics.getDroneBatteryStatus(),
                        storeDroneDynamics.getDroneLastSeen(), storeDroneDynamics.getDroneStatus()
                );
                resultFuture.complete(data);
            }).exceptionally(ex -> {
                resultFuture.completeExceptionally(ex);
                return null;
            });
        });
        return resultFuture;
    }

    private CompletableFuture<Void> getLastDroneDynamic(Integer id) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        String initialUrl = "http://dronesim.facets-labs.com/api/" + id + "/dynamics/?format=json";

        APIRequestAsync(initialUrl).thenAccept(initialResponse -> {
            Gson gson = new Gson();
            DroneDynamicsData.DroneDynamicResult initialApiResponse = gson.fromJson(initialResponse, DroneDynamicsData.DroneDynamicResult.class);

            if (initialApiResponse != null && initialApiResponse.getCount() > 0) {
                int count = initialApiResponse.getCount();
                int offset = count - 1;
                String lastEntityUrl = "http://dronesim.facets-labs.com/api/" + id + "/dynamics/?limit=" + count + "&offset=" + offset + "&format=json";

                APIRequestAsync(lastEntityUrl).thenAccept(lastResponse -> {
                    DroneDynamicsData.DroneDynamicResult lastApiResponse = gson.fromJson(lastResponse, DroneDynamicsData.DroneDynamicResult.class);
                    if (lastApiResponse != null && !lastApiResponse.getResults().isEmpty()) {
                        DroneDynamicsData.DroneDynamic lastDroneDynamic = lastApiResponse.getResults().get(0);
                        storeAPIResponse(lastDroneDynamic);
                    }
                    future.complete(null);
                }).exceptionally(ex -> {
                    future.completeExceptionally(ex);
                    return null;
                });
            } else {
                future.complete(null);
            }
        }).exceptionally(ex -> {
            future.completeExceptionally(ex);
            return null;
        });

        return future;
    }

    private void storeAPIResponse(DroneDynamicsData.DroneDynamic droneDynamic) {
        if (droneDynamic != null) {
            storeDroneDynamics.addDroneDynamics(droneDynamic);
        }
    }
}
