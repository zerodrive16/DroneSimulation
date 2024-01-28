package org.example.GUI.components;

import org.example.API_Properties.DroneDynamicsData;
import org.example.API_Properties.DroneTypesData;
import org.example.API_Properties.DronesData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static org.example.GUI.components.Dashboard.createDronePanel;

/**
 * The Pagination class provides methods for adding pagination controls and displaying paginated data.
 * It helps in navigating through large datasets by displaying a limited number of items per page.
 */
public class Pagination {

    private static int currentPage = 1;
    private static final int ITEMS_PER_PAGE = 10;

    /**
     * Adds pagination controls to the specified topPanel panel via buttons at the buttom of the screen.
     * It also displays the current page number. Pressing one of the two buttons will call the displayPage function.
     *
     * @param topPanel               The JPanel where the paginated data will be displayed.
     * @param bottomPanel              The JPanel used for pagination controls.
     * @param droneData          The data object containing drone information.
     * @param droneTypeData      The data object containing drone type information.
     * @param droneDynamicData   The data object containing drone dynamic information.
     * @param primaryColor       The primary color used for UI components.
     * @param geoData            The geocoding data for location information.
     * @param convertCreateData  The converted creation date data.
     * @param convertLastSeenData The converted last seen date data.
     */
    static protected void addPaginationControls(JPanel topPanel,
                                       JPanel bottomPanel,
                                       DronesData.ReturnDroneData droneData,
                                       DroneTypesData.ReturnDroneTypesData droneTypeData,
                                       DroneDynamicsData.ReturnDroneDynamicData droneDynamicData,
                                       Color primaryColor,
                                       ArrayList<String> geoData,
                                       ArrayList<String> convertCreateData,
                                       ArrayList<String> convertLastSeenData) {
        // calculations for displaying a certain number of drones per page
        int totalItems = droneData.getDroneID().size();
        int totalPages = (int) Math.ceil((double) totalItems / ITEMS_PER_PAGE);

        // Create JButtons and JPanel for the pagination controls
        JButton prevButton = new JButton("Previous");
        prevButton.setFont(new Font("Arial", Font.BOLD, 12));
        prevButton.setForeground(Color.WHITE);
        prevButton.setBackground(Color.DARK_GRAY);
        prevButton.setOpaque(true);
        prevButton.setBorderPainted(false);
        prevButton.setFocusPainted(false);

        JButton nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 12));
        nextButton.setForeground(Color.WHITE);
        nextButton.setBackground(Color.DARK_GRAY);
        nextButton.setOpaque(true);
        nextButton.setBorderPainted(false);
        nextButton.setFocusPainted(false);

        JPanel paginationPanel = new JPanel();
        paginationPanel.setBackground(Color.WHITE);
        paginationPanel.add(prevButton);
        JLabel paginationLabel = new JLabel("Page " + currentPage + " of " + totalPages);
        paginationPanel.add(paginationLabel);
        paginationPanel.add(nextButton);

        // Function of pagination controls
        prevButton.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                displayPage(topPanel, droneData, droneTypeData, droneDynamicData, primaryColor, geoData, convertCreateData, convertLastSeenData);
                paginationLabel.setText("Page " + currentPage + " of " + totalPages);
                bottomPanel.add(paginationPanel,BorderLayout.SOUTH);
            }
        });
        nextButton.addActionListener(e -> {
            if (currentPage < totalPages) {
                currentPage++;
                displayPage(topPanel, droneData, droneTypeData, droneDynamicData, primaryColor, geoData, convertCreateData, convertLastSeenData);
                paginationLabel.setText("Page " + currentPage + " of " + totalPages);
                bottomPanel.add(paginationPanel,BorderLayout.SOUTH);
            }
        });
        bottomPanel.add(paginationPanel,BorderLayout.SOUTH);
    }

    /**
     * Displays the specified page of data on the topPanel panel.
     * First all elements of the page are removed and then calls createDronePanel to display current data.
     *
     * @param topPanel               The JPanel where the data will be displayed.
     * @param droneData          The data object containing drone information.
     * @param droneTypeData      The data object containing drone type information.
     * @param droneDynamicData   The data object containing drone dynamic information.
     * @param primaryColor       The primary color used for UI components.
     * @param geoData            The geocoding data for location information.
     * @param convertCreateData  The converted creation date data.
     * @param convertLastSeenData The converted last seen date data.
     */
    static protected void displayPage(JPanel topPanel,
                             DronesData.ReturnDroneData droneData,
                             DroneTypesData.ReturnDroneTypesData droneTypeData,
                             DroneDynamicsData.ReturnDroneDynamicData droneDynamicData,
                             Color primaryColor,
                             ArrayList<String> geoData,
                             ArrayList<String> convertCreateData,
                             ArrayList<String> convertLastSeenData) {
        int startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, droneData.getDroneID().size());

        topPanel.removeAll();
        for (int i = startIndex; i < endIndex; i++) {
            JPanel dronePanel = createDronePanel(droneData, droneTypeData, droneDynamicData, i, primaryColor, geoData, convertCreateData, convertLastSeenData);
            topPanel.add(dronePanel);
        }
        topPanel.revalidate();
        topPanel.repaint();
    }
}
