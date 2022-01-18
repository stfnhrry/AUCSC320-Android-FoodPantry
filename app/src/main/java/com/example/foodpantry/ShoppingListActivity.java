package com.example.foodpantry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
      t.setText(foodNames.get(i) + "  -" + weight.get(i) + "kg per" + " " + foodNames.get(i));
      linearLayout.addView(t);
    }
    clear = (Button) findViewById(R.id.ClearAll);
    clear.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        linearLayout.removeAllViews();
      }
    });


  } // onCreate

  /**
   * Back to overview page
   * @param aView
   */
  public void backToPantry(View aView) {
    Button backButton = (Button) findViewById(R.id.backToMainActivityButton);
    Intent toPantry = new Intent (this, MainActivity.class);
    startActivity(toPantry);
  } // backToPantry




} // class