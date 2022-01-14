package com.example.foodpantry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddItem {
    public Item newItem;
    Map<Integer, List<String>> pantry = new HashMap<Integer, List<String>>();

    public int itemKey = 0;

    /**
     * James R.
     *
     * creates a new instance of the item class, creates a new hashmap, and stores
     * the instance variables into the hashmap.
     */
    public void createNewItem(){
        newItem = new Item("Beans", "02/03/2020", "20");
        List<String> newSet = new ArrayList<String>();
        newSet.add(newItem.getItemName());
        newSet.add(newItem.getExpDate());
        newSet.add(newItem.getAmount());
        pantry.put(itemKey, newSet);
        itemKey +=1;
        System.out.println(pantry.get(0) + "THIS IS THE TEST");
    }

}
