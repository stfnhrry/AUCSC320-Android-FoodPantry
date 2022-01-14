package com.example.foodpantry;

import java.util.Date;

public class Item {
    public String itemName;

    public Date expDate;

    public int amount;

    public Item(String itemName, Date expDate, int amount){
        this.itemName = itemName;
        this.expDate = expDate;
        this.amount = amount;
    }
    public Item(){
        this.itemName = itemName;
        this.amount = amount;
        this.expDate = expDate;
    }
    public void setItem(String itemName){
        this.itemName = itemName;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public int getAmount() {
        return amount;
    }

    public String getItemName() {
        return itemName;
    }

    public Date getExpDate() {
        return expDate;
    }
}
