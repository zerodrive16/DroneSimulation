package org.example.API_Properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DroneDynamicsData {
    public static class DroneDynamic {
        private String drone;
        private String timestamp;
        private String speed;
        private String align_roll;
        private String align_pitch;
        private String align_yaw;
        private Double longitude;
        private Double latitude;
        private String battery_status;
        private String last_seen;
        private String status;

        public String getDrone() {
            return drone;
        }
        public String getTimestamp() {
            return timestamp;
        }
        public String getSpeed() {
            return speed;
        }
        public String getAlignRoll() {
            return align_roll;
        }
        public String getAlignPitch() {
            return align_pitch;
        }
        public String getAlignYaw() {
            return align_yaw;
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
        String next;
        private int count;

        public List<DroneDynamic> getResults() {
            return results;
        }

        public String getNext() {
            return next;
        }

        public int getCount() {
            return count;
        }
    }

    public static class ReturnDroneDynamicData {
        final private ArrayList<String> data_droneURL;
        final private ArrayList<String> data_droneTimeStamp;
        final private ArrayList<String> data_droneSpeed;
        final private ArrayList<String> data_droneAlignRoll;
        final private ArrayList<String> data_droneAlignPitch;
        final private ArrayList<String> data_droneAlignYaw;
        final private ArrayList<Double> data_droneLongitude;
        final private ArrayList<Double> data_droneLatitude;
        final private ArrayList<String> data_droneBatteryStatus;
        final private ArrayList<String> data_droneLastSeen;
        final private ArrayList<String> data_droneStatus;
        public ReturnDroneDynamicData(ArrayList<String> droneURL, ArrayList<String> droneTimestamp, ArrayList<String> droneSpeed,
                                      ArrayList<String> droneAlignRoll, ArrayList<String> droneAlignPitch, ArrayList<String> droneAlignYaw,
                                      ArrayList<Double> droneLongitude, ArrayList<Double> droneLatitude, ArrayList<String> droneBatteryStatus,
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
        public ArrayList<String> getDroneSpeed(){
            return data_droneSpeed;
        }
        public ArrayList<String> getDroneAlignRoll(){
            return data_droneAlignRoll;
        }
        public ArrayList<String> getDroneAlignPitch(){
            return data_droneAlignPitch;
        }
        public ArrayList<String> getDroneAlignYaw(){
            return data_droneAlignYaw;
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
