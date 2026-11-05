package management;

import java.time.LocalDateTime;

public class SMSRecord {

    private String message;
    private String prediction;
    private String priority;
    private LocalDateTime timestamp;

    public SMSRecord(
            String message,
            String prediction,
            String priority) {

        this.message = message;
        this.prediction = prediction;
        this.priority = priority;

        this.timestamp =
                LocalDateTime.now();
    }

    public String getMessage() {

        return message;
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