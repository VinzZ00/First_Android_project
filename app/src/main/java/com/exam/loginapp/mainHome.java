package com.exam.loginapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.exam.loginapp.databinding.ActivityMainHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class mainHome extends AppCompatActivity {

    ActivityMainHomeBinding binding;
    BottomNavigationView bnv;
    NavController navController;

    private int Counter;
    private int time;
    private Button RegisterBut;

    Thread timeCount = new Thread() {
        @Override
        public void run() {
            for (time = 0; time < 2; time++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            time = 0;
            Counter = 0;
        }


    };

    void validateFinish() {

        if (timeCount.isAlive()) {
            if (Counter >= 3) {
                System.out.println("masuk ke finish");
                finish();
            } else if (Counter < 3) {
                Toast.makeText(this, String.format("Press %d times again for exit", (3 - Counter)),Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Counter++;

        if (Counter == 1) {
            timeCount.start();
        }

        validateFinish();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NavHostFragment nhf = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        bnv = findViewById(R.id.NavigationView);
        navController = nhf.getNavController();
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.home, R.id.profileFrag).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        NavigationUI.setupWithNavController(binding.NavigationView, navController);


                

    }
}