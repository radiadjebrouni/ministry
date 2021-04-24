package com.example.ministerymob.Market;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class market_adapter extends FragmentStatePagerAdapter {
    public market_adapter(FragmentManager fm) {

        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                Fragment fragment = new neededServicesFragment ();

                return fragment;

            case 1:
                Fragment f = new offeredServicesFragment () ;

                return f;

        }
        return new offeredServicesFragment ();

    }

    @Override
    public int getCount() {
        return 2;
    }



}
