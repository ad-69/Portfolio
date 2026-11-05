//Purpose: Learning the weight vector to identify spam/ham
package model;

public class PegasosSVM {

    // Weight vector
    private double[] weights;

    // Regularization parameter
    private double lambda;

    // Number of features
    private int numFeatures;


    // Constructor
    public PegasosSVM(int numFeatures, double lambda) {

        this.numFeatures = numFeatures;
        this.lambda = lambda;

        // Initially all weights are zero
        this.weights = new double[numFeatures];
    }


    // Train the SVM
    public void train(double[][] X, int[] y, int iterations) {

        for (int t = 1; t <= iterations; t++) {

            // Select one training example
            int index = (t - 1) % X.length;

            double[] x = X[index];
            int label = y[index];


            // Learning rate
            double eta = 1.0 / (lambda * t);


            // Calculate y_i * (w^T x_i)
            double prediction =
                    dotProduct(weights, x);

            double margin =
                    label * prediction;


            // ==========================================
            // CASE 1: Margin is violated
            // ==========================================

            if (margin < 1) {

                for (int j = 0; j < numFeatures; j++) {

                    weights[j] =
                            (1 - eta * lambda) * weights[j]
                            + eta * label * x[j];
                }

            }


            // ==========================================
            // CASE 2: Margin is satisfied
            // ==========================================

            else {

                for (int j = 0; j < numFeatures; j++) {

                    weights[j] =
                            (1 - eta * lambda) * weights[j];
                }
            }


            // ==========================================
            // PROJECTION STEP
            // ==========================================

            projectWeights();
        }
    }


    // Calculate dot product: w^T x
    private double dotProduct(
            double[] w,
            double[] x) {

        double result = 0.0;

        for (int i = 0; i < w.length; i++) {

            result += w[i] * x[i];
        }

        return result;
    }


    // Projection step of Pegasos
    private void projectWeights() {

        double norm = 0.0;

        // Calculate ||w||
        for (int i = 0; i < weights.length; i++) {

            norm += weights[i] * weights[i];
        }

        norm = Math.sqrt(norm);


        // Maximum allowed norm
        double maxNorm =
                1.0 / Math.sqrt(lambda);


        // If weights are outside the allowed region,
        // scale them back
        if (norm > maxNorm) {

            double scale =
                    maxNorm / norm;

            for (int i = 0; i < weights.length; i++) {

                weights[i] *= scale;
            }
        }
    }


    // Predict the class of a new example
    public int predict(double[] x) {

        double score =
                dotProduct(weights, x);

        if (score >= 0) {

            return 1;

        } else {

            return -1;
        }
    }
}