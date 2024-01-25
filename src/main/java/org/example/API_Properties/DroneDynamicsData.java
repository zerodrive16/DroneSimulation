package org.example.API_Properties;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the data structure for drone dynamics information.
 */
public class DroneDynamicsData {
    public static class DroneDynamic {
        // variables which represent the elements inside the result array

        String speed;
        Double longitude;
        Double latitude;
        String battery_status;
        String last_seen;
        String status;

        public DroneDynamic() {
        }

        // getters to fetch the API data
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
    /**
     * this function takes the List of the results
     * it also takes the count which identifies how many entries exists
     * it uses the getter to retrieve the information
     */
    public static class DroneDynamicResult {
        List<DroneDynamic> results;
        Integer count;
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

        /**
         * After the asynchronous API call is done, it saves all the fetched data inside the Constructor and later assign it
         *
         * @param droneSpeed         List of drone speeds as Strings.
         * @param droneLongitude     List of drone longitudes as Doubles.
         * @param droneLatitude      List of drone latitudes as Doubles.
         * @param droneBatteryStatus List of drone battery statuses as Strings.
         * @param droneLastSeen      List of last seen timestamps as Strings.
         * @param droneStatus        List of drone statuses as Strings.
         */
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

        // getters to output the data
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
