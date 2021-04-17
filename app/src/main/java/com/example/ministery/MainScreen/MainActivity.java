package com.example.ministery.MainScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ministery.R;
import com.example.ministery.SignUpActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ViewPager mMyViewPager;
    private TabLayout mTabLayout;
    private ImageFirstScreen FIRST ;
    private ImageFirstScreen Second ;
    private ImageFirstScreen THIRD ;
    private ImageFirstScreen FOURTH ;
    private ImageFirstScreen FIFTH ;
    private ImageFirstScreen SIXTH ;
    private  ImageFirstScreen[] images;
    private Button commencer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

            setContentView(R.layout.activity_main);
            mTabLayout = findViewById(R.id.tab_layout);
            mMyViewPager = findViewById(R.id.view_pager);
            commencer =findViewById ( R.id.commencer );

            commencer.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                    Intent signUp=new Intent ( getApplicationContext (),SignUpActivity.class );
                    startActivity ( signUp );
                }
            } );



        /**************************
         *
         * TODO adding ranya's vectors to the first screen instead of chaise.png
         */
        FIRST= new ImageFirstScreen ( R.drawable.chaise );
        Second= new ImageFirstScreen ( R.drawable.chaise );
        THIRD= new ImageFirstScreen ( R.drawable.chaise );
        FOURTH= new ImageFirstScreen ( R.drawable.chaise );
        FIFTH= new ImageFirstScreen ( R.drawable.chaise );
        SIXTH= new ImageFirstScreen ( R.drawable.chaise );
                images = new ImageFirstScreen[]{FIRST, Second, THIRD, FOURTH, FIFTH, SIXTH};
            init();
        }

        private void init(){
            ArrayList<Fragment> fragments = new ArrayList<> ();
            ImageFirstScreen[] hats = images;
            for(ImageFirstScreen hat: hats){
                ImageViewPager fragment = ImageViewPager.getInstance(hat);
                fragments.add(fragment);
            }
            MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
            mMyViewPager.setAdapter(pagerAdapter);
            mTabLayout.setupWithViewPager(mMyViewPager, true);
        }



}


