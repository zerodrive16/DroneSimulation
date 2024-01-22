package org.example.GUI.Utility;

import org.example.API_Properties.DroneDynamicsData;
import org.example.API_Properties.DronesData;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for converting date formats asynchronously.
 */
public class ConvertDate {

    private static final Logger logger = Logger.getLogger(ConvertDate.class.getName());

    /**
     * Asynchronously converts the last seen dates of drone dynamics data to a specific date format.
     *
     * @param droneDynamicData The drone dynamic data containing last seen dates.
     * @return A CompletableFuture that resolves to an ArrayList of formatted last seen dates.
     */
    public CompletableFuture<ArrayList<String>> performConvertLastSeenAsync(DroneDynamicsData.ReturnDroneDynamicData droneDynamicData) {
        return CompletableFuture.supplyAsync(() -> {
            ArrayList<String> resultLastSeen = new ArrayList<>();
            // Define the date formatter for the desired format (e.g., German format)
            DateTimeFormatter germanFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm").withLocale(java.util.Locale.GERMANY);

            for (String inputLastSeen : droneDynamicData.getDroneLastSeen()) {
                try {
                    // Parse the input date and format it
                    ZonedDateTime zonedDateTime = ZonedDateTime.parse(inputLastSeen, DateTimeFormatter.ISO_DATE_TIME);
                    String formattedLastSeen = zonedDateTime.format(germanFormatter);
                    resultLastSeen.add(formattedLastSeen);
                } catch (Exception e) {
                    // Log parsing or formatting exceptions
                    logger.log(Level.WARNING, "Exception during date conversion (last seen)", e);
                }
            }

            return resultLastSeen;
        }).exceptionally(ex -> {
            // Log exceptions occurred during the asynchronous execution
            logger.log(Level.SEVERE, "Exception during asynchronous execution (last seen)", ex);
            return null;
        });
    }

    /**
     * Asynchronously converts the creation dates of drone data to a specific date format.
     *
     * @param droneData The drone data containing creation dates.
     * @return A CompletableFuture that resolves to an ArrayList of formatted creation dates.
     */
    public CompletableFuture<ArrayList<String>> performConvertCreateAsync(DronesData.ReturnDroneData droneData) {
        return CompletableFuture.supplyAsync(() -> {
            ArrayList<String> resultCreate = new ArrayList<>();
            // Define the date formatter for the German format
            DateTimeFormatter germanFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm").withLocale(java.util.Locale.GERMANY);

            for (String inputCreate : droneData.getDroneCreate()) {
                try {
                    // Parse the input date and format it
                    ZonedDateTime zonedDateTime = ZonedDateTime.parse(inputCreate, DateTimeFormatter.ISO_DATE_TIME);
                    String formattedCreate = zonedDateTime.format(germanFormatter);
                    resultCreate.add(formattedCreate);
                } catch (Exception e) {
                    // Log parsing or formatting exceptions
                    logger.log(Level.WARNING, "Exception during date conversion (create)", e);
                }
            }

            return resultCreate;
        }).exceptionally(ex -> {
            // Log exceptions occurred during the asynchronous execution
            logger.log(Level.SEVERE, "Exception during asynchronous execution (create)", ex);
            return null;
        });
    }
}