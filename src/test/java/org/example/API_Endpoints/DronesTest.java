package org.example.API_Endpoints;

import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;
import java.net.URL;


import static org.junit.jupiter.api.Assertions.*;

class DronesTest {
    @Test
    void APIDrones() throws Exception {
        final String token = "Token: 64f0e472cd96156e94da3c3e066c8d89e8b88f72";

        URL url = new URL("http://dronesim.facets-labs.com/api/drones/?limit=20&format=json");
        HttpURLConnection con;
        con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Authorization", token);
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "XYZ");
        int responseCode = con.getResponseCode();
        System.out.println("Response Code: " + responseCode);
        System.out.println("Response Message: " + con.getResponseMessage());
        assertEquals(200, responseCode);
    }
}