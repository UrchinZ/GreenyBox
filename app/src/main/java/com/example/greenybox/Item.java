package com.example.greenybox;

import org.joda.time.LocalDate;

public class Item {
    //Data
    private String name;
    private int count;
    private LocalDate buyDate;
    private LocalDate expDate;

    //Constructor

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
