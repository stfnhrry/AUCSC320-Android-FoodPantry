package com.example.foodpantry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShoppingListActivity extends AppCompatActivity {

  Button clear;
  ArrayList<String> foodNames;
  ArrayList<Integer> weight;
  Boolean isCleared = false;
  Toast lastToast;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shopping_list);

    //get arraylist from main activity
    foodNames = (ArrayList<String>) getIntent().getStringArrayListExtra("names");
    weight = (ArrayList<Integer>) getIntent().getIntegerArrayListExtra("sizes");

    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.THISONEJAMES);
    for(int i= 0;i < foodNames.size(); i++){
      TextView t = new TextView(this);
      t.setTextSize(35);
      t.setText(foodNames.get(i) + "  -" + weight.get(i) + "kg");
      linearLayout.addView(t);
      //a bug
    }
    clear = (Button) findViewById(R.id.ClearAll);
    clear.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        linearLayout.removeAllViews();
        MainActivity.itemNames.clear();
      }
    });



  } // onCreate

  /**
   * Back to overview page
   * @param
   */
  @Override
  public void onBackPressed(){
    super.onBackPressed();
  }
  public void backToPantry(View aView) {
    onBackPressed();
  } // backToPantry

  public void showToast(String text){
    if(lastToast != null){
      lastToast.cancel();
    }
    Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
    toast.setGravity(Gravity.BOTTOM, 0, 0);
    toast.show();
    lastToast = toast;
  }
/*
  public void shareShoppingList(View view) {
    Intent sendIntent = new Intent();
    sendIntent.setAction(Intent.ACTION_SEND);
    sendIntent.putExtra(Intent.EXTRA_TEXT, "printing function");
    sendIntent.setType("text/plain");
    startActivity(sendIntent);
*/





} // class