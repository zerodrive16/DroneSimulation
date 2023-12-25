package org.example.API_Endpoints;

import com.google.gson.Gson;
import org.example.API_Properties.DronesData;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class Drones extends Abs_APIBuilding<DronesData.ReturnDroneData> {
    // declaring ArrayLists which store the API data temporarily
    private final ArrayList<Integer> droneID = new ArrayList<>();
    private final ArrayList<String> droneTypeURL = new ArrayList<>();
    private final ArrayList<String> droneCreate = new ArrayList<>();
    private final ArrayList<String> droneSerialnumber = new ArrayList<>();
    private final ArrayList<Integer> droneCarriageWeight = new ArrayList<>();
    private final ArrayList<String> droneCarriageType = new ArrayList<>();

    @Override
    public CompletableFuture<DronesData.ReturnDroneData> APIBuildAsync() {
        CompletableFuture<DronesData.ReturnDroneData> resultFuture = new CompletableFuture<>();

        final String url = "http://dronesim.facets-labs.com/api/drones/?format=json";

        processAsync(url, resultFuture);
        return resultFuture;
    }

    @Override
    protected void processAsync(String paginationUrl, CompletableFuture<DronesData.ReturnDroneData> resultFuture) {
        if(paginationUrl == null){
            // When no more data, complete the future with the collected data
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
}
