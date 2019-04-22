package com.example.greenybox;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.LocalDate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;

public class Modify extends AppCompatActivity {
    Item i;
    int position;
    private TextView pDate; //purchase date text-box
    private DatePickerDialog.OnDateSetListener pDateSetListener; //purchase date event listener
    private TextView eDate; //expiration date text-box
    private DatePickerDialog.OnDateSetListener eDateSetListener; //expiration date event listener

    private EditText pDays; //days to preserve
    private EditText itemCount; //count of item
    private EditText itemName; //Item Name


    //Image function variables
    private final String TAG = getClass().getSimpleName();
    private ImageView mPicture;
    private static final int CHOOSE_PHOTO = 385;

    private LocalDate today = new LocalDate();

    /**
     * page creation actions
     * @param savedInstanceState
     * @assignee Judy
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initiate layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        //get position information from intent
        Intent intent = getIntent();
        position = intent.getIntExtra("position",-1);
        System.out.println("inside modify, position: " + String.valueOf(position));
        i = MainActivity.getInstance().mData.get(position);

        //link xml element to fields
        itemName = findViewById(R.id.itemName);
        itemCount = findViewById(R.id.unit);
        pDate = findViewById(R.id.purchaseDate);
        eDate = findViewById(R.id.expirationDate);
        pDays = findViewById(R.id.preserveDay);
        itemUpdate(); //display fields

        //Item name on change response
        itemName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //set i's name field
                    i.setName(itemName.getText().toString());
                    Log.d(TAG, "NameChange: "+i.getName());
                    //UI clear focus
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    itemName.clearFocus();
                    handled = true;
                }
                return handled;
            }
        });

        //Item count on change response
        itemCount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId,
                                          KeyEvent keyEvent) { //triggered when done editing (as clicked done on keyboard)
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //user input count
                    String countText = itemCount.getText().toString();
                    int count = Integer.parseInt(countText);
                    //set item count field
                    i.setCount(count);
                    //clear UI focus
                    itemCount.clearFocus();
                }
                return false;
            }
        });
        //change purchase date
        pDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                System.out.println("inside modify purchase date: ");
                System.out.println("today :"+ today.toString());
                month = month+1;
                LocalDate pDateInstance = new LocalDate(year,month,dayOfMonth);

                //logic to prevent user from selecting future date
                if(pDateInstance.isAfter(today)){
                    System.out.println("new pdate: " + pDateInstance.toString());
                    System.out.println("today :"+ today.toString());
                    Toast.makeText(getApplicationContext(),"Please, no purchase in the future" , Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    System.out.println("this is correct, proceed");
                }

                //display date
                String date = "Purchase Date: " + pDateInstance.toString();
                System.out.println(date);
                pDate.setText(date);

                //change item field
                i.setBuyDate(pDateInstance);

                //resolve potential conflict
                if(pDays.getText().length()>0){
                    i.resolve(Integer.parseInt(pDays.getText().toString()));
                    itemUpdate();
                }

            }
        };
        //Purchase date response
        pDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //generate today's date
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                //set date picker dialog to start with today's date
                DatePickerDialog dialog = new DatePickerDialog(
                        Modify.this,
                        android.R.style.Theme_Holo_Light,
                        pDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });




        //expiration date response
        eDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //start with today's date
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                //show date dialog picker
                DatePickerDialog dialog = new DatePickerDialog(
                        Modify.this,
                        android.R.style.Theme_Holo_Light,
                        eDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        //modify expiration date
        eDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                LocalDate eDateInstance = new LocalDate(year,month,dayOfMonth);

                //logic to prevent user from selecting a date after today's and purchase date
                if (today.isAfter(eDateInstance)){

                    Toast.makeText(getApplicationContext()," Please just throw out the food?" , Toast.LENGTH_SHORT).show();
                    return;
                }

                //display date
                String date = "Expiration Date: " + eDateInstance.toString();
                eDate.setText(date);

                //change expDate field in item
                i.setExpDate(eDateInstance);

                //resolve potential conflict
                if (i.getBuyDate() != null){
                    i.resolve(i.preserve());
                    itemUpdate();
                }

                if(pDays.getText().length()>0){
                    i.resolve(Integer.parseInt(pDays.getText().toString()));
                    itemUpdate();
                }
            }
        };


        //preserve input response
        pDays.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId,
                                          KeyEvent keyEvent) { //triggered when done editing (as clicked done on keyboard)
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //user input pdays
                    String pDaysText = pDays.getText().toString();
                    int pdays = Integer.parseInt(pDaysText);
                    //resolve potential conflict
                    i.resolve(pdays);
                    itemUpdate();
                    //UI clear focus
                    pDays.clearFocus();
                }
                return false;
            }
        });
    }

    /**
     * Save actions
     * same as main
     * @param view
     */
    public void onSaveButtonClicked(View view){
        MainActivity.getInstance().save();
        done(view);
    }

    /**
     * Sequence of actions associated to "Done" button
     * @param view
     */
    public void done(View view){
        Intent intent = new Intent(this,MainActivity.class);
        finish();
        startActivity(intent);
    }

    /**
     * Delete actions, same as main
     * @param view
     */
    public void onDeleteButtonClicked(View view){
        MainActivity.getInstance().mData.remove(position);
        MainActivity.getInstance().save();
        done(view);
    }

    /**
     * Update all text fields based on item information
     * @assignee: Judy
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
