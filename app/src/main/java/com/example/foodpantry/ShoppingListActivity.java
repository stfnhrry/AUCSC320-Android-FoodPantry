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
  Toast lastToast;
  //TextView test = (TextView) findViewById(R.id.pantryItem1);
  ArrayList<String> foodNames;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shopping_list);
    foodNames = (ArrayList<String>) getIntent().getStringArrayListExtra("test");
    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.THISONEJAMES);
    for(int i= 0;i < foodNames.size(); i++){
      TextView t = new TextView(this);
      t.setTextSize(35);
      t.setText(foodNames.get(i));
      linearLayout.addView(t);
    }

  } // onCreate

  public void backToPantry(View aView) {
    Button backButton = (Button) findViewById(R.id.backToMainActivityButton);
    Intent toPantry = new Intent (this, MainActivity.class);
    startActivity(toPantry);
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

//  public void createList(){
//    for (int i = 0; i < foodNames.size()-1; i++){
//      TextView t = new TextView(this);
//      t.setText(foodNames.get(i));
//      this.hi.addView(t);
//    }
//  }

} // class