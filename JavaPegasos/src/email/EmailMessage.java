package email;

public class EmailMessage {

    private String id;
    private String sender;
    private String subject;
    private String body;

    public EmailMessage(
            String id,
            String sender,
            String subject,
            String body) {

        this.id = id;
        this.sender = sender;
        this.subject = subject;
        this.body = body;
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

    @Override
    public String toString() {

        return "From: " + sender
                + "\nSubject: " + subject
                + "\n\n"
                + body;
    }
}