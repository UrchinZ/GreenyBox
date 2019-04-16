package com.example.greenybox;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Item implements Serializable {

    //Data
    private String name;
    private int count;
    private LocalDate buyDate;
    private LocalDate expDate;
    private String imgPath;


    /**
     * empty constructor
     */
    public Item(){
        name = "";
        count = 0;
        buyDate = null;
        expDate = null;
    }

    /**
     * Constructor takes in all field
     * @param n string of name
     * @param c int count
     * @param buy localdate buy date
     * @param exp localdate expiration date
     */
    public Item(String n, int c, LocalDate buy, LocalDate exp){
        name = n;
        count = c;
        buyDate = buy;
        expDate=exp;
    }

    //Helper functions: observers
    /**
     * access item name
     * @return string item name
     */
    public String getName() {
        return name;
    }

    /**
     * access item count
     * @return integer item count
     */
    public int getCount(){
        return count;
    }

    /**
     * access localdate buy date
     * @return localdate buy date
     */
    public LocalDate getBuyDate() {
        return buyDate;
    }


    /**
     * access localdate expiration date
     * @return localdate expiration date
     */
    public LocalDate getExpDate() {
        return expDate;
    }

    /**
     * Definition of freshness:
     * diff < 0: discard (0)
     * diff == 0 or diff == 1 (or notification day): urgent (1)
     * diff > 1: fresh (2)
     * @return freshness
     */
    public int Freshness(){
        LocalDate today = new LocalDate();
        int diff = Days.daysBetween(today,expDate).getDays();
        int freshness = 2;
        if (diff < 0) {
            freshness = -1;
        } else if (diff == 0 || diff == 1){
            freshness = 1;
        }
        return freshness;
    }


    //Modifiers
    /**
     * set name
     * @param n string of name
     */
    public void setName(String n)
    {
        name = n;
    }

    /**
     * set count
     * @param c int count
     */
    public void setCount(int c){
        count = c;
    }

    /**
     * One of the modifier for buydate
     * @param buy local date
     */
    public void setBuyDate(LocalDate buy){
        buyDate = buy;
    }

    /**
     * One of the modifier for buydate
     * @param year int
     * @param month int
     * @param day int
     */
    public void setBuyDate(int year, int month, int day) {
        buyDate=new LocalDate(year,month,day);
    }

    /**
     * one of the modifers for expiration date
     * @param exp expiration local date
     */
    public void setExpDate(LocalDate exp) {
        expDate = exp;
    }

    /**
     * one of the modifiers for expiration date
     * @param year int
     * @param month int
     * @param day int
     */
    public void setExpDate(int year, int month, int day){
        expDate= new LocalDate(year, month, day);
    }

    /**
     * bulk rewrite fields
     * @param n string name
     * @param c int count
     * @param buy local date buy date
     * @param exp local date expiration date
     */
    public void rewite(String n, int c, LocalDate buy, LocalDate exp) {
        setName(n);
        setCount(c);
        setBuyDate(buy);
        setExpDate(exp);
    }

    /**
     * builk rewrite fields
     * @param n string name
     * @param c string count
     * @param buyYear int buy year
     * @param buyMonth int buy month
     * @param buyDay int buy day
     * @param expYear int expiration year
     * @param expMonth int expiration month
     * @param expDay int expiration day
     */
    public void rewrite(String n, int c, int buyYear, int buyMonth, int buyDay, int expYear, int expMonth, int expDay){
        setName(n);
        setCount(c);
        setBuyDate(buyYear,buyMonth,buyDay);
        setExpDate(expYear,expMonth,expDay);
    }

    /**
     * calculates difference between buyDate and expDate
     * @return int preserve days
     *  @assignee: Judy
     */
    public int preserve(){
        if(buyDate == null || expDate == null){
            return -1;
        }
        return Days.daysBetween(buyDate,expDate).getDays();
    }

    /**
     * checks if there is conflict between user input days to preserv
     * and current preserve days
     * @param preserveDays days to preserve set based on user input
     * @return boolean if there's conflict
     * @assignee: Judy
     */
    public boolean conflict(int preserveDays){
        return preserveDays != preserve();
    }

    /**
     * resolve conflict between preserve days and
     * buyDate and expDate
     * @param preserveDays days to preserve set based on user input
     * @assignee: Judy
     */
    public void resolve(int preserveDays){
        //return if no conflict
        if(!conflict(preserveDays)){
            return;
        }
        //resolve conflict logic
        if (buyDate != null){
            expDate = buyDate.plusDays(preserveDays);
        } else {
            if (expDate != null) {
                buyDate = expDate.plusDays(-preserveDays);
            } else {
                buyDate = new LocalDate();
                expDate = buyDate.plusDays(preserveDays);
            }
        }
    }
    //Serialization

    /**
     * Writes a single item to a file
     * @param writeable Item to write.
     */
    public static void WriteItemToFile(Item writeable, String FileName) throws IOException {
        FileOutputStream rawFile=new FileOutputStream(FileName);
        ObjectOutputStream obFile= new ObjectOutputStream(rawFile);
        obFile.writeObject(writeable);
        obFile.close();
        rawFile.close();
    }
    /**
     * @param Items Items we want to serialize
     * @param FileName Name of file we want to save to
     * @throws IOException
     */
    public static void WriteEverythingToSingleFile(ArrayList<Item> Items, String FileName) throws IOException {
        FileOutputStream rawFile=new FileOutputStream(FileName);
        ObjectOutputStream obFile= new ObjectOutputStream(rawFile);
        obFile.writeObject(Items);
        obFile.close();
        rawFile.close();
    }
    /**
     * @param files CALL fileList(); FOR THIS ARGUMENT
     * @return all serialized Items from the internal directory
     */
    public static ArrayList<Item> loadEverythingFromManyFiles(String[] files){
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
    public static ArrayList<Item> loadEverythingFromSingleFile(String Filename) throws IOException, ClassNotFoundException {
        FileInputStream rawFile = new FileInputStream(Filename);
        ObjectInputStream obFile = new ObjectInputStream(rawFile);
        ArrayList<Item> ret=(ArrayList<Item>)obFile.readObject();
        obFile.close();
        rawFile.close();
        return ret;
    }



}