package com.example.ministery.Market;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ministery.MainScreen.ImageFirstScreen;
import com.example.ministery.MainScreen.ImageViewPager;
import com.example.ministery.MainScreen.MainActivity;
import com.example.ministery.MainScreen.MyPagerAdapter;
import com.example.ministery.Map.MapsActivity;
import com.example.ministery.R;
import com.example.ministery.UserHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AjouterProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG ="gpss" ;
    public Button entrer_photo;
    public ImageView img_product;
    public Button confirmer;
    public Button entrerAdresse;
    public EditText nom_service;
    public EditText prix;
    public EditText numTel;
    public TextView gps;
    public static String type;                // ou bien il selectionne un type
    public EditText description;
    public Adresse adresse;
    public EditText adresseInput;
    public String longi="";
    public String lat="";
    public product p;


    public String nom;
    public  String img="";
    public String desc;
    public String pri;
    public Long num;
    public String adrsI;


    private static final int REQUEST_GALLERY = 0;
    private static final int REQUEST_CAMERA = 1;
    private static final int MY_PERMISSIONS_REQUESTS = 0;
    private static final int RC_HANDLE_CAMERA_PERM = 2;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    public static Uri imageUri;
    public FirebaseFirestore db = FirebaseFirestore.getInstance ();
    public CollectionReference notebookRef ;
    public CollectionReference uploads=db.collection ( "uploads" ) ;
    private passData listener= new passData () {
        @Override
        public void apply(FirebaseFirestore db) {

        }
    };


    private Boolean mLocationPermissionsGranted=false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private ImageFirstScreen FIRST ;
    private ImageFirstScreen Second ;
    private ImageFirstScreen THIRD ;
    private  ImageFirstScreen[] images;
    private ViewPager mMyViewPager;
    private TabLayout mTabLayout;
    private UserHelper uh;
    private static Uri imagedata;


    private StorageReference mStorageRef;
    private StorageTask mUploadTask;
    private FirebaseAuth auth= FirebaseAuth.getInstance ();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_ajouter_product );

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
         String uid= auth.getCurrentUser ().getUid ();
        notebookRef= db.collection ( "users").document (uid).collection ( "created_articles" );
        img_product = findViewById ( R.id.img_product );
        nom_service = findViewById ( R.id.entrer_nom );
        prix = findViewById ( R.id.entrer_prix );
        numTel = findViewById ( R.id.entrer_numTEL );
        description = findViewById ( R.id.entrer_desc );
        adresseInput=findViewById ( R.id.entrer_adresseInput );
        gps=findViewById ( R.id.adr_gps );


       entrer_photo = findViewById ( R.id.entrer_photo );
        entrerAdresse = findViewById ( R.id.entrer_adresse );



     //   mTabLayout = findViewById(R.id.tab_layout_Aproduct);
        //mMyViewPager = findViewById(R.id.view_pager_Aproduct);


        FIRST= new ImageFirstScreen ( R.drawable.image_acceuil0 );
        Second= new ImageFirstScreen ( R.drawable.image_acceuil1 );
        THIRD= new ImageFirstScreen ( R.drawable.image_acceuil2 );
        images = new ImageFirstScreen[]{FIRST, Second, THIRD};
       // init();




        entrer_photo.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                selectImage ( AjouterProductActivity.this );
            }
        } );
        confirmer = findViewById ( R.id.confirmer_ajout );
        confirmer.setBackgroundResource ( R.drawable.bouton_commencer );
        confirmer.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {


                /***********************
                 *
                 * TODO (DONE)
                 * ADDING DATA TO THE DB
                 */

                nom = nom_service.getText ().toString ();
                desc = description.getText ().toString ();
                pri = prix.getText ().toString ();
                if(!numTel.getText ().toString ().equals ( "" ))
                num = Long.valueOf ( numTel.getText ().toString () );
                FirebaseUser user = FirebaseAuth.getInstance ().getCurrentUser ();
                String nomUser=user.getDisplayName ();
                 String emailUer=user.getEmail ();
                adresse = new Adresse ( lat, longi);
                adrsI=adresseInput.getText ().toString ();



                Log.i ( "imgg",img+" iii" );
                p = new product ( nom, type, desc, pri, nomUser, emailUer, num, adresse,img,"" );
                p.setOffered ( getIntent ().getExtras ().getInt ( "offered" ) );


                // add date
                Date Year = Calendar.getInstance().getTime ();
                DateFormat dateFormat = new SimpleDateFormat ( "yyyy/MM/dd HH:mm" );

                String strDate = dateFormat.format ( Year );
                p.setDate_creation ( strDate );
                p.setIdProprietaire ( auth.getCurrentUser ().getUid () );

                p.setAdresseInput ( adrsI );

               // notebookRef.add(p);
              Log.i ( "moddd",getIntent ().getExtras ().getString ( "modif" )+"  " );
                if((nom.equals ("") || desc.equals ("") || pri.equals ("") ||num==null|| adrsI.equals ("")) && getIntent ().getExtras ().getString ( "modif" )==null)
                {
                    Toast.makeText ( AjouterProductActivity.this, getString( R.string.remplir_tt_leschamps), Toast.LENGTH_LONG ).show ();
                }

                else {if(getIntent ().getExtras ().getString ( "modif" )==null) // add new product
                {
                    DocumentReference doc=notebookRef.document ();
                    String id= doc.getId ();

                    Log.i ( "iddd", id );
                    HashMap<String, product> newp = new HashMap<> ();
                    p.setIdd ( id );
                    newp.put ( id, p );
                    doc.set ( p );

                    /******pass data***/

                    listener.apply ( db );

                    finish ();


                    //url
                }
                else {
                    /****************Recieve data *********************/

                    auth = FirebaseAuth.getInstance();
                    Intent intent = getIntent ();
                    String name = intent.getExtras ().getString ( "name" );
                    String Description = intent.getExtras ().getString ( "Description" );
                    String adrsi = intent.getExtras ().getString ( "adrsI" );
                    String nomUse = intent.getExtras ().getString ( "nomUser" );
                    String emailUser = intent.getExtras ().getString ( "emailUser" );
                    Long numTel = intent.getExtras ().getLong ( "numTel" );
                    String date = intent.getExtras ().getString ( "date" );
                    String type = intent.getExtras ().getString ( "type" );
                    String prixx = intent.getExtras ().getString ( "prix" );
                    String latitude = intent.getExtras ().getString ( "latitude" );
                    String longitude = intent.getExtras ().getString ( "longitude" );
                    String idd = intent.getExtras ().getString ( "id" );
                    int sign = intent.getExtras ().getInt ( "sign" );
                    int off = intent.getExtras ().getInt ( "off" );


                    Log.i("moddd","id "+idd+" nom "+name+" type "+prixx);
                    String image = intent.getExtras ().getString ( "img" );
                    Log.i("immg",image+"jjj");
                    product pm=new product (  );
                    pm.setIdd ( idd );
                    if(p.getName ()==null||p.getName ().equals ( "" )) pm.setName ( name ); else pm.setName ( p.getName () );
                    if(p.getDescription ()==null||p.getDescription ().equals ( "" )) pm.setDescription ( Description );else pm.setDescription ( p.getDescription () );
                    if(p.getAdresseInput ()==null||p.getAdresseInput ().equals ( "" )) pm.setAdresseInput ( adrsi ); else pm.setAdresseInput ( p.getAdresseInput () );
                    if(p.getNomUser ()==null||p.getNomUser ().equals ( "" )) pm.setAdresseInput ( nomUse );else pm.setNomUser ( p.getNomUser () );
                    if(p.getNumTel ()==null) pm.setNumTel (Long.valueOf ( numTel) );else pm.setNumTel ( p.getNumTel () );
                    if(p.getType ()==null||p.getType ().equals ( "" )) pm.setType (type );else pm.setType ( p.getType () );
                    if(p.getPrice ()==null||p.getPrice ().equals ( "" )) pm.setPrice (prixx );else pm.setPrice ( p.getPrice () );
                    if(p.getAdresse ()==null||p.getAdresse ().latitude.equals ( "" )) pm.setAdresse ( new Adresse ( latitude,longitude ) );
                    else pm.setAdresse ( p.getAdresse () );
                    if(img==null||p.getImg ().equals ( "" )) pm.setImg ( image ); else pm.setImg ( img );

                    pm.setNbSignal ( sign );
                    pm.setOffered (  off  );
                   pm.setDate_creation ( p.getDate_creation () );
                    pm.setIdProprietaire ( auth.getCurrentUser ().getUid () );


                    pm.setEmailUser ( auth.getCurrentUser ().getEmail () );


                    //modefy the product


                    Log.i("immgg","id "+idd+" nom "+img);
                    DocumentReference doc=notebookRef.document (pm.getIdd ());
                 //   UserHelper.updateArtcle ( pm.getIdd (),auth.getCurrentUser ().getUid (),pm );
                    doc.set ( pm );
                    finish ();

                }
                }

            }
        } );

        entrerAdresse.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                getLocationPermission();
                showMapChoices ();
            }
        } );

        /***********************
         *
         * Filtering type
         *********************** */
        Spinner spinner = findViewById ( R.id.spinnerAjout );
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource ( this,
                R.array.types_handicapes, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );
        spinner.setAdapter ( adapter );
        spinner.setOnItemSelectedListener ( AjouterProductActivity.this );

        /********************/


    }


    /**********************
     *
     * get the image
     *
     ******************/


    private void selectImage(final Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder ( AjouterProductActivity.this );
        builder.setTitle ( "Add Photo!" );
        builder.setItems ( options, new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals ( "Take Photo" )) {

                    requestPermissions ();
                    if ((ActivityCompat.checkSelfPermission ( getApplicationContext (), Manifest.permission.CAMERA ) == PackageManager.PERMISSION_GRANTED)
                            && ActivityCompat.checkSelfPermission ( getApplicationContext (), Manifest.permission.WRITE_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED) {

                        String filename = System.currentTimeMillis () + ".jpg";

                        ContentValues values = new ContentValues ();
                        values.put ( MediaStore.Images.Media.TITLE, filename );
                        values.put ( MediaStore.Images.Media.MIME_TYPE, "image/jpeg" );


                        try{
                            imageUri = getContentResolver ().insert ( MediaStore.Images.Media.INTERNAL_CONTENT_URI, values );
                        }catch (Exception e){
                            Toast.makeText ( context, "Writing to internal storage is not supported", Toast.LENGTH_LONG ).show ();
                          //  Toast.makeText ( AjouterProductActivity.this, "vous n'avez pas une carte SD,Veuillez choisir une photo dans votre gallerie", Toast.LENGTH_LONG ).show ();


                        }

                        Intent intent = new Intent ();
                        intent.setAction ( MediaStore.ACTION_IMAGE_CAPTURE );
                        intent.putExtra ( MediaStore.EXTRA_OUTPUT, imageUri );
                        startActivityForResult ( intent, REQUEST_CAMERA );

                    } else askCameraPermission ();


                } else if (options[item].equals ( "Choose from Gallery" )) {
/*
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                    */

                    Intent intent = new Intent ();
                    intent.setType ( "image/*" );
                    intent.setAction ( Intent.ACTION_GET_CONTENT );
                    startActivityForResult ( intent, REQUEST_GALLERY );

                } else if (options[item].equals ( "Cancel" )) {
                    dialog.dismiss ();
                }
            }
        } );
        builder.show ();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult ( requestCode, resultCode, data );
        switch (requestCode) {
            case REQUEST_GALLERY:
                if (resultCode == RESULT_OK) {
                    img_product.setImageURI ( null );
                    img_product.setImageURI ( data.getData () );
                    imagedata=data.getData ();
                }
                break;
            case REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {
                    Log.i ( "taaaag", "uriiii" );
                    img_product.setImageURI ( null );
                    if (imageUri == null)
                        Toast.makeText ( this, "Erreur de permission (vous n'avez pas une carte SD),Veuillez choisir une photo dans votre gallerie", Toast.LENGTH_LONG ).show ();
                    img_product.setImageURI ( imageUri );
                    imagedata=imageUri;

                }
                break;

            /*********************
             * todo (done ) add the image uri the new product data
             */

        }  uploadFile();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map

                }
            }
        }
    }

    private void showMapChoices() {

        final CharSequence[] options = {"Detecter par GPS", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder ( AjouterProductActivity.this );
        // builder.setTitle("Add Photo!");
        builder.setItems ( options, new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals ( "Detecter par GPS" )) {

                    /******************************
                     * TODO (DONE) map func
                     */
                    getDeviceLocation();


                } else if (options[item].equals ( "Cancel" )) {
                    dialog.dismiss ();
                }
            }
        } );
        builder.show ();
    }


    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();

                location.addOnCompleteListener(new OnCompleteListener<Location> (){
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.i(TAG, "onComplete: found location!");
                            if(task.getResult ()!=null) {
                                Location currentLocation = (Location) task.getResult ();
                                Log.i ( TAG, "onComplete: " + currentLocation.getLatitude () + currentLocation.getLongitude () );
                                lat= String.valueOf ( currentLocation.getLatitude () );
                                longi=String.valueOf ( currentLocation.getLongitude ());



                                /***************** set the adress to the text view *****/
                                Geocoder geocoder;
                                List<Address> addresses;
                                geocoder = new Geocoder(AjouterProductActivity.this, Locale.getDefault());

                                try {
                                    addresses = geocoder.getFromLocation(currentLocation.getLatitude (), currentLocation.getLongitude (), 1);
                                    String address ="Adresse : "+ addresses.get(0).getAddressLine(0)
                                            + "\n cité : "+ addresses.get(0).getLocality()
                                                  +"\n Pays : "+ addresses.get(0).getCountryName();
                                    String state = addresses.get(0).getAdminArea();
                                    String country = addresses.get(0).getCountryName();
                                    String postalCode = addresses.get(0).getPostalCode();
                                    String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
                                    Log.i(TAG, address);

                                      gps.setText ( address );                                                    // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                                } catch (IOException e) {
                                    e.printStackTrace ();
                                }

                                  }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder ( AjouterProductActivity.this );
                                builder.setMessage ( getString( R.string.localisation_errur_dialog) )
                                        .setNegativeButton ( "OK", new DialogInterface.OnClickListener () {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss ();
                                            }
                                        } );
                                builder.show ();
                            }
                        }else{
                            Log.i(TAG, "onComplete: current location is null");
                            Toast.makeText(AjouterProductActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.i(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }
    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;

            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }
    private void askCameraPermission() {
        Log.i ( "rrrrrrr", "askperm" );

        final String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (!ActivityCompat.shouldShowRequestPermissionRationale ( this,
                Manifest.permission.CAMERA )) {
            ActivityCompat.requestPermissions ( this, permissions, RC_HANDLE_CAMERA_PERM );
            return;
        }
        if (!ActivityCompat.shouldShowRequestPermissionRationale ( this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE )) {
            ActivityCompat.requestPermissions ( this, permissions, RC_HANDLE_CAMERA_PERM );
            return;
        }
    }

    private void requestPermissions() {
        List<String> requiredPermissions = new ArrayList<> ();

        if (ContextCompat.checkSelfPermission ( this, Manifest.permission.WRITE_EXTERNAL_STORAGE )
                != PackageManager.PERMISSION_GRANTED) {
            requiredPermissions.add ( Manifest.permission.WRITE_EXTERNAL_STORAGE );
        }

        if (ContextCompat.checkSelfPermission ( this, Manifest.permission.CAMERA )
                != PackageManager.PERMISSION_GRANTED) {
            requiredPermissions.add ( Manifest.permission.CAMERA );
        }

        if (!requiredPermissions.isEmpty ()) {
            ActivityCompat.requestPermissions ( this,
                    requiredPermissions.toArray ( new String[]{} ),
                    MY_PERMISSIONS_REQUESTS );
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /*******************************
         *
         * todo (DONE) set the type of the new product before adding it to the db
         */
        type = parent.getItemAtPosition ( position ).toString ();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment ( fragment );
        try {
            listener = (passData)fragment;
        } catch (ClassCastException e) {
/*            throw new ClassCastException(fragment.toString() +
                    "must implement passData");*/
        }
    }

    /****************************************************
     *
     *  Firebase
     ********************************************/


    public interface passData {
        public void apply(FirebaseFirestore db);
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


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile() {
        if (imagedata != null) {
            Log.i ( "urrrr","yrrrrrrrr" );
            StorageReference fileReference = mStorageRef.child ( System.currentTimeMillis ()
                    + "." + getFileExtension ( imagedata ) );
            final UploadTask uploadTask = fileReference.putFile ( imagedata );
            uploadTask.addOnSuccessListener ( new OnSuccessListener<UploadTask.TaskSnapshot> () {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    uploadTask.continueWithTask ( new Continuation<UploadTask.TaskSnapshot, Task<Uri>> () {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful ()) {
                                throw task.getException ();

                            }
                            // Continue with the task to get the download URL
                            return fileReference.getDownloadUrl ();

                        }
                    } ).addOnCompleteListener ( new OnCompleteListener<Uri> () {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful ()) {
                                img = task.getResult ().toString ();
                                Log.i ( "loog",img+ "loo" );



                            }
                        }
                    } );

                }
            } ).addOnFailureListener ( new OnFailureListener () {
                @Override
                public void onFailure(@NonNull Exception e) {


                }
            } );
        }
    }}