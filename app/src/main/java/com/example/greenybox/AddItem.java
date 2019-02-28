package com.example.greenybox;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class AddItem extends AppCompatActivity {

    private TextView pDate; //purchase date text-box
    private DatePickerDialog.OnDateSetListener pDateSetListener; //purchase date event listener


    private TextView eDate; //expiration date text-box
    private  DatePickerDialog.OnDateSetListener eDateSetListener; //expiration date event listener

    private EditText pDays; //days to preserve
    private EditText itemName;//Item Name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //show layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        pDays = findViewById(R.id.preserveDay);
        pDate = findViewById(R.id.purchaseDate);
        eDate = findViewById(R.id.expirationDate);
        itemName = findViewById(R.id.itemName);

        /* Item Name response */
        itemName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    //Perform your Actions here.
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    handled = true;
                }
                return handled;
            }
        });

        /*preserve days response*/
        pDays.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                System.out.println("beforeTextChanged s: " + s + " start: "+ start + " count" + count + " after: "+after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("onTextChanged s: " + s + " start: "+ start + " before: "+before+" count" + count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("afterTextChanged: "+s);

                //Do not respond to temporary changes
                if (s.length() == 0){
                    return;
                }

                int days = Integer.parseInt(s.toString());
                System.out.println("converting to int: "+days);

                String purchaseText = (String)pDate.getText();
                String purchase = purchaseText.substring(purchaseText.length()-10);
                SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy");
                try {
                    Date purchaseDate = myFormat.parse(purchase);
                    Calendar c = Calendar.getInstance();
                    c.setTime(purchaseDate);
                    c.add(Calendar.DATE, days);
                    Date expirationDate = c.getTime();
                    String dateFormatted = myFormat.format(expirationDate);
                    System.out.println(dateFormatted);
                    eDate.setText("Purchase Date: "+dateFormatted);

                }catch (ParseException e){
                    e.printStackTrace();
                }
            }
        });

        //============Purchase Date modifier============
        pDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddItem.this,
                        android.R.style.Theme_Holo_Light,
                        pDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        //change purchase date
        pDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = month + "/" + dayOfMonth + "/" + year;
                if(month<10){
                    date = "0" + date;
                }
                date = "Purchase Date: "+ date;
                pDate.setText(date);
            }
        };

        //=============== expiration date dialog picker ===========
        eDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddItem.this,
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
                String date = month + "/" + dayOfMonth + "/" + year;
                if(month<10){
                    date = "0" + date;
                }
                date = "Expiration Date: " + date;
                eDate.setText(date);
                int days = calDayDifference();
                String dayStr = Integer.toString(days);
                pDays.setText(dayStr);
            }
        };

    }

    /**
     * Calculates the difference between 2 days
     * @param
     * @param
     * @return difference between first date and second date
     */
    private static long daysBetween(Date one, Date two) {
        long difference =  (one.getTime()-two.getTime())/86400000;
        return difference;
    }

    /**
     * @function: Calculate the difference between purchase date and expiration date
     * @return Difference in days between purchase date and expiration date
     * @bug: March 10th 2019 is a weird date
     */
    private int calDayDifference(){
        String expirationText = (String)eDate.getText();
        String expiration = expirationText.substring(expirationText.length()-10);
        String purchaseText = (String)pDate.getText();
        String purchase = purchaseText.substring(purchaseText.length()-10);
        SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy");
        System.out.println("I am trying: " + expiration + " " + purchase);
        try {
            Date date1 = myFormat.parse(expiration);
            Date date2 = myFormat.parse(purchase);
            int days = (int)daysBetween(date1,date2);
            System.out.println ("Difference in Days: " + days);
            return days;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
