package email;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GmailService {

    private static final String APPLICATION_NAME =
            "Pegasos Message Manager";

    private static final String CREDENTIALS_FILE =
            "credentials.json";

    private static final String TOKENS_DIRECTORY =
            "tokens";

    private static final List<String> SCOPES =
            Collections.singletonList(
                    GmailScopes.GMAIL_READONLY
            );

    private Gmail gmail;


    // ==========================================
    // CONNECT TO GMAIL
    // ==========================================

    public void connect() throws Exception {

        System.out.println(
                "Starting Gmail connection..."
        );

        NetHttpTransport httpTransport =
                GoogleNetHttpTransport
                        .newTrustedTransport();

        Credential credential =
                getCredentials(httpTransport);

        gmail =
                new Gmail.Builder(
                        httpTransport,
                        GsonFactory.getDefaultInstance(),
                        credential
                )
                .setApplicationName(
                        APPLICATION_NAME
                )
                .build();

        System.out.println(
                "Gmail connected successfully!"
        );
    }


    // ==========================================
    // GET OAUTH CREDENTIALS
    // ==========================================

    private Credential getCredentials(
            NetHttpTransport httpTransport)
            throws Exception {

        InputStream inputStream =
                new FileInputStream(
                        CREDENTIALS_FILE
                );

        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(
                        GsonFactory.getDefaultInstance(),
                        new InputStreamReader(
                                inputStream
                        )
                );

        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        httpTransport,
                        GsonFactory.getDefaultInstance(),
                        clientSecrets,
                        SCOPES
                )
                .setDataStoreFactory(
                        new FileDataStoreFactory(
                                new File(TOKENS_DIRECTORY)
                        )
                )
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver =
                new LocalServerReceiver.Builder()
                        .setPort(8888)
                        .build();

        return new AuthorizationCodeInstalledApp(
                flow,
                receiver
        ).authorize("user");
    }


    // ==========================================
    // FETCH RECENT EMAILS
    // ==========================================

    public List<EmailMessage> getRecentEmails(
            int maxResults)
            throws Exception {

        if (gmail == null) {

            throw new IllegalStateException(
                    "Gmail is not connected."
            );
        }

        System.out.println(
                "Fetching messages from Gmail..."
        );

        ListMessagesResponse response =
                gmail.users()
                        .messages()
                        .list("me")
                        .setMaxResults(
                                (long) maxResults
                        )
                        .setLabelIds(
                                Collections.singletonList(
                                        "INBOX"
                                )
                        )
                        .execute();

        List<EmailMessage> emails =
                new ArrayList<>();

        if (response.getMessages() == null) {

            return emails;
        }


        // Go through each Gmail message

        for (Message message :
                response.getMessages()) {

            Message fullMessage =
                    gmail.users()
                            .messages()
                            .get(
                                    "me",
                                    message.getId()
                            )
                            .setFormat("full")
                            .execute();

            String sender = "";
            String subject = "";
            String body = "";


            // Extract sender and subject

            if (
                    fullMessage.getPayload() != null
                    &&
                    fullMessage
                            .getPayload()
                            .getHeaders() != null
            ) {

                for (
                        var header :
                        fullMessage
                                .getPayload()
                                .getHeaders()
                ) {

                    if (
                            header.getName()
                                    .equalsIgnoreCase(
                                            "From"
                                    )
                    ) {

                        sender =
                                header.getValue();
                    }

                    if (
                            header.getName()
                                    .equalsIgnoreCase(
                                            "Subject"
                                    )
                    ) {

                        subject =
                                header.getValue();
                    }
                }
            }


            // Use Gmail snippet as message body
            body =
                    fullMessage.getSnippet();


            // Create EmailMessage object

            EmailMessage email =
                    new EmailMessage(
                            fullMessage.getId(),
                            sender,
                            subject,
                            body
                    );

            emails.add(email);
        }

        return emails;
    }
}