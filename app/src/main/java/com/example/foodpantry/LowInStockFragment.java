package com.example.foodpantry;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LowInStockFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LowInStockFragment extends Fragment {

  public LowInStockFragment() {
    // Required empty public constructor
  }
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_low_in_stock, container, false);
  }
}