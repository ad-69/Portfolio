package management;

import java.util.ArrayList;
import java.util.List;

public class SMSManager {

    private List<SMSRecord> messages;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public SMSManager() {

        messages = new ArrayList<>();
    }


    // ==========================================
    // ADD MESSAGE
    // ==========================================

    public void addMessage(
            String message,
            String prediction) {

        String priority =
                determinePriority(
                        message,
                        prediction
                );

        SMSRecord record =
                new SMSRecord(
                        message,
                        prediction,
                        priority
                );

        messages.add(record);
    }


    // ==========================================
    // DETERMINE PRIORITY
    // ==========================================

    private String determinePriority(
            String message,
            String prediction) {

        // Spam gets blocked
        if (prediction.equals("SPAM")) {

            return "BLOCK";
        }


        String text =
                message.toLowerCase();


        // Important messages
        if (
                text.contains("otp")
                || text.contains("transaction")
                || text.contains("bank")
                || text.contains("payment")
                || text.contains("appointment")
                || text.contains("meeting")
        ) {

            return "HIGH";
        }


        // Promotional messages
        if (
                text.contains("offer")
                || text.contains("discount")
                || text.contains("sale")
                || text.contains("recharge")
                || text.contains("deal")
        ) {

            return "LOW";
        }


        return "NORMAL";
    }


    // ==========================================
    // GET ALL MESSAGES
    // ==========================================

    public List<SMSRecord> getAllMessages() {

        return messages;
    }


    // ==========================================
    // GET SPAM
    // ==========================================

    public List<SMSRecord> getSpamMessages() {

        List<SMSRecord> spam =
                new ArrayList<>();

        for (SMSRecord record : messages) {

            if (
                    record.getPrediction()
                            .equals("SPAM")
            ) {

                spam.add(record);
            }
        }

        return spam;
    }


    // ==========================================
    // GET HAM
    // ==========================================

    public List<SMSRecord> getHamMessages() {

        List<SMSRecord> ham =
                new ArrayList<>();

        for (SMSRecord record : messages) {

            if (
                    record.getPrediction()
                            .equals("HAM")
            ) {

                ham.add(record);
            }
        }

        return ham;
    }


    // ==========================================
    // GET IMPORTANT
    // ==========================================

    public List<SMSRecord> getImportantMessages() {

        List<SMSRecord> important =
                new ArrayList<>();

        for (SMSRecord record : messages) {

            if (
                    record.getPriority()
                            .equals("HIGH")
            ) {

                important.add(record);
            }
        }

        return important;
    }


    // ==========================================
    // DELETE ALL SPAM
    // ==========================================

    public void deleteAllSpam() {

        messages.removeIf(
                record ->
                        record.getPrediction()
                                .equals("SPAM")
        );
    }


    // ==========================================
    // STATISTICS
    // ==========================================

    public int getTotalMessages() {

        return messages.size();
    }


    public int getSpamCount() {

        int count = 0;

        for (SMSRecord record : messages) {

            if (
                    record.getPrediction()
                            .equals("SPAM")
            ) {

                count++;
            }
        }

        return count;
    }


    public int getHamCount() {

        int count = 0;

        for (SMSRecord record : messages) {

            if (
                    record.getPrediction()
                            .equals("HAM")
            ) {

                count++;
            }
        }

        return count;
    }


    public int getImportantCount() {

        int count = 0;

        for (SMSRecord record : messages) {

            if (
                    record.getPriority()
                            .equals("HIGH")
            ) {

                count++;
            }
        }

        return count;
    }


    // ==========================================
    // OLD METHOD NAME
    // ==========================================
    // Kept so SpamClassifierGUI does not
    // give an error.

    public int getHighPriorityCount() {

        return getImportantCount();
    }


    // ==========================================
    // BLOCKED COUNT
    // ==========================================

    public int getBlockedCount() {

        int count = 0;

        for (SMSRecord record : messages) {

            if (
                    record.getPriority()
                            .equals("BLOCK")
            ) {

                count++;
            }
        }

        return count;
    }
}