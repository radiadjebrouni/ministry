package com.example.ministerymob;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ministerymob.R;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.text_field_email_cnxn) EditText emailEditText;
    @BindView(R.id.text_field_pswd_cnxn) EditText passwordEditText;
    @BindView(R.id.button_log_in) Button logInButton;
    @BindView(R.id.activity_logIn_forgotten_password) TextView forgottenPassword;
    @BindView(R.id.google_log_in) ImageButton googleImageButton;

    private final int RC_GOOGLE_SIGN_IN = 1;

    //firebase authentification
    private FirebaseAuth auth;

    //utilisateur firebase
    private FirebaseUser currentUser;

    //for google signe in
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_log_in);

        //relier les vues au variables
        ButterKnife.bind(this);

        logInButton.setOnClickListener(this);
        forgottenPassword.setOnClickListener(this);
        googleImageButton.setOnClickListener(this);
        
        //intanciate firebase auth
        auth = FirebaseAuth.getInstance();

        //instanciate google sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this,gso);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_log_in)
        {
            //execute loging
            logIn();
        }
        else if(v.getId() == R.id.activity_logIn_forgotten_password)
        {
            //redirect to forgot password
            Intent intent = new Intent(LogInActivity.this,ForgotPasswordActivity.class);
            startActivity(intent);
            finish();
        }
        else if(v.getId() == R.id.google_log_in)
        {
            googleSignIn();
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
                            //get profile infos
                            //to do when the classes are ready
                            Toast.makeText(LogInActivity.this, "LogIn failed", Toast.LENGTH_SHORT).show();
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

    private void googleSignIn()
    {
        Intent googleSignInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(googleSignInIntent,RC_GOOGLE_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        //retour du resultat du googlesignInIntent
        if(requestCode == RC_GOOGLE_SIGN_IN)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Toast.makeText(this, "Utilisateur crée avec succès", Toast.LENGTH_SHORT).show();
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, "Utilisateur non crée", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void firebaseAuthWithGoogle(String idToken)
    {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            currentUser = auth.getCurrentUser();
                            Toast.makeText(LogInActivity.this, currentUser.toString(), Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LogInActivity.this, "Utilisateur non crée", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}