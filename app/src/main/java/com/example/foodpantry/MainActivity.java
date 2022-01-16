package com.example.foodpantry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ItemFragment.OnFragmentInteractionListener {

  Button pantryFragment, addItem, removeItem, lowInStock, outOfStock, expiringSoon, expired, shoppingList;

  Toast lastToast;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ItemFragment one = ItemFragment.newInstance("Bread", "Pastry", 1, 1);
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

    transaction.add(R.id.linearLayout, one);
    transaction.commit();

    pantryFragment = findViewById(R.id.pantryButton);
    addItem = findViewById(R.id.addItemButton);
    removeItem= findViewById(R.id.removeItemButton);
    lowInStock = findViewById(R.id.lowInStockButton);
    outOfStock = findViewById(R.id.outOfStockButton);
    expiringSoon = findViewById(R.id.expiringSoonButton);
    expired = findViewById(R.id.expiredButton);
    shoppingList = findViewById(R.id.shoppingListButton);


    pantryFragment.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        newCard();
      }
    });

    addItem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        addNewItem();
      }
    });

    lowInStock.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        replaceFragment(new LowInStockFragment());
      }
    });

    outOfStock.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        replaceFragment(new OutOfStockFragment());
      }
    });

    expiringSoon.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        replaceFragment(new ExpiringSoonFragment());
      }
    });

    expired.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        replaceFragment(new ExpiredFragment());
      }
    });

    shoppingList.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        replaceFragment(new ShoppingListFragment());
      }
    });
  }

  public void showToast(String text){
    if(lastToast != null){
      lastToast.cancel();
    }
    Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
    toast.setGravity(Gravity.BOTTOM, 0, 0);
    toast.show();
    lastToast = toast;
  }

  private void replaceFragment(Fragment fragment) {

    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.FrameLayout, fragment);
    fragmentTransaction.commit();
  }

  @Override
  public void messageFromChildFragment(Uri uri){
    Log.i("TAG", "Received communication from child fragment");
  }

  public void newCard(){
    ItemFragment two = ItemFragment.newInstance("NewCard", "Testing", 1, 0);
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

    transaction.add(R.id.linearLayout, two);
    transaction.commit();
    showToast("Hello there");
  }

  public void addNewItem(){
    ItemFragment two = ItemFragment.newInstance("Ice Cream", "Sweets", 1, 1);
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

    transaction.add(R.id.linearLayout, two);
    transaction.commit();
    showToast("Hello there");
  }

} // class