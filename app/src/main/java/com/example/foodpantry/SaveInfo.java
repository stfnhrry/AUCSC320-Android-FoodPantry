package com.example.foodpantry;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveInfo {
    public Map<Integer, Item> pantry = new HashMap<Integer, Item>();
    public int itemKey = 0;

    /**
     * James R.
     *
     * creates a new instance of the item class, creates a new hashmap, and stores
     * the class instances into the hashmap.
     */

    public void createNewItem(Item item){

        pantry.put(0, item);
        itemKey +=1;


    }

    public void getHashMap(){
        int numkeys = pantry.size();
        for (int i = 0; i < numkeys; i++){
            System.out.println(pantry.get(i).getItemName());
            System.out.println(pantry.get(i).getExpDate());
            System.out.println(pantry.get(i).getWeight());
            System.out.println(pantry.get(i).getAmount());

        }

    }





}
