package org.example.API_Endpoints;

import com.google.gson.Gson;
import org.example.API_Properties.*;
import org.example.API_StoreData.DroneTypesStore;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

/*
 * Defining the class DroneTypes which builds the asynchronous API request and response
 * Extending the abstract class Abs_APIBuilding with the return of DroneTypesData.ReturnDroneTypeData
*/
public class DroneTypes extends Abs_APIBuilding<DroneTypesData.ReturnDroneTypeData>{
    // calling the class which stores the data temporally
    private final DroneTypesStore storeDroneTypes = new DroneTypesStore();

    /*
     * CompletableFuture function that builds the asynchronous API
     * storing the asynchronous data inside resultFuture
    */
    @Override
    public CompletableFuture<DroneTypesData.ReturnDroneTypeData> APIBuildAsync() {
        CompletableFuture<DroneTypesData.ReturnDroneTypeData> resultFuture = new CompletableFuture<>();

        /*
         * Asynchronously fetches the Drones data and processes it upon completion
         * calling the droneTypeURL to fetch the url of the drones
         * futures is a list for concurrent processing of each url
        */
        new Drones().APIBuildAsync().thenAccept(returnData -> {
            ArrayList<String> droneTypeURL = returnData.getDroneTypeURL();
            ArrayList<CompletableFuture<Void>> futures = new ArrayList<>();

            // loop through the Url
            for (String temp_URL : droneTypeURL) {
                // adding the futures of the asynchronous APi request and use the callback (lambda) to use the response
                futures.add(APIRequestAsync(temp_URL).thenAccept(response -> {

                    // Gson dependency to convert JSON response to objects
                    Gson gson = new Gson();
                    DroneTypesData.DroneType apiResponse = gson.fromJson(response, DroneTypesData.DroneType.class);

                    // storing the API data to the respective Array Lists
                    storeAPIResponse(apiResponse);
                }));
            }

            // It waits for all asynchronous operations to complete
            CompletableFuture<Void> chainFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            chainFutures.thenRun(() -> {
                // Once all asynchronous operations are complete, finish the main future and return the collected data
                resultFuture.complete(new DroneTypesData.ReturnDroneTypeData((ArrayList<String>) storeDroneTypes.getDroneManufacturer(), (ArrayList<String>) storeDroneTypes.getDroneTypeName(),
                        (ArrayList<Integer>) storeDroneTypes.getDroneWeight(), (ArrayList<Integer>) storeDroneTypes.getDroneMaxSpeed(), (ArrayList<Integer>) storeDroneTypes.getDroneBatteryCapacity(),
                        (ArrayList<Integer>) storeDroneTypes.getDroneControlRange(), (ArrayList<Integer>) storeDroneTypes.getDroneMaxCarriage()));
                // Error handling for asynchronous programming
            }).exceptionally(ex -> {
                resultFuture.completeExceptionally(ex);
                return null;
            });
        });

        // returning the resultFuture
        return resultFuture;
    }

    /*
     * function accepts the already converted DroneTypes object as parameter which is the access point to fetch the API data
     * it respectively stores them to the assigned ArrayLists with a getter method */
    private void storeAPIResponse(DroneTypesData.DroneType apiResponse) {
        if (apiResponse != null) {
            storeDroneTypes.addDroneTypes(apiResponse);
        } else {
            // checks the apiResponse if the response is null or empty
            System.err.println("Result error / Null");
        }
    }
}
