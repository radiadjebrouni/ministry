package com.example.ministery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ministery.MainScreen.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SpashScreenActivity extends AppCompatActivity {

    private  static  int TIME_WAIT_SPLASH =3000;
    Animation topAnim ,bassAnim;
    ImageView image ,image2;
    private FirebaseAuth auth=FirebaseAuth.getInstance ();
    TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.spash_activity);
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_anim);
        bassAnim = AnimationUtils.loadAnimation(this,R.anim.bass_anim);
        image =findViewById(R.id.imageView2);
        image2 =findViewById(R.id.imageView);
        /*text = findViewById(R.id.textView);*/
        image.setAnimation(topAnim );
        image2.setAnimation(bassAnim);
        /*text.setAnimation(bassAnim);*/
        new Handler ().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        Intent intent;
                        if(auth.getCurrentUser ()==null || auth.getCurrentUser ().getUid ()==null|| firstStart ){
                            Log.i("usss","nul");
                            intent= new Intent( SpashScreenActivity.this,MainActivity.class );
                            SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putBoolean("firstStart", false);
                            editor.apply();

                        }
                        else {
                            Log.i("usss",auth.getCurrentUser ().getUid ());

                            intent= new Intent( SpashScreenActivity.this,MenuActivity.class );

                        }
                        startActivity(intent);
                        overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left);

                        finish();

                    }
                },TIME_WAIT_SPLASH);

    }
}