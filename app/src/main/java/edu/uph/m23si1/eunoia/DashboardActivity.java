package edu.uph.m23si1.eunoia;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import edu.uph.m23si1.eunoia.databinding.ActivityDashboardBinding;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navBeranda, R.id.navTest, R.id.navKonsul, R.id.navHistori)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_dashboard);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

//        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
//            if (destination.getId() == R.id.navBeranda ||
//                    destination.getId() == R.id.navTest ||
//                    destination.getId() == R.id.navKonsul ||
//                    destination.getId() == R.id.navHistori) {
//
//                getSupportActionBar().setDisplayShowHomeEnabled(true);
//                getSupportActionBar().setLogo(R.drawable.logo);
//                getSupportActionBar().setDisplayUseLogoEnabled(true);
//            } else {
//
//                getSupportActionBar().setDisplayUseLogoEnabled(false);
//            }
        }
}

