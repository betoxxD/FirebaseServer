package com.betoxx.firebaseserver;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static void main(String[] args){
        FileInputStream serviceAccount = null;
        try {
            serviceAccount = new FileInputStream("C:\\Users\\beto_\\Documents\\unified-coyote-307420-firebase-adminsdk-kzcrb-e2b42f735a.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FirebaseOptions options = null;

        try {
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize the default app
        FirebaseApp defaultApp = FirebaseApp.initializeApp(options);

        System.out.println(defaultApp.getName());  // "[DEFAULT]"

// Retrieve services by passing the defaultApp variable...
        FirebaseAuth defaultAuth = FirebaseAuth.getInstance(defaultApp);

// ... or use the equivalent shorthand notation
        defaultAuth = FirebaseAuth.getInstance();

        // This registration token comes from the client FCM SDKs.
        String registrationToken = "ep1DDMDzTRyZiXdtLMsXIa:APA91bHFHfNpE6JZBkkeVKqoKsFDzM10z3Fqir4ftFfEDzxuAddsL9B5FC5kLvwB0w8K8pyKryGrij682X8bN2ytHxozeyTv8Lk6f7TBvyWNhLtm2GnU2ZTzn_Q2dSWzNSoihcVWbbdl";

// See documentation on defining a message payload.
        Message message = Message.builder()
                .putData("score", "850")
                .putData("time", "2:45")
                .setToken(registrationToken)
                .setNotification(Notification.builder()
                        .setTitle("Holi")
                        .setBody("Este mensaje se está enviando desde mi servidor en Java jsjsjsj.")
                        .build())
                .build();

// Send a message to the device corresponding to the provided
// registration token.
        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
// Response is a message ID string.
        System.out.println("Successfully sent message: " + response);
    }
}
