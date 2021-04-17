package com.example.ministery.Market;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.ministery.MainScreen.MainActivity;
import com.example.ministery.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class AjouterProductActivity extends AppCompatActivity {

    private Button entrer_photo ;
    private ImageView img_product ;
    private Button confirmer ;
    private Button entrerAdresse ;
    private static final int RC_HANDLE_CAMERA_PERM = 2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_ajouter_product );
        img_product=findViewById ( R.id.img_product );


        entrer_photo=findViewById ( R.id.entrer_photo );
        entrerAdresse=findViewById ( R.id.entrer_adresse );

        entrer_photo.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                selectImage (AjouterProductActivity.this);
            }
        } );
        confirmer=findViewById ( R.id.confirmer_ajout );
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


                    final String[] permissions = new String[]{Manifest.permission.CAMERA};

                    if (!ActivityCompat.shouldShowRequestPermissionRationale ( AjouterProductActivity.this,
                            Manifest.permission.CAMERA )) {
                        ActivityCompat.requestPermissions ( AjouterProductActivity.this, permissions, RC_HANDLE_CAMERA_PERM );
                    }
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    Uri imageUri = FileProvider.getUriForFile(
                            AjouterProductActivity.this,
                            "com.example.ministery.provider", //(use your app signature + ".provider" )
                            f);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile ( f ));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {

                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
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
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
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
}
