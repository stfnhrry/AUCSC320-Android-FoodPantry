package com.example.foodpantry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.Toast;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity implements PantryFragment.OnFragmentInteractionListener, ItemFragment.OnFragmentInteractionListener {

  Toast lastToast;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    // When the user opens the app, the keyboard doesn't appear automatically
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    //Begin transaction
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.replace(R.id.fragmentContainerView, new PantryFragment());
    ft.commit();
  } // onCreate

  public void showToast(String text){
    if(lastToast != null){
      lastToast.cancel();
    }
    Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
    toast.setGravity(Gravity.BOTTOM, 0, 0);
    toast.show();
    lastToast = toast;
  }

  public void makeNew(){
    Button myButton = new Button(this);
    myButton.setText("I am new here");

  }

  @Override
  public void messageFromParentFragment(Uri uri) {
    Log.i("TAG", "received communication from parent fragment");
  }

  @Override
  public void messageFromChildFragment(Uri uri) {
    Log.i("TAG", "received communication from child fragment");
  }

} // class