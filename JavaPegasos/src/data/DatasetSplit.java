//Purpose: Splits the data into training and test set
package data;

import java.util.*;

public class DatasetSplit {

    private List<DataPoint> trainingData;
    private List<DataPoint> testData;

    public DatasetSplit(List<DataPoint> data, double trainRatio) {

        
        List<DataPoint> shuffledData =
                new ArrayList<>(data);

       
        Collections.shuffle(shuffledData, new Random(42));

       
        int trainSize =
                (int) (shuffledData.size() * trainRatio);

        
        trainingData =
                new ArrayList<>(
                        shuffledData.subList(0, trainSize)
                );

        testData =
                new ArrayList<>(
                        shuffledData.subList(
                                trainSize,
                                shuffledData.size()
                        )
                );
    }

    public List<DataPoint> getTrainingData() {
        return trainingData;
    }

    public List<DataPoint> getTestData() {
        return testData;
    }
}