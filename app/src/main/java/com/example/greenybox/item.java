package com.example.greenybox;

import org.joda.time.LocalDate;

public class item {
    //Data
    private LocalDate expDate;
    private LocalDate buyDate;
    private int reminder;
    private String name;
    private int count;
    //Constructor
    //Takes in 5 values for the data and assigns them.
    public item(String namu, LocalDate EXP, LocalDate PerDate, int rem, int coun){
        name=namu; expDate=EXP; buyDate =PerDate; reminder =rem; count=coun;
    }
    //A constructor for if you have an amount of days until experation.
    public item(String newName, int EXPlus, LocalDate perDate, int rem, int coun){
        name=newName; expDate=perDate.plusDays(EXPlus); buyDate =perDate; reminder =rem; count=coun;
    }
    //Helper functions
    //Returns: reminder date
    public LocalDate remDate(){return expDate.minusDays(reminder);}
    // Observers
    // Returns: number of days from reminder to experation
    public int getReminder() {
        return reminder;
    }
    //Returns: Localdate Purchase date
    public LocalDate getBuyDate() {
        return buyDate;
    }
    //Returns: Localdate of expiration
    public LocalDate getexpDate() {
        return expDate;
    }
    //Returns: name as string
    public String getName() {
        return name;
    }
    //Returns: amount of item
    public int getCount(){
        return count;
    }
    //Notify

    //Modifier
    //Works just like constructor, just replaces everything.
    public void rewite(LocalDate exp, String N, LocalDate perDate, int rem, int c) {
        name=N; expDate=exp; buyDate =perDate; reminder =rem; count=c;
    }
}
