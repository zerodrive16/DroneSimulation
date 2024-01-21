package org.example.API_Properties;

import java.util.ArrayList;

public class DroneTypesData {
    public static class DroneType {
        /**
         * variables which are useful later to fetch the API data
         */
        private String manufacturer;
        private String typename;
        private Integer weight;
        private Integer max_speed;
        private Integer battery_capacity;
        private Integer control_range;
        private Integer max_carriage;

        /**
         * Getters to fetch the API data
         */
        public String getManufacturer(){
            return manufacturer;
        }
        public String getTypename(){
            return typename;
        }
        public Integer getWeight(){
            return weight;
        }
        public Integer getMax_Speed(){
            return max_speed;
        }
        public Integer getBattery_Capacity(){
            return battery_capacity;
        }
        public Integer getControl_Range(){
            return control_range;
        }
        public Integer getMax_Carriage(){
            return max_carriage;
        }
    }

    /**
     * Aggregates data for multiple drone types.
     * This class holds collections of properties for each drone type.
     */
    public static class ReturnDroneTypesData {
        final private ArrayList<String> data_droneManufacturer;
        final private ArrayList<String> data_droneTypeName;
        final private ArrayList<Integer> data_droneWeight;
        final private ArrayList<Integer> data_droneMaxSpeed;
        final private ArrayList<Integer> data_droneBatteryCapacity;
        final private ArrayList<Integer> data_droneControlRange;
        final private ArrayList<Integer> data_droneMaxCarriage;

        /**
         * Constructor to initialize the properties for the drone types.
         * After the finished API call it returns the fetched data to the Constructor which will be then assigned
         *
         * @param droneManufacturer     List of manufacturers for each drone type.
         * @param droneTypeName         List of type names for each drone type.
         * @param droneWeight           List of weights for each drone type.
         * @param droneMaxSpeed         List of maximum speeds for each drone type.
         * @param droneBatteryCapacity  List of battery capacities for each drone type.
         * @param droneControlRange     List of control ranges for each drone type.
         * @param droneMaxCarriage      List of maximum carriage capacities for each drone type.
         */
        public ReturnDroneTypesData(ArrayList<String> droneManufacturer, ArrayList<String> droneTypeName, ArrayList<Integer> droneWeight,
                                   ArrayList<Integer> droneMaxSpeed, ArrayList<Integer> droneBatteryCapacity, ArrayList<Integer> droneControlRange,
                                   ArrayList<Integer> droneMaxCarriage){
            this.data_droneManufacturer = droneManufacturer;
            this.data_droneTypeName = droneTypeName;
            this.data_droneWeight = droneWeight;
            this.data_droneMaxSpeed = droneMaxSpeed;
            this.data_droneBatteryCapacity = droneBatteryCapacity;
            this.data_droneControlRange = droneControlRange;
            this.data_droneMaxCarriage = droneMaxCarriage;
        }

        /**
         * Getters to output the data outside its class
         */
        public ArrayList<String> getDroneManufacturer(){
            return data_droneManufacturer;
        }
        public ArrayList<String> getDroneTypeName(){
            return data_droneTypeName;
        }
        public ArrayList<Integer> getDroneWeight(){
            return data_droneWeight;
        }
        public ArrayList<Integer> getDroneMaxSpeed(){
            return data_droneMaxSpeed;
        }
        public ArrayList<Integer> getDroneBatteryCapacity(){
            return data_droneBatteryCapacity;
        }
        public ArrayList<Integer> getDroneControlRange(){
            return data_droneControlRange;
        }
        public ArrayList<Integer> getDroneMaxCarriage(){
            return data_droneMaxCarriage;
        }
    }
}
