package org.example.API_Endpoints;

import org.example.API_Properties.DroneDynamicsData;
import com.google.gson.Gson;
import org.example.API_StoreData.DroneDynamicsStore;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class DroneDynamics extends Abs_APIBuilding<DroneDynamicsData.ReturnDroneDynamicData> {

    // calling the class which stores the data temporally
    private final DroneDynamicsStore storeDroneDynamics = new DroneDynamicsStore();

    /*
     * building the API call and define the CompletableFuture library
     * call Drones endpoint and get the callback of the id
     * also define the resultFuture and futures which store the data temporarily
    */
    @Override
    public CompletableFuture<DroneDynamicsData.ReturnDroneDynamicData> APIBuildAsync() {
        CompletableFuture<DroneDynamicsData.ReturnDroneDynamicData> resultFuture = new CompletableFuture<>();
        new Drones().APIBuildAsync().thenAccept(returnData -> {
            ArrayList<Integer> droneIDs = returnData.getDroneID();
            ArrayList<CompletableFuture<Void>> futures = new ArrayList<>();

            // iterating through every id
            for (Integer id : droneIDs) {
                futures.add(getLastDroneDynamic(id));
            }

            // once everything is done return the data to the constructor in storeDroneDynamics
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).thenRun(() -> {
                DroneDynamicsData.ReturnDroneDynamicData data = new DroneDynamicsData.ReturnDroneDynamicData(
                        storeDroneDynamics.getDroneTimestamp(), storeDroneDynamics.getDroneSpeed(),
                        storeDroneDynamics.getDroneAlignRoll(), storeDroneDynamics.getDroneAlignPitch(), storeDroneDynamics.getDroneAlignYaw(),
                        storeDroneDynamics.getDroneLongitude(), storeDroneDynamics.getDroneLatitude(), storeDroneDynamics.getDroneBatteryStatus(),
                        storeDroneDynamics.getDroneLastSeen(), storeDroneDynamics.getDroneStatus()
                );
                // complete async
                resultFuture.complete(data);
            }).exceptionally(ex -> {
                resultFuture.completeExceptionally(ex);
                return null;
            });
        });
        return resultFuture;
    }

    /*
     * the function gets the last entry of each drone dynamic id
     * it has the initialURL which has the output of all entries in id
     * then it does the API request and convert it to java objects
    */
    private CompletableFuture<Void> getLastDroneDynamic(Integer id) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        String initialUrl = "http://dronesim.facets-labs.com/api/" + id + "/dynamics/?format=json";

        APIRequestAsync(initialUrl).thenAccept(initialResponse -> {
            Gson gson = new Gson();
            DroneDynamicsData.DroneDynamicResult initialApiResponse = gson.fromJson(initialResponse, DroneDynamicsData.DroneDynamicResult.class);

            /*
             * this part in particular gives us the count of the entries in the id
             * it then uses the count as limit and -1 for the offset
             * it limits the output and redirect us to the last entry of drone dynamic id */
            if (initialApiResponse != null && initialApiResponse.getCount() > 0) {
                int limit = initialApiResponse.getCount();
                int offset = limit - 1;
                String lastEntityUrl = "http://dronesim.facets-labs.com/api/" + id + "/dynamics/?limit=" + limit + "&offset=" + offset + "&format=json";

                /*
                 * once again the API call is called
                 * it retrieves the last entry and calls teh storeAPIResponse
                 * the storeAPIResponse stores all the data of the last entry
                */
                APIRequestAsync(lastEntityUrl).thenAccept(lastResponse -> {
                    DroneDynamicsData.DroneDynamicResult lastApiResponse = gson.fromJson(lastResponse, DroneDynamicsData.DroneDynamicResult.class);
                    if (lastApiResponse != null && !lastApiResponse.getResults().isEmpty()) {
                        DroneDynamicsData.DroneDynamic lastDroneDynamic = lastApiResponse.getResults().get(0);
                        storeAPIResponse(lastDroneDynamic);
                    }
                    // completes the operation
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
