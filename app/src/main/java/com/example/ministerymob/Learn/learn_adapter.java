package com.example.ministerymob.Learn;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class learn_adapter extends FragmentStatePagerAdapter {
    public learn_adapter(FragmentManager fm) {

        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                Fragment fragment = new BrailleFragment ();

                return fragment;

            case 1:
                Fragment f = new SignFragment () ;

                return f;

        }
        return new BrailleFragment ();

    }

    @Override
    public int getCount() {
        return 2;
    }



}
