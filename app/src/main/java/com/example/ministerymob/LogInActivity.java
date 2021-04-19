package com.example.ministerymob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ministerymob.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.text_field_email_cnxn) EditText emailEditText;
    @BindView(R.id.text_field_pswd_cnxn) EditText passwordEditText;
    @BindView(R.id.button_log_in) Button logInButton;
    
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_log_in);

        //relier les vues au variables
        ButterKnife.bind(this);

        logInButton.setOnClickListener(this);
        
        //intanciate firebase auth
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_log_in)
        {
            logIn();
        }
    }

    private void logIn()
    {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        //controls on text fields
        if(email.isEmpty())
        {
            emailEditText.setError(getResources().getString(R.string.missing_email));
            emailEditText.requestFocus();
            return;
        }

        //if the string doesn't match the email form
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailEditText.setError(getResources().getString(R.string.missing_email));
            emailEditText.requestFocus();
            return;
        }

        if(password.isEmpty())
        {
            passwordEditText.setError(getResources().getString(R.string.missing_password));
            passwordEditText.requestFocus();
            return;
        }

        //if all the fields are good
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            //send to the profile activity
                            //to do when the classes are ready
                            Intent i = new Intent( LogInActivity.this, MenuActivity.class );
                            startActivity (i);
                        }
                        else
                        {
                            Toast.makeText(LogInActivity.this, "LogIn failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}