package com.example.foodpantry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

  Button pantryFragment, addItem, removeItem, lowInStock, outOfStock, expiringSoon, expired, shoppingList;

  Toast lastToast;

  LinearLayout cardLayout;

  Fragment activeFragment;

  int numItems;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    cardLayout = findViewById(R.id.linearLayout);



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
        removeFragment();
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
        //replaceFragment(new LowInStockFragment());
        editItem();
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
    activeFragment = fragment;
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.FrameLayout, fragment);
    fragmentTransaction.commit();
  }

  private void removeFragment() {
    if (activeFragment == null) {

    }
    else{
      FragmentManager fragmentManager = getSupportFragmentManager();
      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      fragmentTransaction.remove(activeFragment);
      activeFragment = null;
      fragmentTransaction.commit();
    }
  }

  public void addNewItem(){
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.add(cardLayout.getId(), ItemFragment.newInstance("Toast", "Baked Goods", 1, 1));
    transaction.commitNow();

    TextView cardText = cardLayout.getChildAt(0).findViewById(R.id.titleForItem);
//    ImageButton editButton = cardLayout.getChildAt(0).findViewById(R.id.editButtonForItem);
//    editButton.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        showToast(cardText.getText() + " is the title for this card, changed now to Oreos");
//        cardText.setText("Oreos");
//      }
//    });
  }



  public void next(){
    if(cardLayout.getChildCount() == 0){
      showToast("Still no children");
    }
    else{
      showToast("Enough time has passed");
    }
  }

  public void editItem(){
    //item.titleText.setText("Snail");
    TextView cardText = cardLayout.getChildAt(1).findViewById(R.id.titleForItem);
    ImageButton editButton = cardLayout.getChildAt(1).findViewById(R.id.editButtonForItem);
    editButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        showToast(cardText.getText() + " is the title for this card, changed now to Oreos");
        cardText.setText("Oreos");
      }
    });
  }

} // class