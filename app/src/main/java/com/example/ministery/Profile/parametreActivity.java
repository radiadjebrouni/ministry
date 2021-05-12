package com.example.ministery.Profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ministery.R;
import com.example.ministery.UserHelper;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class parametreActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private UserHelper uh;
    public static boolean langPref=true;
    public static SharedPreferences prefs ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.parametre_activity );


           prefs= getSharedPreferences("prefs", MODE_PRIVATE);

         langPref = prefs.getBoolean("fr", true);


         Button fr=findViewById ( R.id.btn_fr );
         Button ar=findViewById ( R.id.btn_ar );
        RelativeLayout lang_rel = findViewById ( R.id.rel_lang );
        lang_rel.setVisibility ( View.GONE );
        RelativeLayout nom_rel = findViewById ( R.id.rel_nom );
        nom_rel.setVisibility ( View.GONE );
        Button mod = findViewById ( R.id.modif_nom_btn );

        RelativeLayout info_rel = findViewById ( R.id.rel_info );
        info_rel.setVisibility ( View.GONE );


        fr.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Toast.makeText ( parametreActivity.this, "change lang", Toast.LENGTH_SHORT ).show ();
               // LocaleHelper.setLocale (parametreActivity.this, "ar" );
               changeLang ( parametreActivity.this,"fr" );
                recreate ();

                SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("fr", true);
                editor.apply();
            }
        } );

        ar.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Toast.makeText ( parametreActivity.this, "change lang", Toast.LENGTH_SHORT ).show ();
                // LocaleHelper.setLocale (parametreActivity.this, "ar" );
                changeLang ( parametreActivity.this,"ar" );
                recreate ();

                SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("fr", false);
                editor.apply();
            }
        } );





        ImageView lang_arrow = findViewById ( R.id.arrow_lang );
        ImageView nom_arrow = findViewById ( R.id.arrow_nom );
        ImageView info_arrow = findViewById ( R.id.arrow_info );

        TextView deconnect = findViewById ( R.id.dconnecter );
        TextView suppCompte = findViewById ( R.id.supp_compte );
        EditText nom_modif = findViewById ( R.id.modif_nom_edt );

        info_arrow.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                openView ( info_arrow, info_rel );
            }
        } );

        lang_arrow.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                openView ( lang_arrow, lang_rel );
            }
        } );

        nom_arrow.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                openView ( nom_arrow, nom_rel );
            }
        } );


        mod.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String newNom = nom_modif.getText ().toString ();


                /************TODO (DONE) modefy the username******/

                auth = FirebaseAuth.getInstance ();
                UserHelper.updateUserUsername ( auth.getUid (), newNom );


            }
        } );

        deconnect.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder ( parametreActivity.this );
                builder.setMessage ( "Etes vous sur de vouloir se déconnecter?" )
                        .setNegativeButton ( "Concel", new DialogInterface.OnClickListener () {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss ();
                            }
                        } ).setPositiveButton ( "Oui", new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /************TODO se deconnecter *************/
                    }
                } );


                builder.show ();

            }
        } );


        suppCompte.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder ( parametreActivity.this );
                builder.setMessage ( "Etes vous sur de vouloir supprimer votre compte?" )
                        .setNegativeButton ( "Concel", new DialogInterface.OnClickListener () {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss ();
                            }
                        } ).setPositiveButton ( "Oui", new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /************TODO ( DONE)supprimer le compte *************/

                        UserHelper.deleteUser ( auth.getCurrentUser ().getUid () );
                    }
                } );


                builder.show ();

            }
        } );


    }


    private void openView(ImageView i, RelativeLayout r) {

        if (r.getVisibility () == View.GONE) {
            TransitionManager.beginDelayedTransition ( r, new AutoTransition () );
            i.setImageResource ( R.drawable.ic_keyboard_arrow_up_black_24dp );
            r.setVisibility ( View.VISIBLE );
        } else {
            TransitionManager.beginDelayedTransition ( r, new AutoTransition () );
            i.setImageResource ( R.drawable.ic_keyboard_arrow_down_black_24dp );
            r.setVisibility ( View.GONE );
        }


    }

    public static void changeLang(Context context, String lang) {
        Locale myLocale = new Locale ( lang );
        Locale.setDefault ( myLocale );
        android.content.res.Configuration config = new android.content.res.Configuration ();
        config.locale = myLocale;
        context.getResources ().updateConfiguration ( config, context.getResources ().getDisplayMetrics () );

    }
}