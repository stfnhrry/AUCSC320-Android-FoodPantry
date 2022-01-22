package com.example.foodpantry;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple item fragment class.
 */
public class ItemFragment extends Fragment {

  public ImageView icon;
  public int iconInt;
  public String title;
  public TextView titleText;
  public String category;
  public TextView categoryText;
  public int amount;
  public TextView amountText;
  public TextView leftInPantryText;
  public String expiryDate;
  public TextView dateText;
  public TextView daysText;
  public TextView daysTillExpiryText;
  public String size;
  public TextView sizeText;
  private static final String ARG_ICON = "R.drawable.cookies";
  private static final String ARG_TITLE = "title";
  private static final String ARG_CATEGORY = "category";
  public static String ARG_AMOUNT = "1";
  public static String ARG_DATE = "23/04/2022";
  public static String ARG_SIZE = "5kg";

  public ItemFragment() {
    // Required empty public constructor
  }
  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   * @param title    Parameter 1.
   * @param category Parameter 2.
   * @return A new instance of fragment ItemFragment.
   */
  public static ItemFragment newInstance(int icon, String title, String category, int number, String weight, String date) {
    ItemFragment fragment = new ItemFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_ICON, icon);
    args.putString(ARG_TITLE, title);
    args.putString(ARG_CATEGORY, category);
    args.putInt(ARG_AMOUNT, number);
    args.putString(ARG_SIZE, weight);
    args.putString(ARG_DATE, date);
    fragment.setArguments(args);
    return fragment;
  } // newInstance

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      iconInt = getArguments().getInt(ARG_ICON);
      title = getArguments().getString(ARG_TITLE);
      category = getArguments().getString(ARG_CATEGORY);
      amount = getArguments().getInt(ARG_AMOUNT);
      size = getArguments().getString(ARG_SIZE);
      expiryDate = getArguments().getString(ARG_DATE);
    }
  } // onCreate

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_item, container, false);
  } // onCreateView

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    icon = (ImageView) getView().findViewById(R.id.iconForItem);
    icon.setImageResource(iconInt);
    titleText = (TextView) getView().findViewById(R.id.titleForItem);
    titleText.setText(title);
    categoryText = (TextView) getView().findViewById(R.id.categoryNameForItem);
    categoryText.setText(category);
    amountText = (TextView) getView().findViewById(R.id.amountLeftInPantryForItem);
    amountText.setText(amount + "");
    leftInPantryText = (TextView) getView().findViewById(R.id.leftInPantryText);
    sizeText = (TextView) getView().findViewById(R.id.sizeForItem);
    sizeText.setText(size + "");
    dateText = (TextView) getView().findViewById(R.id.expiryDateForItem);
    dateText.setText(expiryDate);
    daysText = (TextView) getView().findViewById(R.id.daysTillExpiryForItem);
    daysTillExpiryText = (TextView) getView().findViewById(R.id.daysTillExpiryText);

    if (calculateDateDifferenceAsLong(dateText.getText().toString()) < 30 && calculateDateDifferenceAsLong(dateText.getText().toString()) > 0) {
      daysText.setText(calculateDateDifferenceAsString(dateText.getText().toString()));
      daysText.setTextColor(getResources().getColor(R.color.orange_warning, null));
      daysTillExpiryText.setTextColor(getResources().getColor(R.color.orange_warning, null));
      daysTillExpiryText.setText("Days Till Expiry");
    } else if (calculateDateDifferenceAsLong(dateText.getText().toString()) < 1) {
      daysText.setText("Expired");
      daysText.setTextColor(getResources().getColor(R.color.red_alert, null));
      daysTillExpiryText.setTextColor(getResources().getColor(R.color.red_alert, null));
      daysTillExpiryText.setText("");
    } else {
      daysText.setText(calculateDateDifferenceAsString(dateText.getText().toString()));
      daysText.setTextColor(getResources().getColor(R.color.blue_item, null));
      daysText.setTextColor(getResources().getColor(R.color.blue_item, null));
      daysTillExpiryText.setText("Days Till Expiry");
    }
    if (Integer.parseInt(amountText.getText().toString()) > 0 && Integer.parseInt(amountText.getText().toString()) < 6) {
      amountText.setTextColor(getResources().getColor(R.color.orange_warning, null));
      leftInPantryText.setTextColor(getResources().getColor(R.color.orange_warning, null));
    } else if (Integer.parseInt(amountText.getText().toString()) < 1) {
      amountText.setTextColor(getResources().getColor(R.color.red_alert, null));
      leftInPantryText.setTextColor(getResources().getColor(R.color.red_alert, null));
    } else {
      amountText.setTextColor(getResources().getColor(R.color.blue_item, null));
      leftInPantryText.setTextColor(getResources().getColor(R.color.blue_item, null));
    }
  } // onViewCreated

  /**
   * Updates the info.
   * @param iconUpdate the icon to be updated
   * @param titleUpdate the title to be updated
   * @param categoryUpdate the category to be updated
   * @param amountUpdate the amount to be updated
   * @param weightUpdate the weight to be updated
   * @param dateUpdate the date to be updated
   */
  public void updateInfo (int iconUpdate, String titleUpdate, String categoryUpdate, int amountUpdate, String weightUpdate, String dateUpdate) {
    iconInt = iconUpdate;
    title = titleUpdate;
    category = categoryUpdate;
    amount = amountUpdate;
    size = weightUpdate;
    expiryDate = dateUpdate;
    icon = (ImageView) getView().findViewById(R.id.iconForItem);
    icon.setImageResource(iconInt);
    titleText = (TextView) getView().findViewById(R.id.titleForItem);
    titleText.setText(title);
    categoryText = (TextView) getView().findViewById(R.id.categoryNameForItem);
    categoryText.setText(category);
    amountText = (TextView) getView().findViewById(R.id.amountLeftInPantryForItem);
    amountText.setText(amount + "");
    sizeText = (TextView) getView().findViewById(R.id.sizeForItem);
    sizeText.setText(size + "");
    dateText = (TextView) getView().findViewById(R.id.expiryDateForItem);
    dateText.setText(expiryDate);
    daysText = (TextView) getView().findViewById(R.id.daysTillExpiryForItem);
    daysText.setText(calculateDateDifferenceAsString(dateText.getText().toString()));
  } // updateInfo

  /**
   * Calculates the date difference as a string.
   * @param expiryDate the current expiry date
   * @return - the date difference as a string
   */
  public String calculateDateDifferenceAsString(String expiryDate) {
    Date calendar = Calendar.getInstance().getTime();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    try {
      Date date2;
      date2 = dateFormat.parse(expiryDate);
      long difference = (date2.getTime() - calendar.getTime());
      long differenceDates = difference / (24 * 60 * 60 * 1000);

      return Long.toString(differenceDates);

    } catch (Exception exception) {
      return "null";
    }
  } // calculateDateDifferenceAsString

  /**
   * Calculates the date difference as a long.
   * @param expiryDate the current expiry date
   * @return - the date difference as a long
   */
  public long calculateDateDifferenceAsLong(String expiryDate) {
    Date calendar = Calendar.getInstance().getTime();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    try {
      Date date2;
      date2 = dateFormat.parse(expiryDate);
      long difference = (date2.getTime() - calendar.getTime());

      return difference / (24 * 60 * 60 * 1000);

    } catch (Exception exception) {
      return 99999;
    }
  } // calculateDateDifferenceAsLong

} // class