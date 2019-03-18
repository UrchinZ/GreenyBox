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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
     * @author Judy
     * @param view
     */
    public void goToSetting(View view) {
        // Do something in response to button
        Intent intent = new Intent(this,Setting.class);
        startActivity(intent);
    }

    /**
     * @param Items Items we want to serialize
     * @param FileName Name of file we want to save to
     * @throws IOException
     */
    public void WriteEverythingToSingleFile(ArrayList<Item> Items,String FileName) throws IOException {
        FileOutputStream rawFile=new FileOutputStream(FileName);
        ObjectOutputStream obFile= new ObjectOutputStream(rawFile);
        obFile.writeObject(Items);
        obFile.close();
        rawFile.close();
    }
    /**
     * @return all serialized Items from the internal directory
     */
    public ArrayList<Item> loadEverythingFromManyFiles(){
        String[] files=fileList();
        ArrayList<Item> ret= new ArrayList<Item>();
        for(int i=0; i<files.length;i++) {
            try {
                FileInputStream rawFile = new FileInputStream(files[i]);
                ObjectInputStream obFile = new ObjectInputStream(rawFile);
                Item readItem=(Item)obFile.readObject();
                obFile.close();
                rawFile.close();
                ret.add(readItem);
            } catch (Exception ex) {continue;}
        }
        return ret;
    }

    /**
     * @param Filename string which we store the item list
     * @return all serialized items from a file of an ArrayList of Items
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ArrayList<Item> loadEverythingFromSingleFile(String Filename) throws IOException, ClassNotFoundException {
        FileInputStream rawFile = new FileInputStream(Filename);
        ObjectInputStream obFile = new ObjectInputStream(rawFile);
        ArrayList<Item> ret=(ArrayList<Item>)obFile.readObject();
        obFile.close();
        rawFile.close();
        return ret;
    }
    /**
     * Called when the user taps the Add button
     * @param view
     */
    public void goToAdd(View view){
        // Do something in response to button
        Intent intent = new Intent(this,AddItem.class);
        startActivity(intent);
    }
}
