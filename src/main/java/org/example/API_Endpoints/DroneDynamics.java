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
                String url = "http://dronesim.facets-labs.com/api/" + id + "/dynamics/?format=json";
                futures.add(processLastPage(url));
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

    private CompletableFuture<Void> processLastPage(String url) {
        return APIRequestAsync(url).thenAccept(response -> {
            Gson gson = new Gson();
            DroneDynamicsData.DroneDynamicResult apiResponse = gson.fromJson(response, DroneDynamicsData.DroneDynamicResult.class);
            if (apiResponse != null && !apiResponse.getResults().isEmpty()) {
                DroneDynamicsData.DroneDynamic lastDroneDynamic = apiResponse.getResults().get(apiResponse.getResults().size() - 1);

                //System.out.println("Last entity details: " + lastDroneDynamic);

                storeAPIResponse(lastDroneDynamic);
            }
        }).exceptionally(ex -> {
            return null;
        });
    }

    private void storeAPIResponse(DroneDynamicsData.DroneDynamic droneDynamic) {
        if (droneDynamic != null) {
            storeDroneDynamics.addDroneDynamics(droneDynamic);
        }
    }
}
