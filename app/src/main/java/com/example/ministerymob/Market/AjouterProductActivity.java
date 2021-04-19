package com.example.ministerymob.Market;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ministerymob.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class AjouterProductActivity extends AppCompatActivity {

    private Button entrer_photo ;
    private ImageView img_product ;
    private Button confirmer ;
    private Button entrerAdresse ;
    private EditText nom_service;
    private EditText prix;
    private EditText type;                // ou bien il selectionne un type
    private EditText description;

    private static final int REQUEST_GALLERY = 0;
    private static final int REQUEST_CAMERA = 1;
    private static final int MY_PERMISSIONS_REQUESTS = 0;
    private static final int RC_HANDLE_CAMERA_PERM = 2;
    private static Uri imageUri;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_ajouter_product );
        img_product=findViewById ( R.id.img_product );
        nom_service=findViewById ( R.id.entrer_nom );
        prix=findViewById ( R.id.entrer_prix );
        type=findViewById ( R.id.entrer_type );
        description=findViewById ( R.id.entrer_desc );


        entrer_photo=findViewById ( R.id.entrer_photo );
        entrerAdresse=findViewById ( R.id.entrer_adresse );
        entrer_photo.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                selectImage (AjouterProductActivity.this);
            }
        } );
        confirmer=findViewById ( R.id.confirmer_ajout );
        confirmer.setBackgroundResource ( R.drawable.bouton_commencer );
        confirmer.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                /***********************
                 *
                 * TODO
                 * ADDING DATA TO THE DB
                 */
            }
        } );

        entrerAdresse.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                showMapChoices();
            }
        } );



    }


    /**********************
     *
     * get the image
     *
     ******************/


    private void selectImage(final Context context) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(AjouterProductActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {

                    requestPermissions ();
                    if ((ActivityCompat.checkSelfPermission(getApplicationContext (), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                        && ActivityCompat.checkSelfPermission(getApplicationContext (), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                        String filename = System.currentTimeMillis () + ".jpg";

                        ContentValues values = new ContentValues ();
                        values.put ( MediaStore.Images.Media.TITLE, filename );
                        values.put ( MediaStore.Images.Media.MIME_TYPE, "image/jpeg" );

                        imageUri = getContentResolver ().insert ( MediaStore.Images.Media.INTERNAL_CONTENT_URI, values );

                        Intent intent = new Intent (  );
                        intent.setAction ( MediaStore.ACTION_IMAGE_CAPTURE );
                        intent.putExtra ( MediaStore.EXTRA_OUTPUT, imageUri );
                        startActivityForResult ( intent, REQUEST_CAMERA );

                    }
                    else  askCameraPermission();


                }
                else if (options[item].equals("Choose from Gallery"))
                {
/*
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                    */

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, REQUEST_GALLERY);

                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //   super.onActivityResult(requestCode, resultCode, data);
      /*  if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File( Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    img_product.setImageBitmap(bitmap);
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream (file);
                        bitmap.compress( Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                img_product.setImageBitmap(thumbnail);
            }
        }

       */
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
                            if(imageUri==null)
                                Toast.makeText ( this, "Erreur de permission,Veuillez choisir une photo dans votre gallerie", Toast.LENGTH_LONG ).show ();
                  img_product.setImageURI ( imageUri);

                }
                break;

            /*********************
             * todo add the image uri the new product data
             */
        }
    }


    private void showMapChoices()

    {

        final CharSequence[] options = { "Detecter par GPS", "Choisir une adresse","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(AjouterProductActivity.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Detecter par GPS"))
                {

                    /******************************
                     * TODO map func
                     */
                }
                else if (options[item].equals("Choisir une adresse"))
                {
                    /***************************
                     *
                     * TODO  map fun
                     */
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void askCameraPermission() {
        Log.i ( "rrrrrrr", "askperm" );

        final String[] permissions = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};

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

    private void requestPermissions()
    {
        List<String> requiredPermissions = new ArrayList<> ();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requiredPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requiredPermissions.add(Manifest.permission.CAMERA);
        }

        if (!requiredPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    requiredPermissions.toArray(new String[]{}),
                    MY_PERMISSIONS_REQUESTS);
        }
    }
}
