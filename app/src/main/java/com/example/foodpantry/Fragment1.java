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
    Button aButton = fragment1View.findViewById(R.id.button);
    aButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Navigation.findNavController(fragment1View).navigate(R.id.action_fragment1_to_scrollingFragment2);
      }
    });
    return fragment1View;
  }
} // class