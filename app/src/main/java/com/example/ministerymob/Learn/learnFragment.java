package com.example.ministerymob.Learn;

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

public class learnFragment extends Fragment {


    learn_adapter learn_adapter;
    ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate ( R.layout.learn_fragment, container, false );
        this.setMenuVisibility ( false );



        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);



        learn_adapter = new learn_adapter (getChildFragmentManager());
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(learn_adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt ( 0 ).setText ("Braille" );
       // tabLayout.getTabAt ( 0 ).setIcon ( R.drawable.ic_braille );
        tabLayout.getTabAt ( 1).setText ( "Sign" );
        //tabLayout.getTabAt ( 1 ).setIcon ( R.drawable.ic_sign );




    }
}
