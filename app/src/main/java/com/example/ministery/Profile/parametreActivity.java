package com.example.ministery.Profile;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ministery.R;
import com.example.ministery.UserHelper;
import com.google.firebase.auth.FirebaseAuth;

public class parametreActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private UserHelper uh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.parametre_activity );

        RelativeLayout lang_rel = findViewById ( R.id.rel_lang );
                lang_rel.setVisibility ( View.GONE );
        RelativeLayout nom_rel = findViewById ( R.id.rel_nom );
        nom_rel.setVisibility ( View.GONE );

        RelativeLayout info_rel = findViewById ( R.id.rel_info );
        info_rel.setVisibility ( View.GONE );



        ImageView lang_arrow = findViewById ( R.id.arrow_lang );
        ImageView nom_arrow = findViewById ( R.id.arrow_nom );
        ImageView info_arrow = findViewById ( R.id.arrow_info );

        TextView deconnect = findViewById ( R.id.dconnecter );
        TextView suppCompte = findViewById ( R.id.supp_compte );
        EditText nom_modif = findViewById ( R.id.modif_nom_edt );

        info_arrow.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
             openView ( info_arrow,info_rel );
            }
        } );

        lang_arrow.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
             openView ( lang_arrow,lang_rel );
            }
        } );

        nom_arrow.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
            openView ( nom_arrow,nom_rel );
            }
        } );


        String newNom= nom_modif.getText ().toString ();


        /************TODO (DONE) modefy the username******/
        auth = FirebaseAuth.getInstance();
        UserHelper.updateUserUsername ( auth.getUid (),newNom );





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
}
