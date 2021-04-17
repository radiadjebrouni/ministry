package com.example.ministery.Market;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ministery.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class neededServicesFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    ArrayList<product> listeProduct = new ArrayList<> (  );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate ( R.layout.needed_services, container, false );


        /***********************
         *
         * Filtering
         *********************** */
        Spinner spinner =view. findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( getActivity (),
                R.array.types_handicapes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener( this);

        /********************/


        /*******************
         * button ajouter
         */

        FloatingActionButton ajouter =view.findViewById ( R.id.ajout_product );
        // ajouter.setImageResource ( R.drawable.ic_add_black_24dp );

        ajouter.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent ajoutActivity =new Intent ( getActivity (),AjouterProductActivity.class);
                getActivity ().startActivity ( ajoutActivity );
            }
        } );



        /*************************
         * TODO
         * Extract info from db and display it according to the selected items
         ************************** */
        Adresse a= new Adresse ("latitude","emptitude");

        listeProduct.add ( new product ( "title","type","description","price","nom user","email user",0123,a,"chaise"  ));

        listeProduct.add ( new product ( "title","type","description","price","nom user","email user",0123,a,"chaise"  ));
        listeProduct.add ( new product ( "title","type","description","price","nom user","email user",0123,a,"chaise"  ));
        listeProduct.add ( new product ( "title","type","description","price","nom user","email user",0123,a,"chaise"  ));
        listeProduct.add ( new product ( "title","type","description","price","nom user","email user",0123,a,"chaise"  ));
        listeProduct.add ( new product ( "title","type","description","price","nom user","email user",0123,a,"chaise"  ));

        RecyclerView myrv = (RecyclerView)view. findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getActivity (),listeProduct);
        myrv.setLayoutManager(new GridLayoutManager (getActivity (),2));
        myrv.setAdapter(myAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated ( view, savedInstanceState );



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


