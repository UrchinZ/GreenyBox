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


    //Notify

    //Modifier
    //Works just like constructor, just replaces everything.
    public void rewite(LocalDate EXP, String N, LocalDate perDate, int Rem, int c) {
        name=N; expDate=EXP; BuyDate=perDate; Reminder=Rem; count=c;
    }
    */

}
