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
    public Map<Integer, View> pantry = new HashMap<Integer, View>();
    public int itemKey = 0;

    /**
     * James R.
     *
     * creates a new instance of the item class, creates a new hashmap, and stores
     * the class instances into the hashmap.
     */

    public void createNewItem(View myview){

        pantry.put(itemKey, myview);
        itemKey +=1;

    }

    public View getItemAt(int index){
        return pantry.get(index);
    }

    public void getHashMap(){
        int numkeys = pantry.size();

    }





}
