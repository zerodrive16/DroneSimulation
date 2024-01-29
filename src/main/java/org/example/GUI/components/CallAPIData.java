package org.example.GUI.components;

import org.example.API_Endpoints.DroneDynamics;
import org.example.API_Endpoints.DroneTypes;
import org.example.API_Endpoints.Drones;
import org.example.API_Properties.DroneDynamicsData;
import org.example.API_Properties.DroneTypesData;
import org.example.API_Properties.DronesData;
import org.example.GUI.Utility.ConvertDate;
import org.example.GUI.Utility.ReverseGeo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.example.GUI.components.Dashboard.createDronePanel;
import static org.example.GUI.components.Pagination.addPaginationControls;
import static org.example.GUI.components.Pagination.displayPage;
/**
 * The CallAPIData class fetches data from multiple asynchronous API calls and updates the UI components accordingly.
 * It fetches drone data, drone types data, and drone dynamics data asynchronously from their respective APIs.
 * When successful, it performs reverse geocoding to obtain location information, converts date formats,
 * and updates the UI components with the fetched data.
 */
public class CallAPIData {
    private static final Logger logger = Logger.getLogger(CallAPIData.class.getName());
    /**
     * Fetches data from multiple asynchronous API calls and updates the UI components accordingly.
     *
     * @param mainColor The primary color used for UI components.
     * @param topPanel         The JPanel where the drone data will be displayed.
     * @param bottomPanel        The JPanel used for pagination controls.
     */
    public void fetchAPIData(Color mainColor, JPanel topPanel, JPanel bottomPanel) {
        Drones dronesAPI = new Drones();
        CompletableFuture<DronesData.ReturnDroneData> futureDronesData = dronesAPI.APIBuildAsync();

        DroneTypes droneTypesAPI = new DroneTypes();
        CompletableFuture<DroneTypesData.ReturnDroneTypesData> futureDroneTypesData = droneTypesAPI.APIBuildAsync();

        DroneDynamics droneDynamicsAPI = new DroneDynamics();
        CompletableFuture<DroneDynamicsData.ReturnDroneDynamicData> futureDroneDynamicsData = droneDynamicsAPI.APIBuildAsync();

        CompletableFuture<Void> combineFuture = CompletableFuture.allOf(
                futureDronesData, futureDroneTypesData, futureDroneDynamicsData
        );

        combineFuture.thenAccept(voidResult -> {
            try {
                DronesData.ReturnDroneData droneData = futureDronesData.get();
                DroneTypesData.ReturnDroneTypesData droneTypeData = futureDroneTypesData.get();
                DroneDynamicsData.ReturnDroneDynamicData droneDynamicData = futureDroneDynamicsData.get();

                ReverseGeo reverseGeo = new ReverseGeo();
                CompletableFuture<ArrayList<String>> geocodingFuture = reverseGeo.performReverseGeoAsync(droneDynamicData);

                ConvertDate convertDate = new ConvertDate();
                CompletableFuture<ArrayList<String>> futureConvertCreate = convertDate.performConvertCreateAsync(droneData);
                CompletableFuture<ArrayList<String>> futureConvertLastSeen = convertDate.performConvertLastSeenAsync(droneDynamicData);

                CompletableFuture<Void> combineConvertDate = CompletableFuture.allOf(futureConvertCreate, futureConvertLastSeen, geocodingFuture);

                combineConvertDate.thenAccept(convertResult -> SwingUtilities.invokeLater(() -> {
                    try {
                        ArrayList<String> geoData = geocodingFuture.get();
                        ArrayList<String> convertCreateData = futureConvertCreate.get();
                        ArrayList<String> convertLastSeenData = futureConvertLastSeen.get();

                        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                        topPanel.removeAll();

                        for (int droneIndex = 0; droneIndex < droneData.getDroneID().size(); droneIndex++) {
                            JPanel dronePanel = createDronePanel(droneData, droneTypeData, droneDynamicData, droneIndex, mainColor, geoData, convertCreateData, convertLastSeenData);
                            topPanel.add(dronePanel);
                        }

                        topPanel.revalidate();
                        topPanel.repaint();

                        displayPage(topPanel, droneData, droneTypeData, droneDynamicData, mainColor, geoData, convertCreateData, convertLastSeenData);
                        addPaginationControls(topPanel, bottomPanel, droneData, droneTypeData, droneDynamicData, mainColor, geoData, convertCreateData, convertLastSeenData);

                    } catch(InterruptedException | ExecutionException ex) {
                        logger.log(Level.SEVERE, "Error in Asynchronous part!", ex);
                    }
                }));

            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Error processing data", ex);
            }
        }).exceptionally(ex -> {
            SwingUtilities.invokeLater(() -> logger.log(Level.SEVERE, "Error in Asynchronous part!", ex));
            return null;
        });
    }
}
