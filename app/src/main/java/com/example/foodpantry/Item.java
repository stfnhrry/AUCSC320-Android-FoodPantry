package com.example.foodpantry;

import java.util.Date;

public class Item {
    public String itemName;

    public Date expDate;

    public int amount;

    public int weight;

    public Item(String itemName){
        this.itemName = itemName;
//        this.amount = amount;
//        this.expDate = expdate;
//        this.weight = weight;
    }
    public Item(){
        this.itemName = itemName;
        this.amount = amount;
        this.expDate = expDate;
        this.weight = weight;
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

    public void setWeight(int weight){this.weight = weight; }

    public int getAmount() {
        return amount;
    }

    public String getItemName() {
        return itemName;
    }

    public Date getExpDate() {
       return expDate;
    }

    public int getWeight() {
        return weight;
    }
}
