package org.example.API_Endpoints;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.example.API_Properties.*;
import org.example.API_StoreData.DroneTypesStore;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Defining the class DroneTypes which builds the asynchronous API request and response
 * Extending the abstract class {@link Abs_APIBuilding} with a return type of {@link DroneTypesData.ReturnDroneTypesData}.
*/
public class DroneTypes extends Abs_APIBuilding<DroneTypesData.ReturnDroneTypesData>{
    private static final Logger logger = Logger.getLogger(DroneTypes.class.getName());
    // storing the drone types fetched data temporarily
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
                // A list to hold the futures, maintaining the order
                ArrayList<CompletableFuture<DroneTypesData.DroneType>> futures = new ArrayList<>();

                for (String temp_URL : droneTypeURL) {
                    CompletableFuture<DroneTypesData.DroneType> future = APIRequestAsync(temp_URL).thenApply(response -> {
                        try {
                            // json format to java object
                            Gson gson = new Gson();
                            return gson.fromJson(response, DroneTypesData.DroneType.class);
                        } catch (JsonSyntaxException ex) {
                            logger.log(Level.SEVERE, "Fatal error in JSON conversion!", ex);
                            throw new CompletionException(ex);
                        }
                    });
                    futures.add(future);
                }

                // ChatGPT generated!
                // combining all futures and set the order of DroneTypes
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                        .thenApply(v -> futures.stream()
                                .map(CompletableFuture::join) // This will get the results in the order of futures
                                .collect(Collectors.toList()))
                        .thenAccept(orderedResponses -> {
                            // storing each response inside the storeAPIResponse function
                            orderedResponses.forEach(this::storeAPIResponse);
                            // we complete the resultFuture operation and send it to the Constructor
                            resultFuture.complete(
                                    new DroneTypesData.ReturnDroneTypesData(storeDroneTypes.getDroneManufacturer(), storeDroneTypes.getDroneTypeName(),
                                            storeDroneTypes.getDroneWeight(), storeDroneTypes.getDroneMaxSpeed(), storeDroneTypes.getDroneBatteryCapacity(),
                                            storeDroneTypes.getDroneControlRange(), storeDroneTypes.getDroneMaxCarriage())
                            );
                        })
                        .exceptionally(ex -> {
                            resultFuture.completeExceptionally(ex);
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
