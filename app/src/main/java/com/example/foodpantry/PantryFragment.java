package com.example.foodpantry;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

  private OnFragmentInteractionListener mListener;

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
    Fragment childFragment = new ItemFragment();
    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
    transaction.replace(R.id.child_fragment_container, childFragment).commit();
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
} // class