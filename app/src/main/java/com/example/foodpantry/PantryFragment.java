package com.example.foodpantry;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 *
 */
public class PantryFragment extends Fragment {

  private OnFragmentInteractionListener mListener;
  Fragment childFragment;
  Toast lastToast;
  int time = 10;

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

    ItemFragment iF = ItemFragment.newInstance("Hi", "Hello");

    childFragment = iF;
    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
    transaction.add(R.id.child_fragment_container, childFragment).commit();
  }

  public void showToast(String text){
    if(lastToast != null){
      lastToast.cancel();
    }
    Toast toast = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
    toast.setGravity(Gravity.BOTTOM, 0, 0);
    toast.show();
    lastToast = toast;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnFragmentInteractionListener) {
      mListener = (OnFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString()
              + " must implement OnFragmentInteractionListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    void messageFromParentFragment(Uri uri);
  }

  public void onClick(View view){
    showToast("Added new thing");
    //ItemFragment iF2 = ItemFragment.newInstance("Hi", "Hello");

    //FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
    //transaction.add(R.id.child_fragment_container, iF2).commit();

    //showToast("Added new thing 2");
  }

} // class