package org.example;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SaveFile {
    public static void main(String[] args) {
        // Create an ArrayList with some data
        ArrayList<String> dataList = new ArrayList<>();
        dataList.add("First line of data");
        dataList.add("Second line of data");
        dataList.add("Third line of data");

        // Specify the file path
        String filePath = "example.txt";

        try {
            // Create a FileWriter object with the specified file path
            FileWriter fileWriter = new FileWriter(filePath);

            // Wrap the FileWriter in a BufferedWriter for efficient writing
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Write each element of the ArrayList to a separate line in the file
            for (String data : dataList) {
                bufferedWriter.write(data);
                bufferedWriter.newLine(); // Add a new line after each element
            }

            // Close the BufferedWriter to ensure all data is flushed and the file is saved
            bufferedWriter.close();

            System.out.println("File saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}