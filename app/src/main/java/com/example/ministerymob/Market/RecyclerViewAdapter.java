package com.example.ministerymob.Market;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.example.ministerymob.Fav.Enregistrement;
import com.example.ministerymob.Profile.modifierProductActivity;
import com.example.ministerymob.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>  implements Filterable {

    private Context mContext ;
    private List<Enregistrement> list_enreg =null;
    public static  int filterType =0;
    private static List<product> mData ;
    private FirebaseFirestore db = FirebaseFirestore.getInstance ();
    private CollectionReference notebookRef ;
  //  private List<product> mData2 ;
    private List<product> mDataFull ; //we need it while searvhing or filtering
    private boolean  myProducts=false ; //if we are on the profile fragment or the market pour afficher les bouttons du modification



    public RecyclerViewAdapter(Context mContext, List<product> mData) {

        this.mContext = mContext;
        this.mData = mData;
    }

    public RecyclerViewAdapter(Context mContext, List<product> mData,boolean myp) {
        this.mContext = mContext;
        this.mData=mData;
        this.myProducts=myp;
       this. mDataFull = mData;
     //  this.mData2=mData;
    }

    public RecyclerViewAdapter(Context mContext , ArrayList<Enregistrement> list_enreg) {
        this.mContext = mContext;
        this.list_enreg=list_enreg;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate( R.layout.product_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

         if(list_enreg==null ) // en est pas ds le fragmnt d'enregistrement
         {
             if (!myProducts) // verifier si on est dans le profil pour afficher modif et delete icon
             {
                 holder.modif.setVisibility ( holder.itemView.GONE );
                 holder.supp.setVisibility ( holder.itemView.GONE );
             } else {

                 holder.modif.setVisibility ( holder.itemView.VISIBLE );
                 holder.supp.setVisibility ( holder.itemView.VISIBLE );
                 holder.modif.setOnClickListener ( new View.OnClickListener () {
                     @Override
                     public void onClick(View v) {

                         /***************************************
                          *
                          * modefy the article
                          */

                         Intent modif = new Intent ( mContext, modifierProductActivity.class );
                         mContext.startActivity ( modif );
                     }
                 } );


                 holder.supp.setOnClickListener ( new View.OnClickListener () {
                     @Override
                     public void onClick(View v) {

                         /***************************************
                          *
                          * deleting confirmation
                          */
                         AlertDialog.Builder builder = new AlertDialog.Builder ( mContext );
                         builder.setMessage ( "Etes vous sur de vouloir supprimer cet article?" )
                                 .setNegativeButton ( "Concel", new DialogInterface.OnClickListener () {
                                     @Override
                                     public void onClick(DialogInterface dialogInterface, int i) {
                                         dialogInterface.dismiss ();
                                     }
                                 } )
                                 .setPositiveButton ( "Oui", new DialogInterface.OnClickListener () {
                                     @Override
                                     public void onClick(DialogInterface dialogInterface, int i) {


                                         /*********************************
                                          * todo delete the article from the db
                                          */
                                     }
                                 } );


                         builder.show ();

                     }
                 } );
             }

             holder.name.setText ( mData.get ( position ).getName () );
           //  holder.img_thumbnail.setImageResource ( mContext.getResources ().getIdentifier ( mData.get ( position ).getImg (), "drawable", mContext.getPackageName () ) );
             holder.relativeLy.setOnClickListener ( new View.OnClickListener () {
                 @Override
                 public void onClick(View v) {

                     Intent intent = new Intent ( mContext, productDescriptionAcivity.class );

                     // passing data to the product description activity

                     intent.putExtra ( "name", mData.get ( position ).getName () );
                     intent.putExtra ( "Description", mData.get ( position ).getDescription () );
                     intent.putExtra ( "adrsI", mData.get ( position ).getAdresseInput () );
                     intent.putExtra ( "nomUser", mData.get ( position ).getNomUser () );
                     intent.putExtra ( "emailUser", mData.get ( position ).getEmailUser () );
                     intent.putExtra ( "numTel", mData.get ( position ).getNumTel () );
                     intent.putExtra ( "date", mData.get ( position ).getDate_creation () );
                     intent.putExtra ( "type", mData.get ( position ).getType () );
                     intent.putExtra ( "prix", mData.get ( position ).getPrice () );
                     intent.putExtra ( "latitude", mData.get ( position ).getAdresse ().latitude );
                     intent.putExtra ( "longitude", mData.get ( position ).getAdresse ().longititude );
                     // start the activity
                     mContext.startActivity ( intent );

                 }
             } );


         }

         else {// en est dans le fragmnt d'enregistrement, afficher juste boutton delete
             /****todo afficher date enreg **/
             holder.modif.setVisibility ( holder.itemView.GONE );
             holder.supp.setVisibility ( holder.itemView.VISIBLE );

             holder.name.setText ( list_enreg.get ( position ).getName () );
//             holder.img_thumbnail.setImageResource ( mContext.getResources ().getIdentifier ( list_enreg.get ( position ).getImg (), "drawable", mContext.getPackageName () ) );
             holder.relativeLy.setOnClickListener ( new View.OnClickListener () {
                 @Override
                 public void onClick(View v) {

                     Intent intent = new Intent ( mContext, productDescriptionAcivity.class );

                     // passing data to the product description activity
                     intent.putExtra ( "name", list_enreg.get ( position ).getName () );
                     intent.putExtra ( "Description", list_enreg.get ( position ).getDescription () );
                     intent.putExtra ( "adrsI", list_enreg.get ( position ).getAdresseInput () );
                     intent.putExtra ( "nomUser", list_enreg.get ( position ).getNomUser () );
                     intent.putExtra ( "emailUser", list_enreg.get ( position ).getEmailUser () );
                     intent.putExtra ( "numTel", list_enreg.get ( position ).getNumTel () );
                     intent.putExtra ( "date", list_enreg.get ( position ).getDate_creation () );
                     intent.putExtra ( "type", list_enreg.get ( position ).getType () );
                     intent.putExtra ( "prix", list_enreg.get ( position ).getPrice () );
                     intent.putExtra ( "latitude", list_enreg.get ( position ).getAdresse ().latitude );
                     intent.putExtra ( "longitude", list_enreg.get ( position ).getAdresse ().longititude );
                     intent.putExtra ( "date_enreg", list_enreg.get ( position ).getDateEnregistrement () );
                     // start the activity
                     mContext.startActivity ( intent );

                 }
             } );

             holder.supp.setOnClickListener ( new View.OnClickListener () {
                 @Override
                 public void onClick(View v) {

                     /***************************************
                      *
                      * deleting confirmation
                      */
                     AlertDialog.Builder builder = new AlertDialog.Builder ( mContext );
                     builder.setMessage ( mContext.getString( R.string.confirmer_annulation_enregistrement) )
                             .setNegativeButton ( "Concel", new DialogInterface.OnClickListener () {
                                 @Override
                                 public void onClick(DialogInterface dialogInterface, int i) {
                                     dialogInterface.dismiss ();
                                 }
                             } )
                             .setPositiveButton ( "Oui", new DialogInterface.OnClickListener () {
                                 @Override
                                 public void onClick(DialogInterface dialogInterface, int i) {


                                     /*********************************
                                      * todo (Done) delete the article from les enregistrement du user
                                      */
                                     notebookRef = db.collection("Favorites");

                                     notebookRef.document (list_enreg.get ( position ).getIdEnreg ())
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

                             } );


                     builder.show ();

                 }
             } );


         }

    }


    @Override
    public int getItemCount() {
        if( list_enreg==null) return  mData.size ();
        else  return list_enreg.size();

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView img_thumbnail;
        //  CardView cardView ;
        ConstraintLayout relativeLy;
        ImageButton modif;
        ImageButton supp;

        public MyViewHolder(View itemView) {
            super ( itemView );

            name = (TextView) itemView.findViewById ( R.id.title_id );
            img_thumbnail = (ImageView) itemView.findViewById ( R.id.img_id );
            //  cardView = (CardView) itemView.findViewById( R.id.cardview_id);
            relativeLy = itemView.findViewById ( R.id.relativl );
            modif = itemView.findViewById ( R.id.modifier_article );
            supp = itemView.findViewById ( R.id.supprimer_article );


        }
    }


    /****************************************************
     *
     *
     * searching action (by name)
     **************************************/
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            Log.i ( "taaag","fnc"+ String.valueOf ( constraint ) );
            List<product> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                Log.i ( "taaag","null"+ String.valueOf ( constraint ) );

                filteredList.addAll(mDataFull);
                Log.i ( "null",constraint.toString () );

            } else {


                String filterPattern = constraint.toString().toLowerCase().trim();
                for (product item : mDataFull) {
                    if (filterType ==1) {
                        if (item.getType ().toLowerCase ().contains ( filterPattern )) {
                            filteredList.add ( item );
                        }
                    } else {
                        Log.i ( "taaag", item.getDescription () );

                        if (item.getName ().toLowerCase ().contains ( filterPattern ) || item.getDescription ().toLowerCase ().contains ( filterPattern )) {
                            filteredList.add ( item );

                        }}}}
                        FilterResults results = new FilterResults ();
                        results.values = filteredList;
                        return results;

                }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mData.clear();
            if(results.values!=null) mData.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

}
