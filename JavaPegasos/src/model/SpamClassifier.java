package model;

import data.DataPoint;
import data.DatasetLoader;
import data.DatasetSplit;
import preprocessing.TextVectorizer;

import java.util.List;

public class SpamClassifier {

    private TextVectorizer vectorizer;
    private PegasosSVM svm;

    private boolean trained;

    public SpamClassifier() {

        vectorizer = new TextVectorizer();

        trained = false;
    }

    // Train the Pegasos SVM
    public void train() throws Exception {

        System.out.println(
                "Loading SMS spam dataset..."
        );

        List<DataPoint> data =
                DatasetLoader.loadDataset(
                        "data/SMSSpamCollection"
                );

        System.out.println(
                "Messages loaded: "
                + data.size()
        );

        // Split dataset
        DatasetSplit split =
                new DatasetSplit(
                        data,
                        0.8
                );

        List<DataPoint> trainingData =
                split.getTrainingData();

        System.out.println(
                "Training messages: "
                + trainingData.size()
        );

        // Build vocabulary ONLY from training data
        vectorizer.buildVocabulary(
                trainingData
        );

        System.out.println(
                "Vocabulary size: "
                + vectorizer.getVocabularySize()
        );

        // Create training matrix
        double[][] X_train =
                new double[
                        trainingData.size()
                ][
                        vectorizer.getVocabularySize()
                ];

        int[] y_train =
                new int[
                        trainingData.size()
                ];

        // Convert training messages into vectors
        for (int i = 0;
             i < trainingData.size();
             i++) {

            DataPoint point =
                    trainingData.get(i);

            X_train[i] =
                    vectorizer.vectorize(
                            point.getText()
                    );

            y_train[i] =
                    point.getLabel();
        }

        // Pegasos parameters
        double lambda = 0.0001;

        int iterations = 10000;

        // Create model
        svm =
                new PegasosSVM(
                        vectorizer.getVocabularySize(),
                        lambda
                );

        System.out.println(
                "Training Pegasos SVM..."
        );

        // Train
        svm.train(
                X_train,
                y_train,
                iterations
        );

        trained = true;

        System.out.println(
                "Pegasos training completed!"
        );
    }

    // Classify a new message
    public String classify(String text) {

        if (!trained) {

            throw new IllegalStateException(
                    "Spam classifier has not been trained."
            );
        }

        // Convert text into same feature representation
        double[] vector =
                vectorizer.vectorize(
                        text
                );

        // Predict using Pegasos
        int prediction =
                svm.predict(vector);

        if (prediction == 1) {
            return "SPAM";
        }

        return "HAM";
    }

    // Check whether model is trained
    public boolean isTrained() {
        return trained;
    }

    // Get vocabulary size
    public int getVocabularySize() {

        return vectorizer
                .getVocabularySize();
    }
}