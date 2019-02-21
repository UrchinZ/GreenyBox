package com.example.greenybox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * Called when the user taps the Setting button
     * @author Judy
     * @param view
     */
    public void goToSetting(View view) {
        // Do something in response to button
        Intent intent = new Intent(this,Setting.class);
        startActivity(intent);
    }

    /**
     * Called when the user taps the Add button
     * @param view
     */
    public void goToAdd(View view){
        // Do something in response to button
    }
}
