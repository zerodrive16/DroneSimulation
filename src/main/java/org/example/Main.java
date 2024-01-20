package org.example;

import org.example.GUI.GUI;

import java.io.IOException;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class Main {
    private static final Logger LOGGER = Logger.getLogger(GUI.class.getName());

    public Main() {
        // setting up logger
        configureLogger();

        // check state of endpoints
        dronesLogger();
        droneTypesLogger();
        droneDynamicsLogger();
    }

    private void configureLogger() {
        try {
            FileHandler fileHandler = new FileHandler("GUI_logger.log", true);
            fileHandler.setLevel(Level.INFO);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.INFO);

        } catch(IOException ex) {
            LOGGER.log(Level.SEVERE, "Error in the setup!", ex);
        }
    }

    private void dronesLogger() {
        LOGGER.info("API call to Drones endpoint...");

        try {
            LOGGER.info("Drones API call was successful!");
        } catch(Exception ex) {
            LOGGER.log(Level.SEVERE, "Drones API call failed!", ex);
        }
    }
    private void droneTypesLogger() {
        LOGGER.info("API call to DroneTypes endpoint...");

        try {
            LOGGER.info("DroneTypes API call was successful!");
        } catch(Exception ex) {
            LOGGER.log(Level.SEVERE, "DroneTypes API call failed!", ex);
        }
    }
    private void droneDynamicsLogger() {
        LOGGER.info("API call to DroneDynamics endpoint...");

        try {
            LOGGER.info("DroneDynamics API call was successful!");
        } catch(Exception ex) {
            LOGGER.log(Level.SEVERE, "DroneDynamics API call failed!", ex);
        }
    }

    public static void main(String[] args) {
        new GUI();
    }
}
