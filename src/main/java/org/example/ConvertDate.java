package org.example;


import org.example.API_Endpoints.DroneDynamics;
import org.example.API_Endpoints.Drones;
import org.example.API_Properties.DronesData;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class ConvertDate {

    public static final ArrayList<String> resultTime =new ArrayList<>();
    public static final ArrayList<String> resultLastSeen = new ArrayList<>();
    public static final ArrayList<String> resultCreated = new ArrayList<>();


    public CompletableFuture<ArrayList<String>> performConvertDateAsync() {
        DroneDynamics droneDynamics = new DroneDynamics();
        CompletableFuture<Void> convertDateFuture = droneDynamics.APIBuildAsync().thenAcceptAsync(response -> {
            for(int i = 0; i<response.getDroneTimeStamp().size();i++){
            // Input date string
            String inputDateString = response.getDroneTimeStamp().get(i);
            String inputLastSeen = response.getDroneLastSeen().get(i);

            // Parse the input string to ZonedDateTime
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(inputDateString, DateTimeFormatter.ISO_DATE_TIME);

            // Create a formatter for the desired output format (with Germany locale)
            DateTimeFormatter germanFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm").withLocale(java.util.Locale.GERMANY);

            // Format the ZonedDateTime using the German formatter
            String formattedDate = zonedDateTime.format(germanFormatter);
                resultTime.add(formattedDate);
        }
            });

        return convertDateFuture.thenApplyAsync(ignored -> resultTime);
    }

    public CompletableFuture<ArrayList<String>> performConvertLastSeenAsync() {
        DroneDynamics droneDynamics = new DroneDynamics();
        CompletableFuture<Void> convertLastSeenFuture = droneDynamics.APIBuildAsync().thenAcceptAsync(response -> {
            for(int i = 0; i<response.getDroneLastSeen().size();i++){
                // Input date string
                String inputLastSeen = response.getDroneLastSeen().get(i);

                // Parse the input string to ZonedDateTime
                ZonedDateTime zonedDateTime = ZonedDateTime.parse(inputLastSeen, DateTimeFormatter.ISO_DATE_TIME);

                // Create a formatter for the desired output format (with Germany locale)
                DateTimeFormatter germanFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm").withLocale(java.util.Locale.GERMANY);

                // Format the ZonedDateTime using the German formatter
                String formattedLastSeen = zonedDateTime.format(germanFormatter);
                resultLastSeen.add(formattedLastSeen);
            }
        });

        return convertLastSeenFuture.thenApplyAsync(ignored -> resultLastSeen);
    }

    public CompletableFuture<ArrayList<String>> performConvertCreatesync() {
        Drones drones = new Drones();
        CompletableFuture<Void> convertCreateFuture = drones.APIBuildAsync().thenAcceptAsync(response -> {
            for(int i = 0; i<response.getDroneCreate().size();i++){
                // Input date string
                String inputLastSeen = response.getDroneCreate().get(i);

                // Parse the input string to ZonedDateTime
                ZonedDateTime zonedDateTime = ZonedDateTime.parse(inputLastSeen, DateTimeFormatter.ISO_DATE_TIME);

                // Create a formatter for the desired output format (with Germany locale)
                DateTimeFormatter germanFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm").withLocale(java.util.Locale.GERMANY);

                // Format the ZonedDateTime using the German formatter
                String formattedCreate = zonedDateTime.format(germanFormatter);
                resultLastSeen.add(formattedCreate);
            }
        });

        return convertCreateFuture.thenApplyAsync(ignored -> resultLastSeen);
    }

}
