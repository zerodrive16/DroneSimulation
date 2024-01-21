package org.example.API_Endpoints;

import com.google.gson.JsonSyntaxException;
import org.example.API_Properties.DroneDynamicsData;
import com.google.gson.Gson;
import org.example.API_StoreData.DroneDynamicsStore;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

/**
* The DroneDynamic class handles the async building of the drone dynamic api call
* It extends the Abs_APIBuilding of the generic type {@link DroneDynamicsData.ReturnDroneDynamicData} which is the returned data
*/
public class DroneDynamics extends Abs_APIBuilding<DroneDynamicsData.ReturnDroneDynamicData> {

    private final DroneDynamicsStore storeDroneDynamics = new DroneDynamicsStore();

    /**
     * function builds the API request. It calls the {@link Drones} class to fetch the droneID
     * Later it iterates through every single id to get every entry of droneDynamics
     *
     * @return {@link DroneDynamicsData.ReturnDroneDynamicData} to the Constructor and store the data there
    */
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
                        storeDroneDynamics.getDroneTimestamp(), storeDroneDynamics.getDroneSpeed(),
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

    /**
     * It initializes the URL with the id and does the API call
     * Then it gets the Count of all entries in drone dynamic.
     * The Count specialize the limit and offset which gives us the last entry of the current drone dynamic id
     * Then it calls the storeAPIResponse to store the data
     *
     * @param id gives us the current id of the iteration
    */
    private CompletableFuture<Void> getLastDroneDynamic(Integer id) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        String initialUrl = "http://dronesim.facets-labs.com/api/" + id + "/dynamics/?format=json";

        APIRequestAsync(initialUrl).thenAccept(initialResponse -> {
            try {
                Gson gson = new Gson();
                DroneDynamicsData.DroneDynamicResult initialApiResponse = gson.fromJson(initialResponse, DroneDynamicsData.DroneDynamicResult.class);

                if (initialApiResponse != null && initialApiResponse.getCount() > 0) {
                    int limit = initialApiResponse.getCount();
                    int offset = limit - 1;
                    String lastEntityUrl = "http://dronesim.facets-labs.com/api/" + id + "/dynamics/?limit=" + limit + "&offset=" + offset + "&format=json";

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
            } catch(JsonSyntaxException ex) {
                System.err.println("JSON error!");
                future.completeExceptionally(ex);
            }
        }).exceptionally(ex -> {
            future.completeExceptionally(ex);
            return null;
        });

        return future;
    }

    /**
     * Stores the last drone dynamic entry
     * it then stores it in the temporarily assigned list of storeAPIResponse
     *
     * @param droneDynamic it takes the elements {@link DroneDynamicsData.DroneDynamic} with a getter
     */
    private void storeAPIResponse(DroneDynamicsData.DroneDynamic droneDynamic) {
        if (droneDynamic != null) {
            storeDroneDynamics.addDroneDynamics(droneDynamic);
        }
    }
}
