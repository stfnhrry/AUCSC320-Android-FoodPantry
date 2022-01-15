package com.example.foodpantry;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 *
 */
public class PantryFragment extends Fragment {
  AddItem test = new AddItem();
  public PantryFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View pantryFragment =  inflater.inflate(R.layout.fragment_pantry, container, false);
    return pantryFragment;
  }
  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {

    test.edit(view);
  }

} // class