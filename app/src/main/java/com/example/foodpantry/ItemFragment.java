package com.example.foodpantry;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemFragment extends Fragment {
  static SaveInfo list;
  public ImageView icon;
  public String title;
  public TextView titleText;
  public String category;
  public TextView categoryText;
  public String amount;
  public TextView amountText;
  public Date expiryDate;
  public TextView dateText;
  public String size;
  public TextView sizeText;

  Toast lastToast;

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static String ARG_TITLE = "title";
  private static final String ARG_CATEGORY = "category";
  public static String ARG_AMOUNT = "1";
  public static String ARG_DATE = "02/04/2022";
  public static String ARG_SIZE = "5";

  public ItemFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param title Parameter 1.
   * @param category Parameter 2.
   * @return A new instance of fragment ItemFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static ItemFragment newInstance(String title, String category, int number, int weight) {
    ItemFragment fragment = new ItemFragment();

    Bundle args = new Bundle();
    args.putString(ARG_TITLE, title);
    args.putString(ARG_CATEGORY, category);
    args.putInt(ARG_AMOUNT, number);
    args.putInt(ARG_SIZE, weight);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      title = getArguments().getString(ARG_TITLE);
      category = getArguments().getString(ARG_CATEGORY);
      amount = getArguments().getString(ARG_AMOUNT);
      size = getArguments().getString(ARG_SIZE);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_item, container, false);

  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
    titleText = (TextView) getView().findViewById(R.id.titleForItem);
    titleText.setText(title);
    categoryText = (TextView) getView().findViewById(R.id.categoryNameForItem);
    categoryText.setText(category);
    amountText = (TextView) getView().findViewById(R.id.amountLeftInPantryForItem);
    amountText.setText(amount);
    sizeText = (TextView) getView().findViewById(R.id.sizeForItem);
    sizeText.setText(size);
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
}