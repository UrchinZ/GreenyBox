package com.example.greenybox;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private GridView gridPhoto;
    public ArrayList<Item> mData;
    static MainActivity mActivity;
    private static final String TAG = "MainActivity";
    public int modify = -1;
    //private static boolean sortNameCounter, sortBuyDateCounter, sortExpDateCounter, sortFreshnessCounter = true;

    /**
     * Activities that start when page is initiated
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        mContext = MainActivity.this;
        gridPhoto = (GridView) findViewById(R.id.grid_photo);

        mData = new ArrayList<Item>();
        restore();
        render();

        //add new click listener to grid photo
        gridPhoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Set img maybe jump some where
                // setImageResource(R.id.img_icon, obj.getiId());
                // THIS IS WHERE MODIFY SHOULD HAPPEND
                Toast.makeText(mContext, "Modify item in position " + position, Toast.LENGTH_SHORT).show();
                modify = position;
                System.out.println("inside main: set position to " + String.valueOf(modify));
                goToModify(view);
            }
        });
    }

    /**
     * Dashboard instance.
     * @return instance of this
     */
    public static MainActivity getInstance(){
        return mActivity;
    }

    /**
     * Called when the user taps the Setting button
     * @param view
     * @assignee Judy
     */
    public void goToSetting(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, Setting.class);
        startActivity(intent);
    }

    /**
     * Called when the user taps the Add button
     * @param view
     * @assignee Judy
     */
    public void goToAdd(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, AddItem.class);
        startActivity(intent);
    }

    /**
     * Called when the user taps the Modify button
     * @param view
     * @assignee SoJung
     */
    public void goToModify(View view) {
        if (modify == -1){
            System.out.println("item not selected");
        }
        System.out.println("item position selected in main: " + String.valueOf(modify));
        Intent intent = new Intent(this, Modify.class);
        intent.putExtra("position",modify);
        startActivity(intent);
    }

    /**
     * When on pause,save
     * @assignee HZ
     */
    @Override
    protected void onPause() {
        super.onPause();
        save();
    }

    /**
     * Save items into storage
     * Assignee Hz
     */
    public void save(){
        try {
            File file = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
            Log.d(TAG, "save: "+file.getAbsolutePath());
            FileOutputStream fileOutputStream = new FileOutputStream(file.getAbsolutePath()+"/items");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(mData);

            objectOutputStream.close();
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * restore items from storage
     * @assignee HZ
     */
    public void restore(){
        try {
            File file = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
            FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath()+"/items");

            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            mData = (ArrayList<Item>) objectInputStream.readObject();
            fileInputStream.close();
            objectInputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * default sort based on name
     * @assignee Judy
     */
    public void sort(View view) {
        Collections.sort(mData, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                //if (sortNameCounter) {
                    return o1.getName().compareTo(o2.getName());
                //} else {
                //    return o2.getName().compareTo(o1.getName());
                //}
            }
        });
        //sortNameCounter = sortNameCounter ? false : true;
        //sortBuyDateCounter = sortExpDateCounter = sortFreshnessCounter = true;
        render();
    }
    //=====================Assignee: CJ ==================
    /**
     *Sort items based on purchase date
     */
    public void sortBuyDate(View view) {
        Collections.sort(mData, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                //if (sortBuyDateCounter) {
                    return o1.getBuyDate().isAfter(o2.getBuyDate()) ? 1 : -1;
                //} else {
                //    return o2.getBuyDate().isAfter(o1.getBuyDate()) ? 1 : -1;
                //}
            }
        });
        //sortBuyDateCounter = sortBuyDateCounter ? false : true;
        //sortNameCounter = sortExpDateCounter = sortFreshnessCounter = true;
        render();
    }

    /**
     *
     *
     */
    public void sortExpDate(View view) {
        Collections.sort(mData, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                //if (sortExpDateCounter) {
                    return o1.getExpDate().isAfter(o2.getExpDate()) ? 1 : -1;
                //} else {
                //    return o2.getExpDate().isAfter(o1.getExpDate()) ? 1 : -1;
                //}
            }
        });
        //sortExpDateCounter = sortExpDateCounter ? false : true;
        //sortNameCounter = sortBuyDateCounter = sortFreshnessCounter = true;
        render();
    }

    /**
     * sort items based on freshness
     *
     */
    public void sortFreshness(View view) {
        Collections.sort(mData, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                //if (sortFreshnessCounter) {
                    return (o1.Freshness() >= o2.Freshness()) ? 1 : -1;
                //} else {
                //    return (o2.Freshness() >= o1.Freshness()) ? 1 : -1;
                //}
            }
        });
        //sortFreshnessCounter = sortFreshnessCounter ? false : true;
        //sortNameCounter = sortBuyDateCounter = sortExpDateCounter = true;
        render();
    }

    /**
     * render grid on dashboard
     * @assignee: Judy
     */
    public void render(){
        final BaseAdapter mAdapter = new MyAdapter<Item>(mData, R.layout.item_grid)
        {
            @Override
            public void bindView(ViewHolder holder, Item obj) {
            }
        };

        gridPhoto.setAdapter(mAdapter);
    }
}
