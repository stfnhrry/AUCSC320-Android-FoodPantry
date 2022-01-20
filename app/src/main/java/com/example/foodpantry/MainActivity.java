package com.example.foodpantry;

import androidx.appcompat.app.AppCompatActivity;
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

  ArrayList<String> itemNames = new ArrayList<>();
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
        showAll();
        enableAllButtons();
        clearAllHighlights();
        pantryButton.setEnabled(false);
        pantryButton.setBackgroundColor(Color.LTGRAY);
      }
    });

    addItem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        showAddItemDialog();
        enableAllButtons();
        clearAllHighlights();
        addItem.setBackgroundColor(Color.LTGRAY);
      }
    });

    removeItem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        enableAllButtons();
        clearAllHighlights();
        removeItem.setBackgroundColor(Color.LTGRAY);
        loadFromHashmap();
      }
    });

    lowInStock.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
//        showLowInStock();
        enableAllButtons();
        clearAllHighlights();
//        lowInStock.setEnabled(false);
        lowInStock.setBackgroundColor(Color.LTGRAY);
        refreshAllItems();
      }
    });

    outOfStock.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
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
        enableAllButtons();
        clearAllHighlights();
        //shoppingList.setBackgroundColor(Color.LTGRAY);
      }
    });
  } // onCreate

  @Override
  protected void onStart(){
    super.onStart();
    Log.i("ACTIVITY STATE", "On Start");
    refreshAllItems();
  }

  @Override
  protected void onRestart(){
    super.onRestart();
    Log.i("ACTIVITY STATE", "On Restart");
  }

  @Override
  protected void onResume(){
    super.onResume();
    Log.i("ACTIVITY STATE", "On Resume");
  }

  @Override
  protected void onPause(){
    super.onPause();
    Log.i("ACTIVITY STATE", "On Pause");
  }

  @Override
  protected void onStop(){
    super.onStop();
    Log.i("ACTIVITY STATE", "On Stop");
  }

  @Override
  protected void onDestroy(){
    super.onDestroy();
    Log.i("ACTIVITY STATE", "On Destroy");
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

  public void addNewItem(int icon, String name, String category, int amount, int weight, String expDate){
    Log.i("SAVE", "add new item");
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.add(cardLayout.getId(), ItemFragment.newInstance(icon, name, category, amount, weight, expDate));
    transaction.commitNow();

    Log.i("SAVE", "commit to fragment");

    numItems = cardLayout.getChildCount();
    Log.i("SAVE", "increment num items");

    View card = cardLayout.getChildAt(numItems - 1);
    ImageButton editButton = card.findViewById(R.id.editButtonForItem);
    ImageButton addToShop = cardLayout.getChildAt(numItems -1 ).findViewById(R.id.addToShoppingCartButtonForItem);

    int id = numItems - 1;
    Log.i("SAVE", "set id index number to" + id);
    Log.i("SAVE", "Call save to array next with parameters icon: " + icon + " , name: "+ name + " , id: " + id);
    saveToArray(icon, name, category, amount, weight, expDate, id);

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
        showToast("Items has been added to cart");
      }
    });
  }

  public void loadNewItem(int icon, String name, String category, int amount, int weight, String expDate){
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.add(cardLayout.getId(), ItemFragment.newInstance(icon, name, category, amount, weight, expDate));
    transaction.commitNow();

    numItems = cardLayout.getChildCount();

    View card = cardLayout.getChildAt(numItems - 1);
    ImageButton editButton = card.findViewById(R.id.editButtonForItem);
    ImageButton addToShop = cardLayout.getChildAt(numItems -1 ).findViewById(R.id.addToShoppingCartButtonForItem);

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
        showToast("Items has added to cart");
      }
    });
  }

  public void editItem(View view, EditText name, Spinner category, EditText amount, EditText size, EditText expDate){
    ImageView currentIcon = view.findViewById(R.id.iconForItem);
    TextView currentName = view.findViewById(R.id.titleForItem);
    TextView currentAmount = view.findViewById(R.id.amountLeftInPantryForItem);
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
    //currentDaysTillExpiry.setText(getDateDifferenceAsString(currentExpDate.getText().toString()));
    currentCategory.setText(category.getSelectedItem().toString());
    if(getDateDifferenceAsLong(currentExpDate.getText().toString()) < 1){
      currentDaysTillExpiry.setText("Expired");
      currentDaysTillExpiry.setTextColor(Color.parseColor("#FF0000"));
      daysTillExpiry.setText("");
    }
    else{
      currentDaysTillExpiry.setText(getDateDifferenceAsString(currentExpDate.getText().toString()));
      currentDaysTillExpiry.setTextColor(Color.parseColor("#2196F3"));
      daysTillExpiry.setText("Days Till Expiry");
    }

    int index = cardLayout.indexOfChild(view);

    saveToArray(setIconFromCategory(category), name.getText().toString(), category.getSelectedItem().toString(), Integer.parseInt(amount.getText().toString()), Integer.parseInt(size.getText().toString()), expDate.getText().toString(), index);

  }

  public void showAll(){
    for (int i = 0; i < cardLayout.getChildCount(); i++) {
      cardLayout.getChildAt(i).setVisibility(View.VISIBLE);
    }
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
  }

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
//    showToast(categorySpinner.getSelectedItem().toString() + " is what the category returns");
    // Need to disable the user from clicking anywhere because if the user clicks on the buttons on
    // the side, then the dialog closes

    addButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        int image = setIconFromCategory(categorySpinner);
        String nameString = name.getText().toString();
        String categoryString = categorySpinner.getSelectedItem().toString();
        int amountInteger = Integer.parseInt(amount.getText().toString());
        int sizeInteger = Integer.parseInt(size.getText().toString());
        String expDateString = expDate.getText().toString();
        // Need to disable the user from clicking anywhere because if the user clicks on the buttons on
        // the side, then the dialog closes
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

    Log.i("SAVE", "Call save to hashmap with parameters icon: " + iconString + " , name: " + name + " , id: " + index);
    saveToHashmapNew(temp, index);
  }

  public void loadFromArray(){
    Log.i("SAVE", "loadFromArray");
    for (int i = 0; i < map.size(); i++) {
      loadNewItem(Integer.parseInt(map.get(i)[0]), map.get(i)[1], map.get(i)[2], Integer.parseInt(map.get(i)[3]), Integer.parseInt(map.get(i)[4]), map.get(i)[5]);
    }
    //addNewItem();
  }

  public void saveToHashmapNew(String[] array, int index){
    Log.i("SAVE", "SaveToHashmapNew with index: " + index);
    if (map != null) {
      Log.i("SAVE", "Before saving the map is : " + map);
    }
    map.put(index, array);
    if (map != null) {
      Log.i("SAVE", "After saving the map is : " + map);
    }
    if(map.get(0) != null){
      Log.i("SAVE", "Saved to hashmap with 0: " + map.get(0) + " being " + map.get(0)[1].toString());
    }
    if(map.get(1) != null){
      Log.i("SAVE", "Saved to hashmap with 1: " + map.get(1) + " being " + map.get(1)[1].toString());
    }
    if(map.get(2) != null){
    Log.i("SAVE", "Saved to hashmap with 2: " + map.get(2) + " being " + map.get(2)[1].toString());
    }

    //showToast(map + " is the map");

    //convert to string using gson
    Gson gson = new Gson();
    String hashMapString = gson.toJson(map);

    //save in shared prefs
    SharedPreferences sharedPref2 = getPreferences(Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPref2.edit();
    editor.clear();
    editor.putString("hashString", hashMapString).apply();

    //showToast(map + " Map 1");
  }

  public void loadFromHashmap(){
    Log.i("SAVE", "Load from hashmap");
    //get from shared prefs
    SharedPreferences sharedPref2 = getPreferences(Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPref2.edit();

    Gson gson = new Gson();

    String storedHashMapString = sharedPref2.getString("hashString", "oopsDidntWork");
    java.lang.reflect.Type type = new TypeToken<HashMap<Integer, String[]>>(){}.getType();
    HashMap<Integer, String[]> testHashMap2 = gson.fromJson(storedHashMapString, type);

    if (testHashMap2 == null){
      Log.i("SAVE", "Loaded hashmap is null");
    }else{
      Log.i("DATE", "Loaded hashmap is valid");
    }

    Log.i("SAVE", "TestHashmap loaded with 0: " + testHashMap2.get(0) + " being " + testHashMap2.get(0)[1].toString());
    Log.i("SAVE", "TestHashmap loaded with 1: " + testHashMap2.get(1) + " being " + testHashMap2.get(1)[1].toString());
    Log.i("SAVE", "TestHashmap loaded with 2: " + testHashMap2.get(2) + " being " + testHashMap2.get(2)[1].toString());
    map = testHashMap2;
    Log.i("SAVE", "map declared with 0: " + map.get(0) + " being " + map.get(0)[1].toString());
    Log.i("SAVE", "map declared with 1: " + map.get(1) + " being " + map.get(1)[1].toString());
    Log.i("SAVE", "map declared with 2: " + map.get(2) + " being " + map.get(2)[1].toString());

    Log.i("SAVE", "Load from array called");
    loadFromArray();
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