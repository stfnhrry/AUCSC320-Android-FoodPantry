package com.example.foodpantry;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 *
 */
public class PantryFragment extends Fragment {

  public PantryFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View pantryFragment =  inflater.inflate(R.layout.fragment_pantry, container, false);

    //Button myButton1 = new Button(getActivity());
    //myButton1.setText("Press me");

    //View newButton = inflater.inflate(R.layout.item_card, container, false);

    final Button myButton = new Button(pantryFragment.getContext());
    myButton.setText("Press me please");

    myButton.setBackgroundColor(getResources().getColor(R.color.orange_warning));
    myButton.setTextSize(18);
    myButton.setPadding(20, 0, 20, 0);

    ConstraintLayout constraintLayout = (ConstraintLayout) pantryFragment.findViewById(R.id.scrollIViewForPantryFragmentConstraint);

    //LinearLayout linearlayout = (LinearLayout) myView.findViewById(R.id.btnholder);
    //linearlayout.setOrientation(LinearLayout.VERTICAL);

    ConstraintLayout.LayoutParams cParams = new ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
    );
    //buttonParams.setMargins(0, 0, 0, 10);
    //LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
    //        LinearLayout.LayoutParams.MATCH_PARENT,
    //        LinearLayout.LayoutParams.WRAP_CONTENT);
    //buttonParams.setMargins(0, 0, 0, 10);

    constraintLayout.addView(myButton);
    //linearlayout.addView(myButton, buttonParams);


    return pantryFragment;
  }
} // class