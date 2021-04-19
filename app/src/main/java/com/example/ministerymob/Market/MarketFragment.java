package com.example.ministerymob.Market;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.ministerymob.R;
import com.google.android.material.tabs.TabLayout;

public class MarketFragment extends Fragment {


    market_adapter market_adapter;
    ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate ( R.layout.market_fragment, container, false );
        this.setMenuVisibility ( false );



        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);



        market_adapter = new market_adapter (getChildFragmentManager());
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(market_adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt ( 0 ).setText (getString( R.string.besoin) );
      //  tabLayout.getTabAt ( 0 ).setIcon ( R.drawable.ic_braille );
        tabLayout.getTabAt ( 1).setText ( getString( R.string.offres) );
       // tabLayout.getTabAt ( 1 ).setIcon ( R.drawable.ic_sign );




    }
}