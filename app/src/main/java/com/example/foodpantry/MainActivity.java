package com.example.foodpantry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

  Button pantryFragment, addItem, removeItem, lowInStock, outOfStock, expiringSoon, expired, shoppingList;

  Toast lastToast;

  LinearLayout cardLayout;

  Fragment activeFragment;

  TextView currentPageText;

  ArrayList<String> hello = new ArrayList<>();

  int numItems;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    cardLayout = findViewById(R.id.linearLayout);
    numItems = cardLayout.getChildCount();
    pantryFragment = findViewById(R.id.pantryButton);
    addItem = findViewById(R.id.addItemButton);
    removeItem = findViewById(R.id.removeItemButton);
    lowInStock = findViewById(R.id.lowInStockButton);
    outOfStock = findViewById(R.id.outOfStockButton);
    expiringSoon = findViewById(R.id.expiringSoonButton);
    expired = findViewById(R.id.expiredButton);
    shoppingList = findViewById(R.id.shoppingListButton);
    // etActionBar().setTitle("Pantry");
    currentPageText = findViewById(R.id.currentPageText);
    pantryFragment.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        showAll();
        enableAllButtons();
        clearAllHighlights();
        pantryFragment.setEnabled(false);
        pantryFragment.setBackgroundColor(Color.LTGRAY);
      }
    });
    addItem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //addNewItem();
        showAddItemDialog();
        enableAllButtons();
        clearAllHighlights();
        addItem.setEnabled(false);
        addItem.setBackgroundColor(Color.LTGRAY);
      }
    });
    lowInStock.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //replaceFragment(new LowInStockFragment());
        currentPageText.setText("Low In Stock");
        showLowInStock();
        enableAllButtons();
        clearAllHighlights();
        lowInStock.setEnabled(false);
        lowInStock.setBackgroundColor(Color.LTGRAY);
      }
    });
    outOfStock.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //replaceFragment(new OutOfStockFragment());
        currentPageText.setText("Out Of Stock");
        showOutOfStock();
        enableAllButtons();
        clearAllHighlights();
        outOfStock.setEnabled(false);
        outOfStock.setBackgroundColor(Color.LTGRAY);
      }
    });
    expiringSoon.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //replaceFragment(new ExpiringSoonFragment());
        currentPageText.setText("Expiring Soon");
        showExpiringSoon();
        enableAllButtons();
        clearAllHighlights();
        expiringSoon.setEnabled(false);
        expiringSoon.setBackgroundColor(Color.LTGRAY);
      }
    });
    expired.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //replaceFragment(new ExpiredFragment());
        currentPageText.setText("Expired");
        showExpired();
        enableAllButtons();
        clearAllHighlights();
        expired.setEnabled(false);
        expired.setBackgroundColor(Color.LTGRAY);
      }
    });

    shoppingList.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        toShoppingList();
      }
    });
  } // onCreate

  private void toShoppingList() {
    Intent toShoppingActivity = new Intent(this, ShoppingListActivity.class);
    toShoppingActivity.putStringArrayListExtra("Testing", hello);
    startActivity(toShoppingActivity);
  }

  /**
   * Inflates a menu, allowing the user to search for items in the catalog.
   * // TODO Add search functionality so the user can search for items that will be in the pantry
   * // TODO When a new item is added, it should be added to the array adapter to search for items
   * @param menu the menu with the search bar
   * @return - the current menu
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.search_menu, menu);
    MenuItem searchBar = menu.findItem(R.id.search_bar);
    SearchView searchView = (SearchView) searchBar.getActionView();
    searchView.setQueryHint("Type here to search");
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        return false;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        return false;
      }
    });
    return super.onCreateOptionsMenu(menu);
  } // onCreateOptionsMenu

  /**
   * Enables all buttons.
   */
  private void enableAllButtons() {
    pantryFragment.setEnabled(true);
    addItem.setEnabled(true);
    removeItem.setEnabled(true);
    lowInStock.setEnabled(true);
    outOfStock.setEnabled(true);
    expiringSoon.setEnabled(true);
    expired.setEnabled(true);
    shoppingList.setEnabled(true);
  } // enableAllButtons

  /**
   * Clears all highlighted buttons by setting the background of the buttons to be transparent.
   */
  private void clearAllHighlights() {
    pantryFragment.setBackgroundColor(Color.TRANSPARENT);
    addItem.setBackgroundColor(Color.TRANSPARENT);
    removeItem.setBackgroundColor(Color.TRANSPARENT);
    lowInStock.setBackgroundColor(Color.TRANSPARENT);
    outOfStock.setBackgroundColor(Color.TRANSPARENT);
    expiringSoon.setBackgroundColor(Color.TRANSPARENT);
    expired.setBackgroundColor(Color.TRANSPARENT);
    shoppingList.setBackgroundColor(Color.TRANSPARENT);
  } // clearAllHighlights

  public void showToast(String text) {
    if(lastToast != null){
      lastToast.cancel();
    }
    Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
    toast.setGravity(Gravity.BOTTOM, 0, 0);
    toast.show();
    lastToast = toast;
  } // showToast

  private void replaceFragment(Fragment fragment) {
    activeFragment = fragment;
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.FrameLayout, fragment);
    fragmentTransaction.commit();
  }

  private void removeAllFragmentsFromScreen() {
    if (activeFragment == null) {
      return;
    }
    else{
      FragmentManager fragmentManager = getSupportFragmentManager();
      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      fragmentTransaction.remove(activeFragment);
      activeFragment = null;
      fragmentTransaction.commit();
    }
  }

  public void addNewItem() {
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.add(cardLayout.getId(), ItemFragment.newInstance("Toast", "Baked Goods", 1, 100, "23/01/2022"));
    transaction.commitNow();

    numItems = cardLayout.getChildCount();

    View card = cardLayout.getChildAt(numItems - 1);
    TextView cardText = cardLayout.getChildAt(numItems - 1).findViewById(R.id.titleForItem);
    ImageButton editButton = cardLayout.getChildAt(numItems - 1).findViewById(R.id.editButtonForItem);
    ImageButton addToCartButton = cardLayout.getChildAt(numItems - 1).findViewById(R.id.addToShoppingCartButtonForItem);

    editButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //editItem(cardText);
        showEditItemDialog(card);
      }
    });
    addToCartButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        System.out.println("You clicked on the add button!");
        addToCart(card);
      }
    });
  }

  public void editItem(TextView view){
    //showToast(view.getText() + " is the title for this card, changed now to Oreos");
    view.setText("Potato Chips");
  }

  public void showAll(){
    for (int i = 0; i < cardLayout.getChildCount(); i++) {
      cardLayout.getChildAt(i).setVisibility(View.VISIBLE);
    }
    removeAllFragmentsFromScreen();
  }

  public void showLowInStock(){
    for (int i = 0; i < cardLayout.getChildCount(); i++) {
      TextView text = cardLayout.getChildAt(i).findViewById(R.id.amountLeftInPantryForItem);
      int num = Integer.parseInt(text.getText().toString());
      if(num < 6 && num > 0){
        cardLayout.getChildAt(i).setVisibility(View.VISIBLE);
      }
      else{
        cardLayout.getChildAt(i).setVisibility(View.INVISIBLE);
      }
    }
    removeAllFragmentsFromScreen();
  }

  public void showOutOfStock(){
    for (int i = 0; i < cardLayout.getChildCount(); i++) {
      TextView text = cardLayout.getChildAt(i).findViewById(R.id.amountLeftInPantryForItem);
      int num = Integer.parseInt(text.getText().toString());
      if(num < 1){
        cardLayout.getChildAt(i).setVisibility(View.VISIBLE);
      }
      else{
        cardLayout.getChildAt(i).setVisibility(View.INVISIBLE);
      }
    }
    removeAllFragmentsFromScreen();
  }

  public void showExpiringSoon(){
    for (int i = 0; i < cardLayout.getChildCount(); i++) {
      TextView date = cardLayout.getChildAt(i).findViewById(R.id.expiryDateForItem);

      try{
        String currentDate = "17/01/2022";
        String finalDate = date.getText().toString();
        Date date1;
        Date date2;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date1 = dateFormat.parse(currentDate);
        date2 = dateFormat.parse(finalDate);
        long difference = (date2.getTime() - date1.getTime());
        long differenceDates = difference / (24 * 60 * 60 * 1000);
        String dayDifference = Long.toString(differenceDates);
        //showToast("The difference between the two dates is " + dayDifference + " days");

        if(differenceDates < 30 && differenceDates > 0){
          cardLayout.getChildAt(i).setVisibility(View.VISIBLE);
        }
        else{
          cardLayout.getChildAt(i).setVisibility(View.INVISIBLE);
        }

      } catch (Exception exception){
        showToast("Cannot find day difference");
      }
    }//for

    removeAllFragmentsFromScreen();
  }

  public void showExpired(){
    for (int i = 0; i < cardLayout.getChildCount(); i++) {
      TextView date = cardLayout.getChildAt(i).findViewById(R.id.expiryDateForItem);

      try{
        String currentDate = "17/01/2022";
        String finalDate = date.getText().toString();
        Date date1;
        Date date2;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date1 = dateFormat.parse(currentDate);
        date2 = dateFormat.parse(finalDate);
        long difference = (date2.getTime() - date1.getTime());
        long differenceDates = difference / (24 * 60 * 60 * 1000);
        String dayDifference = Long.toString(differenceDates);
        //showToast("The difference between the two dates is " + dayDifference + " days");

        if(differenceDates < 1){
          cardLayout.getChildAt(i).setVisibility(View.VISIBLE);
        }
        else{
          cardLayout.getChildAt(i).setVisibility(View.INVISIBLE);
        }

      } catch (Exception exception){
        showToast("Cannot find day difference");
      }
    }//for

    removeAllFragmentsFromScreen();
  }

  public void showAddItemDialog(){
    Dialog addDialog = new Dialog(this);
    addDialog.setContentView(R.layout.add_item_dialog);
    Button add = addDialog.findViewById(R.id.confirmButton);
    EditText name = addDialog.findViewById(R.id.editName);
    EditText size = addDialog.findViewById(R.id.editSize);
    EditText amount = addDialog.findViewById(R.id.editAmount);
    EditText expDate = addDialog.findViewById(R.id.editDate);
    Spinner categorySpinner = addDialog.findViewById(R.id.spinner);
    ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
    categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    categorySpinner.setAdapter(categoryAdapter);
    // Need to disable the user from clicking anywhere because if the user clicks on the buttons on
    // the side, then the dialog closes
    add.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(cardLayout.getId(), ItemFragment.newInstance(name.getText().toString(), categorySpinner.getSelectedItem().toString(), Integer.parseInt(amount.getText().toString()), Integer.parseInt(size.getText().toString()), expDate.getText().toString()));
        transaction.commitNow();
      }
    });
    addDialog.show();
  }

  public void addToCart(View card) {
    TextView itemName = card.findViewById(R.id.titleForItem);
    String name = itemName.getText().toString();
    hello.add(name);
    System.out.println("Size of hello: " + hello);
  } // addToCart

  public void showEditItemDialog(View view){
    //code here
  }
} // class