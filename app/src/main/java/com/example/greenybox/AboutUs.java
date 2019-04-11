package com.example.greenybox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

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
        String intro = "The Team Eleven-Seven met in RPI's Software Development & Documentations class. ...";
        TextView introTxt = (TextView) findViewById(R.id.introUs);
        introTxt.setText(intro);

        String contact = "The Team can be reached via emails: ...";
        TextView contactInfo = (TextView) findViewById(R.id.contactInfo);
        contactInfo.setText(contact);

    }


}

