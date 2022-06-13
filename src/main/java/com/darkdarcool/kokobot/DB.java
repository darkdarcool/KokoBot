package com.darkdarcool.kokobot;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class DB {
    private Firestore db;
    public DB() {
        try {
            InputStream serviceAccount = DB.class.getResourceAsStream("/serviceaccount.json");
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);

            db = FirestoreClient.getFirestore();
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
    public void addData(String col, String child, HashMap data) {
        DocumentReference doc = db.collection(col).document(child);
        doc.create(data);
    }
    public boolean doesExistChild(String col, String child) throws Exception {
        DocumentReference docRef = db.collection(col).document(child);
        ApiFuture<DocumentSnapshot> future = docRef.get();
// ...
        DocumentSnapshot document = future.get();
        return document.exists();
    }
    public Map getData(String col, String child) throws Exception {
        DocumentReference docRef = db.collection(col).document(child);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        return document.getData();
    }
    public void updateData(String col, String child, HashMap data) throws Exception {
        DocumentReference docRef = db.collection(col).document(child);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            docRef.update(data);
        }
    }
}
