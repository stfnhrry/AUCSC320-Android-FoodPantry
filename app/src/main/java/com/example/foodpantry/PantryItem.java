package com.example.foodpantry;

import java.util.Date;

public class PantryItem {

  String itemName;
  Date expiryDate;
  int foodCategory;
  int amount;

  public String getFoodName(String name){
    return itemName;
  }

  public void Item(String itemName, Date expDate, int amount){
    this.itemName = itemName;
    this.expiryDate = expDate;
    this.amount = amount;
  }
  public void Item(){
    this.itemName = itemName;
    this.amount = amount;
    this.expiryDate = expiryDate;
  }

  public void setItem(String itemName){
    this.itemName = itemName;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public void setExpDate(Date expDate) {
    this.expiryDate = expDate;
  }

  public int getAmount() {
    return amount;
  }

  public String getItemName() {
    return itemName;
  }

  public Date getExpDate() {
    return expiryDate;
  }
}
