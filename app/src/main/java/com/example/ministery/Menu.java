package com.example.ministery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ToggleButton;


import com.example.ministery.Fav.messaging;
import com.example.ministery.Learn.learn;
import com.example.ministery.Market.market;
import com.example.ministery.Profile.profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;


public class Menu extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        BottomNavigationView bottomNav = findViewById ( R.id.bottom_navigation );
        bottomNav.setOnNavigationItemSelectedListener ( navListener );
        if (savedInstanceState == null) {
            getSupportFragmentManager ().beginTransaction ().replace ( R.id.fragment_container,
                    new profile () ).commit ();
        }



        //getSupportFragmentManager ().beginTransaction ().replace ( R.id.container,
          //      new Braille () ).commit ();
            

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.learn:
                            selectedFragment = new learn ();
                            break;
                        case R.id.profile:
                            selectedFragment = new profile ();
                            break;
                        case R.id.market:
                            selectedFragment = new market ();
                            break;
                        case R.id.chat:
                            selectedFragment = new messaging ();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };
    }