package com.example.ministerymob.MainScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ministerymob.LogInActivity;
import com.example.ministerymob.MenuActivity;
import com.example.ministerymob.R;
import com.example.ministerymob.SignUpActivity;
import com.example.ministerymob.TexttoSpeech;
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
    private TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

            setContentView(R.layout.activity_main);
            mTabLayout = findViewById(R.id.tab_layout);
            mMyViewPager = findViewById(R.id.view_pager);
            commencer =findViewById ( R.id.commencer );
            login = findViewById(R.id.activity_main_goLogin);

            commencer.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                    Intent signUp=new Intent ( getApplicationContext (), MenuActivity.class );
                    startActivity ( signUp );
                    overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left);

                }
            } );

            login.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                    Intent logIn = new Intent ( getApplicationContext (), LogInActivity.class );
                    startActivity (logIn);
                    overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left);

                }
            } );




        FIRST= new ImageFirstScreen ( R.drawable.image_acceuil0 );
        Second= new ImageFirstScreen ( R.drawable.image_acceuil1 );
        THIRD= new ImageFirstScreen ( R.drawable.image_acceuil2 );
        FOURTH= new ImageFirstScreen ( R.drawable.image_acceuil3 );
        FIFTH= new ImageFirstScreen ( R.drawable.image_acceuil5 );
        SIXTH= new ImageFirstScreen ( R.drawable.image_acceuil6 );
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


