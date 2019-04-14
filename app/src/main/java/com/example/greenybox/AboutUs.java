package com.example.greenybox;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.greenybox.R.id.copyBtn;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        /**
         * Since the texts will get long, I brought string value into the XML
         * No need to set separate Android:text= in XML
         * @author SoJung
         */
        String intro = "The Team Eleven-Seven met in RPI's Software Development & Documentations class in Spring 2019." +
                " Judy pitched an idea thinking of her grandmother not keeping track of grocery items in the fridge and ending up throwing everything away most of the time." +
                " The Team hopes this app would be helpful in tracking grocery items, quickly grasping what is in the fridge, deciding what to make for dinner, and eventually reducing food waste. \n \n" +
                "GreenyBox is a Free Open Source Software. The source code can be reached by clicking the below button and copying the link to the GitHub. ";
        TextView introTxt = (TextView) findViewById(R.id.introUs);
        introTxt.setText(intro);

        /**
         * Button to copy the link to our GitHub
         * @author SoJung
         */
        final String link = "https://github.com/UrchinZ/GreenyBox";
        Button copybtn = findViewById(R.id.copyBtn);
        copybtn.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Link", link);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getApplicationContext(), "Successfully copied!", Toast.LENGTH_SHORT).show();
        });
    }
}

