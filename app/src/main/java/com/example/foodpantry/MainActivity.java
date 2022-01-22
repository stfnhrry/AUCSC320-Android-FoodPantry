package com.example.foodpantry;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Inside this activity includes all the methods needed for the pantry app to run.
 */
public class MainActivity extends AppCompatActivity {

  Button pantryButton, addItem, removeItem, lowInStock, outOfStock, expiringSoon, expired, shoppingList;
  Toast lastToast;
  LinearLayout cardLayout;

  int numItems;
  SaveFile hashMapFile = new SaveFile();
  Map<Integer, String[]> map = hashMapFile.pantry;
  static ShoppingListFile staticShoppingList = new ShoppingListFile();
  //static ArrayList<String> itemNames = staticShoppingList.itemNames;
  static ArrayList<String> itemNames = ShoppingListFile.itemNames;
  ArrayList<String> sizes = new ArrayList<>();
  Boolean inRemovingMode = false;

  Dialog addDialog;
  Dialog editDialog;
  boolean isEveryFieldChecked = false;
  Button addButton;
  Button closeButton;
  EditText name;
  EditText amount;
  EditText weight;
  EditText expDate;

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
        setRemoveModeInactive();
        showAll();
        enableAllButtons();
        clearAllHighlights();
        pantryButton.setBackgroundColor(Color.LTGRAY);
      }
    });

    addItem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        setRemoveModeInactive();
        showAddItemDialog();
        enableAllButtons();
        clearAllHighlights();
        addItem.setBackgroundColor(Color.LTGRAY);
      }
    });

    removeItem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        setRemoveModeActive();
        enableAllButtons();
        clearAllHighlights();
        removeItem.setBackgroundColor(Color.LTGRAY);
      }
    });

    lowInStock.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        setRemoveModeInactive();
        showLowInStock();
        enableAllButtons();
        clearAllHighlights();
        lowInStock.setBackgroundColor(Color.LTGRAY);
      }
    });

    outOfStock.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        setRemoveModeInactive();
        showOutOfStock();
        enableAllButtons();
        clearAllHighlights();
        outOfStock.setBackgroundColor(Color.LTGRAY);
      }
    });

    expiringSoon.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        setRemoveModeInactive();
        showExpiringSoon();
        enableAllButtons();
        clearAllHighlights();
        expiringSoon.setBackgroundColor(Color.LTGRAY);
      }
    });

    expired.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        setRemoveModeInactive();
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
  protected void onStart() {
    super.onStart();
    refreshAllItems();
    refreshShoppingList();
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

  public void showToast(String text) {
    if (lastToast != null) {
      lastToast.cancel();
    }
    Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
    toast.setGravity(Gravity.BOTTOM, 0, 20);
    toast.show();
    lastToast = toast;
  } // showToast

  /**
   * Adding new item to pantry.
   *
   * @param icon     - image of the category
   * @param name     - name of the item
   * @param category - which category it is in (like cans, jars, cookies, grains, other)
   * @param amount   - the amount in stock for a specific item
   * @param weight   - size of the item
   * @param expDate  - the date the item expires
   */
  public void addNewItem(int icon, String name, String category, int amount, String weight, String expDate) {
    Log.i("SAVE", "add new item");
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.add(cardLayout.getId(), ItemFragment.newInstance(icon, name, category, amount, weight, expDate));
    transaction.commitNow();

    Log.i("SAVE", "commit to fragment");

    numItems = cardLayout.getChildCount();
    Log.i("SAVE", "increment num items");

    View card = cardLayout.getChildAt(numItems - 1);
    ImageButton removeItemButton = cardLayout.getChildAt(numItems - 1).findViewById(R.id.removeIcon);
    TextView cardText = cardLayout.getChildAt(numItems - 1).findViewById(R.id.titleForItem);
    ImageButton editButton = card.findViewById(R.id.editButtonForItem);
    ImageButton addToShopButton = cardLayout.getChildAt(numItems - 1).findViewById(R.id.addToShoppingCartButtonForItem);
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
        showToast("Item has been added to shopping list");
      }
    });
    removeItemButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        removeItemFromPantry(id);
      }
    });
  }

  public void loadNewItem(int icon, String name, String category, int amount, String weight, String expDate) {
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.add(cardLayout.getId(), ItemFragment.newInstance(icon, name, category, amount, weight, expDate));
    transaction.commitNow();

    numItems = cardLayout.getChildCount();

    View card = cardLayout.getChildAt(numItems - 1);
    ImageButton removeItemButton = cardLayout.getChildAt(numItems - 1).findViewById(R.id.removeIcon);
    TextView cardText = cardLayout.getChildAt(numItems - 1).findViewById(R.id.titleForItem);
    ImageButton editButton = card.findViewById(R.id.editButtonForItem);
    ImageButton addToShopButton = cardLayout.getChildAt(numItems - 1).findViewById(R.id.addToShoppingCartButtonForItem);
    removeItemButton.setVisibility(View.GONE);
    int id = numItems - 1;

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
        showToast("Item has been added to shopping list");

        addToShopButton.setEnabled(false);
        addToShopButton.postDelayed(new Runnable() {
          @Override
          public void run() {
            addToShopButton.setEnabled(true);
            Log.d(TAG, "disabled button");
          }
        }, 500);
      }

    });
    removeItemButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        removeItemFromPantry(id);
      }
    });
  }

  /**
   * Edits an item already in the pantry.
   *
   * @param view     - the edit button
   * @param name     - name of the item
   * @param category - category of the item (can, jar, cookies,...)
   * @param amount   - the amount in stock of the item
   * @param size     - size of the item
   * @param expDate  - expiry date of the item
   */
  public void editItem(View view, EditText name, Spinner category, EditText amount, EditText size, EditText expDate) {
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
    currentName.setText(name.getText().toString());
    currentAmount.setText(amount.getText().toString());
    currentSize.setText(size.getText().toString());
    currentExpDate.setText(expDate.getText().toString());
    currentCategory.setText(category.getSelectedItem().toString());

    if (getDateDifferenceAsLong(currentDaysTillExpiry.getText().toString()) < 30 && getDateDifferenceAsLong(currentDaysTillExpiry.getText().toString()) > 0) {
      currentDaysTillExpiry.setText(getDateDifferenceAsString(currentDaysTillExpiry.getText().toString()));
      currentDaysTillExpiry.setTextColor(getResources().getColor(R.color.orange_warning, null));
      daysTillExpiry.setTextColor(getResources().getColor(R.color.orange_warning, null));
      daysTillExpiry.setText("Days Till Expiry");
    } else if (getDateDifferenceAsLong(currentExpDate.getText().toString()) < 1) {
      currentDaysTillExpiry.setText("Expired");
      currentDaysTillExpiry.setTextColor(getResources().getColor(R.color.red_alert, null));
      daysTillExpiry.setTextColor(getResources().getColor(R.color.red_alert, null));
      daysTillExpiry.setText("");
    } else {
      currentDaysTillExpiry.setText(getDateDifferenceAsString(currentExpDate.getText().toString()));
      currentDaysTillExpiry.setTextColor(getResources().getColor(R.color.blue_item, null));
      daysTillExpiry.setTextColor(getResources().getColor(R.color.blue_item, null));
      daysTillExpiry.setText("Days Till Expiry");
    }
    if (Integer.parseInt(amount.getText().toString()) > 0 && Integer.parseInt(amount.getText().toString()) < 6) {
      currentAmount.setTextColor(getResources().getColor(R.color.orange_warning, null));
      leftInPantry.setTextColor(getResources().getColor(R.color.orange_warning, null));
      currentDaysTillExpiry.setVisibility(View.VISIBLE);
      daysTillExpiry.setVisibility(View.VISIBLE);
    } else if (Integer.parseInt(amount.getText().toString()) < 1) {
      currentAmount.setTextColor(getResources().getColor(R.color.red_alert, null));
      leftInPantry.setTextColor(getResources().getColor(R.color.red_alert, null));
      currentDaysTillExpiry.setVisibility(View.INVISIBLE);
      daysTillExpiry.setVisibility(View.INVISIBLE);
    } else {
      currentAmount.setTextColor(getResources().getColor(R.color.blue_item, null));
      leftInPantry.setTextColor(getResources().getColor(R.color.blue_item, null));
      currentDaysTillExpiry.setVisibility(View.VISIBLE);
      daysTillExpiry.setVisibility(View.VISIBLE);
    }
    int index = cardLayout.indexOfChild(view);
    saveToArray(setIconFromCategory(category), name.getText().toString(), category.getSelectedItem().toString(), Integer.parseInt(amount.getText().toString()), size.getText().toString(), expDate.getText().toString(), index);
  } // editItem

  /**
   * Sets the icon on item cards back to the edit symbol and sets boolean for removing items to false.
   */
  public void setRemoveModeInactive() {
    for (int i = 0; i < cardLayout.getChildCount(); i++) {
      cardLayout.getChildAt(i).findViewById(R.id.removeIcon).setVisibility(View.GONE);
      cardLayout.getChildAt(i).findViewById(R.id.editButtonForItem).setVisibility(View.VISIBLE);
    }
    inRemovingMode = false;
  } // setRemoveModeInactive

  /**
   * Sets the icon on item cards to the remove symbol and sets boolean for removing items to true.
   */
  public void setRemoveModeActive() {
    for (int i = 0; i < cardLayout.getChildCount(); i++) {
      cardLayout.getChildAt(i).findViewById(R.id.editButtonForItem).setVisibility(View.GONE);
      cardLayout.getChildAt(i).findViewById(R.id.removeIcon).setVisibility(View.VISIBLE);
    }
    inRemovingMode = true;
  } // setRemoveModeActive

  /**
   * Shows all the items in the pantry.
   */
  public void showAll() {
    for (int i = 0; i < cardLayout.getChildCount(); i++) {
      cardLayout.getChildAt(i).setVisibility(View.VISIBLE);
    }
  } // showAll

  /**
   * Shows the items low in stock.
   */
  public void showLowInStock() {
    for (int i = 0; i < cardLayout.getChildCount(); i++) {
      TextView text = cardLayout.getChildAt(i).findViewById(R.id.amountLeftInPantryForItem);
      int num = Integer.parseInt(text.getText().toString());
      if (num < 6 && num > 0) {
        cardLayout.getChildAt(i).setVisibility(View.VISIBLE);
      } else {
        cardLayout.getChildAt(i).setVisibility(View.GONE);
      }
    }
  } // showLowInStock

  /**
   * Shows the items that are out of stock.
   */
  public void showOutOfStock() {
    for (int i = 0; i < cardLayout.getChildCount(); i++) {
      TextView text = cardLayout.getChildAt(i).findViewById(R.id.amountLeftInPantryForItem);
      int num = Integer.parseInt(text.getText().toString());
      if (num < 1) {
        cardLayout.getChildAt(i).setVisibility(View.VISIBLE);
      } else {
        cardLayout.getChildAt(i).setVisibility(View.GONE);
      }
    }
  } // showOutOfStock

  /**
   * Shows the items that are expiring soon.
   */
  public void showExpiringSoon() {
    for (int i = 0; i < cardLayout.getChildCount(); i++) {
      TextView date = cardLayout.getChildAt(i).findViewById(R.id.expiryDateForItem);
      String finalDate = date.getText().toString();

      long differenceDates = getDateDifferenceAsLong(finalDate);

      if (differenceDates < 30 && differenceDates > 0) {
        cardLayout.getChildAt(i).setVisibility(View.VISIBLE);
      } else {
        cardLayout.getChildAt(i).setVisibility(View.GONE);
      }
    } // for
  } // showExpiringSoon

  /**
   * Shows the items that are expired.
   */
  public void showExpired() {
    for (int i = 0; i < cardLayout.getChildCount(); i++) {
      TextView date = cardLayout.getChildAt(i).findViewById(R.id.expiryDateForItem);
      String finalDate = date.getText().toString();

      long difference = getDateDifferenceAsLong(finalDate);

      if (difference < 1) {
        cardLayout.getChildAt(i).setVisibility(View.VISIBLE);
      } else {
        cardLayout.getChildAt(i).setVisibility(View.GONE);
      }
    } // for
  } // showExpired

  public void showNone() {
    for (int i = 0; i < cardLayout.getChildCount(); i++) {
      cardLayout.getChildAt(i).setVisibility(View.GONE);
    }
  } // showNone

  /**
   * Calculates the amount of days.
   * @param expiryDate the expiry date
   * @return - the amount of days left as a string
   */
  public String getDateDifferenceAsString(String expiryDate) {
    Date calendar = Calendar.getInstance().getTime();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    try {
      Date date2;
      date2 = dateFormat.parse(expiryDate);
      long difference = (date2.getTime() - calendar.getTime());
      long differenceDates = difference / (24 * 60 * 60 * 1000);

      return Long.toString(differenceDates);

    } catch (Exception exception) {
      Log.i("DATE", "Cannot find day difference as string");
      return "null";
    }
  } // getDateDifferenceAsString

  /**
   * Calculates the date difference as a long.
   * @param expiryDate the expiry date
   * @return - amount of days left as a long value
   */
  public long getDateDifferenceAsLong(String expiryDate) {
    Date calendar = Calendar.getInstance().getTime();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    try {
      Date date2;
      date2 = dateFormat.parse(expiryDate);
      long difference = (date2.getTime() - calendar.getTime());

      return difference / (24 * 60 * 60 * 1000);

    } catch (Exception exception) {
      Log.i("DATE", "Cannot find day difference as long");
      return 99999;
    }
  } // getDateDifferenceAsLong

  /**
   * Shows the add item dialog.
   */
  public void showAddItemDialog() {
    Log.i("SAVE", "Show add item dialog");
    addDialog = new Dialog(this);
    addDialog.setContentView(R.layout.add_item_dialog);

    addButton = addDialog.findViewById(R.id.confirmButton);
    closeButton = addDialog.findViewById(R.id.cancelButton);
    name = addDialog.findViewById(R.id.editName);
    amount = addDialog.findViewById(R.id.editAmount);
    weight = addDialog.findViewById(R.id.editSize);
    weight.setText("10kg");
    expDate = addDialog.findViewById(R.id.editDate);
    expDate.setText("21/02/2022");

    Spinner categorySpinner = addDialog.findViewById(R.id.spinner);
    ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
    categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    categorySpinner.setAdapter(categoryAdapter);

    addButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        isEveryFieldChecked = checkAllFields();

        if (isEveryFieldChecked) {
          int image = setIconFromCategory(categorySpinner);
          String nameString = name.getText().toString();
          String categoryString = categorySpinner.getSelectedItem().toString();
          int amountInteger = Integer.parseInt(amount.getText().toString());
          String weightString = weight.getText().toString();
          String expDateString = expDate.getText().toString();
          addNewItem(image, nameString, categoryString, amountInteger, weightString, expDateString);
        }
      }
    });

    closeButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        hideKeyboard(name);
        addDialog.dismiss();
      }
    });

    addDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
      @Override
      public void onDismiss(DialogInterface dialogInterface) {
        addItem.setBackgroundColor(Color.TRANSPARENT);
      }
    });

    addDialog.show();
  } // showAddItemDialog

  /**
   * Shows the edit item dialog.
   * @param card current card
   */
  public void showEditItemDialog(View card) {
    editDialog = new Dialog(this);
    editDialog.setContentView(R.layout.edit_item_dialog);

    addButton = editDialog.findViewById(R.id.confirmButton);
    closeButton = editDialog.findViewById(R.id.cancelButton);

    name = editDialog.findViewById(R.id.editName);
    amount = editDialog.findViewById(R.id.editAmount);
    weight = editDialog.findViewById(R.id.editSize);
    expDate = editDialog.findViewById(R.id.editDate);
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
    weight.setText(currentSize.getText());
    amount.setText(currentAmount.getText());
    expDate.setText(currentExpDate.getText());

    for (int i = 0; i < (categorySpinner.getCount()); i++) {
      if (categorySpinner.getItemAtPosition(i).toString().equalsIgnoreCase(currentCategory.getText().toString())) {
        categorySpinner.setSelection(i);
      }
    }

    addButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        isEveryFieldChecked = checkAllFields();

        if (isEveryFieldChecked) {
          editItem(card, name, categorySpinner, amount, weight, expDate);
          hideKeyboard(name);
          editDialog.dismiss();
        }
      }
    });

    closeButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        hideKeyboard(name);
        editDialog.dismiss();
      }
    });

    editDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
      @Override
      public void onDismiss(DialogInterface dialogInterface) {
        hideKeyboard(name);
      }
    });
    editDialog.show();
  } // showEditItemDialog

  /**
   * Adds an item to the shopping cart.
   * @param card current card
   */
  public void addToCart(View card) {
    TextView itemName = card.findViewById(R.id.titleForItem);
    TextView size = card.findViewById(R.id.sizeForItem);

    String name = itemName.getText().toString();
    String sze = size.getText().toString();

    itemNames.add(name);
    sizes.add(sze);

    storeShoppingListToPreference();
  } // addToCart

  /**
   * Removes an item from the pantry.
   * @param index the index of the current card to be removed
   */
  public void removeItemFromPantry(int index) {
    Log.i("SAVE", "Remove item from hashmap at index " + index);
    Log.i("SAVE", "Hashmap is currently " + map);
    cardLayout.removeViewAt(index);
    map.remove(index);
    Log.i("SAVE", "Hashmap is now " + map);

    int id = 0;
    HashMap<Integer, String[]> tempMap = new HashMap<Integer, String[]>();

    Set<Map.Entry<Integer, String[]>> entries = map.entrySet();

    Iterator<Map.Entry<Integer, String[]>> iterator =
            entries.iterator();

    while (iterator.hasNext()) {
      Map.Entry<Integer, String[]> entry = iterator.next();
      Integer key = entry.getKey();
      String[] value = entry.getValue();

      Log.i("SAVE", "Hashmap index " + key + " is " + value);

      tempMap.put(id, value);
      id++;
    }
    map = tempMap;
    saveHashmapToPreferences();
    refreshAllItems();
    if (inRemovingMode == true) {
      setRemoveModeActive();
    } else {
      setRemoveModeInactive();
    }
  } // removeItemFromPantry

  /**
   * Saves an item to an array.
   * @param icon the icon of the item
   * @param name the name of the item
   * @param category the category of the item
   * @param amount the amount of the item
   * @param weight the weight of the item
   * @param expDate the expiry date of the item
   * @param index the index of the item
   */
  public void saveToArray(int icon, String name, String category, int amount, String weight, String expDate, int index) {
    Log.i("SAVE", "saveToArray");
    String iconString = icon + "";
    String amountString = amount + "";
    String[] temp = new String[6];
    temp[0] = iconString;
    temp[1] = name;
    temp[2] = category;
    temp[3] = amountString;
    temp[4] = weight;
    temp[5] = expDate;
    saveToHashMap(index, temp);
  } // saveToArray

  /**
   * Loads a new item from the array.
   */
  public void loadFromArray() {
    for (int i = 0; i < map.size(); i++) {
      loadNewItem(Integer.parseInt(map.get(i)[0]), map.get(i)[1], map.get(i)[2], Integer.parseInt(map.get(i)[3]), map.get(i)[4], map.get(i)[5]);
    }
  } // loadFromArray

  /**
   * Saves an item to the hashmap.
   * @param index the index of the current card
   * @param ItemInfo the information in the current card
   */
  public void saveToHashMap(int index, String[] ItemInfo) {
    map.put(index, ItemInfo);
    saveHashmapToPreferences();
  } // saveToHashMap

  /**
   * Saves the hashmap to shared preferences.
   */
  public void saveHashmapToPreferences() {
    //convert to string using gson
    Gson gson = new Gson();
    String hashMapString = gson.toJson(map);

    //save in shared prefs
    SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = preferences.edit();
    editor.clear();
    editor.putString("hashString", hashMapString).apply();
  } // saveHashmapToPreferences

  /**
   * Loads information from the hasmap.
   */
  public void loadFromHashmap() {
    Log.i("SAVE", "Load from hashmap");
    //get shared prefs
    SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
    Gson gson = new Gson();
    //get HashMap as string from preferences
    String storedHashMapString = preferences.getString("hashString", "Empty");
    if (storedHashMapString.equals("Empty")) {
      return;
    } else {
      java.lang.reflect.Type type = new TypeToken<HashMap<Integer, String[]>>() {
      }.getType();
      HashMap<Integer, String[]> testHashMap2 = gson.fromJson(storedHashMapString, type);
      map = testHashMap2;
      loadFromArray();
    }
  } // loadFromHashmap

  /**
   * Takes the user to the shopping list activity.
   */
  public void toShoppingList() {
    Intent switchActivityIntent = new Intent(this, ShoppingListActivity.class);
    switchActivityIntent.putStringArrayListExtra("names", itemNames);
    switchActivityIntent.putStringArrayListExtra("sizes", sizes);
    startActivity(switchActivityIntent);
  } // toShoppingList

  /**
   * Hides the keyboard from the user when opening an edit text.
   * @param input the current input
   */
  public void hideKeyboard(EditText input) {
    Log.i("SAVE", "Hide keyboard run");
    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
  } // hideKeyboard

  /**
   * Sets the correct icon depending on the category that the user has chosen.
   * @param category the category in question
   * @return the corresponding icon
   */
  public int setIconFromCategory(Spinner category) {
    int index = category.getSelectedItemPosition();
    int itemIcon = R.drawable.cookies;
    switch (index) {
      case 0:
        itemIcon = R.drawable.can_icon;
        break;
      case 1:
        itemIcon = R.drawable.jar_icon;
        break;
      case 2:
        itemIcon = R.drawable.juice_box_icon;
        break;
      case 3:
        itemIcon = R.drawable.granola_bar_icon;
        break;
      case 4:
        itemIcon = R.drawable.wheat_icon;
        break;
      case 5:
        itemIcon = R.drawable.cookies;
        break;
      case 6:
        itemIcon = R.drawable.picture2;
    }
    return itemIcon;
  } // setIconFromCategory

  /**
   * Refreshes all items.
   */
  public void refreshAllItems() {
    cardLayout.removeAllViewsInLayout();
    loadFromHashmap();
  } // refreshAllItems

  /**
   * Checks all fields.
   * @return - true if all fields are not 0, false otherwise
   */
  public boolean checkAllFields() {
    if (name.length() == 0) {
      name.setError("This field is required");
      return false;
    }
    if (amount.length() == 0) {
      amount.setError("This field is required");
      return false;
    }
    if (weight.length() == 0) {
      weight.setError("This field is required");
      return false;
    }
    if (expDate.length() == 0) {
      expDate.setError("This field is required");
      return false;
    }
    return true;
  } // checkAllFields

  /**
   * Sets the string array preference.
   * @param context the context of the application
   * @param key the key
   * @param values the values
   */
  public void setStringArrayPrefNotStatic(Context context, String key, ArrayList<String> values) {
    SharedPreferences prefs = getSharedPreferences("LIST", 0);
    SharedPreferences.Editor editor = prefs.edit();
    JSONArray a = new JSONArray();
    for (int i = 0; i < values.size(); i++) {
      a.put(values.get(i));
    }
    if (!values.isEmpty()) {
      editor.putString(key, a.toString());
    } else {
      editor.putString(key, null);
    }
    editor.commit();
  } // setStringArrayPref

  /**
   * Gets the string array preference.
   * @param context the context of the current application
   * @param key the key
   * @return - the urls
   */
  public ArrayList<String> getStringArrayPrefNotStatic(Context context, String key) {
    SharedPreferences prefs = getSharedPreferences("LIST", 0);
    String json = prefs.getString(key, null);
    ArrayList<String> urls = new ArrayList<String>();
    if (json != null) {
      try {
        JSONArray a = new JSONArray(json);
        for (int i = 0; i < a.length(); i++) {
          String url = a.optString(i);
          urls.add(url);
        }
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    return urls;
  } // getStringArrayPref

  /**
   * Stores the shopping list to preferences.
   */
  public void storeShoppingListToPreference() {
    ArrayList<String> list = itemNames;
    setStringArrayPrefNotStatic(this, "ShoppingList", list);
  } // storeShoppingListToPreference

  /**
   * Gets the shopping list from preferences.
   */
  public void getShoppingListFromPreferences() {
    ArrayList<String> list = new ArrayList<String>();
    list = getStringArrayPrefNotStatic(this, "ShoppingList");
    itemNames = list;
  } // getShoppingListFromPreferences

  /**
   * Refreshes the shopping list.
   */
  public void refreshShoppingList() {
    itemNames.clear();
    getShoppingListFromPreferences();
  } // refreshShoppingList

} // class