package com.example.foodpantry;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 *
 */
public class PantryFragment extends Fragment {
  TextView test;
  ImageButton edit;
  View view;
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view =  inflater.inflate(R.layout.fragment_pantry, container, false);
    test = (TextView) view.findViewById(R.id.titleForItem0);
    edit = (ImageButton) view.findViewById(R.id.editButtonForItem0);
    edit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        System.out.println(test.getText().toString()+ "HARRY DICK IS 17 INCHES");
      }
    });

    return view;
  }
} // class