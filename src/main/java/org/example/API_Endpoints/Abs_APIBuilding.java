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

/* The generic type doesn't assign an Object or datatype but rather temporarily holds a generic type parameter. The
object or datatype can be assigned later if the subclass of the abstract function is implemented */
public abstract class Abs_APIBuilding<Generic> {
    /* The CPUCoreCount counts the number of available cores which should be used as threads */
    protected static final int CPUCoreCount = Runtime.getRuntime().availableProcessors();
    /* The ExecutorService creates a thread pool with the desired threads. It can submit tasks for execution and provides a cycle
    that manages the execution or shutdown of the thread services */
    protected static final ExecutorService executor = Executors.newFixedThreadPool(CPUCoreCount);

    /* The Asynchronous function takes an Integer as parameter which stores the current id for current iteration. Then it
    uses supplyAsync which means that the HTTP request runs on a different thread asynchronously. Within the code it prepares
    an HTTP request which takes token, URL, CRUD operation and  reads it inside the inputStreamReader. It iterates through every
    entity till it reaches null. After that it returns the response (holds json format) as a String. The try-catch block handles
    the error of the HTTP request. Lastly, the executor creates a pool of threads, managing concurrent execution of multiple threads */
    protected CompletableFuture<String> APIRequestAsync(String url) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                HttpURLConnection con;
                con = (HttpURLConnection) new URL(url).openConnection();
                con.setRequestProperty("Authorization", retrieveToken());
                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", "XYZ");

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return String.valueOf(response);
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

    // this function checks if the token is either empty or null and if so it throws an Exception if not it returns the token
    protected String retrieveToken(){
        if(token == null || token.isEmpty()){
            throw new IllegalStateException("Token is either null or empty");
        }
        return token;
    }

    /* Two abstract classes that play a crucial part in building the REST API request which can be implemented in a
    subclass with the "extend" implementation */
    public abstract CompletableFuture<Generic> APIBuildAsync();

    protected abstract void processAsync(String paginationUrl, CompletableFuture<Generic> resultFuture);
}
