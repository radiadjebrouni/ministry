package com.example.ministery.Market;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
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


import com.example.ministery.Fav.Enregistrement;
import com.example.ministery.MainScreen.ImageFirstScreen;
import com.example.ministery.MainScreen.ImageViewPager;
import com.example.ministery.MainScreen.MainActivity;
import com.example.ministery.MainScreen.MyPagerAdapter;
import com.example.ministery.Map.MapsActivity;
import com.example.ministery.Model.Key;
import com.example.ministery.Model.User;
import com.example.ministery.R;
import com.example.ministery.TexttoSpeech;
import com.example.ministery.UserHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.StringValue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;


public class productDescriptionAcivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";

    private static final int ERROR_DIALOG_REQUEST = 9001;
    private TextView nameu;
    private ImageView img;
    private ToggleButton fav;
    private static boolean signal =false;
    private boolean checked = false;


    private static RecyclerView recyclerView;
    private CircleImageView imgProfil;
    private LinearLayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    private LinearLayout adresse;
    private LinearLayout desc_audio;
    private LinearLayout enreg_date;
    private TextView date_enreg;
    private TextView date_cre;
    private TextView typee;
    private TextView descriptionn;
    private TextView adressei;
    private TextView nomp;
    private TextView emailp;
    private TextView phonep;
    private TextView pri;
    private LinearLayout signaler;
    private Button appeler;
    public Enregistrement enrg;
    protected int MY_PERMISSIONS_REQUEST_CALL_PHONE=0;
    protected int MY_PERMISSIONS_REQUEST_SEND_SMS=1;
    public FirebaseFirestore db = FirebaseFirestore.getInstance ();
    public CollectionReference notebookRef ;
    private ImageFirstScreen FIRST ;
    private ImageFirstScreen Second ;
    private ImageFirstScreen THIRD ;
    private  ImageFirstScreen[] images;
    private ViewPager mMyViewPager;
    private TabLayout mTabLayout;
    private product p;
    private FirebaseAuth auth= FirebaseAuth.getInstance ();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.product_activity );

        imgProfil=findViewById ( R.id.profile_img );
        nameu = (TextView) findViewById ( R.id.nom );
        adresse = findViewById ( R.id.click_afficher_map );
        desc_audio = findViewById ( R.id.click_audio_description );
        signaler = findViewById ( R.id.click_signaler );
        date_cre = findViewById ( R.id.date_creation );
        date_enreg = findViewById ( R.id.date_enreg );
        pri = findViewById ( R.id.prix );

        typee=findViewById ( R.id.type );
        descriptionn=findViewById ( R.id.description );
        adressei=findViewById ( R.id.adressei );
        nomp=findViewById ( R.id.nom_proprietaire );
        emailp=findViewById ( R.id.email_proprietaire );
        phonep=findViewById ( R.id.num_tel_proprietaire );

        img = (ImageView) findViewById ( R.id.img_product );
        fav = findViewById ( R.id.click_fav );
        appeler = findViewById ( R.id.appeler );
        appeler.setBackgroundResource ( R.drawable.bouton_demander );

      //  mTabLayout = findViewById(R.id.tab_layout_product);
       // mMyViewPager = findViewById(R.id.view_pager_product);

        //date
        enreg_date=findViewById ( R.id.enreg_lay );
        if(getIntent ().getExtras ().getString ( "date_enreg" )==null) enreg_date.setVisibility ( View.GONE );
        else {
            enreg_date.setVisibility ( View.VISIBLE );
             date_enreg.setText ( getIntent ().getExtras ().getString ( "date_enreg" ) );
        }
        date_cre.setText ( getIntent ().getExtras ().getString ( "date" ) );

        FIRST= new ImageFirstScreen ( R.drawable.image_acceuil0 );
        Second= new ImageFirstScreen ( R.drawable.image_acceuil1 );
        THIRD= new ImageFirstScreen ( R.drawable.image_acceuil2 );
        images = new ImageFirstScreen[]{FIRST, Second, THIRD};
