package com.example.ministerymob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.text_field_email_forgot_password) EditText emailEditText;
    @BindView(R.id.button_forgot_password) Button resetButton;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //relier les vues
        ButterKnife.bind(this);

        resetButton.setOnClickListener(this);

        //instanciate firebase auth
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_forgot_password)
        {
            resetPassword();
        }
    }

    private void resetPassword()
    {
        String email = emailEditText.getText().toString().trim();

        if(email.isEmpty())
        {
            emailEditText.setError(getResources().getString(R.string.missing_email));
            emailEditText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailEditText.setError(getResources().getString(R.string.missing_email));
            emailEditText.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Intent intent = new Intent(ForgotPasswordActivity.this, LogInActivity.class);
                            startActivity(intent);
                            Toast.makeText(ForgotPasswordActivity.this, "Un email vous a été envoyé pour réinitialiser votre mot de passe", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(ForgotPasswordActivity.this, "Un email vous a été envoyé pour réinitialiser votre mot de passe", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}