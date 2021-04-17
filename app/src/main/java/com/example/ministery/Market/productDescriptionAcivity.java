package com.example.ministery.Market;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.ministery.R;

import java.io.File;


public class productDescriptionAcivity extends AppCompatActivity {

    private TextView name;
    private ImageView img;


    private static RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    private Button adresse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.product_activity );

        name = (TextView) findViewById( R.id.nom);
        adresse=findViewById ( R.id.map );

        img = (ImageView) findViewById( R.id.img_product);

        // Recieve data
        Intent intent = getIntent();
        String name = intent.getExtras().getString("name");
        String Description = intent.getExtras().getString("Description");
        String image = intent.getExtras().getString("img") ;

        img.setImageResource (getResources().getIdentifier(image, "drawable", this.getPackageName()));

        adresse.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                /***********
                 * TODO SHOW ADRESS
                 */
            }
        } );



    }


}
