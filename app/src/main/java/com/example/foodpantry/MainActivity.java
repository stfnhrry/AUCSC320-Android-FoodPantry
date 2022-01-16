package com.example.foodpantry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

  private AppBarConfiguration mAppBarConfiguration;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    // When the user opens the app, the keyboard doesn't appear automatically
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
    DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
    NavigationView navigationView = findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
              new PantryFragment()).commit();
        navigationView.setCheckedItem(R.id.nav_pantry);
    }
  } // onCreate


  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    NavigationView navigationView = findViewById(R.id.nav_view);
    View headerView = navigationView.getHeaderView(0);
    TextView currentPageText = (TextView) headerView.findViewById(R.id.currentPage);
    switch (item.getItemId()) {
        case R.id.nav_pantry:
          getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                  new PantryFragment()).commit();
          navigationView.setCheckedItem(R.id.nav_pantry);
          currentPageText.setText("Pantry");
          break;
        case R.id.nav_low_in_stock:
          getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                  new LowInStockFragment()).commit();
          navigationView.setCheckedItem(R.id.nav_low_in_stock);
          currentPageText.setText("Low In Stock");
          break;
        case R.id.nav_out_of_stock:
          getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                  new OutOfStockFragment()).commit();
          navigationView.setCheckedItem(R.id.nav_out_of_stock);
          currentPageText.setText("Out Of Stock");

          break;
        case R.id.nav_expiring_soon:
          getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                  new ExpiringSoonFragment()).commit();
          navigationView.setCheckedItem(R.id.nav_expiring_soon);
          currentPageText.setText("Expiring Soon");
          break;
        case R.id.nav_expired:
          getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                  new ExpiredFragment()).commit();
          navigationView.setCheckedItem(R.id.nav_expired);
          currentPageText.setText("Expired");
          break;
        case R.id.nav_shopping_list:
          getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                  new ShoppingListFragment()).commit();
          navigationView.setCheckedItem(R.id.nav_shopping_list);
          currentPageText.setText("Shopping List");
          break;
      }
    return false;
  }
} // class