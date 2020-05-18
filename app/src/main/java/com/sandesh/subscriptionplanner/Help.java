package com.sandesh.subscriptionplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        getSupportActionBar().setTitle("Help");

        TextView helpDescription = findViewById(R.id.textView_helpDescription);
        helpDescription.setText("This app helps to keep track of your subscription. " +
                "You can open the app and see when your next subscription is due.\n" +
                "\n Features of the app\n" +
                "1. App details of the subscription\n" +
                "2. Delete already existing subscription\n\n\n" +
                "Press back button to return to main page.\n\n\n" +
                "Developed By: Sandesh Timilsina");



    }
}
