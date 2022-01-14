package com.example.foodpantry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

  Button pantryFragment, addItem, removeItem, lowInStock, outOfStock, expiringSoon, expired, shoppingList;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    pantryFragment = findViewById(R.id.pantryButton1);
    addItem = findViewById(R.id.addItemButton1);
    removeItem= findViewById(R.id.removeItemButton1);
    lowInStock = findViewById(R.id.lowInStockButton1);
    outOfStock = findViewById(R.id.outOfStockButton1);
    expiringSoon = findViewById(R.id.expiringSoonButton1);
    expired = findViewById(R.id.expiredButton1);
    shoppingList = findViewById(R.id.shoppingListButton1);

    pantryFragment.setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                replaceFragment(new PantryFragment());
              }
            });
    lowInStock.setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                replaceFragment(new LowInStockFragment());
              }
            });
  }

  private void replaceFragment(Fragment fragment) {

    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
    fragmentTransaction.commit();
  }

} // class