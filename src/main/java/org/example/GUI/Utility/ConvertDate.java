package org.example.GUI.Utility;


import org.example.API_Endpoints.DroneDynamics;
import org.example.API_Endpoints.Drones;
import org.example.API_Properties.DroneDynamicsData;
import org.example.API_Properties.DronesData;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class ConvertDate {

    public CompletableFuture<ArrayList<String>> performConvertLastSeenAsync(DroneDynamicsData.ReturnDroneDynamicData droneDynamicData) {
        return CompletableFuture.supplyAsync(() -> {
            ArrayList<String> resultLastSeen = new ArrayList<>();
            DateTimeFormatter germanFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm").withLocale(java.util.Locale.GERMANY);

            for (String inputLastSeen : droneDynamicData.getDroneLastSeen()) {
                ZonedDateTime zonedDateTime = ZonedDateTime.parse(inputLastSeen, DateTimeFormatter.ISO_DATE_TIME);
                String formattedLastSeen = zonedDateTime.format(germanFormatter);
                resultLastSeen.add(formattedLastSeen);
            }

            return resultLastSeen;
        });
    }

    public CompletableFuture<ArrayList<String>> performConvertCreateAsync(DronesData.ReturnDroneData droneData) {
        return CompletableFuture.supplyAsync(() -> {
            ArrayList<String> resultCreate = new ArrayList<>();
            DateTimeFormatter germanFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm").withLocale(java.util.Locale.GERMANY);

            for (String inputLastSeen : droneData.getDroneCreate()) {
                ZonedDateTime zonedDateTime = ZonedDateTime.parse(inputLastSeen, DateTimeFormatter.ISO_DATE_TIME);
                String formattedLastSeen = zonedDateTime.format(germanFormatter);
                resultCreate.add(formattedLastSeen);
            }

            return resultCreate;
        });
    }

}