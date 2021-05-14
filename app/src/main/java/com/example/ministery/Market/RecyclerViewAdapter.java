package com.example.ministery.Market;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.ministery.Fav.Enregistrement;
import com.example.ministery.Model.Key;
import com.example.ministery.Profile.modifierProductActivity;
import com.example.ministery.R;
import com.example.ministery.UserHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>  implements Filterable {

    private Context mContext ;
    private List<Enregistrement> list_enreg =null;
    public static  int filterType =0;
    private static  boolean eng ;
    private static boolean fav=false;
    public static List<product> mData = new ArrayList<> (  );
    private FirebaseFirestore db = FirebaseFirestore.getInstance ();
    private CollectionReference notebookRef ;
    private static List<product> mData2 = null;
    private FirebaseAuth auth=FirebaseAuth.getInstance ();
    public   static    List<product> mDataFull ; //we need it while searvhing or filtering
    private boolean  myProducts=false ; //if we are on the profile fragment or the market pour afficher les bouttons du modification



    public RecyclerViewAdapter(Context mContext, List<product> mData) {

        this.mContext = mContext;
        this.mData = mData;
        this.eng=false;
    }

    public RecyclerViewAdapter(Context mContext, List<product> mData,boolean myp) {
        this.mContext = mContext;
        this.mData =mData;
        this.myProducts=myp;
        this.mDataFull = mData;
        this.mData2 = mData;
        Log.i("datafull","constr "+mDataFull.size ()+" "+mData.size ());
        this.eng=false;

        //  this.mData2=mData;
    }

    public RecyclerViewAdapter(Context mContext , ArrayList<Enregistrement> list_enreg) {
        this.mContext = mContext;
        this.list_enreg=list_enreg;
        this.eng=true;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("datafull","oncre "+mDataFull.size ()+" "+mData.size ());

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate( R.layout.product_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Log.i("datafull","onbind "+mDataFull.size ()+" "+mData.size ());

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

                         Intent intent = new Intent ( mContext, AjouterProductActivity.class );
                         intent.putExtra ( "modif","modif" );


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
                         intent.putExtra ( "img", mData.get ( position ).getImg () );
                         intent.putExtra ( "longitude", mData.get ( position ).getAdresse ().longititude );
                         intent.putExtra ( "id", mData.get ( position ).getIdd () );
                         intent.putExtra ( "off", mData.get ( position ).getOffered ());
                         intent.putExtra ( "sign", mData.get ( position ).getNbSignal ());
                         intent.putExtra ( "idp", mData.get ( position ).getIdProprietaire ());
                         intent.putExtra ( "fav", fav );
                         Log.i ( "immg", mData.get ( position ).getImg ()+" off");

                         Log.i ( "modd", mData.get ( position ).getOffered ()+" off");
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
                                          * todo( DONE )delete the article from the db
                                          */

                                         UserHelper.deleteArticleFromUser ( auth.getCurrentUser ().getUid (),mData.get(position).getIdd () );
                                         removeAt(position,1);
                                     }
                                 } );


                         builder.show ();

                     }
                 } );
             }

             holder.name.setText ( mData.get ( position ).getName () );
           //  holder.img_thumbnail.setImageResource ( mContext.getResources ().getIdentifier ( mData.get ( position ).getImg (), "drawable", mContext.getPackageName () ) );

             Log.i ( "recycle",mData.get(position).getImg ()+"tt" );
          //   holder.img_thumbnail.setImageBitmap ( getImageBitmap(mData.get(position).getImg ()));
             if(mData.get(position).getImg ()==null||mData.get(position).getImg ().equals ( "" ))
                 holder.img_thumbnail.setImageResource ( mContext.getResources ().getIdentifier ( "ic_image_black_24dp","drawable", mContext.getPackageName ()) );
             else new DownLoadImageTask(holder.img_thumbnail).execute(mData.get(position).getImg ());
             holder.relativeLy.setOnClickListener ( new View.OnClickListener () {
                 @Override
                 public void onClick(View v) {

                     Intent intent = new Intent ( mContext, productDescriptionAcivity.class );




                     /***********check if the product is in fav list******/

                     FirebaseAuth auth= FirebaseAuth.getInstance ();
                     String uid =auth.getCurrentUser ().getUid ();
                     notebookRef = db.collection("users").document (uid).collection ( "favorit_articles" );
                     list_enreg=new ArrayList<> (  );
                     notebookRef.get ()
                             .addOnSuccessListener(new OnSuccessListener<QuerySnapshot> () {
                                 @Override
                                 public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                     Log.i("prodd","sucees");

                                      fav=false;
                                     for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                         Enregistrement p = documentSnapshot.toObject(Enregistrement.class);
                                         //   p.setIdd (documentSnapshot.getId());
                                         //  Enregistrement pr=new Enregistrement (p);



                                             if(p!=null && p.getIdd ()!=null && p.getIdd ().equals ( mData.get(position ).getIdd ()))
                                             fav =true;
                                     }


                                 }
                             }).addOnFailureListener ( new OnFailureListener () {
                         @Override
                         public void onFailure(@NonNull Exception e) {
                             Log.i("prodd",e.getMessage ());
                         }
                     } );

                     // passing data to the product description activity

                     Log.i ( "desccccc",mData.get ( position ).getDescription ()+"" );
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
                     intent.putExtra ( "img", mData.get ( position ).getImg () );
                     intent.putExtra ( "longitude", mData.get ( position ).getAdresse ().longititude );
                     Log.i ( "modd", mData.get ( position ).getOffered ()+" off");
                     intent.putExtra ( "off", mData.get ( position ).getOffered ());
                     intent.putExtra ( "sign", mData.get ( position ).getNbSignal ());
                     intent.putExtra ( "id", mData.get ( position ).getIdd ());
                     intent.putExtra ( "idp", mData.get ( position ).getIdProprietaire () );
                     intent.putExtra ( "fav", fav );



                     if(mData.get ( position ).getIsSignaleurs ().contains ( auth.getCurrentUser ().getUid () ))
                         intent.putExtra ( "issign",true);
                     else intent.putExtra ( "issign",false );


                     // start the activity
                     mContext.startActivity ( intent );

                 }
             } );


         }

         else {// en est dans le fragmnt d'enregistrement, afficher juste boutton delete


             holder.modif.setVisibility ( holder.itemView.GONE );
             holder.supp.setVisibility ( holder.itemView.VISIBLE );
           //  holder.img_thumbnail.setImageBitmap ( getImageBitmap(list_enreg.get(position).getImg ()));

             holder.name.setText ( list_enreg.get ( position ).getName () );
//             holder.img_thumbnail.setImageResource ( mContext.getResources ().getIdentifier ( list_enreg.get ( position ).getImg (), "drawable", mContext.getPackageName () ) );

             if(list_enreg.get(position).getImg ()==null||list_enreg.get(position).getImg ().equals ( "" ))
                 holder.img_thumbnail.setImageResource ( mContext.getResources ().getIdentifier ( "ic_image_black_24dp","drawable", mContext.getPackageName ()) );
             else new DownLoadImageTask(holder.img_thumbnail).execute(list_enreg.get(position).getImg ());


             Log.i("faaaaav",list_enreg.get(position).getImg ()+"   "+list_enreg.get ( position ).getIdProprietaire ());
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
                     intent.putExtra ( "img", list_enreg.get ( position ).getImg () );
                     intent.putExtra ( "idp", list_enreg.get ( position ).getIdProprietaire () );
                     intent.putExtra ( "fav", true );
                     if(list_enreg.get ( position ).getIsSignaleurs ().contains ( auth.getCurrentUser ().getUid () ))
                         intent.putExtra ( "issign",true);
                     else intent.putExtra ( "issign",false);

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
                                     notebookRef = db.collection("users").document (auth.getCurrentUser ().getUid ()).collection ( "favorit_articles" );

                                     notebookRef.document (list_enreg.get ( position ).getIdEnreg ())
                                             .delete()
                                             .addOnSuccessListener(new OnSuccessListener<Void> () {
                                                 @Override
                                                 public void onSuccess(Void aVoid) {
                                                     Log.d("fav", "DocumentSnapshot successfully deleted!");
                                                     removeAt(position,0);
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
//        Log.i ( "datafull", "item " + mDataFull.size () + " " + mData2.size () );

        if (!this.eng) {

         //   if (filterType != 4) {
                return mData.size ();
       //     } else return mData2.size ();
        } else {
            return list_enreg.size ();
        }
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


            name = (TextView)  itemView.findViewById ( R.id.title_id );
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
        Log.i("datafull","getfiltr "+mDataFull.size ()+" "+mData.size ());

        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
           // mDataFull.clear ();
           // mDataFull.addAll (  mData2);
            Log.i("datafull","tag "+mDataFull.size ()+" "+mData2.size ());
      //  filterType=4;
            Log.i ( "taaag","fnc"+ String.valueOf ( constraint ) );
            List<product> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                Log.i ( "taaag","null"+ String.valueOf ( constraint ) );

                filteredList.addAll(mDataFull);
                Log.i ( "null",mDataFull.size ()+"" );

            } else {

                Log.i("datafull","eslse "+mDataFull.size ()+" "+mData.size ());

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
            Log.i("datafull","res "+ mDataFull.size ()+" "+mData.size ());

            mData.clear();
         //  if(results.values!=null)
            mData.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public void removeAt(int position, int enreg_ou_data) {
        if(enreg_ou_data==0) {
            list_enreg.remove ( position );
            notifyItemRemoved ( position );
            notifyItemRangeChanged ( position, list_enreg.size () );
        }else {
            mData.remove ( position );
            notifyItemRemoved ( position );
            notifyItemRangeChanged ( position, mData.size () );
        }
    }


}
