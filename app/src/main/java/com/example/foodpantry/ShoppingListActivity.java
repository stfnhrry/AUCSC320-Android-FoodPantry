package com.example.foodpantry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ShoppingListActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shopping_list);

  } // onCreate

  public void backToPantry(View aView) {
    Button backButton = (Button) findViewById(R.id.backToMainActivityButton);
    Intent toPantry = new Intent (this, MainActivity.class);
    startActivity(toPantry);
  } // backToPantry

  public void printShoppingList(View view) {
    Intent sendIntent = new Intent();
    sendIntent.setAction(Intent.ACTION_SEND);
    sendIntent.putExtra(Intent.EXTRA_TEXT, "printing function");
    sendIntent.setType("text/plain");
    startActivity(sendIntent);

  }

} // class