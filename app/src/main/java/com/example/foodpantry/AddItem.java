package com.example.foodpantry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddItem {
    public Item newItem;
    public Date date;
    Map<Integer, Item> pantry = new HashMap<Integer, Item>();

    public int itemKey = 0;

    /**
     * James R.
     *
     * creates a new instance of the item class, creates a new hashmap, and stores
     * the class instances into the hashmap.
     */

    public void createNewItem(){
        Item newItem = new Item();

        //Take textview inputs and stores that info into newItem
        //Set name
        //Set date
        //set amount

        pantry.put(itemKey, newItem);
        itemKey +=1;


    }



}
