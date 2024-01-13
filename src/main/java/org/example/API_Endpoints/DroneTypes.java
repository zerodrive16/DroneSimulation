package org.example.API_Endpoints;

import com.google.gson.Gson;
import org.example.API_Properties.*;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

/*
 * Defining the class DroneTypes which builds the asynchronous API request and response
 * Extending the abstract class Abs_APIBuilding with the return of DroneTypesData.ReturnDroneTypeData
*/
public class DroneTypes extends Abs_APIBuilding<DroneTypesData.ReturnDroneTypeData>{

    // declaring ArrayLists which store the REST API data temporarily
    private final ArrayList<String> droneManufacturer = new ArrayList<>();
    private final ArrayList<String> droneTypeName = new ArrayList<>();
    private final ArrayList<Integer> droneWeight = new ArrayList<>();
    private final ArrayList<Integer> droneMaxSpeed = new ArrayList<>();
    private final ArrayList<Integer> droneBatteryCapacity = new ArrayList<>();
    private final ArrayList<Integer> droneControlRange = new ArrayList<>();
    private final ArrayList<Integer> droneMaxCarriage = new ArrayList<>();

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
                resultFuture.complete(new DroneTypesData.ReturnDroneTypeData(droneManufacturer, droneTypeName, droneWeight, droneMaxSpeed,
                        droneBatteryCapacity, droneControlRange, droneMaxCarriage));
                // Error handling for asynchronous programming
            }).exceptionally(ex -> {
                resultFuture.completeExceptionally(ex);
                return null;
            });
        });

        // returning the resultFuture
        return resultFuture;
    }

    @Override
    protected void processAsync(String paginationUrl, CompletableFuture<DroneTypesData.ReturnDroneTypeData> resultFuture) {

    }

    /*
     * function accepts the already converted DroneTypes object as parameter which is the access point to fetch the API data
     * it respectively stores them to the assigned ArrayLists with a getter method */
    private void storeAPIResponse(DroneTypesData.DroneType apiResponse) {
        if (apiResponse != null) {
            droneManufacturer.add(apiResponse.getManufacturer());
            droneTypeName.add(apiResponse.getTypename());
            droneWeight.add(apiResponse.getWeight());
            droneMaxSpeed.add(apiResponse.getMax_Speed());
            droneBatteryCapacity.add(apiResponse.getBattery_Capacity());
            droneControlRange.add(apiResponse.getControl_Range());
            droneMaxCarriage.add(apiResponse.getMax_Carriage());
        } else {
            // checks the apiResponse if the response is null or empty
            System.err.println("Result error / Null");
        }
    }
}
