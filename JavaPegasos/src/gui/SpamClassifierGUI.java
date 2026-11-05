package gui;

import data.DataPoint;
import data.DatasetLoader;
import data.DatasetSplit;
import preprocessing.TextVectorizer;
import model.PegasosSVM;
import Evaluation.Evaluator;

import management.SMSManager;
import management.SMSRecord;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class SpamClassifierGUI extends JFrame {

    // ============================================================
    // MODEL
    // ============================================================

    private PegasosSVM svm;

    private TextVectorizer vectorizer;

    private SMSManager smsManager;


    private double lambda = 0.0001;

    private int iterations = 10000;


    // ============================================================
    // INPUT
    // ============================================================

    private JTextArea messageArea;


    // ============================================================
    // PREDICTION
    // ============================================================

    private JLabel predictionLabel;

    private JLabel priorityLabel;


    // ============================================================
    // MODEL INFORMATION
    // ============================================================

    private JLabel algorithmLabel;
    private JLabel kernelLabel;
    private JLabel lambdaLabel;
    private JLabel iterationsLabel;
    private JLabel featureLabel;
    private JLabel trainLabel;
    private JLabel testLabel;


    // ============================================================
    // MODEL PERFORMANCE
    // ============================================================

    private JLabel accuracyLabel;
    private JLabel precisionLabel;
    private JLabel recallLabel;
    private JLabel f1Label;


    // ============================================================
    // SMS MANAGEMENT
    // ============================================================

    private JLabel totalMessagesLabel;
    private JLabel spamCountLabel;
    private JLabel hamCountLabel;
    private JLabel importantCountLabel;
    private JLabel blockedCountLabel;


    // ============================================================
    // STATUS
    // ============================================================

    private JLabel statusLabel;


    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public SpamClassifierGUI() {

        setTitle(
                "Intelligent SMS Management System"
        );

        setSize(
                1250,
                850
        );

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);


        // Create SMS manager

        smsManager =
                new SMSManager();


        // Create GUI

        createGUI();


        // Train model

        trainModel();
    }


    // ============================================================
    // CREATE GUI
    // ============================================================

    private void createGUI() {

        setLayout(
                new BorderLayout(
                        10,
                        10
                )
        );


        // ========================================================
        // TITLE
        // ========================================================

        JLabel titleLabel =
                new JLabel(
                        "Intelligent SMS Management System",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        25
                )
        );

        titleLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        add(
                titleLabel,
                BorderLayout.NORTH
        );


        // ========================================================
        // MAIN PANEL
        // ========================================================

        JPanel mainPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                15,
                                15
                        )
                );


        // ========================================================
        // LEFT PANEL
        // ========================================================

        JPanel leftPanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );


        // --------------------------------------------------------
        // ENTER SMS
        // --------------------------------------------------------

        JLabel enterLabel =
                new JLabel(
                        "ENTER SMS MESSAGE"
                );

        enterLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        23
                )
        );

        leftPanel.add(
                enterLabel,
                BorderLayout.NORTH
        );


        // --------------------------------------------------------
        // TEXT AREA
        // --------------------------------------------------------

        messageArea =
                new JTextArea();

        messageArea.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        19
                )
        );

        messageArea.setLineWrap(true);

        messageArea.setWrapStyleWord(true);


        JScrollPane messageScroll =
                new JScrollPane(
                        messageArea
                );

        leftPanel.add(
                messageScroll,
                BorderLayout.CENTER
        );


        // --------------------------------------------------------
        // BUTTON PANEL
        // --------------------------------------------------------

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout()
                );


        JButton checkButton =
                new JButton(
                        "CHECK MESSAGE"
                );

        JButton clearButton =
                new JButton(
                        "CLEAR"
                );

        JButton exampleButton =
                new JButton(
                        "LOAD EXAMPLE"
                );


        checkButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        15
                )
        );

        clearButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        15
                )
        );

        exampleButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        15
                )
        );


        buttonPanel.add(
                checkButton
        );

        buttonPanel.add(
                clearButton
        );

        buttonPanel.add(
                exampleButton
        );


        leftPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );


        // ========================================================
        // RIGHT PANEL
        // ========================================================

        JPanel rightPanel =
                new JPanel();

        rightPanel.setLayout(
                new BoxLayout(
                        rightPanel,
                        BoxLayout.Y_AXIS
                )
        );


        // ========================================================
        // MODEL INFORMATION
        // ========================================================

        JPanel informationPanel =
                new JPanel();

        informationPanel.setLayout(
                new BoxLayout(
                        informationPanel,
                        BoxLayout.Y_AXIS
                )
        );

        informationPanel.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(
                                Color.LIGHT_GRAY
                        ),
                        "MODEL INFORMATION",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font(
                                "Arial",
                                Font.BOLD,
                                17
                        )
                )
        );


        algorithmLabel =
                createInfoLabel(
                        "Algorithm: Pegasos SVM"
                );

        kernelLabel =
                createInfoLabel(
                        "Kernel: Linear"
                );

        lambdaLabel =
                createInfoLabel(
                        "Lambda (λ): --"
                );

        iterationsLabel =
                createInfoLabel(
                        "Iterations: --"
                );

        featureLabel =
                createInfoLabel(
                        "Features: --"
                );

        trainLabel =
                createInfoLabel(
                        "Training samples: --"
                );

        testLabel =
                createInfoLabel(
                        "Test samples: --"
                );


        informationPanel.add(
                algorithmLabel
        );

        informationPanel.add(
                kernelLabel
        );

        informationPanel.add(
                lambdaLabel
        );

        informationPanel.add(
                iterationsLabel
        );

        informationPanel.add(
                featureLabel
        );

        informationPanel.add(
                trainLabel
        );

        informationPanel.add(
                testLabel
        );


        // ========================================================
        // PERFORMANCE
        // ========================================================

        JPanel performancePanel =
                new JPanel();

        performancePanel.setLayout(
                new BoxLayout(
                        performancePanel,
                        BoxLayout.Y_AXIS
                )
        );

        performancePanel.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(
                                Color.LIGHT_GRAY
                        ),
                        "MODEL PERFORMANCE",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font(
                                "Arial",
                                Font.BOLD,
                                17
                        )
                )
        );


        accuracyLabel =
                createInfoLabel(
                        "Accuracy: --"
                );

        precisionLabel =
                createInfoLabel(
                        "Precision: --"
                );

        recallLabel =
                createInfoLabel(
                        "Recall: --"
                );

        f1Label =
                createInfoLabel(
                        "F1 Score: --"
                );


        performancePanel.add(
                accuracyLabel
        );

        performancePanel.add(
                precisionLabel
        );

        performancePanel.add(
                recallLabel
        );

        performancePanel.add(
                f1Label
        );


        // ========================================================
        // SMS MANAGEMENT
        // ========================================================

        JPanel managementPanel =
                new JPanel();

        managementPanel.setLayout(
                new BoxLayout(
                        managementPanel,
                        BoxLayout.Y_AXIS
                )
        );

        managementPanel.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(
                                Color.LIGHT_GRAY
                        ),
                        "SMS MANAGEMENT",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font(
                                "Arial",
                                Font.BOLD,
                                17
                        )
                )
        );


        totalMessagesLabel =
                createInfoLabel(
                        "Total Messages: 0"
                );

        spamCountLabel =
                createInfoLabel(
                        "Spam: 0"
                );

        hamCountLabel =
                createInfoLabel(
                        "Ham: 0"
                );

        importantCountLabel =
                createInfoLabel(
                        "Important: 0"
                );

        blockedCountLabel =
                createInfoLabel(
                        "Blocked: 0"
                );


        managementPanel.add(
                totalMessagesLabel
        );

        managementPanel.add(
                spamCountLabel
        );

        managementPanel.add(
                hamCountLabel
        );

        managementPanel.add(
                importantCountLabel
        );

        managementPanel.add(
                blockedCountLabel
        );


        // ========================================================
        // PREDICTION
        // ========================================================

        JPanel predictionPanel =
                new JPanel(
                        new GridLayout(
                                2,
                                1
                        )
                );

        predictionPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "CLASSIFICATION RESULT"
                )
        );


        predictionLabel =
                new JLabel(
                        "Prediction: --",
                        SwingConstants.CENTER
                );

        predictionLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );


        priorityLabel =
                new JLabel(
                        "Priority: --",
                        SwingConstants.CENTER
                );

        priorityLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );


        predictionPanel.add(
                predictionLabel
        );

        predictionPanel.add(
                priorityLabel
        );


        // ========================================================
        // ADD EVERYTHING TO RIGHT PANEL
        // ========================================================

        rightPanel.add(
                informationPanel
        );

        rightPanel.add(
                Box.createVerticalStrut(10)
        );

        rightPanel.add(
                performancePanel
        );

        rightPanel.add(
                Box.createVerticalStrut(10)
        );

        rightPanel.add(
                managementPanel
        );

        rightPanel.add(
                Box.createVerticalStrut(10)
        );

        rightPanel.add(
                predictionPanel
        );


        // ========================================================
        // MAKE RIGHT SIDE SCROLLABLE
        // ========================================================

        JScrollPane rightScroll =
                new JScrollPane(
                        rightPanel
                );

        rightScroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );


        // ========================================================
        // ADD LEFT + RIGHT
        // ========================================================

        mainPanel.add(
                leftPanel
        );

        mainPanel.add(
                rightScroll
        );


        add(
                mainPanel,
                BorderLayout.CENTER
        );


        // ========================================================
        // STATUS
        // ========================================================

        statusLabel =
                new JLabel(
                        "Status: Starting..."
                );

        statusLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        5,
                        10,
                        5,
                        10
                )
        );


        add(
                statusLabel,
                BorderLayout.SOUTH
        );


        // ========================================================
        // BUTTON ACTIONS
        // ========================================================

        checkButton.addActionListener(
                e -> checkMessage()
        );


        clearButton.addActionListener(
                e -> {

                    messageArea.setText("");

                    predictionLabel.setText(
                            "Prediction: --"
                    );

                    priorityLabel.setText(
                            "Priority: --"
                    );
                }
        );


        exampleButton.addActionListener(
                e -> {

                    messageArea.setText(
                            "Congratulations! You have won "
                            + "a free prize! Call now to claim "
                            + "your reward."
                    );
                }
        );
    }


    // ============================================================
    // CREATE LABEL
    // ============================================================

    private JLabel createInfoLabel(
            String text) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        17
                )
        );

        label.setBorder(
                BorderFactory.createEmptyBorder(
                        5,
                        8,
                        5,
                        8
                )
        );

        return label;
    }


    // ============================================================
    // TRAIN MODEL
    // ============================================================

    private void trainModel() {

        try {

            statusLabel.setText(
                    "Status: Loading dataset..."
            );


            // ----------------------------------------------------
            // 1. LOAD DATA
            // ----------------------------------------------------

            List<DataPoint> data =
                    DatasetLoader.loadDataset(
                            "data/SMSSpamCollection"
                    );


            // ----------------------------------------------------
            // 2. SPLIT DATA
            // ----------------------------------------------------

            DatasetSplit split =
                    new DatasetSplit(
                            data,
                            0.8
                    );


            List<DataPoint> trainingData =
                    split.getTrainingData();

            List<DataPoint> testData =
                    split.getTestData();


            // ----------------------------------------------------
            // 3. BUILD VOCABULARY
            // ----------------------------------------------------

            vectorizer =
                    new TextVectorizer();

            vectorizer.buildVocabulary(
                    trainingData
            );


            int vocabularySize =
                    vectorizer.getVocabularySize();


            // ----------------------------------------------------
            // 4. CREATE TRAINING ARRAYS
            // ----------------------------------------------------

            double[][] X_train =
                    new double[
                            trainingData.size()
                    ][
                            vocabularySize
                    ];

            int[] y_train =
                    new int[
                            trainingData.size()
                    ];


            for (
                    int i = 0;
                    i < trainingData.size();
                    i++
            ) {

                DataPoint point =
                        trainingData.get(i);


                X_train[i] =
                        vectorizer.vectorize(
                                point.getText()
                        );


                y_train[i] =
                        point.getLabel();
            }


            // ----------------------------------------------------
            // 5. CREATE TEST ARRAYS
            // ----------------------------------------------------

            double[][] X_test =
                    new double[
                            testData.size()
                    ][
                            vocabularySize
                    ];

            int[] y_test =
                    new int[
                            testData.size()
                    ];


            for (
                    int i = 0;
                    i < testData.size();
                    i++
            ) {

                DataPoint point =
                        testData.get(i);


                X_test[i] =
                        vectorizer.vectorize(
                                point.getText()
                        );


                y_test[i] =
                        point.getLabel();
            }


            // ----------------------------------------------------
            // 6. CREATE PEGASOS
            // ----------------------------------------------------

            svm =
                    new PegasosSVM(
                            vocabularySize,
                            lambda
                    );


            // ----------------------------------------------------
            // 7. TRAIN
            // ----------------------------------------------------

            statusLabel.setText(
                    "Status: Training Pegasos SVM..."
            );


            svm.train(
                    X_train,
                    y_train,
                    iterations
            );


            // ----------------------------------------------------
            // 8. EVALUATE
            // ----------------------------------------------------

            statusLabel.setText(
                    "Status: Evaluating model..."
            );


            Evaluator.Results results =
                    Evaluator.evaluate(
                            svm,
                            X_test,
                            y_test
                    );


            // ----------------------------------------------------
            // 9. MODEL INFORMATION
            // ----------------------------------------------------

            lambdaLabel.setText(
                    "Lambda (λ): "
                    + lambda
            );

            iterationsLabel.setText(
                    "Iterations: "
                    + iterations
            );

            featureLabel.setText(
                    "Features: "
                    + vocabularySize
            );

            trainLabel.setText(
                    "Training samples: "
                    + trainingData.size()
            );

            testLabel.setText(
                    "Test samples: "
                    + testData.size()
            );


            // ----------------------------------------------------
            // 10. MODEL PERFORMANCE
            // ----------------------------------------------------

            accuracyLabel.setText(
                    String.format(
                            "Accuracy: %.2f%%",
                            results.accuracy * 100
                    )
            );


            precisionLabel.setText(
                    String.format(
                            "Precision: %.2f%%",
                            results.precision * 100
                    )
            );


            recallLabel.setText(
                    String.format(
                            "Recall: %.2f%%",
                            results.recall * 100
                    )
            );


            f1Label.setText(
                    String.format(
                            "F1 Score: %.2f%%",
                            results.f1Score * 100
                    )
            );


            statusLabel.setText(
                    "Status: Model trained successfully."
            );


        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading dataset:\n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );


            statusLabel.setText(
                    "Status: Error loading dataset."
            );
        }
    }


    // ============================================================
    // CHECK MESSAGE
    // ============================================================

    private void checkMessage() {

        String message =
                messageArea.getText().trim();


        if (message.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter an SMS message.",
                    "No Message",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (
                svm == null
                || vectorizer == null
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Model is not ready.",
                    "Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // --------------------------------------------------------
        // 1. CONVERT TEXT TO VECTOR
        // --------------------------------------------------------

        double[] vector =
                vectorizer.vectorize(
                        message
                );


        // --------------------------------------------------------
        // 2. PEGASOS PREDICTION
        // --------------------------------------------------------

        int prediction =
                svm.predict(vector);


        String predictionText;


        if (prediction == 1) {

            predictionText =
                    "SPAM";

        } else {

            predictionText =
                    "HAM";
        }


        // --------------------------------------------------------
        // 3. ADD TO MANAGEMENT SYSTEM
        // --------------------------------------------------------

        smsManager.addMessage(
                message,
                predictionText
        );


        // --------------------------------------------------------
        // 4. GET LATEST RECORD
        // --------------------------------------------------------

        List<SMSRecord> allMessages =
                smsManager.getAllMessages();


        SMSRecord latest =
                allMessages.get(
                        allMessages.size() - 1
                );


        // --------------------------------------------------------
        // 5. DISPLAY RESULT
        // --------------------------------------------------------

        predictionLabel.setText(
                "Prediction: "
                + latest.getPrediction()
        );


        priorityLabel.setText(
                "Priority: "
                + latest.getPriority()
        );


        // --------------------------------------------------------
        // 6. UPDATE STATISTICS
        // --------------------------------------------------------

        updateManagementStatistics();


        statusLabel.setText(
                "Status: SMS classified and managed."
        );
    }


    // ============================================================
    // UPDATE MANAGEMENT STATISTICS
    // ============================================================

    private void updateManagementStatistics() {

        totalMessagesLabel.setText(
                "Total Messages: "
                + smsManager.getTotalMessages()
        );


        spamCountLabel.setText(
                "Spam: "
                + smsManager.getSpamCount()
        );


        hamCountLabel.setText(
                "Ham: "
                + smsManager.getHamCount()
        );


        importantCountLabel.setText(
                "Important: "
                + smsManager.getHighPriorityCount()
        );


        blockedCountLabel.setText(
                "Blocked: "
                + smsManager.getBlockedCount()
        );
    }


    // ============================================================
    // MAIN
    // ============================================================

    public static void main(
            String[] args) {

        SwingUtilities.invokeLater(
                () -> {

                    SpamClassifierGUI gui =
                            new SpamClassifierGUI();

                    gui.setVisible(true);
                }
        );
    }
}