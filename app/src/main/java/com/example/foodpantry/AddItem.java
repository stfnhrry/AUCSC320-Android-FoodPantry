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

public class AddItem {

    Map<Integer, Item> pantry = new HashMap<Integer, Item>();
    Button editButton;
    public int itemKey = 0;

    /**
     * James R.
     *
     * creates a new instance of the item class, creates a new hashmap, and stores
     * the class instances into the hashmap.
     */

    public void createNewItem(Item item){


        //Take textview inputs and stores that info into newItem
        //Set name
        //Set date
        //set amount

        pantry.put(itemKey, item);
        itemKey +=1;
        System.out.println("harrys dick is delicous" + "" + pantry.get(0));

    }

    public void edit(){


        // edit the amount
        //map.get(itemkey).setAmount()
        //edit the expDate
        //map.get(itemkey).setExpDate()
        //edit the itemName
        //map.get(itemKey).setitemName()
    }

    //Save state -- Save preferences



}
