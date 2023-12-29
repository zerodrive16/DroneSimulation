package org.example.API_Endpoints;

import org.example.API_Properties.DroneDynamicsData;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class DroneDynamics extends Abs_APIBuilding<DroneDynamicsData.ReturnDroneDynamicData> {

    private Map<Integer, ArrayList<String>> droneURL = new HashMap<>();
    private Map<Integer, ArrayList<String>> droneTimestamp = new HashMap<>();
    private Map<Integer, ArrayList<String>> droneSpeed = new HashMap<>();
    private Map<Integer, ArrayList<String>> droneAlignRoll = new HashMap<>();
    private Map<Integer, ArrayList<String>> droneAlignPitch = new HashMap<>();
    private Map<Integer, ArrayList<String>> droneAlignYaw = new HashMap<>();
    private Map<Integer, ArrayList<String>> droneLongitude = new HashMap<>();
    private Map<Integer, ArrayList<String>> droneLatitude = new HashMap<>();
    private Map<Integer, ArrayList<String>> droneBatteryStatus = new HashMap<>();
    private Map<Integer, ArrayList<String>> droneLastSeen = new HashMap<>();
    private Map<Integer, ArrayList<String>> droneStatus = new HashMap<>();


    @Override
    public CompletableFuture<DroneDynamicsData.ReturnDroneDynamicData> APIBuildAsync() {
        return null;
    }

    @Override
    protected void processAsync(String paginationUrl, CompletableFuture<DroneDynamicsData.ReturnDroneDynamicData> resultFuture) {

    }
}
