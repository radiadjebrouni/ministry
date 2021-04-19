package com.example.ministerymob.Market;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.ministerymob.MainScreen.MainActivity;
import com.example.ministerymob.R;

import java.util.List;


public class productDescriptionAcivity extends AppCompatActivity {

    private TextView name;
    private ImageView img;
    private ToggleButton fav;
    private boolean checked = false;


    private static RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    private LinearLayout adresse;
    private LinearLayout desc_audio;
    private LinearLayout signaler;
    private Button appeler;
    protected int MY_PERMISSIONS_REQUEST_CALL_PHONE=0;
    protected int MY_PERMISSIONS_REQUEST_SEND_SMS=1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.product_activity );

        name = (TextView) findViewById ( R.id.nom );
        adresse = findViewById ( R.id.click_afficher_map );
        desc_audio = findViewById ( R.id.click_audio_description );
        signaler = findViewById ( R.id.click_signaler );

        img = (ImageView) findViewById ( R.id.img_product );
        fav = findViewById ( R.id.click_fav );
        appeler = findViewById ( R.id.appeler );
        appeler.setBackgroundResource ( R.drawable.bouton_demander );
        // Recieve data
        Intent intent = getIntent ();
        String name = intent.getExtras ().getString ( "name" );
        String Description = intent.getExtras ().getString ( "Description" );
        String image = intent.getExtras ().getString ( "img" );

        img.setImageResource ( getResources ().getIdentifier ( image, "drawable", this.getPackageName () ) );

        adresse.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                /***********
                 * TODO SHOW ADRESS
                 */
                Toast.makeText ( productDescriptionAcivity.this, "Localisation map", Toast.LENGTH_SHORT ).show ();
            }
        } );


        desc_audio.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Toast.makeText ( productDescriptionAcivity.this, "Description Audio", Toast.LENGTH_SHORT ).show ();

                /*********************
                 * TODO DESC AUDIO
                 */

            }
        } );


        /********************************
         * enregistrer le service
         *******************************/

        fav.setOnCheckedChangeListener ( new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    fav.setBackgroundResource ( R.drawable.bouton_favorit_grand_actif );
                    checked = true;

                    /**********************
                     * TODO adding it to favotites table/collection
                     */
                }

                if (!isChecked) {
                    fav.setBackgroundResource ( R.drawable.bouton_favorit_grand_des );
                    checked = false;
                    /**********************
                     * TODO removing it from favotites table/collection
                     */
                }

            }
        } );


        if (checked) fav.setBackgroundResource ( R.drawable.bouton_favorit_grand_actif );
        if (!checked) fav.setBackgroundResource ( R.drawable.bouton_favorit_grand_des );


        /*******************************************
         * signaler
         *****************************************/
        signaler.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                //show(getSupportFragmentManager(), "confirmation");
                AlertDialog.Builder builder = new AlertDialog.Builder ( productDescriptionAcivity.this );
                builder.setTitle ( R.string.signaler )
                        .setMessage ( "Etes vous sure de vouloir signaler ce service?" )
                        .setPositiveButton ( "Oui", new DialogInterface.OnClickListener () {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                /***************************************
                                 *
                                 * TODO checking if the user's signal number on this product is 0, else incremente the number of
                                 * the product's signals which will be deleted after a limited number of signals
                                 */

                            }
                        } )
                        .setNegativeButton ( "Concel", new DialogInterface.OnClickListener () {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.dismiss ();
                            }
                        } );

                builder.show ();
            }
        } );


        /**********************************************
         * Contacter
         *******************************************/

        appeler.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                //show(getSupportFragmentManager(), "confirmation");
                AlertDialog.Builder builder = new AlertDialog.Builder ( productDescriptionAcivity.this );
                builder.setTitle ( R.string.Contacter )
                        .setNegativeButton ( "Appeler", new DialogInterface.OnClickListener () {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                /***********************************
                                 *
                                 * TODO replace number with the phone number of the service's owner
                                 */
                               callPhone("0897986866");
                            }
                        })
                        .setPositiveButton("Envoyer un SMS", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Log.i("Send SMS", "");

                                sendSMS("087654");
                            }
                        });


                      /*  .setNegativeButton ("Concel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.dismiss ();
                            }
                        });*/

                builder.show ();

            }
        } );

    }





        public void callPhone(String number){

            Intent callIntent = new Intent ( Intent.ACTION_CALL );
            callIntent.setData ( Uri.parse ( "tel:" + number ) );
            if (ActivityCompat.checkSelfPermission ( productDescriptionAcivity.this, Manifest.permission.CALL_PHONE ) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(productDescriptionAcivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
                return;
            }
            startActivity ( callIntent );
        }







    public void sendSMS(String num) {

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + Uri.encode("06446658787")));


            if (ActivityCompat.checkSelfPermission ( productDescriptionAcivity.this, Manifest.permission.SEND_SMS ) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions ( productDescriptionAcivity.this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS );
                return;
            }
            try {

                startActivity ( intent );
                finish ();
                Log.i ( "Finished sending SMS...", "" );
            } catch (ActivityNotFoundException ex) {
                Log.i ( "taaa", ex.getMessage () );
                Toast.makeText ( productDescriptionAcivity.this,
                        R.string.echec_sms, Toast.LENGTH_SHORT ).show ();
            }

        }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted, yay! Do the phone call

                /**
                 * TODO replaceing the number
                 */
                callPhone ( "087896689" );

            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }

            if (requestCode == MY_PERMISSIONS_REQUEST_SEND_SMS) {// If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    /**
                     * TODO replaceing the number
                     */
                    sendSMS ( "0978675" );
                    // permission was granted, yay! Do the phone call

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;

            // other 'case' lines to check for other
            // permissions this app might request
        }}}}

