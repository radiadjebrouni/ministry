package com.example.ministerymob;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.ministerymob.MainScreen.MainActivity;

public class SpashScreenActivity extends AppCompatActivity {

    private  static  int TIME_WAIT_SPLASH =2800;
    Animation topAnim ,bassAnim;
    ImageView image ,image2;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.spash_activity);
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
                        Intent intent = new Intent( SpashScreenActivity.this,MainActivity.class );
                        startActivity(intent);
                        finish();

                    }
                },TIME_WAIT_SPLASH);

    }
}