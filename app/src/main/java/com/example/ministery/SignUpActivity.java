package com.example.ministery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.ministery.Model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import javax.security.auth.login.LoginException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    //les champs de texte (utilisation
    @BindView(R.id.text_field_name_insc) EditText nameEditText;
    @BindView(R.id.text_field_email_insc) EditText emailEditText;
    @BindView(R.id.text_field_pswd_insc) EditText passwordEditText;
    @BindView(R.id.text_field_confirm_pswrd) EditText passwordConfirmationEditText;
    @BindView(R.id.button_sign_in) Button signInButton;
    @BindView(R.id.google_sign_in) ImageButton googleImageButton;

    private final int RC_GOOGLE_SIGN_IN = 1;

    //firebase authentification
    private FirebaseAuth auth;

    //utilisateur firebase
    private FirebaseUser currentUser;

    //for google signe in
    GoogleSignInClient googleSignInClient;

    private  UserHelper uh =new UserHelper ();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.sign_up_activity );

        //relier les vues au variables
        ButterKnife.bind(this);

        //instanciation de l'object auth
        auth = FirebaseAuth.getInstance();

        signInButton.setOnClickListener(this);
        googleImageButton.setOnClickListener(this);


        //instanciate google sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this,gso);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_sign_in)
        {
            registration();
        }
        else if(v.getId() == R.id.google_sign_in)
        {
            googleSignIn();
        }
    }

    private void registration()
    {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
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

        //creation d'un utilisateur par email et mot de passe
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) //si la création est réussite
                        {   currentUser = auth.getCurrentUser();

                            assert currentUser != null;
                            if(currentUser.isEmailVerified()) //if the email is verified
                            {
                                //upload profile infos
                                //todo when the classes are ready
                                Toast.makeText(SignUpActivity.this, "User created", Toast.LENGTH_LONG).show();

                             //   User u =new User(auth.getCurrentUser ().getUid (),nameEditText.getText ().toString (),email);
                               // uh.insertUser ( u );
                                Intent i = new Intent ( SignUpActivity.this,MenuActivity.class );
                                startActivity (i);
                                overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left);


                            }
                            else
                            {
                                currentUser.sendEmailVerification(); //send verification link to his email
                                Toast.makeText(SignUpActivity.this, "Veuillez confirmer votre mail en vous connectant à celui-ci", Toast.LENGTH_LONG).show();

                                Intent i = new Intent ( SignUpActivity.this,LogInActivity.class );
                                i.putExtra ( "name",name );
                                startActivity (i);
                                overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left);
                                finish ();
                            }



                        }
                        else
                        {
                            Log.i("tttt",task.getException ().getMessage ());
                            Toast.makeText(SignUpActivity.this, "User creation failed ou bien existe déja", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    private void googleSignIn()
    {
        Intent googleSignInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(googleSignInIntent,RC_GOOGLE_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //retour du resultat du googlesignInIntent
        if(requestCode == RC_GOOGLE_SIGN_IN)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Toast.makeText(this, " succès", Toast.LENGTH_SHORT).show();
                firebaseAuthWithGoogle(account.getIdToken());
                Log.i ( "thhh", "token"+account.getIdToken());
                Log.i ( "thhh","id"+ account.getId ());

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, "Utilisateur non crée", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            currentUser = auth.getCurrentUser();

                            User u =new User(currentUser.getUid (),currentUser.getDisplayName (),currentUser.getEmail ());
                            uh.insertUser ( u );
                            Toast.makeText(SignUpActivity.this,"Sign in success"+ currentUser.toString(), Toast.LENGTH_SHORT).show();
                            Intent i = new Intent ( SignUpActivity.this, MenuActivity.class );
                            startActivity (i);
                            overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left);
                            finish ();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this, "Utilisateur non crée", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
