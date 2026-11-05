package gui;

import email.EmailManager;
import email.EmailMessage;
import email.EmailRecord;
import email.GmailService;
import model.SpamClassifier;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DashboardGUI extends JFrame {

    // =====================================================
    // MACHINE LEARNING COMPONENTS
    // =====================================================

    private SpamClassifier spamClassifier;
    private EmailManager emailManager;

    // =====================================================
    // DASHBOARD COMPONENTS
    // =====================================================

    private JLabel totalLabel;
    private JLabel spamLabel;
    private JLabel hamLabel;
    private JLabel importantLabel;
    private JLabel statusLabel;

    private JTextArea messageArea;

    // =====================================================
    // COLORS
    // =====================================================

    private final Color BACKGROUND =
            new Color(245, 247, 250);

    private final Color HEADER_COLOR =
            new Color(45, 75, 120);

    private final Color CARD_COLOR =
            Color.WHITE;

    private final Color SPAM_COLOR =
            new Color(220, 70, 70);

    private final Color HAM_COLOR =
            new Color(70, 160, 100);

    private final Color IMPORTANT_COLOR =
            new Color(240, 170, 60);

    private final Color BUTTON_COLOR =
            new Color(70, 110, 170);

    private final Color BUTTON_TEXT =
            Color.WHITE;

    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public DashboardGUI(String username) {

        // Create email manager
        emailManager =
                new EmailManager();

        // Create Pegasos spam classifier
        spamClassifier =
                new SpamClassifier();

        // -------------------------------------------------
        // WINDOW SETTINGS
        // -------------------------------------------------

        setTitle(
                "Personal Intelligent Message Manager"
        );

        setSize(
                950,
                650
        );

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        // -------------------------------------------------
        // CREATE DASHBOARD
        // -------------------------------------------------

        createDashboard(username);

        setVisible(true);

        // -------------------------------------------------
        // TRAIN PEGASOS SVM
        // -------------------------------------------------

        trainClassifier();
    }

    // =====================================================
    // CREATE DASHBOARD
    // =====================================================

    private void createDashboard(
            String username) {

        // -------------------------------------------------
        // MAIN PANEL
        // -------------------------------------------------

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                15,
                                15
                        )
                );

        mainPanel.setBackground(
                BACKGROUND
        );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );

        // =================================================
        // HEADER
        // =================================================

        JLabel titleLabel =
                new JLabel(
                        "Personal Intelligent Message Manager"
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        titleLabel.setForeground(
                HEADER_COLOR
        );

        JLabel welcomeLabel =
                new JLabel(
                        "Welcome, " + username
                );

        welcomeLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        welcomeLabel.setForeground(
                Color.DARK_GRAY
        );

        JPanel headerPanel =
                new JPanel(
                        new BorderLayout()
                );

        headerPanel.setBackground(
                BACKGROUND
        );

        headerPanel.add(
                titleLabel,
                BorderLayout.WEST
        );

        headerPanel.add(
                welcomeLabel,
                BorderLayout.EAST
        );

        // =================================================
        // STATISTICS PANEL
        // =================================================

        JPanel statisticsPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                12,
                                12
                        )
                );

        statisticsPanel.setBackground(
                BACKGROUND
        );

        // Total emails
        totalLabel =
                createStatisticLabel(
                        "Total Emails\n0",
                        HEADER_COLOR
                );

        // Spam
        spamLabel =
                createStatisticLabel(
                        "Spam\n0",
                        SPAM_COLOR
                );

        // Ham
        hamLabel =
                createStatisticLabel(
                        "Ham\n0",
                        HAM_COLOR
                );

        // Important
        importantLabel =
                createStatisticLabel(
                        "Important\n0",
                        IMPORTANT_COLOR
                );

        statisticsPanel.add(
                totalLabel
        );

        statisticsPanel.add(
                spamLabel
        );

        statisticsPanel.add(
                hamLabel
        );

        statisticsPanel.add(
                importantLabel
        );

        // =================================================
        // MESSAGE AREA
        // =================================================

        messageArea =
                new JTextArea(
                        6,
                        40
                );

        messageArea.setLineWrap(
                true
        );

        messageArea.setWrapStyleWord(
                true
        );

        messageArea.setBackground(
                Color.WHITE
        );

        messageArea.setForeground(
                Color.DARK_GRAY
        );

        messageArea.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        messageArea.setBorder(
                BorderFactory.createTitledBorder(
                        "Enter a message to classify"
                )
        );

        // =================================================
        // BUTTONS
        // =================================================

        JButton checkButton =
                new JButton(
                        "Check Message"
                );

        JButton fetchGmailButton =
                new JButton(
                        "Fetch Gmail"
                );

        JButton viewSpamButton =
                new JButton(
                        "View Spam"
                );

        JButton viewHamButton =
                new JButton(
                        "View Ham"
                );

        JButton viewImportantButton =
                new JButton(
                        "View Important"
                );

        JButton deleteSpamButton =
                new JButton(
                        "Delete All Spam"
                );

        // -------------------------------------------------
        // STYLE BUTTONS
        // -------------------------------------------------

        styleButton(
                checkButton
        );

        styleButton(
                fetchGmailButton
        );

        styleButton(
                viewSpamButton
        );

        styleButton(
                viewHamButton
        );

        styleButton(
                viewImportantButton
        );

        styleButton(
                deleteSpamButton
        );

        // =================================================
        // BUTTON ACTIONS
        // =================================================

        checkButton.addActionListener(
                e -> checkMessage()
        );

        fetchGmailButton.addActionListener(
                e -> fetchGmail()
        );

        viewSpamButton.addActionListener(
                e ->
                        showEmails(
                                emailManager
                                        .getSpamEmails(),
                                "Spam Emails"
                        )
        );

        viewHamButton.addActionListener(
                e ->
                        showEmails(
                                emailManager
                                        .getHamEmails(),
                                "Ham Emails"
                        )
        );

        viewImportantButton.addActionListener(
                e ->
                        showEmails(
                                emailManager
                                        .getImportantEmails(),
                                "Important Emails"
                        )
        );

        deleteSpamButton.addActionListener(
                e -> deleteSpam()
        );

        // =================================================
        // BUTTON PANEL
        // =================================================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                8,
                                8
                        )
                );

        buttonPanel.setBackground(
                BACKGROUND
        );

        buttonPanel.add(
                checkButton
        );

        buttonPanel.add(
                fetchGmailButton
        );

        buttonPanel.add(
                viewSpamButton
        );

        buttonPanel.add(
                viewHamButton
        );

        buttonPanel.add(
                viewImportantButton
        );

        buttonPanel.add(
                deleteSpamButton
        );

        // =================================================
        // STATUS LABEL
        // =================================================

        statusLabel =
                new JLabel(
                        "Starting Pegasos SVM..."
                );

        statusLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        statusLabel.setForeground(
                HEADER_COLOR
        );

        statusLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        // =================================================
        // CENTER PANEL
        // =================================================

        JPanel centerPanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        centerPanel.setBackground(
                BACKGROUND
        );

        centerPanel.add(
                statisticsPanel,
                BorderLayout.NORTH
        );

        centerPanel.add(
                new JScrollPane(
                        messageArea
                ),
                BorderLayout.CENTER
        );

        centerPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        // =================================================
        // ADD COMPONENTS TO MAIN PANEL
        // =================================================

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                statusLabel,
                BorderLayout.SOUTH
        );

        // Add dashboard to window
        add(mainPanel);
    }

    // =====================================================
    // CREATE STATISTIC CARD
    // =====================================================

    private JLabel createStatisticLabel(
            String text,
            Color color) {

        JLabel label =
                new JLabel(
                        "<html><center>"
                        + text.replace(
                                "\n",
                                "<br>"
                        )
                        + "</center></html>",
                        SwingConstants.CENTER
                );

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        label.setForeground(
                color
        );

        label.setBackground(
                CARD_COLOR
        );

        label.setOpaque(
                true
        );

        label.setBorder(
                BorderFactory.createLineBorder(
                        color,
                        2
                )
        );

        return label;
    }

    // =====================================================
    // STYLE BUTTON
    // =====================================================

    private void styleButton(
            JButton button) {

        button.setBackground(
                BUTTON_COLOR
        );

        button.setForeground(
                BUTTON_TEXT
        );

        button.setFocusPainted(
                false
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        15,
                        10,
                        15
                )
        );
    }

    // =====================================================
    // TRAIN PEGASOS SVM
    // =====================================================

    private void trainClassifier() {

        statusLabel.setText(
                "Training Pegasos SVM..."
        );

        SwingWorker<Void, Void> worker =
                new SwingWorker<>() {

                    @Override
                    protected Void doInBackground()
                            throws Exception {

                        spamClassifier.train();

                        return null;
                    }

                    @Override
                    protected void done() {

                        try {

                            get();

                            statusLabel.setText(
                                    "Pegasos SVM ready!"
                            );

                        } catch (Exception e) {

                            statusLabel.setText(
                                    "Training failed."
                            );

                            JOptionPane.showMessageDialog(
                                    DashboardGUI.this,
                                    "Could not train Pegasos SVM:\n"
                                    + e.getMessage(),
                                    "Training Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                    }
                };

        worker.execute();
    }

    // =====================================================
    // CHECK MANUAL MESSAGE
    // =====================================================

    private void checkMessage() {

        String message =
                messageArea
                        .getText()
                        .trim();

        // Check empty message
        if (message.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a message.",
                    "Message Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Check whether model is trained
        if (!spamClassifier.isTrained()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Pegasos SVM is still training.\n"
                    + "Please wait.",
                    "Model Not Ready",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        String prediction;

        try {

            prediction =
                    spamClassifier.classify(
                            message
                    );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Classification failed:\n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // Store message
        emailManager.addEmail(
                "manual-"
                + System.currentTimeMillis(),

                "Manual Message",

                "User Entered Message",

                message,

                prediction
        );

        // Update counters
        updateStatistics();

        // Show result
        JOptionPane.showMessageDialog(
                this,
                "Prediction: "
                + prediction,
                "Pegasos SVM Result",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =====================================================
    // FETCH GMAIL
    // =====================================================

    private void fetchGmail() {

        // Make sure model is ready
        if (!spamClassifier.isTrained()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Pegasos SVM is still training.\n"
                    + "Please wait.",
                    "Model Not Ready",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        statusLabel.setText(
                "Connecting to Gmail..."
        );

        SwingWorker<List<EmailMessage>, Void>
                worker =
                new SwingWorker<>() {

                    @Override
                    protected List<EmailMessage>
                    doInBackground()
                            throws Exception {

                        GmailService gmailService =
                                new GmailService();

                        // Connect to Gmail
                        gmailService.connect();

                        // Fetch latest 10 emails
                        return gmailService
                                .getRecentEmails(10);
                    }

                    @Override
                    protected void done() {

                        try {

                            List<EmailMessage>
                                    emails =
                                    get();

                            classifyGmailEmails(
                                    emails
                            );

                            statusLabel.setText(
                                    "Gmail emails classified!"
                            );

                        } catch (Exception e) {

                            statusLabel.setText(
                                    "Gmail operation failed."
                            );

                            JOptionPane.showMessageDialog(
                                    DashboardGUI.this,
                                    "Could not fetch Gmail:\n"
                                    + e.getMessage(),
                                    "Gmail Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                    }
                };

        worker.execute();
    }

    // =====================================================
    // CLASSIFY GMAIL EMAILS
    // =====================================================

    private void classifyGmailEmails(
            List<EmailMessage> emails) {

        int spamCount = 0;

        for (
                EmailMessage email :
                emails
        ) {

            // Combine subject + message
            String text =
                    email.getSubject()
                    + " "
                    + email.getBody();

            // Predict using Pegasos
            String prediction =
                    spamClassifier.classify(
                            text
                    );

            // Store email
            emailManager.addEmail(
                    email.getId(),
                    email.getSender(),
                    email.getSubject(),
                    email.getBody(),
                    prediction
            );

            if (
                    prediction.equals(
                            "SPAM"
                    )
            ) {

                spamCount++;
            }
        }

        // Update dashboard counters
        updateStatistics();

        // Show result
        JOptionPane.showMessageDialog(
                this,
                "Fetched "
                + emails.size()
                + " Gmail emails.\n\n"
                + "Spam detected: "
                + spamCount
                + "\nHam detected: "
                + (
                        emails.size()
                        - spamCount
                ),
                "Gmail Classification Complete",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =====================================================
    // UPDATE STATISTICS
    // =====================================================

    private void updateStatistics() {

        totalLabel.setText(
                "<html><center>"
                + "Total Emails<br>"
                + emailManager
                        .getTotalEmails()
                + "</center></html>"
        );

        spamLabel.setText(
                "<html><center>"
                + "Spam<br>"
                + emailManager
                        .getSpamCount()
                + "</center></html>"
        );

        hamLabel.setText(
                "<html><center>"
                + "Ham<br>"
                + emailManager
                        .getHamCount()
                + "</center></html>"
        );

        importantLabel.setText(
                "<html><center>"
                + "Important<br>"
                + emailManager
                        .getImportantCount()
                + "</center></html>"
        );
    }

    // =====================================================
    // SHOW EMAILS
    // =====================================================

    private void showEmails(
            List<EmailRecord> emails,
            String title) {

        if (emails.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No emails found.",
                    title,
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        StringBuilder text =
                new StringBuilder();

        for (
                EmailRecord email :
                emails
        ) {

            text.append(
                    "From: "
            );

            text.append(
                    email.getSender()
            );

            text.append(
                    "\nSubject: "
            );

            text.append(
                    email.getSubject()
            );

            text.append(
                    "\nPrediction: "
            );

            text.append(
                    email.getPrediction()
            );

            text.append(
                    "\nPriority: "
            );

            text.append(
                    email.getPriority()
            );

            text.append(
                    "\n\n"
            );

            text.append(
                    email.getBody()
            );

            text.append(
                    "\n"
            );

            text.append(
                    "--------------------------------\n\n"
            );
        }

        JTextArea area =
                new JTextArea(
                        text.toString()
                );

        area.setEditable(
                false
        );

        area.setLineWrap(
                true
        );

        area.setWrapStyleWord(
                true
        );

        area.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        area
                );

        scrollPane.setPreferredSize(
                new Dimension(
                        700,
                        400
                )
        );

        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                title,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =====================================================
    // DELETE SPAM
    // =====================================================

    private void deleteSpam() {

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete all classified spam emails?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION
                );

        if (
                result
                == JOptionPane.YES_OPTION
        ) {

            emailManager.deleteAllSpam();

            updateStatistics();

            JOptionPane.showMessageDialog(
                    this,
                    "All spam emails removed from the application."
            );
        }
    }

    // =====================================================
    // MAIN METHOD
    // =====================================================

    public static void main(
            String[] args) {

        SwingUtilities.invokeLater(
                () ->
                        new DashboardGUI(
                                "Aditi"
                        )
        );
    }
}