package com.example.ministery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ministery.Model.User;
import com.example.ministery.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;

import javax.security.auth.login.LoginException;

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

    private  UserHelper uh =new UserHelper ();



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

        if(auth.isSignInWithEmailLink ( email )){
            Log.i("siiii","yes");
            auth.signOut ();
        }else             Log.i("siiii","no");

        // if(auth.getCurrentUser ()!=null && auth. ( auth.getCurrentUser ().getEmail () ))  auth.getCurrentUser ().unlink (  );


        //if all the fields are good
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            if(auth.getCurrentUser ().isEmailVerified ()){
                            if(getIntent ().getExtras ()!=null && getIntent ().getExtras ().getString ( "name" )!=null)
                            {
                                String name= getIntent ().getExtras ().getString ( "name" );
                                User u =new User(auth.getCurrentUser ().getUid (),name,email);
                                uh.insertUser ( u );

                            }

                            //get profile infos
                            //to do when the classes are ready
                            Log.i("authh",auth.getCurrentUser ().getEmail ()+"");
                            Toast.makeText(LogInActivity.this, "LogIn success", Toast.LENGTH_LONG).show();
                            Intent i = new Intent( LogInActivity.this, MenuActivity.class );
                            i.putExtra ( "id", auth.getCurrentUser ().getUid ());
                            startActivity (i);
                            finish ();
                        }
                        else Toast.makeText(LogInActivity.this, "Veuillez confirmer votre email", Toast.LENGTH_LONG).show();
                        }
                        else
                        { //  Log.i("logauth",auth.getCurrentUser ().getEmail ()+"");

                            Toast.makeText(LogInActivity.this, "LogIn failed", Toast.LENGTH_LONG).show();
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
            Log.i("loooog","before try");
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Toast.makeText(LogInActivity.this, "Sign in success", Toast.LENGTH_SHORT).show();

                firebaseAuthWithGoogle(account.getIdToken(),account.getEmail ());
                Log.i("loooog","after try "+account.getIdToken());


            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(LogInActivity.this, "Sign in failed", Toast.LENGTH_SHORT).show();
                Log.i("loooog","fail try");

            }

        }
    }

    private void firebaseAuthWithGoogle(String idToken,String email) {
        AuthCredential credential = GoogleAuthProvider.getCredential ( idToken, null );


      //  auth = FirebaseAuth.getInstance ();

        Log.i ( "loooog", "before auth " + email );



        Log.i ( "loooog", "else ");

        auth.signInWithCredential ( credential )

                .addOnCompleteListener ( new OnCompleteListener<AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.i ( "loooog", "before success " );

                        if (task.isSuccessful ()) {
                            Log.i ( "loooog", "auth try success" );

                            // Sign in success, update UI with the signed-in user's information
                            currentUser = auth.getCurrentUser ();
                            Log.i ( "loooog", "auth try getuser "+ currentUser.getEmail () );

                            Toast.makeText ( LogInActivity.this, currentUser.toString (), Toast.LENGTH_SHORT ).show ();
                            Intent i = new Intent ( LogInActivity.this, MenuActivity.class );
                            startActivity (i);
                            overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left);
                            finish ();
                        } else {
                            Log.i ( "loooog", "auth failed" );

                            // If sign in fails, display a message to the user.
                            Toast.makeText ( LogInActivity.this, "Utilisateur non crée", Toast.LENGTH_SHORT ).show ();
                        }
                    }
                } );

    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null){
            auth.signOut ();
        }
    }
}