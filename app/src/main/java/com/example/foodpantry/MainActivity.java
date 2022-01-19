package com.example.foodpantry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

  Button pantryFragment, addItem, removeItem, lowInStock, outOfStock, expiringSoon, expired, shoppingList;

  Toast lastToast;

  LinearLayout cardLayout;

  Fragment activeFragment;

  int numItems;

  Boolean shoppingListVisible;

  SaveInfo hashMap = new SaveInfo();

  ArrayList<String> itemNames = new ArrayList<>();
  ArrayList<Integer> sizes = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    cardLayout = findViewById(R.id.thisoneJames);
    numItems = cardLayout.getChildCount();


    pantryFragment = findViewById(R.id.pantryButton);
    addItem = findViewById(R.id.addItemButton);
    removeItem= findViewById(R.id.removeItemButton);
    lowInStock = findViewById(R.id.lowInStockButton);
    outOfStock = findViewById(R.id.outOfStockButton);
    expiringSoon = findViewById(R.id.expiringSoonButton);
    expired = findViewById(R.id.expiredButton);
    shoppingList = findViewById(R.id.shoppingListButton);

    // When the user opens the app, the keyboard doesn't appear automatically
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    pantryFragment.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //removeFragment();
        setIcon();
        showAll();
      }
    });

    removeItem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        for (int i = 0; i < cardLayout.getChildCount(); i++) {
          cardLayout.getChildAt(i).findViewById(R.id.editButtonForItem).setVisibility(View.GONE);
          cardLayout.getChildAt(i).findViewById(R.id.removeIcon).setVisibility(View.VISIBLE);
        }
      }
    });

    addItem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //addNewItem();
        setIcon();
        showAddItemDialog();
      }
    });

    lowInStock.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //replaceFragment(new LowInStockFragment());
        setIcon();
        showLowInStock();
      }
    });

    outOfStock.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //replaceFragment(new OutOfStockFragment());
        setIcon();
        showOutOfStock();
      }
    });

    expiringSoon.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //replaceFragment(new ExpiringSoonFragment());
        setIcon();
        showExpiringSoon();
      }
    });

    expired.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //replaceFragment(new ExpiredFragment());
        setIcon();
        showExpired();
      }
    });

    shoppingList.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
       // replaceFragment(new ShoppingListFragment());
        toShoppingList();
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

  public void addNewItem(String name, String category, int amount, int weight, String expDate){
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.add(cardLayout.getId(), ItemFragment.newInstance(name, category, amount, weight, expDate));
    transaction.commitNow();

    numItems = cardLayout.getChildCount();

    Date c = Calendar.getInstance().getTime();
    System.out.println("Current time => " + c);

    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    String formattedDate = df.format(c);
    showToast(formattedDate + "is the current date");

    View card = cardLayout.getChildAt(numItems - 1);
    ImageButton removeTest = cardLayout.getChildAt(numItems -1).findViewById(R.id.removeIcon);
    TextView cardText = cardLayout.getChildAt(numItems - 1).findViewById(R.id.titleForItem);
    ImageButton editButton = cardLayout.getChildAt(numItems - 1).findViewById(R.id.editButtonForItem);
    ImageButton addToShop = cardLayout.getChildAt(numItems -1 ).findViewById(R.id.addToShoppingCartButtonForItem);
    removeTest.setVisibility(View.GONE);

    editButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //editItem(cardText);
        showEditItemDialog(card);
      }

    });
    addToShop.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        addToCart(card);
        addToShop.setEnabled(false);
        showToast("Item added to cart");
      }
    });
    removeTest.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        cardLayout.removeViewAt(0);
      }
    });
  }

  public void removing(View view){

  }

  public void setIcon(){
    for (int i = 0; i < cardLayout.getChildCount(); i++) {
      cardLayout.getChildAt(i).findViewById(R.id.removeIcon).setVisibility(View.GONE);
      cardLayout.getChildAt(i).findViewById(R.id.editButtonForItem).setVisibility(View.VISIBLE);
    }
  }

  public void editItem(View view, EditText name, Spinner category, EditText amount, EditText size, EditText expDate){
    TextView currentName = view.findViewById(R.id.titleForItem);
    TextView currentAmount = view.findViewById(R.id.amountLeftInPantryForItem);
    TextView currentSize = view.findViewById(R.id.sizeForItem);
    TextView currentExpDate = view.findViewById(R.id.expiryDateForItem);
    TextView currentCategory = view.findViewById(R.id.categoryNameForItem);

    currentName.setText(name.getText());
    currentAmount.setText(amount.getText());
    currentSize.setText(size.getText());
    currentExpDate.setText(expDate.getText());
    currentCategory.setText(category.getSelectedItem().toString());
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
        cardLayout.getChildAt(i).setVisibility(View.GONE);
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
        cardLayout.getChildAt(i).setVisibility(View.GONE);
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
          cardLayout.getChildAt(i).setVisibility(View.GONE);
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
          cardLayout.getChildAt(i).setVisibility(View.GONE);
        }

      } catch (Exception exception){
        showToast("Cannot find day difference");
      }
    }//for

    removeAllFragmentsFromScreen();
  }

  public void showNone(){
    for (int i = 0; i < cardLayout.getChildCount(); i++) {
      cardLayout.getChildAt(i).setVisibility(View.GONE);
    }
  }

  public String calculateDateDifference(View newCard, String expiryDate){
    TextView date = newCard.findViewById(R.id.expiryDateForItem);
    TextView daysCount = newCard.findViewById(R.id.daysTillExpiryForItem);

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

      Date c = Calendar.getInstance().getTime();
      System.out.println("Current time => " + c);

      SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
      String formattedDate = df.format(c);

      return dayDifference;

    } catch (Exception exception){
      showToast("Cannot find day difference");
      return "null";
    }
  }

  public void showAddItemDialog(){
    Dialog addDialog = new Dialog(this);
    addDialog.setContentView(R.layout.add_item_dialog);
    Button add = addDialog.findViewById(R.id.confirmButton);
    EditText name = addDialog.findViewById(R.id.editName);
    name.setText("Bread");
//    String nameString = name.getText().toString();
    EditText amount = addDialog.findViewById(R.id.editAmount);
    amount.setText("2");
//    int amountInteger = Integer.parseInt(amount.getText().toString());
    EditText size = addDialog.findViewById(R.id.editSize);
    size.setText("3");
//    int sizeInteger = Integer.parseInt(size.getText().toString());
    EditText expDate = addDialog.findViewById(R.id.editDate);
    expDate.setText("21/02/2022");
//    String expDateString = expDate.getText().toString();
    Spinner categorySpinner = addDialog.findViewById(R.id.spinner);
    ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
    categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    categorySpinner.setAdapter(categoryAdapter);
//    showToast(categorySpinner.getSelectedItem().toString() + " is what the category returns");
    // Need to disable the user from clicking anywhere because if the user clicks on the buttons on
    // the side, then the dialog closes
    add.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String nameString = name.getText().toString();
        String categoryString = categorySpinner.getSelectedItem().toString();
        int amountInteger = Integer.parseInt(amount.getText().toString());
        int sizeInteger = Integer.parseInt(size.getText().toString());
        String expDateString = expDate.getText().toString();
        // Need to disable the user from clicking anywhere because if the user clicks on the buttons on
        // the side, then the dialog closes
        addNewItem(nameString, categoryString, amountInteger, sizeInteger, expDateString);
        saveToHashMap();
      }
    });
    addDialog.show();
  }

  public void showEditItemDialog(View card){
    Dialog editDialog = new Dialog(this);
    editDialog.setContentView(R.layout.edit_item_dialog);
    Button edit = editDialog.findViewById(R.id.confirmButton);
    EditText name = editDialog.findViewById(R.id.editName);
    EditText amount = editDialog.findViewById(R.id.editAmount);
    EditText size = editDialog.findViewById(R.id.editSize);
    EditText expDate = editDialog.findViewById(R.id.editDate);
    Spinner categorySpinner = editDialog.findViewById(R.id.spinner);
    ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
    categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    categorySpinner.setAdapter(categoryAdapter);

    TextView currentName = card.findViewById(R.id.titleForItem);
    TextView currentAmount = card.findViewById(R.id.amountLeftInPantryForItem);
    TextView currentSize = card.findViewById(R.id.sizeForItem);
    TextView currentExpDate = card.findViewById(R.id.expiryDateForItem);
    TextView currentCategory = card.findViewById(R.id.categoryNameForItem);

    name.setText(currentName.getText());
    size.setText(currentSize.getText());
    amount.setText(currentAmount.getText());
    expDate.setText(currentExpDate.getText());

    for (int i = 0; i < (categorySpinner.getCount()); i++) {
      if(categorySpinner.getItemAtPosition(i).toString() == currentCategory.getText().toString()){
        //showToast(categorySpinner.getItemAtPosition(i) + " is the item of items");
        categorySpinner.setSelection(i);
      }
    }

    edit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        editItem(card, name, categorySpinner, amount, size, expDate);
      }
    });
    editDialog.show();
  }
  public void addToCart(View card){

    TextView itemName = card.findViewById(R.id.titleForItem);
    TextView size = card.findViewById(R.id.sizeForItem);

    String name = itemName.getText().toString();
    int sze = Integer.parseInt(size.getText().toString());

    itemNames.add(name);
    sizes.add(sze);

  }
  public void saveToHashMap(){
    try{
      TextView title = cardLayout.getChildAt(numItems - 1).findViewById(R.id.titleForItem);
      TextView date = cardLayout.getChildAt(numItems-1).findViewById(R.id.expiryDateForItem);
      TextView amount = cardLayout.getChildAt(numItems-1).findViewById(R.id.amountLeftInPantryForItem);
      TextView size = cardLayout.getChildAt(numItems-1).findViewById(R.id.sizeForItem);
      String test = date.getText().toString();
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
      Date d;
      d = dateFormat.parse(test);
      String t = title.getText().toString();
      int a = Integer.parseInt(amount.getText().toString());
      int s = Integer.parseInt(size.getText().toString());
      Item item = new Item(t, d, a, s);
      hashMap.createNewItem(item);
      hashMap.getHashMap();

    } catch (ParseException e) {
      e.printStackTrace();
    }


  }

  public void toShoppingList(){
    Intent switchActivityIntent = new Intent(this, ShoppingListActivity.class);
    switchActivityIntent.putStringArrayListExtra("names", itemNames);
    switchActivityIntent.putIntegerArrayListExtra("sizes", sizes);
    startActivity(switchActivityIntent);

  }

} // class