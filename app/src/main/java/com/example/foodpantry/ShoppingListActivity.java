package com.example.foodpantry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ShoppingListActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shopping_list);
    addToShoppingList();
  } // onCreate

  /**
   * Takes the user back to the main activity, where they can view the pantry, pantry items that
   * are low in stock, out of stock, expiring soon and expired.
   * @param aView
   */
  public void backToPantry(View aView) {
    Button backButton = (Button) findViewById(R.id.backToMainActivityButton);
    Intent toPantry = new Intent (this, MainActivity.class);
    startActivity(toPantry);
  } // backToPantry

  /**
   * Adds all pantry items sent from the main activity to the shopping list.
   */
  public void addToShoppingList() {
    ArrayList<String> pantryItems = new ArrayList<>();
    // ArrayList<TextView> pantryItemsTextView = new ArrayList<>();
    LinearLayout linearLayout = (LinearLayout)findViewById(R.id.shoppingListLinearLayout);
    pantryItems.add("Bread");
    pantryItems.add("Cheese");
    for (int index = 0; index < pantryItems.size(); index++) {
      TextView aTextView = new TextView(this);
      // pantryItemsTextView.add(new TextView(this));
      aTextView.setText(pantryItems.get(index));
      linearLayout.addView(aTextView);
      // pantryItemsTextView.add(aTextView);
    }
  } // addToShoppingList

} // class