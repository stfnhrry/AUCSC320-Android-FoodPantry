package com.example.foodpantry;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class PantryFragment extends Fragment {
    public AddItem test = new AddItem();
  TextView title;
  TextView expDate;
  TextView amount;
  ImageButton edit;
  View view;
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view =  inflater.inflate(R.layout.fragment_pantry, container, false);
    title = (TextView) view.findViewById(R.id.titleForItem0);
    amount = (TextView) view.findViewById(R.id.amountLeftInPantryForItem0);
    expDate = (TextView) view.findViewById(R.id.expiryDateForItem0);
    edit = (ImageButton) view.findViewById(R.id.editButtonForItem0);
    edit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          String e1 = expDate.getText().toString();
          String e2 = title.getText().toString();
          String e3 = amount.getText().toString();
          Date date = new Date(e1);
          int amnt = Integer.parseInt(e3);
          Item item = new Item(e2, date, amnt);
          System.out.println(item.getExpDate());
          System.out.println(item.getItemName());
          System.out.println(item.getAmount());






      }
    });

    return view;
  }
} // class