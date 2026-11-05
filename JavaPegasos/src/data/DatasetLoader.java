//Purpose: Reading the dataset file, and converting each row into a datapoint object
package data;

import java.io.*;
import java.util.*;

public class DatasetLoader {

    public static List<DataPoint> loadDataset(String filePath) //accesses the file
            throws IOException {
 //What is the file is not found?
        List<DataPoint> data = new ArrayList<>(); //List that stores the data points

        BufferedReader reader =
                new BufferedReader(new FileReader(filePath)); //helps read one line at a time

        String line;

        while ((line = reader.readLine()) != null) {

            String[] parts = line.split("\t", 2); //Splitting into message and label

            if (parts.length < 2) {
                continue;
            }

            String labelText = parts[0];
            String message = parts[1];

            int label;

            if (labelText.equals("spam")) {
                label = 1;
            } else if (labelText.equals("ham")) {
                label = -1;
            } else {
                continue; //labels the message as spam/ham that the algorithm will learn from
            }

            data.add(new DataPoint(message, label)); 
        }

        reader.close();

        return data;
    }
}