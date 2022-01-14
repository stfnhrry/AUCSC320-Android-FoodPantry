package com.example.foodpantry;

import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/** Used to navigate through other fragments, I guess. */
public class NavigationBarFragment extends Fragment {
View view;
  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View pantryFragment = inflater.inflate(R.layout.fragment_pantry, container, false);
    return pantryFragment;
  }
}
