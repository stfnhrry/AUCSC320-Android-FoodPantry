package com.example.foodpantry;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveInfo {

    Map<Integer, View> pantry = new HashMap<Integer, View>();
    public int itemKey = 0;

    /**
     * James R.
     *
     * creates a new instance of the item class, creates a new hashmap, and stores
     * the class instances into the hashmap.
     */

    public void createNewItem(View view){

        pantry.put(itemKey, view);
        itemKey +=1;


    }

    public void getHashMap(){
        System.out.println(pantry.get(0));
    }





}
