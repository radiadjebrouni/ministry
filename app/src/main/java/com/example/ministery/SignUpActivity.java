package com.example.ministery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.sign_up_activity );

        Button signUp= findViewById ( R.id.button_sign_in);


        /*******************
         * TODO  authentification
         */
        signUp.setOnClickListener (( new View.OnClickListener () {

            @Override
            public void onClick(View v) {
                Intent menu= new Intent (getApplicationContext (), MenuActivity.class);
                startActivity ( menu );

            }
        } ));
    }
}
