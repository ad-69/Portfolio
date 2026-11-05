package email;

import java.time.LocalDateTime;

public class EmailRecord {

    private String id;
    private String sender;
    private String subject;
    private String body;
    private String prediction;
    private String priority;
    private LocalDateTime timestamp;

    public EmailRecord(
            String id,
            String sender,
            String subject,
            String body,
            String prediction) {

        this.id = id;
        this.sender = sender;
        this.subject = subject;
        this.body = body;
        this.prediction = prediction;

        this.priority =
                determinePriority(
                        subject,
                        body,
                        prediction
                );

        this.timestamp =
                LocalDateTime.now();
    }

    private String determinePriority(
            String subject,
            String body,
            String prediction) {

        // Spam emails are blocked
        if (prediction.equals("SPAM")) {
            return "BLOCK";
        }

        String text =
                (subject + " " + body)
                        .toLowerCase();

        // Important emails
        if (
                text.contains("otp")
                || text.contains("transaction")
                || text.contains("bank")
                || text.contains("payment")
                || text.contains("appointment")
                || text.contains("meeting")
                || text.contains("interview")
                || text.contains("exam")
        ) {
            return "HIGH";
        }

        // Promotional emails
        if (
                text.contains("offer")
                || text.contains("discount")
                || text.contains("sale")
                || text.contains("deal")
                || text.contains("coupon")
                || text.contains("recharge")
        ) {
            return "LOW";
        }

        return "NORMAL";
    }

    public String getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getPrediction() {
        return prediction;
    }

    public String getPriority() {
        return priority;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}