package org.example.API_Properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DroneDynamicsData {
    public static class DroneDynamic {
        private String drone;
        private String timestamp;
        private Integer speed;
        private Float align_roll;
        private Float align_pitch;
        private Float align_yaw;
        private Double longitude;
        private Double latitude;
        private Integer battery_status;
        private String last_seen;
        private String status;

        public String getDrone() {
            return drone;
        }
        public String getTimestamp() {
            return timestamp;
        }
        public Integer getSpeed() {
            return speed;
        }
        public Float getAlignRoll() {
            return align_roll;
        }
        public Float getAlignPitch() {
            return align_pitch;
        }
        public Float getAlignYaw() {
            return align_yaw;
        }
        public Double getLongitude() {
            return longitude;
        }
        public Double getLatitude() {
            return latitude;
        }
        public Integer getBatteryStatus() {
            return battery_status;
        }
        public String getLastSeen() {
            return last_seen;
        }
        public String getStatus() { return status; }
    }
    public static class DroneDynamicResult {
        private List<DroneDynamic> results;
        String next;

        public List<DroneDynamic> getResults() {
            return results;
        }

        public String getNext() {
            return next;
        }
    }

    public static class ReturnDroneDynamicData {
        final private Map<Integer, ArrayList<String>> data_droneURL;
        final private Map<Integer, ArrayList<String>> data_droneTimeStamp;
        final private Map<Integer, ArrayList<String>> data_droneSpeed;
        final private Map<Integer, ArrayList<String>> data_droneAlignRoll;
        final private Map<Integer, ArrayList<String>> data_droneAlignPitch;
        final private Map<Integer, ArrayList<String>> data_droneAlignYaw;
        final private Map<Integer, ArrayList<String>> data_droneLongitude;
        final private Map<Integer, ArrayList<String>> data_droneLatitude;
        final private Map<Integer, ArrayList<String>> data_droneBatteryStatus;
        final private Map<Integer, ArrayList<String>> data_droneLastSeen;
        final private Map<Integer, ArrayList<String>> data_droneStatus;
        public ReturnDroneDynamicData(Map<Integer, ArrayList<String>> droneURL, Map<Integer, ArrayList<String>> droneTimestamp, Map<Integer, ArrayList<String>> droneSpeed,
                                      Map<Integer, ArrayList<String>> droneAlignRoll, Map<Integer, ArrayList<String>> droneAlignPitch, Map<Integer, ArrayList<String>> droneAlignYaw,
                                      Map<Integer, ArrayList<String>> droneLongitude, Map<Integer, ArrayList<String>> droneLatitude, Map<Integer, ArrayList<String>> droneBatteryStatus,
                                      Map<Integer, ArrayList<String>> droneLastSeen, Map<Integer, ArrayList<String>> droneStatus){
            this.data_droneURL = droneURL;
            this.data_droneTimeStamp = droneTimestamp;
            this.data_droneSpeed = droneSpeed;
            this.data_droneAlignRoll = droneAlignRoll;
            this.data_droneAlignPitch = droneAlignPitch;
            this.data_droneAlignYaw = droneAlignYaw;
            this.data_droneLongitude = droneLongitude;
            this.data_droneLatitude = droneLatitude;
            this.data_droneBatteryStatus = droneBatteryStatus;
            this.data_droneLastSeen = droneLastSeen;
            this.data_droneStatus = droneStatus;
        }
        public Map<Integer, ArrayList<String>> getDroneURL(){
            return data_droneURL;
        }
        public Map<Integer, ArrayList<String>> getDroneTimeStamp(){
            return data_droneTimeStamp;
        }
        public Map<Integer, ArrayList<String>> getDroneSpeed(){
            return data_droneSpeed;
        }
        public Map<Integer, ArrayList<String>> getDroneAlignRoll(){
            return data_droneAlignRoll;
        }
        public Map<Integer, ArrayList<String>> getDroneAlignPitch(){
            return data_droneAlignPitch;
        }
        public Map<Integer, ArrayList<String>> getDroneAlignYaw(){
            return data_droneAlignYaw;
        }
        public Map<Integer, ArrayList<String>> getDroneLongitude(){
            return data_droneLongitude;
        }
        public Map<Integer, ArrayList<String>> getDroneLatitude(){
            return data_droneLatitude;
        }
        public Map<Integer, ArrayList<String>> getDroneBatteryStatus(){
            return data_droneBatteryStatus;
        }
        public Map<Integer, ArrayList<String>> getDroneLastSeen(){
            return data_droneLastSeen;
        }
        public Map<Integer, ArrayList<String>> getDroneStatus(){
            return data_droneStatus;
        }
    }
}
