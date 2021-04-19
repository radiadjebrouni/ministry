package com.example.ministerymob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity{

    //les champs de texte (utilisation
    @BindView(R.id.text_field_name_insc) EditText nameEditText;
    @BindView(R.id.text_field_email_insc) EditText emailEditText;
    @BindView(R.id.text_field_phone_insc) EditText phoneEditText;
    @BindView(R.id.text_field_pswd_insc) EditText passwordEditText;
    @BindView(R.id.text_field_confirm_pswrd) EditText passwordConfirmationEditText;

    ProgressBar progressBar;
    Button signInButton;

    //firebase authentification
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.sign_up_activity );

        //relier les vues au variables
        ButterKnife.bind(this);

        //instanciation de l'object auth
        auth = FirebaseAuth.getInstance();

        signInButton = findViewById(R.id.button_sign_in);

        signInButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                registration();
            }
        } );

        overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left);

    }

    private void registration()
    {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String passwordConfirmation = passwordConfirmationEditText.getText().toString().trim();

        //controls on text fields

        if(name.isEmpty())
        {
            nameEditText.setError(getResources().getString(R.string.missing_name));
            nameEditText.requestFocus();
            return;
        }

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

        //if the phone number is empty or have less than 10 numbers
        if(phone.length()<10)
        {
            phoneEditText.setError(getResources().getString(R.string.missing_phone));
            phoneEditText.requestFocus();
            return;
        }

        if(password.isEmpty())
        {
            passwordEditText.setError(getResources().getString(R.string.missing_password));
            passwordEditText.requestFocus();
            return;
        }

        if(passwordConfirmation.isEmpty())
        {
            passwordConfirmationEditText.setError(getResources().getString(R.string.missing_password_confirmation));
            passwordConfirmationEditText.requestFocus();
            return;
        }

        //if password doesn't match confirmation
        if(!passwordConfirmation.equals(password))
        {
            passwordConfirmationEditText.setText("");
            passwordConfirmationEditText.setError(getResources().getString(R.string.mismatching_password));
            passwordConfirmationEditText.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        //creation d'un utilisateur par email et mot de passe
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) //si la création est réussite
                        {
                            progressBar.setVisibility(View.INVISIBLE);
                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            if(currentUser.isEmailVerified()) //if the email is verified
                            {
                                //upload profile infos
                                //to do when the classes are ready
                                Intent i = new Intent ( SignUpActivity.this,MenuActivity.class );
                                startActivity (i);
                            }
                            else
                            {
                                currentUser.sendEmailVerification(); //send verification link to his email
                                Toast.makeText(SignUpActivity.this, "Veuillez confirmer votre mail en vous connectant à celle-ci", Toast.LENGTH_LONG).show();
                                Intent i = new Intent ( SignUpActivity.this,LogInActivity.class );
                                startActivity (i);
                            }
                        }
                        else
                        {
                            Toast.makeText(SignUpActivity.this, "User creation failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
