package com.example.ministery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_sign_up );

        Button signUp= findViewById ( R.id.sign_up );

        signUp.setOnClickListener (( new View.OnClickListener () {

            @Override
            public void onClick(View v) {

                Intent menu= new Intent (getApplicationContext (), MenuActivity.class);
                startActivity ( menu );

            }
        } ));
    }
}
