package com.example.greenybox;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private GridView grid_photo;
    private BaseAdapter mAdapter = null;
    private ArrayList<Item> mData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;
        grid_photo = (GridView) findViewById(R.id.grid_photo);

        mData = new ArrayList<Item>();
        mData.add(new Item("Item1", "img1"));
        mData.add(new Item("Item1", "img2"));
        mData.add(new Item("Item1", "img3"));
        mData.add(new Item("Item1", "img4"));
        mData.add(new Item("Item1", "img5"));
        mData.add(new Item("Item1", "img6"));
        mData.add(new Item("Item1", "img7"));

        mAdapter = new MyAdapter<Item>(mData, R.layout.item_grid) {
            @Override
            public void bindView(ViewHolder holder, Item obj) {

                holder.setText(R.id.txt_icon, obj.getName());
            }
        };

        grid_photo.setAdapter(mAdapter);

        grid_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Set img maybe jump some where
                // setImageResource(R.id.img_icon, obj.getiId());
                Toast.makeText(mContext, "Clicked" + position + ":", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Called when the user taps the Setting button
     *
     * @param view
     * @author Judy
     */
    public void goToSetting(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, Setting.class);
        startActivity(intent);
    }

    /**
     * Called when the user taps the Add button
     *
     * @param view
     */
    public void goToAdd(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, AddItem.class);
        startActivity(intent);
    }

    /**
     * Called when the user taps the Modify button
     *
     * @param view
     * @author SoJung
     */
    public void goToModify(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, ModifyItem.class);
        startActivity(intent);
    }
}
