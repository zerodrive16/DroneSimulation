package org.example.API_Properties;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the data structure for drone information.
 */
public class DronesData {

    /**
     * function represents the variables which are used to fetch the API data
     * It uses the getter method to fetch the data from the webserver
     */
    public static class Drone {
        private Integer id;
        private String dronetype;
        private String created;
        private String serialnumber;
        private Integer carriage_weight;
        private String carriage_type;

        // Getters for the Drone class properties
        public Integer getId() { return id; }
        public String getDronetype() { return dronetype; }
        public String getCreated() { return created; }
        public String getSerialnumber() { return serialnumber; }
        public Integer getCarriage_weight() { return carriage_weight; }
        public String getCarriage_type() { return carriage_type; }
    }

    /**
     * function takes the results which is the list of data to be fetched
     * it uses next to get the pagination
     * getters are used to get the data and the pagination
     */
    public static class DroneResult {
        private List<Drone> results;
        private String next;

        public List<Drone> getDroneResults() { return results; }
        public String getNext() { return next; }
    }

    /**
     * the lists are used to store the fetched API data
     */
    public static class ReturnDroneData {
        final private ArrayList<Integer> data_droneID;
        final private ArrayList<String> data_droneType;
        final private ArrayList<String> data_droneCreate;
        final private ArrayList<String> data_droneSerialnumber;
        final private ArrayList<Integer> data_droneCarriageWeight;
        final private ArrayList<String> data_droneCarriageType;

        /**
         * After the asynchronous API call is done, it saves all the fetched data inside the Constructor and later assign it
         *
         * @param droneID List of drone IDs.
         * @param droneTypeURL List of drone type URLs.
         * @param droneCreate List of drone creation dates.
         * @param droneSerialnumber List of drone serial numbers.
         * @param droneCarriageWeight List of drone carriage weights.
         * @param droneCarriageType List of drone carriage types.
         */
        public ReturnDroneData(ArrayList<Integer> droneID, ArrayList<String> droneTypeURL, ArrayList<String> droneCreate, ArrayList<String> droneSerialnumber,
                               ArrayList<Integer> droneCarriageWeight, ArrayList<String> droneCarriageType) {
            this.data_droneID = droneID;
            this.data_droneType = droneTypeURL;
            this.data_droneCreate = droneCreate;
            this.data_droneSerialnumber = droneSerialnumber;
            this.data_droneCarriageWeight = droneCarriageWeight;
            this.data_droneCarriageType = droneCarriageType;
        }

        // Getters are used to output the data outside its class
        public ArrayList<Integer> getDroneID() { return data_droneID; }
        public ArrayList<String> getDroneTypeURL() { return data_droneType; }
        public ArrayList<String> getDroneCreate() { return data_droneCreate; }
        public ArrayList<String> getDroneSerialnumber() { return data_droneSerialnumber; }
        public ArrayList<Integer> getDroneCarriageWeight() { return data_droneCarriageWeight; }
        public ArrayList<String> getDroneCarriageType() { return data_droneCarriageType; }
    }
}
