package org.example.API_Endpoints;

import com.google.gson.Gson;
import org.example.API_Properties.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.example.Config.token;

public class DroneTypes {

    // declaring ArrayLists which store the REST API data temporarily
    private final ArrayList<String> droneManufacturer = new ArrayList<>();
    private final ArrayList<String> droneTypeName = new ArrayList<>();
    private final ArrayList<Integer> droneWeight = new ArrayList<>();
    private final ArrayList<Integer> droneMaxSpeed = new ArrayList<>();
    private final ArrayList<Integer> droneBatteryCapacity = new ArrayList<>();
    private final ArrayList<Integer> droneControlRange = new ArrayList<>();
    private final ArrayList<Integer> droneMaxCarriage = new ArrayList<>();

    // The ExecutorService services managing threads and choose the desired amount
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    // defining the Async function and declare the resultFuture as Async to transfer data outside its class
    public CompletableFuture<DroneTypesData.ReturnDroneTypeData> APIDroneTypesAsync() {
        CompletableFuture<DroneTypesData.ReturnDroneTypeData> resultFuture = new CompletableFuture<>();

        // calling the Drones class with the function and fetch the returnData (droneID with getter)
        // declare ArrayList which accepts the CompletableFuture
        new Drones().APIDronesAsync().thenAccept(returnData -> {
            ArrayList<Integer> droneID = returnData.getDroneID();
            ArrayList<CompletableFuture<Void>> futures = new ArrayList<>();

            /* iterating through every element of droneID and calling the APIRequestAsync function which accepts the id as
            parameter which can be used inside the url. ThenAccept handles the callback after CompletableFuture has been done.
            After I/O of inputStream has been done we declare Gson to convert json format to java object and enter the list of
            properties such as manufacturer etc... and store the java object in apiResponse */
            for (int temp_id : droneID) {
                futures.add(APIRequestAsync(temp_id).thenAccept(response -> {
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
            exceptionally is to handle the errors which might occur in Asynchronous programming. */
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

    /* The Asynchronous function takes an Integer as parameter which stores the current id for current iteration. Then it
    uses supplyAsync which means that the HTTP request runs on a different thread asynchronously. Within the code it prepares
    an HTTP request which takes token, URL, CRUD operation and  reads it inside the inputStreamReader. It iterates through every
    entity till it reaches null. After that it returns the response (holds json format) as a String. The try-catch block handles
    the error of the HTTP request. Lastly, the executor creates a pool of threads, managing concurrent execution of multiple threads */
    private CompletableFuture<String> APIRequestAsync(Integer x){
        return CompletableFuture.supplyAsync(() -> {
            try {
                URL url = new URL("http://dronesim.facets-labs.com/api/dronetypes/" + x + "/?format=json");
                HttpURLConnection con;
                con = (HttpURLConnection) url.openConnection();
                con.setRequestProperty("Authorization", retrieveToken());
                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", "XYZ");


                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return response.toString();
            } catch(IOException e){
                throw new RuntimeException(e);
            }
        }, executor);
    }

    /* The function accepts the apiResponse which stores the java objects and checks with an if statement if its null or not
    Then it assign the getters to their respective ArrayLists and store the data temporarily */
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

    // this function checks if the token is either empty or null and if so it throws an Exception if not it returns the token
    private String retrieveToken() {
        if(token == null || token.isEmpty()){
            throw new IllegalStateException("Token is either null or empty");
        }
        return token;
    }
}
