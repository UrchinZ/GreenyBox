package com.example.greenybox;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.joda.time.LocalDate;

public class Modify extends AppCompatActivity {
    Item i;
    private TextView pDate; //purchase date text-box
    private DatePickerDialog.OnDateSetListener pDateSetListener; //purchase date event listener
    private TextView eDate; //expiration date text-box
    private DatePickerDialog.OnDateSetListener eDateSetListener; //expiration date event listener

    private EditText pDays; //days to preserve
    private EditText itemCount; //count of item
    private EditText itemName; //Item Name


    private final String TAG = getClass().getSimpleName();
    private ImageView mPicture;
    private static final int CHOOSE_PHOTO = 385;

    private LocalDate today = new LocalDate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        Intent intent = getIntent();
        int position = intent.getIntExtra("position",-1);
        System.out.println("inside modify, position: " + String.valueOf(position));
        i = MainActivity.getInstance().mData.get(position);

        //link xml element to fields
        itemName = findViewById(R.id.itemName);
        itemCount = findViewById(R.id.unit);
        pDate = findViewById(R.id.purchaseDate);
        eDate = findViewById(R.id.expirationDate);
        pDays = findViewById(R.id.preserveDay);

        itemUpdate();
    }

    public void onSaveButtonClicked(View view){

    }

    public void onDeleteButtonClicked(View view){

    }

    /**
     * Update all text fields based on item information
     */
    private void itemUpdate(){
        if (i.getCount() > 0){
            itemCount.setText(String.valueOf(i.getCount()));
        }
        if(i.getName().length() > 0){
            itemName.setText(i.getName());
        }
        if(i.getBuyDate() != null ){
            pDate.setText("Purchase Date: " + i.getBuyDate().toString());
        }
        if(i.getExpDate() != null){
            eDate.setText("Expiration Date: " + i.getExpDate().toString());
        }
        if(i.getBuyDate() != null && i.getExpDate() != null){
            pDays.setText(String.valueOf(i.preserve()));
        }
    }

}
