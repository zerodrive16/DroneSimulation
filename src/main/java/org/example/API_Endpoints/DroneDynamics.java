package org.example.API_Endpoints;

import org.example.API_Properties.DroneDynamicsData;
import com.google.gson.Gson;
import org.example.API_Properties.DroneTypesData;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class DroneDynamics extends Abs_APIBuilding<DroneDynamicsData.ReturnDroneDynamicData> {
       private final ArrayList<String> droneURL = new ArrayList<>();
       private final ArrayList<String> droneTimestamp = new ArrayList<>();
       private final ArrayList<String> droneSpeed = new ArrayList<>();
       private final ArrayList<String> droneAlignRoll = new ArrayList<>();
       private final ArrayList<String> droneAlignPitch = new ArrayList<>();
       private final ArrayList<String> droneAlignYaw = new ArrayList<>();
       private final ArrayList<String> droneLongitude = new ArrayList<>();
       private final ArrayList<String> droneLatitude = new ArrayList<>();
       private final ArrayList<String> droneBatteryStatus = new ArrayList<>();
       private final ArrayList<String> droneLastSeen = new ArrayList<>();
       private final ArrayList<String> droneStatus = new ArrayList<>();

       private String url;
       public void urlGen(int id){
           StringBuilder builder = new StringBuilder();
           builder.append("http://dronesim.facets-labs.com/api/");
           builder.append(id);
           builder.append("/dynamics/?format=json");
           url = builder.toString();
           //return builder.toString();
           APIBuildAsync().thenAccept(droneDynamicData -> {

               System.out.println(droneDynamicData.getDroneURL().size());
               for (int i = 0; i < droneDynamicData.getDroneURL().size(); i++) {
                   System.out.println("Drone: " + droneDynamicData.getDroneURL().get(i));
                   System.out.println("Timestamp: " + droneDynamicData.getDroneTimeStamp().get(i));
                   System.out.println("Speed: " + droneDynamicData.getDroneSpeed().get(i));
                   System.out.println("AlignRoll: " + droneDynamicData.getDroneAlignRoll().get(i));
                   System.out.println("AlignPitch: " + droneDynamicData.getDroneAlignPitch().get(i));
                   System.out.println("AlignYaw: " + droneDynamicData.getDroneAlignYaw().get(i));
                   System.out.println("Longitude: " + droneDynamicData.getDroneLongitude().get(i));
                   System.out.println("Latitude: " + droneDynamicData.getDroneLatitude().get(i));
                   System.out.println("BatteryStatus: " + droneDynamicData.getDroneBatteryStatus().get(i));
                   System.out.println("Last Seen: " + droneDynamicData.getDroneLastSeen().get(i));
                   System.out.println("Status: " + droneDynamicData.getDroneStatus().get(i));
                   System.out.println();
               }
           }).exceptionally(ex -> {
               System.err.println("Error fetching API data: " + ex.getMessage());
               return null;
           });
       }
       @Override
        public CompletableFuture<DroneDynamicsData.ReturnDroneDynamicData> APIBuildAsync(){
           CompletableFuture<DroneDynamicsData.ReturnDroneDynamicData> resultFuture = new CompletableFuture<>();

           //final String url = "http://dronesim.facets-labs.com/api/dronedynamics/?format=json";
           System.out.println(url);
           processAsync(url, resultFuture);
           return resultFuture;

        }
        @Override
        protected void processAsync(String paginationUrl, CompletableFuture<DroneDynamicsData.ReturnDroneDynamicData> resultFuture) {
          if(paginationUrl == null){
               System.out.println("process Async ausgeführt");
               // When no more data, complete the future with the collected data
               resultFuture.complete(new DroneDynamicsData.ReturnDroneDynamicData(droneURL, droneTimestamp, droneSpeed, droneAlignRoll, droneAlignPitch, droneAlignYaw,droneLongitude,droneLatitude,droneBatteryStatus,droneLastSeen,droneStatus));
               return;
           }


            APIRequestAsync(paginationUrl).thenAccept(response -> {
                Gson gson = new Gson();
                DroneDynamicsData.DroneDynamicResult apiResponse = gson.fromJson(response, DroneDynamicsData.DroneDynamicResult.class);
                // storing data to the constructor
                storeAPIResponse(apiResponse);
                // proceeds with the next entity in the webserver and do the recursion
                processAsync(apiResponse.getNext(), resultFuture);

                // Error handling the Asynchronous programming
            }).exceptionally(ex -> {
                resultFuture.completeExceptionally(ex);
                return null;
            });
        }

private void storeAPIResponse(DroneDynamicsData.DroneDynamicResult apiResponse){
    if (apiResponse != null && apiResponse.getResults() != null) {
        for(DroneDynamicsData.DroneDynamic drone : apiResponse.getResults()) {
            droneURL.add(drone.getDrone());
            droneTimestamp.add(drone.getTimestamp());
            droneSpeed.add(drone.getSpeed());
            droneAlignRoll.add(drone.getAlignRoll());
            droneAlignPitch.add(drone.getAlignPitch());
            droneAlignYaw.add(drone.getAlignYaw());
            droneLongitude.add(drone.getLongitude());
            droneLatitude.add(drone.getLatitude());
            droneBatteryStatus.add(drone.getBatteryStatus());
            droneLastSeen.add(drone.getLastSeen());
            droneStatus.add(drone.getStatus());
        }

    } else {
        System.err.println("Result error / Null");
        }
    }
}