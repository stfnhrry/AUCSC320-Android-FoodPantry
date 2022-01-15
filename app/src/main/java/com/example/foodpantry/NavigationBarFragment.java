package com.example.foodpantry;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Used to navigate through other fragments, I guess.
 */
public class NavigationBarFragment extends Fragment {


  public NavigationBarFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View navigationBarView = inflater.inflate(R.layout.fragment_navigation_bar, container, false);
    // Inflate the layout for this fragment
    return navigationBarView;
  } // onCreateView






} // class
