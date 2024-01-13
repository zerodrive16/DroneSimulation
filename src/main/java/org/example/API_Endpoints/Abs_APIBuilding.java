package org.example.API_Endpoints;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.example.Config.token;

/*
 * The abstract class defines the templates that can be used in subclasses
 * It has a generic type, which can be used in subclasses to define the specific datatype or object
*/
public abstract class Abs_APIBuilding<Generic> {
    // number of available CPU cores to optimise the executor thread pool
    protected static final int CPUCoreCount = Runtime.getRuntime().availableProcessors();
    // Executor service to manage the thread pool in asynchronous task operations
    protected static final ExecutorService executor = Executors.newFixedThreadPool(CPUCoreCount);

    /*
     * Asynchronously sends an HTTP request with GET operation to the specified URL and returns the response
     * @param url: The url to send the request to
     * @return: It's a CompletableFuture that , when complete, will convert the response as a String value
    */
    protected CompletableFuture<String> APIRequestAsync(String url) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                HttpURLConnection con;
                con = (HttpURLConnection) new URL(url).openConnection();
                // setting the Authorization and token, GET operation
                con.setRequestProperty("Authorization", retrieveToken());
                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", "XYZ");

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                // it reads through the inputStream and appends each line to the response
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return String.valueOf(response);
                // exception for error handling the HTTP request
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

    /*
     * Retrieves the authorization token for the API request
     * It checks if the token is either empty or null
     * @throw it throws the IllegalStateException
    */
    protected String retrieveToken(){
        if(token == null || token.isEmpty()){
            throw new IllegalStateException("Token is either null or empty");
        }
        return token;
    }

    /*
     * Abstract function to build the asynchronous API subclass
     * @return it returns the CompletableFuture resultFuture
    */
    public abstract CompletableFuture<Generic> APIBuildAsync();

}
