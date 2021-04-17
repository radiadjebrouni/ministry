package com.example.ministery.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ministery.Market.Adresse;
import com.example.ministery.Market.RecyclerViewAdapter;
import com.example.ministery.Market.product;
import com.example.ministery.R;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    ArrayList<product> MyProducts = new ArrayList<> (  );
    Adresse a= new Adresse ("latitude","emptitude");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate ( R.layout.profile_fragment,container ,false);

        /*********************
         * TODO display the users main info
         */
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated ( view, savedInstanceState );

        /************************
         *
         * TODO
         * Geting my products from db
         */
        MyProducts.add ( new product ( "title","type","description","price","nom user","email user",0123,a,"chaise"  ));
        MyProducts.add ( new product ( "title","type","description","price","nom user","email user",0123,a,"chaise"  ));
        MyProducts.add ( new product ( "title","type","description","price","nom user","email user",012,a,"chaise"  ));
        MyProducts.add ( new product ( "title","type","description","price","nom user","email user",0123,a,"chaise"  ));
        MyProducts.add ( new product ( "title","type","description","price","nom user","email user",0123,a,"chaise"  ));


        LinearLayoutManager linearLayoutManager =new LinearLayoutManager ( getContext (),LinearLayoutManager.HORIZONTAL,false );
        RecyclerView myrv = (RecyclerView)view. findViewById(R.id.recyclerview_profile);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getActivity (),MyProducts);

        myrv.setLayoutManager ( linearLayoutManager );
        myrv.setAdapter(myAdapter);
        myrv.setItemAnimator (new DefaultItemAnimator() );
    }
}