//        init();
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
        String id = intent.getExtras ().getString ( "id" );
        String longitude = intent.getExtras ().getString ( "longitude" );
        String idp = intent.getExtras ().getString ( "idp" );  //id proprietaire
        int sign =intent.getExtras ().getInt ( "sign" );
        boolean issign =intent.getExtras ().getBoolean ( "issign" );


        Boolean favv =intent.getExtras ().getBoolean ( "fav" );


        String image = intent.getExtras ().getString ( "img" );

         p =new product (  );
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
        p.setNbSignal ( sign );
        /********todo add image ********/
         p.setImg (image);



        /*****************set data *************/
        nameu.setText ( name );
        typee.setText ( type );
        descriptionn.setText ( Description );
        adressei.setText ( adrsI );
        nomp.setText ( nomUser );
        emailp.setText ( emailUser );
        phonep.setText ( String.valueOf ( numTel ) );
        pri.setText ( prix );

        if(p.getImg ()==null||p.getImg ().equals ( "" ))  img.setImageResource ( getResources ().getIdentifier ( "ic_image_black_24dp","drawable", getPackageName ()) );

       else  new DownLoadImageTask(img).execute(p.getImg ());
    //        img.setImageResource ( getResources ().getIdentifier ( image, "drawable", this.getPackageName () ) );

        adresse.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                /***********
                 * TODO (DONE )SHOW ADRESS
                 */

                Toast.makeText ( productDescriptionAcivity.this, "Localisation map", Toast.LENGTH_SHORT ).show ();
                if(isServicesOK()){
                    initMap ();
                }

            }
        } );


        desc_audio.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Toast.makeText ( productDescriptionAcivity.this, "Description Audio", Toast.LENGTH_SHORT ).show ();

                /*********************
                 * TODO (DONE) DESC AUDIO
                 */
                String descripAudio= "Nom : "+ p.getName ()+"\n : Type :"+p.getType ()+
                        " : Prix : "+ p.getPrice ()+"\n  : Description : "+p.getDescription ()+
                        " : Adresse : "+ p.getAdresseInput ();

                TexttoSpeech tts =new TexttoSpeech ();
                tts.speak ( descripAudio,productDescriptionAcivity.this );

            }
        } );


        /********************************
         * enregistrer le service
         *******************************/
        if (favv) {fav.setBackgroundResource ( R.drawable.bouton_favorit_grand_actif ); checked=true;}
        if (!favv){ fav.setBackgroundResource ( R.drawable.bouton_favorit_grand_des ); checked=false;}



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
                   notebookRef = db.collection ( "users" ).document (auth.getCurrentUser ().getUid ()).collection ( "favorit_articles" );
                    Date Year = Calendar.getInstance().getTime ();
                    DateFormat dateFormat = new SimpleDateFormat ( "yyyy/MM/dd HH:mm" );

                    String strDate = dateFormat.format ( Year );

                   //  enrg.setIdEnregistreur (  Objects.requireNonNull ( FirebaseAuth.getInstance ().getCurrentUser () ).getUid() );
                     enrg =new Enregistrement ( p,strDate );
                    DocumentReference doc=notebookRef.document ();
                    String idd= doc.getId ();
                     enrg.setIdEnreg ( idd );
                     enrg.setIdd ( id );
                    doc.set ( enrg );





                }

                if (!isChecked) {
                    fav.setBackgroundResource ( R.drawable.bouton_favorit_grand_des );
                    checked = false;
                    /**********************
                     * TODO (DONE) removing it from favotites table/collection
                     */

                    UserHelper.deleteFavoriteFromUser ( auth.getCurrentUser ().getUid (), enrg.getIdEnreg () );

                }} });

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
                                if(issign ||signal)
                                {
                                    Toast.makeText ( productDescriptionAcivity.this, getString( R.string.vous_avez_deja_signaler), Toast.LENGTH_SHORT ).show ();
                                }
                                else {
                                    // modifier le nombre de signal du produit
                                    UserHelper.updateUsernSignal ( idp, id,sign+1);
                                    //ajouter lid de ce user a la liste des signaleurs de ce produits


                                   Task<DocumentSnapshot> gets=UserHelper.getSignaleursFromProduct ( idp,id );

                                   gets.addOnSuccessListener ( new OnSuccessListener<DocumentSnapshot> () {
                                       @Override
                                       public void onSuccess(DocumentSnapshot documentSnapshot) {

                                           ArrayList<String> list=gets.getResult ().toObject ( product.class ).getIsSignaleurs ();
                                           list.add ( auth.getCurrentUser ().getUid ()) ;

                                           //update la liste

                                           UserHelper.updateidSignaleur ( idp,id,list );
                                           signal=true;


                                       }
                                   } )
                                   .addOnFailureListener ( new OnFailureListener () {
                                       @Override
                                       public void onFailure(@NonNull Exception e) {
                                           Toast.makeText ( productDescriptionAcivity.this, getString( R.string.signal_echoue), Toast.LENGTH_SHORT ).show ();
                                       }
                                   } );




                                }



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
                                 * TODO (DONE )replace number with the phone number of the service's owner
                                 */
                                if (options[item].equals ( "Appeler" )) {
                                    callPhone ( String.valueOf ( p.getNumTel () ) );
                                }
                                    else {
                                    if (options[item].equals ( "Envoyer un SMS" )) {
                                        sendSMS (  String.valueOf ( p.getNumTel () ) );
                                    } else {
                                        if (options[item].equals ( "Envoyer un Email" )) {
                                            sendEmail ( v.getContext (), p.getEmailUser () );

                                        }
                                        else {
                                            if (options[item].equals ( "Viber Send" )) {
                                                addViberNumber ( v.getContext (), "213" +  String.valueOf ( p.getNumTel () ) );
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



        /*****set the profile pic****/

        Task<DocumentSnapshot> du = UserHelper.getUser (auth.getCurrentUser ().getUid () );
        du.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot> () {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

               User  u = du.getResult ().toObject ( User.class );

                Log.i ( "proo", u.getEmail () + "" );
                /*********************
                 * TODO (DONE )display the users main info
                 */
                if( u.getProfilePic ()!=null && !u.getProfilePic ().equals ( "" ))
                    new DownLoadImageTask (imgProfil).execute(u.getProfilePic ());

            }   }).addOnFailureListener ( new OnFailureListener () {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("proo",e.getMessage ());
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
        intent.setData(Uri.parse("smsto:" + Uri.encode(num)));


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
                 * TODO (DONE) replaceing the number
                 */
                callPhone (  String.valueOf ( p.getNumTel () ) );

            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }

            if (requestCode == MY_PERMISSIONS_REQUEST_SEND_SMS) {// If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    /**
                     * TODO (DONE) replaceing the number
                     */
                    sendSMS (  String.valueOf ( p.getNumTel () ) );
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
//        mMyViewPager.setAdapter(pagerAdapter);
  //      mTabLayout.setupWithViewPager(mMyViewPager, true);
    }



    private void initMap(){
     /*  Button btnMap = (Button) findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {*/

        Intent intent = new Intent( productDescriptionAcivity.this, MapsActivity.class);
        intent.putExtra ( "latitude",p.getAdresse ().latitude );
        intent.putExtra ( "longitude",p.getAdresse ().longititude );
        startActivity(intent);

    }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(productDescriptionAcivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(productDescriptionAcivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

                        }