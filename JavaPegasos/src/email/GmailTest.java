package email;

import java.util.List;

public class GmailTest {

    public static void main(String[] args) {

        try {

            System.out.println(
                    "================================"
            );

            System.out.println(
                    "       GMAIL EMAIL TEST"
            );

            System.out.println(
                    "================================"
            );

            System.out.println();

            GmailService gmailService =
                    new GmailService();

            System.out.println(
                    "Connecting to Gmail..."
            );

            gmailService.connect();

            System.out.println();

            System.out.println(
                    "Gmail connection successful!"
            );

            System.out.println();

            System.out.println(
                    "Fetching latest emails..."
            );

            // Fetch 10 emails
            List<EmailMessage> emails =
                    gmailService.getRecentEmails(10);

            System.out.println();

            System.out.println(
                    "===== INBOX EMAILS ====="
            );

            System.out.println();

            int count = 1;

            for (EmailMessage email : emails) {

                System.out.println(
                        "EMAIL " + count
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
                        "Message: "
                        + email.getBody()
                );

                System.out.println(
                        "--------------------------------"
                );

                count++;
            }

            System.out.println();

            System.out.println(
                    "Total emails fetched: "
                    + emails.size()
            );

        } catch (Exception e) {

            System.out.println();

            System.out.println(
                    "Gmail operation failed."
            );

            e.printStackTrace();
        }
    }
}