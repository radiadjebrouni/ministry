package com.example.ministerymob.Market;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ministerymob.Fav.Enregistrement;
import com.example.ministerymob.MainScreen.ImageFirstScreen;
import com.example.ministerymob.MainScreen.ImageViewPager;
import com.example.ministerymob.MainScreen.MainActivity;
import com.example.ministerymob.MainScreen.MyPagerAdapter;
import com.example.ministerymob.MenuActivity;
import com.example.ministerymob.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AjouterProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public Button entrer_photo;
    public ImageView img_product;
    public Button confirmer;
    public Button entrerAdresse;
    public EditText nom_service;
    public EditText prix;
    public EditText numTel;
    public static String type;                // ou bien il selectionne un type
    public EditText description;
    public Adresse adresse;
    public EditText adresseInput;
    public product p;


    public String nom;
    public String desc;
    public String pri;
    public Long num;
    public String adrsI;


    private static final int REQUEST_GALLERY = 0;
    private static final int REQUEST_CAMERA = 1;
    private static final int MY_PERMISSIONS_REQUESTS = 0;
    private static final int RC_HANDLE_CAMERA_PERM = 2;
    public static Uri imageUri;
    public FirebaseFirestore db = FirebaseFirestore.getInstance ();
    public CollectionReference notebookRef = db.collection ( "Products" );
    private passData listener= new passData () {
        @Override
        public void apply(FirebaseFirestore db) {

        }
    };


    private ImageFirstScreen FIRST ;
    private ImageFirstScreen Second ;
    private ImageFirstScreen THIRD ;
    private  ImageFirstScreen[] images;
    private ViewPager mMyViewPager;
    private TabLayout mTabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_ajouter_product );
     //   img_product = findViewById ( R.id.img_product );
        nom_service = findViewById ( R.id.entrer_nom );
        prix = findViewById ( R.id.entrer_prix );
        numTel = findViewById ( R.id.entrer_numTEL );
        description = findViewById ( R.id.entrer_desc );
        adresseInput=findViewById ( R.id.entrer_adresseInput );


       // entrer_photo = findViewById ( R.id.entrer_photo );
        entrerAdresse = findViewById ( R.id.entrer_adresse );



        mTabLayout = findViewById(R.id.tab_layout_Aproduct);
        mMyViewPager = findViewById(R.id.view_pager_Aproduct);


        FIRST= new ImageFirstScreen ( R.drawable.image_acceuil0 );
        Second= new ImageFirstScreen ( R.drawable.image_acceuil1 );
        THIRD= new ImageFirstScreen ( R.drawable.image_acceuil2 );
        images = new ImageFirstScreen[]{FIRST, Second, THIRD};
        init();

/*        entrer_photo.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                selectImage ( AjouterProductActivity.this );
            }
        } );*/
        confirmer = findViewById ( R.id.confirmer_ajout );
        confirmer.setBackgroundResource ( R.drawable.bouton_commencer );
        confirmer.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                /***********************
                 *
                 * TODO
                 * ADDING DATA TO THE DB
                 */

                nom = nom_service.getText ().toString ();
                desc = description.getText ().toString ();
                pri = prix.getText ().toString ();
                num = Long.valueOf ( numTel.getText ().toString () );
                FirebaseUser user = FirebaseAuth.getInstance ().getCurrentUser ();
                // String nomUser=user.getDisplayName ();
                // String emailUer=user.getEmail ();
                adresse = new Adresse ( "latitude", "logitude" );
                adrsI=adresseInput.getText ().toString ();
                p = new product ( nom, type, desc, pri, "nomuser", "emailuser", num, adresse, "img" );
                p.setOffered ( getIntent ().getExtras ().getInt ( "offered" ) );


                // add date
                Date Year = Calendar.getInstance().getTime ();
                DateFormat dateFormat = new SimpleDateFormat ( "yyyy/MM/dd HH:mm" );

                String strDate = dateFormat.format ( Year );
                p.setDate_creation ( strDate );
                p.setAdresseInput ( adrsI );

                DocumentReference doc=notebookRef.document ();
                String id= doc.getId ();
               p.setId ( id );
                doc.set ( id );

                /******pass data***/

                listener.apply(db);

                finish ();


            }
        } );

        entrerAdresse.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {

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

                        imageUri = getContentResolver ().insert ( MediaStore.Images.Media.INTERNAL_CONTENT_URI, values );

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
                }
                break;
            case REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {
                    Log.i ( "taaaag", "uriiii" );
                    img_product.setImageURI ( null );
                    if (imageUri == null)
                        Toast.makeText ( this, "Erreur de permission,Veuillez choisir une photo dans votre gallerie", Toast.LENGTH_LONG ).show ();
                    img_product.setImageURI ( imageUri );

                }
                break;

            /*********************
             * todo add the image uri the new product data
             */
        }
    }


    private void showMapChoices() {

        final CharSequence[] options = {"Detecter par GPS", "Choisir une adresse", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder ( AjouterProductActivity.this );
        // builder.setTitle("Add Photo!");
        builder.setItems ( options, new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals ( "Detecter par GPS" )) {

                    /******************************
                     * TODO map func
                     */
                } else if (options[item].equals ( "Choisir une adresse" )) {
                    /***************************
                     *
                     * TODO  map fun
                     */
                } else if (options[item].equals ( "Cancel" )) {
                    dialog.dismiss ();
                }
            }
        } );
        builder.show ();
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
         * todo set the type of the new product before adding it to the db
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

}