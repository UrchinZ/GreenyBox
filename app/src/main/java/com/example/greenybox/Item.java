package com.example.greenybox;

import java.util.Calendar;

//https://stackoverflow.com/questions/40124189/android-difference-between-two-dates
public class Item {
    String name;
    Calendar buyDate;
    Calendar expDate;
    int storeDay;

    //default constructor
    public Item(){
        name = "";
        storeDay = -1;
    }

    //constructor from storage string
    public Item(String s){

    }

    //modifier
    public void setName(String n){
        name = n;
    }

    public void setBuyDate(int year, int month, int day){
        /*somehow magically parse year, month, day into a calendar object*/

    }

    public void setExpDate(int year, int month, int day){
        /*somehow magically parse year, month, day into a calendar object*/

    }

    public void setStoreDay(int day){
        storeDay = day;
    }

    //accessor
    public String getName(){
        return name;
    }

    public  Calendar getBuyDate(){
        return buyDate;
    }

    public Calendar getExpDate(){
        return expDate;
    }

    public int getStoreDay(){
        return storeDay;
    }

    /* public helper functions*/
    /**
     *Store information to internal storage
     **/
    public void storeToInternal(){

    }

    /* private helper function*/
    private int dayDifference(){
        /*calculate difference between 2 dates*/
        return 0;
    }

    private void makeConsistent(){
        /*resolve conflict between all variables*/
    }


}
