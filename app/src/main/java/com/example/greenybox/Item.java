package com.example.greenybox;

import org.joda.time.LocalDate;

import java.util.Calendar;

//https://stackoverflow.com/questions/40124189/android-difference-between-two-dates
public class Item {
    //Data
    private LocalDate expDate;
    private LocalDate BuyDate;
    private int Reminder;
    private String name;
    private int count;
    //Constructor
    //Takes in 5 values for the data and assigns them.
    public Item(String namu, LocalDate EXP, LocalDate PerDate, int Rem, int coun){
        name=namu; expDate=EXP; BuyDate=PerDate; Reminder=Rem; count=coun;
    }
    //A constructor for if you have an amount of days until experation.
    public Item(String Nname, int EXPlus, LocalDate PerDate, int Rem, int coun){
        name=Nname; expDate=PerDate.plusDays(EXPlus); BuyDate=PerDate; Reminder=Rem; count=coun;
    }
    //Helper functions
    //Get reminder date
    public LocalDate remDate(){return expDate.minusDays(Reminder);}
    // Observers
    // Get number of days until reminder
    public int getReminder() {
        return Reminder;
    }
    //Get LocalDate of purchase
    /*public LocalDate getBuyDate() {
        return BuyDate;
    }
    //Get LocalDate of expiration
    public LocalDate getexpDate() {
        return expDate;
    }

    //Get name as string
    public String getName() {
        return name;
    }
    //Get amount of item
    public int getCount(){
        return count;
    }
    //Notify

    //Modifier
    //Works just like constructor, just replaces everything.
    public void rewite(LocalDate EXP, String N, LocalDate perDate, int Rem, int c) {
        name=N; expDate=EXP; BuyDate=perDate; Reminder=Rem; count=c;
    }
    */

}
