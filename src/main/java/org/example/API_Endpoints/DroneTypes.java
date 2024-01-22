package org.example.API_Endpoints;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.example.API_Properties.*;
import org.example.API_StoreData.DroneTypesStore;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Defining the class DroneTypes which builds the asynchronous API request and response
 * Extending the abstract class {@link Abs_APIBuilding} with a return type of {@link DroneTypesData.ReturnDroneTypesData}.
*/
public class DroneTypes extends Abs_APIBuilding<DroneTypesData.ReturnDroneTypesData>{
    private static final Logger logger = Logger.getLogger(DroneTypes.class.getName());
    private final DroneTypesStore storeDroneTypes = new DroneTypesStore();

    /**
     * resultFuture stores the async data temporarily
     * Builds, process and executes the API call and fetch drone types data.
     * It takes the Drone URL and iterates through each URL to get the corresponding drone type data.
     * Data fetched and use storeAPIResponse function to store  in {@link DroneTypesStore}.
     * @return CompletableFuture which resolves to {@link DroneTypesData.ReturnDroneTypesData} on successful completion.
    */
    @Override
    public CompletableFuture<DroneTypesData.ReturnDroneTypesData> APIBuildAsync() {
        CompletableFuture<DroneTypesData.ReturnDroneTypesData> resultFuture = new CompletableFuture<>();

        new Drones().APIBuildAsync().thenAccept(returnData -> {
            ArrayList<String> droneTypeURL = returnData.getDroneTypeURL();
            if(!droneTypeURL.isEmpty()) {
                ArrayList<CompletableFuture<Void>> futures = new ArrayList<>();

                for (String temp_URL : droneTypeURL) {
                    futures.add(APIRequestAsync(temp_URL).thenAccept(response -> {
                        try {
                            Gson gson = new Gson();
                            DroneTypesData.DroneType apiResponse = gson.fromJson(response, DroneTypesData.DroneType.class);

                            storeAPIResponse(apiResponse);
                        } catch(JsonSyntaxException ex) {
                            logger.log(Level.SEVERE, "Fatal error in JSON conversion!", ex);
                            resultFuture.completeExceptionally(ex);
                        }
                    }));
                }

                CompletableFuture<Void> chainFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
                logger.info("Pagination has reached 0, Complete the resultFuture with its data.");
                chainFutures.thenRun(() -> resultFuture.complete(
                        new DroneTypesData.ReturnDroneTypesData(storeDroneTypes.getDroneManufacturer(), storeDroneTypes.getDroneTypeName(),
                        storeDroneTypes.getDroneWeight(), storeDroneTypes.getDroneMaxSpeed(), storeDroneTypes.getDroneBatteryCapacity(),
                        storeDroneTypes.getDroneControlRange(), storeDroneTypes.getDroneMaxCarriage())
                )).exceptionally(ex -> {
                    resultFuture.completeExceptionally(new RuntimeException(ex));
                    return null;
                });
            } else {
                logger.log(Level.WARNING, "DroneTypeURL is empty!");
                resultFuture.completeExceptionally(new IllegalStateException("DroneTypeURL is empty"));
            }
        });
        return resultFuture;
    }

    /**
     * Stores the fetched API response in {@link DroneTypesStore}.
     *
     * @param apiResponse The response object containing the drone types data.
     */
    private void storeAPIResponse(DroneTypesData.DroneType apiResponse) {
        if (apiResponse != null) {
            storeDroneTypes.addDroneTypes(apiResponse);
        } else {
            logger.log(Level.WARNING, "apiResponse is null!");
        }
    }
}
