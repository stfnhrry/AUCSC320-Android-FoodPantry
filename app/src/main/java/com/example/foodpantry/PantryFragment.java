package com.example.foodpantry;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

/**
 *
 */
public class PantryFragment extends Fragment implements View.OnClickListener {

  public PantryFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View pantryFragment =  inflater.inflate(R.layout.fragment_pantry, container, false);
    ImageButton editButton = (ImageButton) pantryFragment.findViewById(R.id.editButtonForItem0);
    ImageButton addToShoppingCartButton =  (ImageButton) pantryFragment.findViewById(R.id.addToShoppingCartButtonForItem0);
    editButton.setOnClickListener(this);
    addToShoppingCartButton.setOnClickListener(this);
    return pantryFragment;
  } //onCreateView

  @Override
  public void onClick(View pantryFragmentView) {
    switch (pantryFragmentView.getId()) {
      case R.id.editButtonForItem0:
        System.out.println("You clicked on the edit button!");
        break;
      case R.id.addToShoppingCartButtonForItem0:
        System.out.println("You clicked on the add to shopping cart button!");
        break;
      default:
        break;
    } // switch
  } // onClick

} // class