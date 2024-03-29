package com.example.ministery.Market;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ministery.Model.User;
import com.example.ministery.R;
import com.example.ministery.UserHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class offeredServicesFragment extends Fragment implements AdapterView.OnItemSelectedListener, AjouterProductActivity.passData {
    private  static ArrayList<product> listeProduct ;
    private SearchView searchView;
    private RecyclerView myrv;
    private static RecyclerViewAdapter myAdapter;

    private FirebaseFirestore db;
    private FirebaseAuth auth=FirebaseAuth.getInstance ();
   private String nomUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate ( R.layout.offered_services, container, false );
        myrv = (RecyclerView) view.findViewById ( R.id.recyclerview_id_offer );
        db = FirebaseFirestore.getInstance ();

        Task<DocumentSnapshot> du = UserHelper.getUser (auth.getCurrentUser ().getUid () );
        du.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot> () {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                User u = du.getResult ().toObject ( User.class );

                /*********************
                 * TODO (DONE )display the users main info
                 */

                nomUser= u.getUsername () ;
                Log.i ( "uuuuuus",nomUser+" jhkj" );

            }   }).addOnFailureListener ( new OnFailureListener () {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.i("proo",e.getMessage ());
            }
        } );


        /*******************
         * button ajouter
         *
         * pass offered=1 to the add button
         */

        ImageButton ajouter =view.findViewById ( R.id.ajout_product );
       // ajouter.setImageResource ( R.drawable.ic_add_black_24dp );

        ajouter.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                Intent ajoutActivity =new Intent ( getActivity (),AjouterProductActivity.class);
                ajoutActivity.putExtra ( "nomU", nomUser);
                ajoutActivity.putExtra ( "offered",1 );
                getActivity ().startActivity ( ajoutActivity );


            }
        } );




        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {





        /****************************
         *
         * Searching
         ********************* */
        searchView= (SearchView) view.findViewById ( R.id.search_bar );
        searchView.setQueryHint ( getString( R.string.rechercher) );
        searchView.setOnQueryTextListener (new SearchView.OnQueryTextListener () {
            @Override
            public boolean onQueryTextSubmit(String query) {
                RecyclerViewAdapter.filterType=0;

               // myAdapter.getFilter ().filter ( query );

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                RecyclerViewAdapter.filterType=0;

                myAdapter.getFilter ().filter ( newText);//search filtering
                return false;
            }

        });


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



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();

        if(!text.equals ( "Général" )) {
            RecyclerViewAdapter.filterType=1;
            myAdapter.getFilter ().filter ( text);// type filtering
        }




    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onStart() {
        super.onStart ();
     //   if(myAdapter!=null)myAdapter.notifyDataSetChanged ();
    }

    /********************Firestore************************
     *
     **********************************************
     */


    private void setupAdapter(View view,FirebaseFirestore db)
    {
        listeProduct=new ArrayList<> (  );
        Task<QuerySnapshot>  tu= UserHelper.getUsersCollection ().get ();
        tu.addOnSuccessListener(new OnSuccessListener<QuerySnapshot> () {
            @Override
            public void onSuccess(QuerySnapshot querySnapshot) {
                Log.i ( "prooo","usuccess" );

                for (DocumentSnapshot d : querySnapshot){
                    Log.i ( "prooo","u" );

                    User u= d.toObject ( User.class ) ;
                   Task<QuerySnapshot> prod=UserHelper.gerArticlesFromUser ( u.getUserId (),1 );
                   prod.addOnSuccessListener(new OnSuccessListener<QuerySnapshot> () {
                       @Override
                       public void onSuccess(QuerySnapshot querySnapshot) {
                           Log.i ( "prooo","dsucess" );

                           for (DocumentSnapshot d : querySnapshot){
                               Log.i ( "prooo","d" );
                               listeProduct.add ( d.toObject ( product.class ) );

                               }

                           myrv = (RecyclerView) view.findViewById ( R.id.recyclerview_id_offer );

                           myAdapter = new RecyclerViewAdapter (  view.getContext (), listeProduct, false );

                           myrv.setLayoutManager ( new GridLayoutManager ( getActivity (), 2 ) );
                           if(listeProduct!=null ) {
                               myrv.setHasFixedSize ( true );
                               myAdapter.notifyDataSetChanged ();
                               myrv.setAdapter ( myAdapter );
                           }

                       }   }).addOnFailureListener ( new OnFailureListener () {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Log.i("proo",e.getMessage ());
                       }
                   } );
                }

            }   }).addOnFailureListener ( new OnFailureListener () {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("proo",e.getMessage ());
            }
        } );

        /*
        notebookRef = db.collection("Products");
        listeProduct=new ArrayList<> (  );
        notebookRef.whereEqualTo ( "offered",1 ).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot> () {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.i("prodd","sucees");


                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            product p = documentSnapshot.toObject(product.class);
                            p.setIdd (documentSnapshot.getId());
                            product pr=new product(p);
                            Log.i ( "proddd",p.getName ()+" name "+p.offered );
                            if( !listeProduct.contains ( pr ))
                                listeProduct.add ( pr );


                        }
                        Adresse a = new Adresse ( "latitude", "emptitude" );
//                Log.i ( "lissss"," "+listeProduct.size () );
                        myrv = (RecyclerView) view.findViewById ( R.id.recyclerview_id_offer );

                        myAdapter = new RecyclerViewAdapter (  view.getContext (), listeProduct, false );
                        myrv.setLayoutManager ( new GridLayoutManager ( getActivity (), 2 ) );
                        if(listeProduct!=null ) {
                            myrv.setHasFixedSize ( true );
                            myAdapter.notifyDataSetChanged ();
                            myrv.setAdapter ( myAdapter );
                        }


                    }
                }).addOnFailureListener ( new OnFailureListener () {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("prodd",e.getMessage ());
            }
        } );*/
    }


    @Override
    public void apply(FirebaseFirestore db) {
        setupAdapter ( getView (),db );
    }

    @Override
    public void onResume() {
        super.onResume ();



        /*************************
         * TODO : done
         * Extract info from db and display it according to the selected items
         ************************** */
        setupAdapter ( getView (),db );

    }
}

