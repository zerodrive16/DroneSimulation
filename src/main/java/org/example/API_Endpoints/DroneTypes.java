package org.example.API_Endpoints;

import com.google.gson.Gson;
import org.example.API_Properties.*;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;


public class DroneTypes extends Abs_APIBuilding<DroneTypesData.ReturnDroneTypeData>{

    // declaring ArrayLists which store the REST API data temporarily
    private final ArrayList<String> droneManufacturer = new ArrayList<>();
    private final ArrayList<String> droneTypeName = new ArrayList<>();
    private final ArrayList<Integer> droneWeight = new ArrayList<>();
    private final ArrayList<Integer> droneMaxSpeed = new ArrayList<>();
    private final ArrayList<Integer> droneBatteryCapacity = new ArrayList<>();
    private final ArrayList<Integer> droneControlRange = new ArrayList<>();
    private final ArrayList<Integer> droneMaxCarriage = new ArrayList<>();

    // defining the Async function and declare the resultFuture as Async to transfer data outside its class
    @Override
    public CompletableFuture<DroneTypesData.ReturnDroneTypeData> APIBuildAsync() {
        CompletableFuture<DroneTypesData.ReturnDroneTypeData> resultFuture = new CompletableFuture<>();

        /* calling the Drones class with the function and thenAccept which usually is used after the process of Drones call
        and accepts returnData as lambda parameter. Inside the body it declares the ArrayList with the droneTypeURL and use the futures
        as ArrayLists which contain the Asynchronous function. */
        new Drones().APIBuildAsync().thenAccept(returnData -> {
            ArrayList<String> droneTypeURL = returnData.getDroneTypeURL();
            ArrayList<CompletableFuture<Void>> futures = new ArrayList<>();

            /* iterating through every element of droneTypeURL and calling the APIRequestAsync function which uses the url as
            parameter to parse it to the http request. ThenAccept handles the callback after CompletableFuture has been done.
            After I/O of inputStream has been done we declare Gson to convert json format to java object and enter the list of
            properties such as manufacturer etc... and store the java object in apiResponse */
            for (String temp_URL : droneTypeURL) {
                futures.add(APIRequestAsync(temp_URL).thenAccept(response -> {
                    Gson gson = new Gson();
                    DroneTypesData.DroneType apiResponse = gson.fromJson(response, DroneTypesData.DroneType.class);

                    /* calling the storeAPIResponse which ideally gets the data with getters and store them in the ArrayLists which
                    were declared above */
                    storeAPIResponse(apiResponse);
                }));
            }

            /* Creating a CompletableFuture which handles an amount of asynchronous operations. chainFutures converts the future
            CompletedFuture to an Array. thenRun is a callback and is used once the chainFutures is completed. It runs a lambda function
            which completes the asynchronous programming and adds a new return value and save the ArrayLists inside the constructor. The
            exceptionally is to handle the errors which might occur in Asynchronous programming while it waits the async in the other thread
            to be finished before executing the exception error. */
            CompletableFuture<Void> chainFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            chainFutures.thenRun(() -> {
                resultFuture.complete(new DroneTypesData.ReturnDroneTypeData(droneManufacturer, droneTypeName, droneWeight, droneMaxSpeed,
                        droneBatteryCapacity, droneControlRange, droneMaxCarriage));
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

    /* The storeAPIResponse takes the data from apiResponse in the ArrayLists and store them temporarily. It also checks if
    data exists in the droneTypes data.  */
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
            System.err.println("Result error / Null");
        }
    }
}
