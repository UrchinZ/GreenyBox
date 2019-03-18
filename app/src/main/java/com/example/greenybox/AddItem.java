package com.example.greenybox;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    private EditText itemCount; //count of item
    private EditText itemName; //Item Name

    private final String TAG = getClass().getSimpleName();
    private ImageView mPicture;
    private static final int CHOOSE_PHOTO = 385;

    private final Item i = new Item();
    private LocalDate today = new LocalDate();
    //test
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //show layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        //link xml element to fields
        itemName = findViewById(R.id.itemName);
        itemCount = findViewById(R.id.unit);
        pDate = findViewById(R.id.purchaseDate);
        eDate = findViewById(R.id.expirationDate);
        pDays = findViewById(R.id.preserveDay);

        //Item name on change response
        itemName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //set i's name field
                    i.setName(itemName.getText().toString());
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
                LocalDate pDateInstance = new LocalDate(year,month,dayOfMonth);

                //logic to prevent user from selecting future date
                if(pDateInstance.isAfter(today)){
                    pDate.setHint("Purchased in the future?");
                    return;
                }

                //display date
                String date = "Purchase Date: " + pDateInstance.toString();
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
                LocalDate eDateInstance = new LocalDate(year,month,dayOfMonth);

                //logic to prevent user from selecting a date after today's and purchase date
                if (today.isAfter(eDateInstance)){
                    eDate.setHint("Please throw item out");
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

        // Click to select image from album
        mPicture = findViewById(R.id.ivPicture);
        ImageView mChooseFromAlbum = findViewById(R.id.ivPicture);


        mChooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlbum();
            }
        });

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

    /**
     * Create Intent to open imageView.
     */
    private void openAlbum() {
        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
        openAlbumIntent.setType("image/*");
        startActivityForResult(openAlbumIntent, CHOOSE_PHOTO);
    }

    /**
     * Get Permission Request Results.
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "onRequestPermissionsResult: permission granted");
        } else {
            Log.i(TAG, "onRequestPermissionsResult: permission denied");
            Toast.makeText(this, "You Denied Permission", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Get Image Activity data.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (data == null) {
                    return;
                }
                Log.i(TAG, "onActivityResult: ImageUriFromAlbum: " + data.getData());
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * Get data image path. (Standard)
     * @param data
     */
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    /**
     * Get data image path. (version compatible)
     * @param data
     */
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    /**
     * Display Image.
     * @param imagePath
     */
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            mPicture.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     *
     * @param uri
     * @param selection
     * @return
     */
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}
