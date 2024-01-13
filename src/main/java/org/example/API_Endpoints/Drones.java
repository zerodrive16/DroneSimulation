package org.example.API_Endpoints;

import com.google.gson.Gson;
import org.example.API_Properties.DronesData;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

/*
 * The class handles the asynchronous building program of the Drones that fetches the API data
 * It extends the Abs_APIBuilding of the generic type DronesData.ReturnDroneData which is returns the data
*/
public class Drones extends Abs_APIBuilding<DronesData.ReturnDroneData> {
    // ArrayLists to store the API fetch data temporarily
    private final ArrayList<Integer> droneID = new ArrayList<>();
    private final ArrayList<String> droneTypeURL = new ArrayList<>();
    private final ArrayList<String> droneCreate = new ArrayList<>();
    private final ArrayList<String> droneSerialnumber = new ArrayList<>();
    private final ArrayList<Integer> droneCarriageWeight = new ArrayList<>();
    private final ArrayList<String> droneCarriageType = new ArrayList<>();

    /*
     * The asynchronous function build the API drone data
     * It defines the CompletableFuture of resultFuture that should be returned with the "already" fetched data
    */
    @Override
    public CompletableFuture<DronesData.ReturnDroneData> APIBuildAsync() {
        CompletableFuture<DronesData.ReturnDroneData> resultFuture = new CompletableFuture<>();
        final String url = "http://dronesim.facets-labs.com/api/drones/?format=json";
        // using the initial url inside the asynchronous process
        processAsync(url, resultFuture);
        return resultFuture;
    }

    /*
     * The process Async accepts the url and the CompletableFuture resultFuture
     * It recursively checks for the pagination, if it reaches null or if it is completed
    */
    @Override
    protected void processAsync(String paginationUrl, CompletableFuture<DronesData.ReturnDroneData> resultFuture) {
        if(paginationUrl == null){
            // when no more data is available it stores the data in DronesData class
            resultFuture.complete(new DronesData.ReturnDroneData(droneID, droneTypeURL, droneCreate, droneSerialnumber, droneCarriageWeight, droneCarriageType));
            return;
        }

        // If data is fetched, immediately continue with the rest of the code
        APIRequestAsync(paginationUrl).thenAccept(response -> {
            Gson gson = new Gson();
            DronesData.DroneResult apiResponse = gson.fromJson(response, DronesData.DroneResult.class);

            // store the fetched API data
            storeAPIResponse(apiResponse);

            // proceeds the next entity in the next page url
            processAsync(apiResponse.getNext(), resultFuture);

            // this handles the error handling in asynchronous programming
        }).exceptionally(ex -> {
            resultFuture.completeExceptionally(ex);
            return null;
        });
    }

    /*
     * Stores the API response data inside the defined ArrayLists above
     * apiResponse checks the specific data element inside the results array
     * it uses the getter method to fetch the API data and store them
    */
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
            // notifies the user if response is null or result into no results
            System.err.println("Result error / Null");
        }
    }
}
