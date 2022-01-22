package com.example.foodpantry;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Includes all the methods needed for the shopping list activity to function.
 */
public class ShoppingListActivity extends AppCompatActivity {

  ImageButton clear;
  ArrayList<String> foodNames;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shopping_list);

    //get arraylist from main activity
    foodNames = (ArrayList<String>) getIntent().getStringArrayListExtra("names");
    //weight = (ArrayList<String>) getIntent().getStringArrayListExtra("sizes");

    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.THISONEJAMES);
    for (int i = 0; i < foodNames.size(); i++) {
      TextView t = new TextView(this);
      t.setTextSize(35);
      t.setText(foodNames.get(i) + "  -  ");
      // + weight.get(i)
      linearLayout.addView(t);
      //a bug
    }
    clear = (ImageButton) findViewById(R.id.ClearAll);
    clear.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        linearLayout.removeAllViews();
        MainActivity.itemNames.clear();
      }
    });
  } // onCreate

  /**
   * Takes the user back to the pantry.
   * @param
   */
  @Override
  public void onBackPressed() {
    super.onBackPressed();
  } // onBackPressed

  /**
   * Takes the user back to the pantry.
   * @param aView the shopping list view
   */
  public void backToPantry(View aView) {
    onBackPressed();
  } // backToPantry

  /**
   * Shares the shopping list to the user.
   * @param view the shopping list view
   */
  public void shareShoppingList(View view) {
    Intent sendIntent = new Intent();
    sendIntent.setAction(Intent.ACTION_SEND);
    sendIntent.putExtra(Intent.EXTRA_TEXT, "printing function");
    sendIntent.setType("text/plain");
    startActivity(sendIntent);
  } // shareShoppingList

} // class