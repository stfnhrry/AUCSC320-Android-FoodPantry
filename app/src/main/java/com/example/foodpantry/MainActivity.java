package com.example.foodpantry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.Toast;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

  Toast lastToast;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    // When the user opens the app, the keyboard doesn't appear automatically
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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

} // class