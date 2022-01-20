package com.example.foodpantry;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveFile {
    public Map<Integer, String[]> pantry = new HashMap<Integer, String[]>();
    public int itemKey = 0;

    /**
     * James R.
     *
     * creates a new instance of the item class, creates a new hashmap, and stores
     * the class instances into the hashmap.
     */

    public void createNewEntry(int index, ItemFragment myItem){
        //pantry.put(index, myItem);
        itemKey +=1;
    }

    public void removeEntry(int index, View myview){
        pantry.remove(index);
    }

    public String[] getItemAt(int index){
        return pantry.get(index);
    }

    public void getHashMap(){
        int numkeys = pantry.size();
    }

    public void refreshAll(LinearLayout itemContainer, int lastIndex){
        for (int i = 0; i < lastIndex; i++) {
          //createNewEntry(i, itemContainer.getChildAt(i));
        }
    }



}
