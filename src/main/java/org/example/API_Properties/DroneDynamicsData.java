package org.example.API_Properties;

import java.util.ArrayList;
import java.util.List;

public class DroneDynamicsData {
    public static class DroneDynamic {
        private String speed;
        private Double longitude;
        private Double latitude;
        private String battery_status;
        private String last_seen;
        private String status;

        public String getSpeed() {
            return speed;
        }
        public Double getLongitude() {
            return longitude;
        }
        public Double getLatitude() {
            return latitude;
        }
        public String getBatteryStatus() {
            return battery_status;
        }
        public String getLastSeen() {
            return last_seen;
        }
        public String getStatus() { return status; }
    }
    public static class DroneDynamicResult {
        private List<DroneDynamic> results;
        private Integer count;
        public List<DroneDynamic> getResults() {
            return results;
        }
        public Integer getCount() {
            return count;
        }
    }

    public static class ReturnDroneDynamicData {
        final private ArrayList<String> data_droneSpeed;
        final private ArrayList<Double> data_droneLongitude;
        final private ArrayList<Double> data_droneLatitude;
        final private ArrayList<String> data_droneBatteryStatus;
        final private ArrayList<String> data_droneLastSeen;
        final private ArrayList<String> data_droneStatus;
        public ReturnDroneDynamicData(ArrayList<String> droneSpeed, ArrayList<Double> droneLongitude,
                                      ArrayList<Double> droneLatitude, ArrayList<String> droneBatteryStatus,
                                      ArrayList<String> droneLastSeen, ArrayList<String> droneStatus){
            this.data_droneSpeed = droneSpeed;
            this.data_droneLongitude = droneLongitude;
            this.data_droneLatitude = droneLatitude;
            this.data_droneBatteryStatus = droneBatteryStatus;
            this.data_droneLastSeen = droneLastSeen;
            this.data_droneStatus = droneStatus;
        }

        public ArrayList<String> getDroneSpeed(){
            return data_droneSpeed;
        }
        public ArrayList<Double> getDroneLongitude(){
            return data_droneLongitude;
        }
        public ArrayList<Double> getDroneLatitude(){
            return data_droneLatitude;
        }
        public ArrayList<String> getDroneBatteryStatus(){
            return data_droneBatteryStatus;
        }
        public ArrayList<String> getDroneLastSeen(){
            return data_droneLastSeen;
        }
        public ArrayList<String> getDroneStatus(){
            return data_droneStatus;
        }
    }
}
