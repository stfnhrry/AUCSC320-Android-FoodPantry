package com.example.foodpantry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
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

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

  Button pantryFragment, addItem, removeItem, lowInStock, outOfStock, expiringSoon, expired, shoppingList;

  Toast lastToast;

  LinearLayout cardLayout;

  Fragment activeFragment;

  int numItems;

  Boolean shoppingListVisible;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    cardLayout = findViewById(R.id.linearLayout);
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
        showAll();
      }
    });

    addItem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //addNewItem();
        showAddItemDialog();
      }
    });

    lowInStock.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //replaceFragment(new LowInStockFragment());
        showLowInStock();
      }
    });

    outOfStock.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //replaceFragment(new OutOfStockFragment());
        showOutOfStock();
      }
    });

    expiringSoon.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //replaceFragment(new ExpiringSoonFragment());
        showExpiringSoon();
      }
    });

    expired.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //replaceFragment(new ExpiredFragment());
        showExpired();
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

    View card = cardLayout.getChildAt(numItems - 1);
    TextView cardText = cardLayout.getChildAt(numItems - 1).findViewById(R.id.titleForItem);
    ImageButton editButton = cardLayout.getChildAt(numItems - 1).findViewById(R.id.editButtonForItem);

    editButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //editItem(cardText);
        showEditItemDialog(card);
      }
    });
  }

  public void editItem(View view, EditText name, EditText amount, EditText expDate){
    TextView currentName = view.findViewById(R.id.titleForItem);
    TextView currentAmount = view.findViewById(R.id.amountLeftInPantryForItem);
    TextView currentExpDate = view.findViewById(R.id.expiryDateForItem);

    currentName.setText(name.getText());
    currentAmount.setText(amount.getText());
    currentExpDate.setText(expDate.getText());
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

  public void showAddItemDialog(){
    Dialog addDialog = new Dialog(this);
    addDialog.setContentView(R.layout.add_item_dialog);
    Button add = addDialog.findViewById(R.id.confirmButton);
    EditText name = addDialog.findViewById(R.id.editName);
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
        addNewItem(name.getText().toString(), "Baked Goods", Integer.parseInt(amount.getText().toString()), 10, expDate.getText().toString());
      }
    });
    addDialog.show();
  }

  public void showEditItemDialog(View card){
    Dialog editDialog = new Dialog(this);
    editDialog.setContentView(R.layout.edit_item_dialog);
    Button edit = editDialog.findViewById(R.id.confirmEditButton);
    EditText name = editDialog.findViewById(R.id.editNameEdit);
    EditText amount = editDialog.findViewById(R.id.editAmountEdit);
    EditText expDate = editDialog.findViewById(R.id.editDateEdit);
    TextView currentName = card.findViewById(R.id.titleForItem);
    TextView currentAmount = card.findViewById(R.id.amountLeftInPantryForItem);
    TextView currentExpDate = card.findViewById(R.id.expiryDateForItem);

    name.setText(currentName.getText());
    amount.setText(currentAmount.getText());
    expDate.setText(currentExpDate.getText());

    edit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        editItem(card, name, amount, expDate);
      }
    });
    editDialog.show();
  }
} // class