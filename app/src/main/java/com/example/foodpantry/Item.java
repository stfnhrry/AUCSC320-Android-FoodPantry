package com.example.foodpantry;

public class Item {
    public String itemName;

    public String expDate;

    public String amount;

    public Item(String itemName, String expDate, String amount){
        this.itemName = itemName;
        this.expDate = expDate;
        this.amount = amount;
    }
    public void setItem(String itemName){
        this.itemName = itemName;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getAmount() {
        return amount;
    }

    public String getItemName() {
        return itemName;
    }

    public String getExpDate() {
        return expDate;
    }
}
