package com.example.foodpantry;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 *
 */
public class Fragment1 extends Fragment {

  public Fragment1() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View fragment1View =  inflater.inflate(R.layout.fragment_1, container, false);
    return fragment1View;
  }
} // class