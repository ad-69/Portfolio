package email;

import java.util.ArrayList;
import java.util.List;

public class EmailManager {

    private List<EmailRecord> emails;

    public EmailManager() {
        emails = new ArrayList<>();
    }

    // Add a classified email
    public void addEmail(
            String id,
            String sender,
            String subject,
            String body,
            String prediction) {

        EmailRecord record =
                new EmailRecord(
                        id,
                        sender,
                        subject,
                        body,
                        prediction
                );

        emails.add(record);
    }

    // Get all emails
    public List<EmailRecord> getAllEmails() {
        return emails;
    }

    // Get only spam
    public List<EmailRecord> getSpamEmails() {

        List<EmailRecord> spam =
                new ArrayList<>();

        for (EmailRecord email : emails) {

            if (email.getPrediction()
                    .equals("SPAM")) {

                spam.add(email);
            }
        }

        return spam;
    }

    // Get only ham
    public List<EmailRecord> getHamEmails() {

        List<EmailRecord> ham =
                new ArrayList<>();

        for (EmailRecord email : emails) {

            if (email.getPrediction()
                    .equals("HAM")) {

                ham.add(email);
            }
        }

        return ham;
    }

    // Get important emails
    public List<EmailRecord> getImportantEmails() {

        List<EmailRecord> important =
                new ArrayList<>();

        for (EmailRecord email : emails) {

            if (email.getPriority()
                    .equals("HIGH")) {

                important.add(email);
            }
        }

        return important;
    }

    // Delete all spam
    public void deleteAllSpam() {

        emails.removeIf(
                email ->
                        email.getPrediction()
                                .equals("SPAM")
        );
    }

    // Total number of emails
    public int getTotalEmails() {
        return emails.size();
    }

    // Number of spam emails
    public int getSpamCount() {

        int count = 0;

        for (EmailRecord email : emails) {

            if (email.getPrediction()
                    .equals("SPAM")) {

                count++;
            }
        }

        return count;
    }

    // Number of ham emails
    public int getHamCount() {

        int count = 0;

        for (EmailRecord email : emails) {

            if (email.getPrediction()
                    .equals("HAM")) {

                count++;
            }
        }

        return count;
    }

    // Number of important emails
    public int getImportantCount() {

        int count = 0;

        for (EmailRecord email : emails) {

            if (email.getPriority()
                    .equals("HIGH")) {

                count++;
            }
        }

        return count;
    }

    // Number of blocked emails
    public int getBlockedCount() {

        int count = 0;

        for (EmailRecord email : emails) {

            if (email.getPriority()
                    .equals("BLOCK")) {

                count++;
            }
        }

        return count;
    }

    // Number of low-priority emails
    public int getLowPriorityCount() {

        int count = 0;

        for (EmailRecord email : emails) {

            if (email.getPriority()
                    .equals("LOW")) {

                count++;
            }
        }

        return count;
    }
}