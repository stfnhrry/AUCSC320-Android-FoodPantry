package com.example.foodpantry;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddItem {

  public PantryItem newItem;
  public Date date;
  Map<Integer, PantryItem> pantry = new HashMap<Integer, PantryItem>();

  public int itemKey = 0;

  /**
   * James R.
   *
   * creates a new instance of the item class, creates a new hashmap, and stores
   * the class instances into the hashmap.
   */

  public void createNewItem(){
    PantryItem newItem = new PantryItem();

    //Take textview inputs and stores that info into newItem
    //Set name
    //Set date
    //set amount

    pantry.put(itemKey, newItem);
    itemKey +=1;


  }


}
