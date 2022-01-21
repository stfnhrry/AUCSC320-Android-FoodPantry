package com.example.foodpantry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

  Button pantryButton, addItem, removeItem, lowInStock, outOfStock, expiringSoon, expired, shoppingList;

  Toast lastToast;

  LinearLayout cardLayout;

  int numItems;

  SaveFile hashMapFile = new SaveFile();

  //use firebase for data streaming

  Map<Integer, String[]> map = hashMapFile.pantry;

  public static ArrayList<String> itemNames = new ArrayList<>();
  ArrayList<Integer> sizes = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    cardLayout = findViewById(R.id.linearLayout);

    numItems = cardLayout.getChildCount();

    pantryButton = findViewById(R.id.pantryButton);
    addItem = findViewById(R.id.addItemButton);
    removeItem = findViewById(R.id.removeItemButton);
    lowInStock = findViewById(R.id.lowInStockButton);
    outOfStock = findViewById(R.id.outOfStockButton);
    expiringSoon = findViewById(R.id.expiringSoonButton);
    expired = findViewById(R.id.expiredButton);
    shoppingList = findViewById(R.id.shoppingListButton);

    // When the user opens the app, the keyboard doesn't appear automatically
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    pantryButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        setIcon();
        showAll();
        enableAllButtons();
        clearAllHighlights();
        pantryButton.setBackgroundColor(Color.LTGRAY);
      }
    });

    addItem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        setIcon();
        showAddItemDialog();
        enableAllButtons();
        clearAllHighlights();
        addItem.setBackgroundColor(Color.LTGRAY);
      }
    });

    removeItem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        for (int i = 0; i < cardLayout.getChildCount(); i++) {
          cardLayout.getChildAt(i).findViewById(R.id.editButtonForItem).setVisibility(View.GONE);
          cardLayout.getChildAt(i).findViewById(R.id.removeIcon).setVisibility(View.VISIBLE);
        }
        enableAllButtons();
        clearAllHighlights();
        removeItem.setBackgroundColor(Color.LTGRAY);
      }
    });

    lowInStock.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        setIcon();
        showLowInStock();
        enableAllButtons();
        clearAllHighlights();
        lowInStock.setBackgroundColor(Color.LTGRAY);
      }
    });

    outOfStock.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        setIcon();
        showOutOfStock();
        enableAllButtons();
        clearAllHighlights();
        outOfStock.setBackgroundColor(Color.LTGRAY);
      }
    });

    expiringSoon.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        setIcon();
        showExpiringSoon();
        enableAllButtons();
        clearAllHighlights();
        expiringSoon.setBackgroundColor(Color.LTGRAY);
      }
    });

    expired.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        setIcon();
        showExpired();
        enableAllButtons();
        clearAllHighlights();
        expired.setBackgroundColor(Color.LTGRAY);
      }
    });

    shoppingList.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        toShoppingList();
        enableAllButtons();
        clearAllHighlights();
      }
    });
  } // onCreate

  @Override
  protected void onStart(){
    super.onStart();
    refreshAllItems();
  }

  /**
   * Enables all buttons.
   */
  private void enableAllButtons() {
    pantryButton.setEnabled(true);
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
    pantryButton.setBackgroundColor(Color.TRANSPARENT);
    addItem.setBackgroundColor(Color.TRANSPARENT);
    removeItem.setBackgroundColor(Color.TRANSPARENT);
    lowInStock.setBackgroundColor(Color.TRANSPARENT);
    outOfStock.setBackgroundColor(Color.TRANSPARENT);
    expiringSoon.setBackgroundColor(Color.TRANSPARENT);
    expired.setBackgroundColor(Color.TRANSPARENT);
    shoppingList.setBackgroundColor(Color.TRANSPARENT);
  } // clearAllHighlights

  public void showToast(String text){
    if(lastToast != null){
      lastToast.cancel();
    }
    Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
    toast.setGravity(Gravity.BOTTOM, 0, 0);
    toast.show();
    lastToast = toast;
  } // showToast

  /**
   * Adding new item to pantry.
   * @param icon - image of the category
   * @param name - name of the item
   * @param category - which category it is in (like cans, jars, cookies, grains, other)
   * @param amount - the amount in stock for a specific item
   * @param weight - size of the item
   * @param expDate - the date the item expires
   */
  public void addNewItem(int icon, String name, String category, int amount, int weight, String expDate){
    Log.i("SAVE", "add new item");
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.add(cardLayout.getId(), ItemFragment.newInstance(icon, name, category, amount, weight, expDate));
    transaction.commitNow();

    Log.i("SAVE", "commit to fragment");

    numItems = cardLayout.getChildCount();
    Log.i("SAVE", "increment num items");

    View card = cardLayout.getChildAt(numItems - 1);
    ImageButton removeItemButton = cardLayout.getChildAt(numItems -1).findViewById(R.id.removeIcon);
    TextView cardText = cardLayout.getChildAt(numItems - 1).findViewById(R.id.titleForItem);
    ImageButton editButton = card.findViewById(R.id.editButtonForItem);
    ImageButton addToShopButton = cardLayout.getChildAt(numItems -1 ).findViewById(R.id.addToShoppingCartButtonForItem);
    removeItemButton.setVisibility(View.GONE);

    int id = numItems - 1;
    saveToArray(icon, name, category, amount, weight, expDate, id);

    editButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        showEditItemDialog(card);
      }

    });
    addToShopButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        addToCart(card);
        showToast("Items has been added to cart");
      }
    });
    removeItemButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        removeItemFromPantry(id);
      }
    });
  }

  public void loadNewItem(int icon, String name, String category, int amount, int weight, String expDate){
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.add(cardLayout.getId(), ItemFragment.newInstance(icon, name, category, amount, weight, expDate));
    transaction.commitNow();

    numItems = cardLayout.getChildCount();

    View card = cardLayout.getChildAt(numItems - 1);
    ImageButton removeTest = cardLayout.getChildAt(numItems -1).findViewById(R.id.removeIcon);
    TextView cardText = cardLayout.getChildAt(numItems - 1).findViewById(R.id.titleForItem);
    ImageButton editButton = card.findViewById(R.id.editButtonForItem);
    ImageButton addToShop = cardLayout.getChildAt(numItems -1 ).findViewById(R.id.addToShoppingCartButtonForItem);
    removeTest.setVisibility(View.GONE);
    int id = numItems - 1;

    editButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
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
        removeItemFromPantry(id);
      }
    });
  }

  /**
   * Sets the icon to the correct image.
   */
  public void setIcon(){
    for (int i = 0; i < cardLayout.getChildCount(); i++) {
      cardLayout.getChildAt(i).findViewById(R.id.removeIcon).setVisibility(View.GONE);
      cardLayout.getChildAt(i).findViewById(R.id.editButtonForItem).setVisibility(View.VISIBLE);
    }
  }

  /**
   * Edits an item already in the pantry.
   * @param view - the edit button
   * @param name - name of the item
   * @param category - category of the item (can, jar, cookies,...)
   * @param amount - the amount in stock of the item
   * @param size - size of the item
   * @param expDate - expiry date of the item
   */
  public void editItem(View view, EditText name, Spinner category, EditText amount, EditText size, EditText expDate){
    ImageView currentIcon = view.findViewById(R.id.iconForItem);
    TextView currentName = view.findViewById(R.id.titleForItem);
    TextView currentAmount = view.findViewById(R.id.amountLeftInPantryForItem);
    TextView leftInPantry = view.findViewById(R.id.leftInPantryText);
    TextView currentSize = view.findViewById(R.id.sizeForItem);
    TextView currentExpDate = view.findViewById(R.id.expiryDateForItem);
    TextView currentDaysTillExpiry = view.findViewById(R.id.daysTillExpiryForItem);
    TextView daysTillExpiry = view.findViewById(R.id.daysTillExpiryText);
    TextView currentCategory = view.findViewById(R.id.categoryNameForItem);

    currentIcon.setImageResource(setIconFromCategory(category));
    currentName.setText(name.getText());
    currentAmount.setText(amount.getText());
    currentSize.setText(size.getText());
    currentExpDate.setText(expDate.getText());
    currentCategory.setText(category.getSelectedItem().toString());

    if(getDateDifferenceAsLong(currentDaysTillExpiry.getText().toString()) < 30 && getDateDifferenceAsLong(currentDaysTillExpiry.getText().toString()) > 0){
      currentDaysTillExpiry.setText(getDateDifferenceAsString(currentDaysTillExpiry.getText().toString()));
      currentDaysTillExpiry.setTextColor(getResources().getColor(R.color.orange_warning, null));
      daysTillExpiry.setTextColor(getResources().getColor(R.color.orange_warning, null));
      daysTillExpiry.setText("Days Till Expiry");
    }
    else if(getDateDifferenceAsLong(currentExpDate.getText().toString()) < 1){
      currentDaysTillExpiry.setText("Expired");
      currentDaysTillExpiry.setTextColor(getResources().getColor(R.color.red_alert, null));
      daysTillExpiry.setTextColor(getResources().getColor(R.color.red_alert, null));
      daysTillExpiry.setText("");
    }
    else{
      currentDaysTillExpiry.setText(getDateDifferenceAsString(currentExpDate.getText().toString()));
      currentDaysTillExpiry.setTextColor(getResources().getColor(R.color.blue_item, null));
      daysTillExpiry.setTextColor(getResources().getColor(R.color.blue_item, null));
      daysTillExpiry.setText("Days Till Expiry");
    }

    if(Integer.parseInt(amount.getText().toString()) > 0 && Integer.parseInt(amount.getText().toString()) < 6){
      currentAmount.setTextColor(getResources().getColor(R.color.orange_warning, null));
      leftInPantry.setTextColor(getResources().getColor(R.color.orange_warning, null));
    }
    else if(Integer.parseInt(amount.getText().toString()) < 1){
      currentAmount.setTextColor(getResources().getColor(R.color.red_alert, null));
      leftInPantry.setTextColor(getResources().getColor(R.color.red_alert, null));
    }
    else{
      currentAmount.setTextColor(getResources().getColor(R.color.blue_item, null));
      leftInPantry.setTextColor(getResources().getColor(R.color.blue_item, null));
    }
    int index = cardLayout.indexOfChild(view);

    saveToArray(setIconFromCategory(category), name.getText().toString(), category.getSelectedItem().toString(), Integer.parseInt(amount.getText().toString()), Integer.parseInt(size.getText().toString()), expDate.getText().toString(), index);
  }

  /**
   * Shows all the items in the pantry
   */
  public void showAll(){
    for (int i = 0; i < cardLayout.getChildCount(); i++) {
      cardLayout.getChildAt(i).setVisibility(View.VISIBLE);
    }
  }

  /**
   * Shows the items low in stock
   */
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
  }

  /**
   * Shows the items that are out of stock
   */
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
  }

  /**
   * Shows the items that are expiring soon
   */
  public void showExpiringSoon(){
    for (int i = 0; i < cardLayout.getChildCount(); i++) {
      TextView date = cardLayout.getChildAt(i).findViewById(R.id.expiryDateForItem);
      String finalDate = date.getText().toString();

      long differenceDates = getDateDifferenceAsLong(finalDate);

      if(differenceDates < 30 && differenceDates > 0){
        cardLayout.getChildAt(i).setVisibility(View.VISIBLE);
      }
      else{
        cardLayout.getChildAt(i).setVisibility(View.GONE);
      }
    }//for
  }

  /**
   * Shows the items that are expired
   */
  public void showExpired(){
    for (int i = 0; i < cardLayout.getChildCount(); i++) {
      TextView date = cardLayout.getChildAt(i).findViewById(R.id.expiryDateForItem);
      String finalDate = date.getText().toString();

      long difference = getDateDifferenceAsLong(finalDate);

      if(difference < 1){
        cardLayout.getChildAt(i).setVisibility(View.VISIBLE);
      }
      else{
        cardLayout.getChildAt(i).setVisibility(View.GONE);
      }
    }//for
  }

  public void showNone(){
    for (int i = 0; i < cardLayout.getChildCount(); i++) {
      cardLayout.getChildAt(i).setVisibility(View.GONE);
    }
  }

  /**
   * Calculates the amount of days.
   * @param expiryDate
   * @return
   */
  public String getDateDifferenceAsString(String expiryDate){
    Date calendar = Calendar.getInstance().getTime();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    try{
      Date date2;
      date2 = dateFormat.parse(expiryDate);
      long difference = (date2.getTime() - calendar.getTime());
      long differenceDates = difference / (24 * 60 * 60 * 1000);

      return Long.toString(differenceDates);

    } catch (Exception exception){
      Log.i("DATE", "Cannot find day difference as string");
      return "null";
    }
  }

  public long getDateDifferenceAsLong(String expiryDate){
    Date calendar = Calendar.getInstance().getTime();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    try{
      Date date2;
      date2 = dateFormat.parse(expiryDate);
      long difference = (date2.getTime() - calendar.getTime());

      return difference / (24 * 60 * 60 * 1000);

    } catch (Exception exception){
      Log.i("DATE", "Cannot find day difference as long");
      return 99999;
    }
  }

  public void showAddItemDialog(){
    Log.i("SAVE", "Show add item dialog");
    Dialog addDialog = new Dialog(this);
    addDialog.setContentView(R.layout.add_item_dialog);
    Button addButton = addDialog.findViewById(R.id.confirmButton);
    Button closeButton = addDialog.findViewById(R.id.cancelButton);
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

    addButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        int image = setIconFromCategory(categorySpinner);
        String nameString = name.getText().toString();
        String categoryString = categorySpinner.getSelectedItem().toString();
        int amountInteger = Integer.parseInt(amount.getText().toString());
        int sizeInteger = Integer.parseInt(size.getText().toString());
        String expDateString = expDate.getText().toString();
        addNewItem(image, nameString, categoryString, amountInteger, sizeInteger, expDateString);
      }
    });

    closeButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        addDialog.dismiss();
      }
    });

    addDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
      @Override
      public void onDismiss(DialogInterface dialogInterface) {
        addItem.setBackgroundColor(Color.TRANSPARENT);
        hideKeyboard();
      }
    });
    addDialog.show();
  }

  public void showEditItemDialog(View card){
    Dialog editDialog = new Dialog(this);
    editDialog.setContentView(R.layout.edit_item_dialog);
    Button edit = editDialog.findViewById(R.id.confirmButton);
    Button closeButton = editDialog.findViewById(R.id.cancelButton);
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
      if(categorySpinner.getItemAtPosition(i).toString().equalsIgnoreCase(currentCategory.getText().toString())){
        categorySpinner.setSelection(i);
      }
    }

    edit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        editItem(card, name, categorySpinner, amount, size, expDate);
        editDialog.dismiss();
      }
    });

    closeButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        editDialog.dismiss();
      }
    });

    editDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
      @Override
      public void onDismiss(DialogInterface dialogInterface) {
        hideKeyboard();
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

  public void removeItemFromPantry(int index){
    cardLayout.removeViewAt(index);
  }

  public void saveToArray(int icon, String name, String category, int amount, int weight, String expDate, int index){
    Log.i("SAVE", "saveToArray");
    String iconString = icon + "";
    String amountString = amount + "";
    String weightString = weight + "";

    String[] temp = new String[6];

    temp[0] = iconString;
    temp[1] = name;
    temp[2] = category;
    temp[3] = amountString;
    temp[4] = weightString;
    temp[5] = expDate;

    saveToHashmapNew(temp, index);
  }

  public void loadFromArray(){
    for (int i = 0; i < map.size(); i++) {
      loadNewItem(Integer.parseInt(map.get(i)[0]), map.get(i)[1], map.get(i)[2], Integer.parseInt(map.get(i)[3]), Integer.parseInt(map.get(i)[4]), map.get(i)[5]);
    }
  }

  public void saveToHashmapNew(String[] array, int index){
    map.put(index, array);

    //convert to string using gson
    Gson gson = new Gson();
    String hashMapString = gson.toJson(map);

    //save in shared prefs
    SharedPreferences sharedPref2 = getPreferences(Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPref2.edit();
    editor.clear();
    editor.putString("hashString", hashMapString).apply();
  }

  public void loadFromHashmap(){
    Log.i("SAVE", "Load from hashmap");
    //get from shared prefs
    SharedPreferences sharedPref2 = getPreferences(Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPref2.edit();

    Gson gson = new Gson();

    String storedHashMapString = sharedPref2.getString("hashString", "Empty");

    if(storedHashMapString.equals("Empty")){
      return;
    }
    else{
      java.lang.reflect.Type type = new TypeToken<HashMap<Integer, String[]>>(){}.getType();
      HashMap<Integer, String[]> testHashMap2 = gson.fromJson(storedHashMapString, type);
      map = testHashMap2;
      loadFromArray();
    }
  }

  public void toShoppingList(){
    Intent switchActivityIntent = new Intent(this, ShoppingListActivity.class);
    switchActivityIntent.putStringArrayListExtra("names", itemNames);
    switchActivityIntent.putIntegerArrayListExtra("sizes", sizes);
    startActivity(switchActivityIntent);

  }

  public void hideKeyboard() {
    Log.i("SAVE", "Hide keyboard");
    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
    if(imm.isActive()){
      imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
    //imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
  }

  public int setIconFromCategory(Spinner category){
    int index = category.getSelectedItemPosition();
    int itemIcon = R.drawable.cookies;
    switch (index){
      case 0 : itemIcon = R.drawable.can_icon;
      break;
      case 1 : itemIcon = R.drawable.jar_icon;
      break;
      case 2 : itemIcon = R.drawable.juice_box_icon;
      break;
      case 3 : itemIcon = R.drawable.granola_bar_icon;
      break;
      case 4 : itemIcon = R.drawable.wheat_icon;
      break;
      case 5 : itemIcon = R.drawable.cookies;
      break;
      case 6 : itemIcon = R.drawable.picture2;
    }
    return itemIcon;
  }

  public void refreshAllItems(){
    cardLayout.removeAllViewsInLayout();
    loadFromHashmap();
  }

} // class