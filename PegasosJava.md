
# Java Pegasos SVM – Message Management System

## About the Project

This project is a Java implementation of the Pegasos Support Vector Machine (SVM) algorithm, developed as part of my Software Lab in Java.

The idea was to explore how the Pegasos algorithm works in practice and use it to build a message classification system that can distinguish between Spam and Ham messages.

I implemented the algorithm in Java, starting with text preprocessing and feature extraction, followed by training the SVM model and evaluating its classification performance. I then extended the project by integrating the Gmail API to retrieve emails and work towards classifying actual Gmail messages through a graphical interface.

## What I Worked On

- Implemented the Pegasos SVM algorithm in Java.
- Worked with text preprocessing and feature extraction using a Bag-of-Words approach.
- Converted messages into numerical feature vectors for classification.
- Trained the model to distinguish between Spam and Ham messages.
- Evaluated the model using metrics such as accuracy, precision, recall and F1-score.
- Integrated the Gmail API to retrieve email messages.
- Worked on connecting the trained classifier with a graphical interface to display email classification results.

## About the Algorithm

Pegasos (Primal Estimated sub-GrAdient SOlver for SVM) is an optimization algorithm used to train linear Support Vector Machines.

It combines stochastic gradient descent with regularization and projection to minimize the SVM objective function.

Working on this implementation helped me understand how optimization techniques are used in machine learning, particularly how the model weights are updated during training and how the algorithm learns to distinguish between two classes.

## Classification

The project focuses on binary classification:

- **Spam (1):** Messages identified as spam.
- **Ham (-1):** Messages identified as legitimate.

The text is first preprocessed and converted into numerical features. These features are then passed to the trained Pegasos SVM model, which predicts the class of each message.

## Technologies Used

- Java
- Maven
- Pegasos SVM
- Gmail API
- OAuth 2.0

## Security and OAuth Credentials

The Gmail integration uses OAuth 2.0 for authentication.

The Google OAuth client credentials, client secret and stored access tokens have been kept locally and excluded from this GitHub repository. I used `.gitignore` to prevent these files from being uploaded, as they contain sensitive authentication information.

As a result, the OAuth credential files are not included in this repository. Anyone wishing to run the Gmail integration will need to configure their own Google OAuth credentials.

## What I Learned

This project gave me an opportunity to move beyond studying the Pegasos algorithm theoretically and implement it in Java. It helped me understand the connection between mathematical optimization and machine learning, while also giving me experience with text processing, API integration and application development.
