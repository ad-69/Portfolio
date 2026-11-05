package email;

import data.DataPoint;
import data.DatasetLoader;
import data.DatasetSplit;
import preprocessing.TextVectorizer;
import model.PegasosSVM;

import java.util.List;

public class GmailSpamTest {

    public static void main(String[] args) {

        try {

            // =====================================
            // STEP 1: LOAD SMS DATASET
            // =====================================

            System.out.println(
                    "Loading SMS dataset..."
            );

            List<DataPoint> data =
                    DatasetLoader.loadDataset(
                            "data/SMSSpamCollection"
                    );

            System.out.println(
                    "Messages loaded: "
                    + data.size()
            );


            // =====================================
            // STEP 2: SPLIT DATA
            // =====================================

            DatasetSplit split =
                    new DatasetSplit(
                            data,
                            0.8
                    );

            List<DataPoint> trainingData =
                    split.getTrainingData();


            // =====================================
            // STEP 3: BUILD VOCABULARY
            // =====================================

            TextVectorizer vectorizer =
                    new TextVectorizer();

            vectorizer.buildVocabulary(
                    trainingData
            );

            System.out.println(
                    "Vocabulary size: "
                    + vectorizer.getVocabularySize()
            );


            // =====================================
            // STEP 4: VECTORIZE TRAINING DATA
            // =====================================

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


            // =====================================
            // STEP 5: TRAIN PEGASOS
            // =====================================

            double lambda = 0.0001;

            PegasosSVM svm =
                    new PegasosSVM(
                            vectorizer.getVocabularySize(),
                            lambda
                    );

            System.out.println(
                    "Training Pegasos SVM..."
            );

            svm.train(
                    X_train,
                    y_train,
                    10000
            );

            System.out.println(
                    "Training completed!"
            );


            // =====================================
            // STEP 6: CONNECT TO GMAIL
            // =====================================

            GmailService gmailService =
                    new GmailService();

            gmailService.connect();


            // =====================================
            // STEP 7: FETCH EMAILS
            // =====================================

            List<EmailMessage> emails =
                    gmailService.getRecentEmails(10);

            System.out.println();

            System.out.println(
                    "===== GMAIL SPAM CLASSIFICATION ====="
            );


            // =====================================
            // STEP 8: CLASSIFY EACH EMAIL
            // =====================================

            int emailNumber = 1;

            for (EmailMessage email :
                    emails) {

                /*
                 * Combine subject and message.
                 */

                String text =
                        email.getSubject()
                        + " "
                        + email.getBody();


                /*
                 * Convert email into the same
                 * numerical representation used
                 * during training.
                 */

                double[] vector =
                        vectorizer.vectorize(
                                text
                        );


                /*
                 * Ask Pegasos SVM to classify.
                 */

                int prediction =
                        svm.predict(vector);


                String result;

                if (prediction == 1) {

                    result = "SPAM";

                } else {

                    result = "HAM";
                }


                // =================================
                // DISPLAY RESULT
                // =================================

                System.out.println();

                System.out.println(
                        "EMAIL " + emailNumber
                );

                System.out.println(
                        "From: "
                        + email.getSender()
                );

                System.out.println(
                        "Subject: "
                        + email.getSubject()
                );

                System.out.println(
                        "Prediction: "
                        + result
                );

                System.out.println(
                        "--------------------------------"
                );

                emailNumber++;
            }


            System.out.println();

            System.out.println(
                    "Total emails classified: "
                    + emails.size()
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}

