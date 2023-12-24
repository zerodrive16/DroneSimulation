package org.example.API_Properties;

import java.util.ArrayList;
import java.util.List;

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
        final private ArrayList<String> data_droneURL;
        final private ArrayList<String> data_droneTimeStamp;
        final private ArrayList<Integer> data_droneSpeed;
        final private ArrayList<Float> data_droneAlignRoll;
        final private ArrayList<Float> data_droneAlignPitch;
        final private ArrayList<Float> data_droneAlignYaw;
        final private ArrayList<Double> data_droneLongitude;
        final private ArrayList<Double> data_droneLatitude;
        final private ArrayList<Integer> data_droneBatteryStatus;
        final private ArrayList<String> data_droneLastSeen;
        final private ArrayList<String> data_droneStatus;
        public ReturnDroneDynamicData(ArrayList<String> droneURL, ArrayList<String> droneTimestamp, ArrayList<Integer> droneSpeed,
                                      ArrayList<Float> droneAlignRoll, ArrayList<Float> droneAlignPitch, ArrayList<Float> droneAlignYaw,
                                      ArrayList<Double> droneLongitude, ArrayList<Double> droneLatitude, ArrayList<Integer> droneBatteryStatus,
                                      ArrayList<String> droneLastSeen, ArrayList<String> droneStatus){
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
        public ArrayList<String> getDroneURL(){
            return data_droneURL;
        }
        public ArrayList<String> getDroneTimeStamp(){
            return data_droneTimeStamp;
        }
        public ArrayList<Integer> getDroneSpeed(){
            return data_droneSpeed;
        }
        public ArrayList<Float> getDroneAlignRoll(){
            return data_droneAlignRoll;
        }
        public ArrayList<Float> getDroneAlignPitch(){
            return data_droneAlignPitch;
        }
        public ArrayList<Float> getDroneAlignYaw(){
            return data_droneAlignYaw;
        }
        public ArrayList<Double> getDroneLongitude(){
            return data_droneLongitude;
        }
        public ArrayList<Double> getDroneLatitude(){
            return data_droneLatitude;
        }
        public ArrayList<Integer> getDroneBatteryStatus(){
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
