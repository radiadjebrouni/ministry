package com.example.ministery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity{

    //les champs de texte (utilisation
    @BindView(R.id.text_field_name_insc) EditText nameEditText;
    @BindView(R.id.text_field_email_insc) EditText emailEditText;
    @BindView(R.id.text_field_phone_insc) EditText phoneEditText;
    @BindView(R.id.text_field_pswd_insc) EditText passwordEditText;
    @BindView(R.id.text_field_confirm_pswrd) EditText passwordConfirmationEditText;

    Button signInButton;

    //firebase authentification
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.sign_up_activity );

        //instanciation de l'object auth
        auth = FirebaseAuth.getInstance();

        signInButton = findViewById(R.id.button_sign_in);

        signInButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                registration();
            }
        } );

        ButterKnife.bind(this);

    }

    private void registration()
    {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String passwordConfirmation = passwordConfirmationEditText.getText().toString().trim();

        //controles sur les champs de texte

        if(email.isEmpty())
        {
            emailEditText.setError("Entrez un email valide");
            emailEditText.requestFocus();
            return;
        }

        if(password.isEmpty())
        {
            passwordEditText.setError("Entrez un mot de passe");
            passwordEditText.requestFocus();
            return;
        }

        //creation d'un utilisateur par email et mot de passe
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) //si la création est réussite
                        {
                            Toast.makeText(SignUpActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(SignUpActivity.this, "User creation failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
