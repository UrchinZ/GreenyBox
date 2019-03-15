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
    
}
//Test