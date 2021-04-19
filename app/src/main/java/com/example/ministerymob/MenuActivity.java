package com.example.ministerymob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;


import com.example.ministerymob.Fav.FavoriteFragment;
import com.example.ministerymob.Learn.learnFragment;
import com.example.ministerymob.Market.MarketFragment;
import com.example.ministerymob.Profile.ProfileFragment;
import com.example.ministerymob.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MenuActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.menu_activity );

        BottomNavigationView bottomNav = findViewById ( R.id.bottom_navigation );
        bottomNav.setOnNavigationItemSelectedListener ( navListener );
        if (savedInstanceState == null) {
            getSupportFragmentManager ().beginTransaction ().replace ( R.id.fragment_container,
                    new ProfileFragment () ).commit ();
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
                            selectedFragment = new learnFragment ();
                            break;
                        case R.id.profile:
                            selectedFragment = new ProfileFragment ();
                            break;
                        case R.id.market:
                            selectedFragment = new MarketFragment ();
                            break;
                        case R.id.chat:
                            selectedFragment = new FavoriteFragment ();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };
    }