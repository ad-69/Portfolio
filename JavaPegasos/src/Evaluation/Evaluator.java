//Purpose: After learning the data, how well does the algorithm classify a message as spam/ham.

package Evaluation;

import model.PegasosSVM;

public class Evaluator {

    
    public static class Results {

        public double accuracy;
        public double precision;
        public double recall;
        public double f1Score;

        public Results(
                double accuracy,
                double precision,
                double recall,
                double f1Score) {

            this.accuracy = accuracy;
            this.precision = precision;
            this.recall = recall;
            this.f1Score = f1Score;
        }
    }


   
    public static Results evaluate(
            PegasosSVM svm,
            double[][] X_test,
            int[] y_test) {

        int truePositive = 0; //correctly identified spam
        int trueNegative = 0; //correctly identified ham
        int falsePositive = 0; //incorrectly identified spam
        int falseNegative = 0; //incorrectly identified ham


        
        for (int i = 0;
             i < X_test.length;
             i++) {

            int actual =
                    y_test[i];

            int predicted =
                    svm.predict(
                            X_test[i]
                    );


            if (actual == 1 && predicted == 1) {

                truePositive++;

            } else if (actual == -1 && predicted == -1) {

                trueNegative++;

            } else if (actual == -1 && predicted == 1) {

                falsePositive++;

            } else if (actual == 1 && predicted == -1) {

                falseNegative++;
            }
        }


        

        int total =
                truePositive
                + trueNegative
                + falsePositive
                + falseNegative;


        

        double accuracy = //How many are classified correctly 
                (double)
                (truePositive + trueNegative)
                / total;


        

        double precision = 0.0;

        if (truePositive + falsePositive != 0) { //How many of the models were actually spam

            precision =
                    (double) truePositive
                    / (truePositive + falsePositive);
        }


       

        double recall = 0.0;

        if (truePositive + falseNegative != 0) {

            recall =
                    (double) truePositive
                    / (truePositive + falseNegative); //How many were successfully found as spam
        }


        

        double f1Score = 0.0; //Combines precision and recall into one number

        if (precision + recall != 0) {

            f1Score =
                    2 * precision * recall
                    / (precision + recall);
        }


        

        System.out.println();
        System.out.println(
                "===== CONFUSION MATRIX ====="
        );

        System.out.println();

        System.out.println(
                "                 Predicted"
        );

        System.out.println(
                "                 Ham    Spam"
        );

        System.out.printf(
                "Actual Ham      %5d  %5d%n",
                trueNegative,
                falsePositive
        );

        System.out.printf(
                "Actual Spam     %5d  %5d%n",
                falseNegative,
                truePositive
        );


        

        System.out.println();

        System.out.println(
                "===== PERFORMANCE ====="
        );

        System.out.printf(
                "Accuracy : %.4f%n",
                accuracy
        );

        System.out.printf(
                "Precision: %.4f%n",
                precision
        );

        System.out.printf(
                "Recall   : %.4f%n",
                recall
        );

        System.out.printf(
                "F1 Score : %.4f%n",
                f1Score
        );


       

        return new Results(
                accuracy,
                precision,
                recall,
                f1Score
        );
    }
}