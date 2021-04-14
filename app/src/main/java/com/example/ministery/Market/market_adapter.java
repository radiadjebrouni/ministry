package com.example.ministery.Market;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.ministery.Learn.Braille;
import com.example.ministery.Learn.Sign;

public class market_adapter extends FragmentStatePagerAdapter {
    public market_adapter(FragmentManager fm) {

        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                Fragment fragment = new offeredServicesFragment ();

                return fragment;

            case 1:
                Fragment f = new neededServicesFragment () ;

                return f;

        }
        return new offeredServicesFragment ();

    }

    @Override
    public int getCount() {
        return 2;
    }



}
