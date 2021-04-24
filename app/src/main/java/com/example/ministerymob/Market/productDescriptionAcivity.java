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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.example.ministerymob.Fav.Enregistrement;
import com.example.ministerymob.MainScreen.ImageFirstScreen;
import com.example.ministerymob.MainScreen.ImageViewPager;
import com.example.ministerymob.MainScreen.MainActivity;
import com.example.ministerymob.MainScreen.MyPagerAdapter;
import com.example.ministerymob.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;


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
    public Enregistrement enrg;
    protected int MY_PERMISSIONS_REQUEST_CALL_PHONE=0;
    protected int MY_PERMISSIONS_REQUEST_SEND_SMS=1;
    public FirebaseFirestore db = FirebaseFirestore.getInstance ();
    public CollectionReference notebookRef = db.collection ( "Favorites" );
    private ImageFirstScreen FIRST ;
    private ImageFirstScreen Second ;
    private ImageFirstScreen THIRD ;
    private  ImageFirstScreen[] images;
    private ViewPager mMyViewPager;
    private TabLayout mTabLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.product_activity );

        name = (TextView) findViewById ( R.id.nom );
        adresse = findViewById ( R.id.click_afficher_map );
        desc_audio = findViewById ( R.id.click_audio_description );
        signaler = findViewById ( R.id.click_signaler );

       // img = (ImageView) findViewById ( R.id.img_product );
        fav = findViewById ( R.id.click_fav );
        appeler = findViewById ( R.id.appeler );
        appeler.setBackgroundResource ( R.drawable.bouton_demander );

        mTabLayout = findViewById(R.id.tab_layout_product);
        mMyViewPager = findViewById(R.id.view_pager_product);


        FIRST= new ImageFirstScreen ( R.drawable.image_acceuil0 );
        Second= new ImageFirstScreen ( R.drawable.image_acceuil1 );
        THIRD= new ImageFirstScreen ( R.drawable.image_acceuil2 );
        images = new ImageFirstScreen[]{FIRST, Second, THIRD};
        init();
        /****************Recieve data *********************/

        Intent intent = getIntent ();
        String name = intent.getExtras ().getString ( "name" );
        String Description = intent.getExtras ().getString ( "Description" );
        String adrsI = intent.getExtras ().getString ( "adrsI" );
        String nomUser = intent.getExtras ().getString ( "nomUser" );
        String emailUser = intent.getExtras ().getString ( "emailUser" );
        Long numTel = intent.getExtras ().getLong ( "numTel" );
        String date = intent.getExtras ().getString ( "date" );
        String type = intent.getExtras ().getString ( "type" );
        String prix = intent.getExtras ().getString ( "prix" );
        String latitude = intent.getExtras ().getString ( "latitude" );
        String longitude = intent.getExtras ().getString ( "longitude" );

        String image = intent.getExtras ().getString ( "img" );

        product p =new product (  );
        p.setAdresse ( new Adresse ( latitude,longitude ) );
        p.setAdresseInput ( adrsI );
        p.setName ( name );
        p.setDescription ( Description );
        p.setNomUser ( nomUser );
        p.setEmailUser ( emailUser );
        p.setNumTel ( numTel );
        p.setDate_creation ( date );
        p.setType ( type );
        p.setPrice ( prix );
        /********todo add image ********/
     //   p.setImg (  );


//        img.setImageResource ( getResources ().getIdentifier ( image, "drawable", this.getPackageName () ) );

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
                     *
                     * TODO (DONE)  adding it to favotites table/collection
                     */

                    // enregistrer date
                    Date Year = Calendar.getInstance().getTime ();
                    DateFormat dateFormat = new SimpleDateFormat ( "yyyy/MM/dd HH:mm" );

                    String strDate = dateFormat.format ( Year );

                    // todo set the user's id
                   //  enrg.setIdEnregistreur (  Objects.requireNonNull ( FirebaseAuth.getInstance ().getCurrentUser () ).getUid() );
                     enrg =new Enregistrement ( p,strDate );
                    DocumentReference doc=notebookRef.document ();
                    String id= doc.getId ();
                    enrg.setIdEnreg ( id );
                    doc.set ( enrg );



                }

                if (!isChecked) {
                    fav.setBackgroundResource ( R.drawable.bouton_favorit_grand_des );
                    checked = false;
                    /**********************
                     * TODO (DONE) removing it from favotites table/collection
                     */
                    notebookRef.document (enrg.getIdEnreg ())
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void> () {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("fav", "DocumentSnapshot successfully deleted!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener () {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("fav", "Error deleting document", e);
                                }
                            });
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

                final CharSequence[] options = { "Appeler", "Envoyer un SMS","Envoyer un Email","Viber Send" };
                //show(getSupportFragmentManager(), "confirmation");
                AlertDialog.Builder builder = new AlertDialog.Builder ( productDescriptionAcivity.this );
                builder.setTitle ( R.string.Contacter )
                        .setItems ( options, new DialogInterface.OnClickListener () {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int item) {

                                /***********************************
                                 *
                                 * TODO replace number with the phone number of the service's owner
                                 */
                                if (options[item].equals ( "Appeler" )) {
                                    callPhone ( "0897986866" );
                                }
                                    else {
                                    if (options[item].equals ( "Envoyer un SMS" )) {
                                        sendSMS ( "087654" );
                                    } else {
                                        if (options[item].equals ( "Envoyer un Email" )) {
                                            sendEmail ( v.getContext (), "radiadj2000@gmail.com" );

                                        }
                                        else {
                                            if (options[item].equals ( "Viber Send" )) {
                                                addViberNumber ( v.getContext (), "213" + "555555550" );
                                            }
                                        }}}}

                                })

                        .setNegativeButton ("Concel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.dismiss ();
                            }
                        });

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
        }}}

    public void addViberNumber(Context context,String phone) {
        String viberPackageName = "com.viber.voip";

        try {
            context.startActivity(new
                            Intent(Intent.ACTION_VIEW,
                            Uri.parse("viber://add?number="+phone)
                    )
            );
        } catch (ActivityNotFoundException ex) {
            try {
                context.startActivity
                        (new Intent(Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=+" + viberPackageName))
                        );
            } catch (ActivityNotFoundException exe) {
                context.startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=" + viberPackageName)
                        )
                );
            }
        }
    }

    public void sendEmail(Context context, String email)
    {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
            "mailto",email, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "EXTRA_SUBJECT");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }


    private void init(){
        ArrayList<Fragment> fragments = new ArrayList<> ();
        ImageFirstScreen[] hats = images;
        for(ImageFirstScreen hat: hats){
            ImageViewPager fragment = ImageViewPager.getInstance(hat);
            fragments.add(fragment);
        }
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
        mMyViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mMyViewPager, true);
    }



                        }