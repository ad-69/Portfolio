import data.DataPoint;
import data.DatasetLoader;
import data.DatasetSplit;
import preprocessing.TextVectorizer;
import model.PegasosSVM;
import Evaluation.Evaluator;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {

            // ==================================================
            // 1. LOAD THE DATASET
            // ==================================================

            List<DataPoint> data =
                    DatasetLoader.loadDataset(
                            "data/SMSSpamCollection"
                    );

            System.out.println(
                    "Number of messages: " + data.size()
            );


            // ==================================================
            // 2. SPLIT DATA INTO TRAINING AND TESTING
            // ==================================================

            DatasetSplit split =
                    new DatasetSplit(data, 0.8);

            List<DataPoint> trainingData =
                    split.getTrainingData();

            List<DataPoint> testData =
                    split.getTestData();

            System.out.println(
                    "Training messages: "
                    + trainingData.size()
            );

            System.out.println(
                    "Test messages: "
                    + testData.size()
            );


            // ==================================================
            // 3. CREATE TEXT VECTORIZER
            // ==================================================

            TextVectorizer vectorizer =
                    new TextVectorizer();


            // ==================================================
            // 4. BUILD VOCABULARY
            // ==================================================
            // IMPORTANT:
            // Vocabulary is built ONLY using training data.
            // This prevents data leakage.

            vectorizer.buildVocabulary(trainingData);

            System.out.println(
                    "Vocabulary size: "
                    + vectorizer.getVocabularySize()
            );


            // ==================================================
            // 5. CREATE TRAINING MATRICES
            // ==================================================

            double[][] X_train =
                    new double[
                            trainingData.size()
                    ][
                            vectorizer.getVocabularySize()
                    ];

            int[] y_train =
                    new int[trainingData.size()];


            // ==================================================
            // 6. CONVERT TRAINING SMS INTO NUMBERS
            // ==================================================

            for (int i = 0;
                 i < trainingData.size();
                 i++) {

                DataPoint point =
                        trainingData.get(i);

                // Convert SMS into numerical vector
                X_train[i] =
                        vectorizer.vectorize(
                                point.getText()
                        );

                // Store label
                y_train[i] =
                        point.getLabel();
            }


            // ==================================================
            // 7. CREATE TEST MATRICES
            // ==================================================

            double[][] X_test =
                    new double[
                            testData.size()
                    ][
                            vectorizer.getVocabularySize()
                    ];

            int[] y_test =
                    new int[testData.size()];


            // ==================================================
            // 8. CONVERT TEST SMS INTO NUMBERS
            // ==================================================

            for (int i = 0;
                 i < testData.size();
                 i++) {

                DataPoint point =
                        testData.get(i);

                // Use SAME vocabulary
                X_test[i] =
                        vectorizer.vectorize(
                                point.getText()
                        );

                // Store actual label
                y_test[i] =
                        point.getLabel();
            }


            // ==================================================
            // 9. CREATE PEGASOS SVM
            // ==================================================

            double lambda = 0.0001;

            PegasosSVM svm =
                    new PegasosSVM(
                            vectorizer.getVocabularySize(),
                            lambda
                    );


            // ==================================================
            // 10. TRAIN PEGASOS SVM
            // ==================================================

            int iterations = 10000;

            System.out.println();
            System.out.println(
                    "Training Pegasos SVM..."
            );

            svm.train(
                    X_train,
                    y_train,
                    iterations
            );

            System.out.println(
                    "Training completed!"
            );


            // ==================================================
            // 11. EVALUATE THE MODEL
            // ==================================================

            Evaluator.evaluate(
                    svm,
                    X_test,
                    y_test
            );


        } catch (IOException e) {

            System.out.println(
                    "Error reading dataset: "
                    + e.getMessage()
            );
        }
    }
}