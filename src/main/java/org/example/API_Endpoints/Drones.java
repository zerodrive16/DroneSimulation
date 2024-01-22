package org.example.API_Endpoints;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.example.API_Properties.DronesData;
import org.example.API_StoreData.DronesDataStore;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Drones class handles the asynchronous building of drone data fetched from an API.
 * It extends the Abs_APIBuilding of the generic type {@link DronesData.ReturnDroneData} which is the returned data
 */
public class Drones extends Abs_APIBuilding<DronesData.ReturnDroneData> {
    private static final Logger logger = Logger.getLogger(Drones.class.getName());
    private final DronesDataStore storeDrones = new DronesDataStore();

    /**
     * The asynchronous function build the API drone data
     * The method initializes a CompletableFuture for the drone data, which starts the async process
     * it later returns the future result
     *
     * @return CompletableFuture that will eventually return the drone data
    */
    @Override
    public CompletableFuture<DronesData.ReturnDroneData> APIBuildAsync() {
        CompletableFuture<DronesData.ReturnDroneData> resultFuture = new CompletableFuture<>();
        final String url = "http://dronesim.facets-labs.com/api/drones/?format=json";

        processAsync(url, resultFuture);
        return resultFuture;
    }

    /**
     * Processes API data asynchronously
     * With each operation, it stores the data in storeAPIResponse function
     * Then it recursively handles the pagination, and checks if data is available or not
     *
     * @param paginationUrl The URL is for the current page of the API data
     * @param resultFuture The CompletableFuture to be completed with the resulted fetched data
     * @return resultFuture It returns the finished {@link DronesData.ReturnDroneData} and stores in the Constructor {@link DronesData.ReturnDroneData}.
     */
    protected void processAsync(String paginationUrl, CompletableFuture<DronesData.ReturnDroneData> resultFuture) {
        if(paginationUrl == null){
            logger.info("Pagination has reached 0, Complete the resultFuture with its data.");
            resultFuture.complete(new DronesData.ReturnDroneData(storeDrones.getDroneID(), storeDrones.getDroneTypeURL(),
                    storeDrones.getDroneCreate(), storeDrones.getDroneSerialnumber(),
                    storeDrones.getDroneCarriageWeight(), storeDrones.getDroneCarriageType()));
            return;
        }

        APIRequestAsync(paginationUrl).thenAccept(response -> {
            try {
                Gson gson = new Gson();
                DronesData.DroneResult apiResponse = gson.fromJson(response, DronesData.DroneResult.class);

                if(apiResponse != null) {
                    storeAPIResponse(apiResponse);
                    processAsync(apiResponse.getNext(), resultFuture);
                } else {
                    logger.log(Level.WARNING, "API response is null!");
                    resultFuture.completeExceptionally(new NullPointerException("API response is null!"));
                }
            } catch(JsonSyntaxException ex) {
                // Handles the JSON parsing error
                logger.log(Level.SEVERE, "Error in parsing JSON!", ex);
                resultFuture.completeExceptionally(ex);
            }

        }).exceptionally(ex -> {
            logger.log(Level.SEVERE, "Error in Asynchronous Programming!", ex);
            resultFuture.completeExceptionally(ex);
            return null;
        });
    }

    /**
     * Stores the API response data in the DronesDataStore
     * Iterates through each drone in the response and adds it to the store
     * it uses the getter method to fetch the API data and store them
     *
     * @param apiResponse response containing the API drone data
    */
    private void storeAPIResponse(DronesData.DroneResult apiResponse) {
        if (apiResponse != null && apiResponse.getDroneResults() != null) {
            for (DronesData.Drone drone : apiResponse.getDroneResults()) {
                storeDrones.addDrones(drone);
            }
        } else {
            logger.log(Level.WARNING, "Result error / result null!");
        }
    }
}
