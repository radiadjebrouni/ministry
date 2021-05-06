package com.example.ministery.Market;

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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ministery.Fav.Enregistrement;
import com.example.ministery.Profile.modifierProductActivity;
import com.example.ministery.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterNeed extends RecyclerView.Adapter<RecyclerViewAdapterNeed.MyViewHolder>  implements Filterable {

    private Context mContext ;
    private List<Enregistrement> list_enreg =null;
    private static  int filterType =0;
    private static List<product> mData ;
    private FirebaseAuth auth=FirebaseAuth.getInstance ();
    //  private List<product> mData2 ;
    private List<product> mDataFull ; //we need it while searvhing or filtering
    private boolean  myProducts=false ; //if we are on the profile fragment or the market pour afficher les bouttons du modification



    public RecyclerViewAdapterNeed(Context mContext, List<product> mData) {

        this.mContext = mContext;
        this.mData = mData;
    }

    public RecyclerViewAdapterNeed(Context mContext, List<product> mData,boolean myp) {
        this.mContext = mContext;
        this.mData=mData;
        this.myProducts=myp;
        this. mDataFull = mData;
        //  this.mData2=mData;
    }

    public RecyclerViewAdapterNeed(Context mContext , ArrayList<Enregistrement> list_enreg) {
        this.mContext = mContext;
        this.list_enreg=list_enreg;
    }

    @Override
    public RecyclerViewAdapterNeed.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate( R.layout.product_item,parent,false);
        return new RecyclerViewAdapterNeed.MyViewHolder (view);
    }


    @Override
    public void onBindViewHolder(RecyclerViewAdapterNeed.MyViewHolder holder, final int position) {

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

                        /***************************************
                         *
                         * modefy the article
                         */

                        Intent intent = new Intent ( mContext, AjouterProductActivity.class );
                        intent.putExtra ( "modif",mData.get ( position ).getIdd () );


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
                                         * todo delete the article from the db
                                         */
                                    }
                                } );


                        builder.show ();

                    }
                } );
            }

            if(mData.get(position).getImg ()==null||mData.get(position).getImg ().equals ( "" ))
                holder.img_thumbnail.setImageResource ( mContext.getResources ().getIdentifier ( "ic_image_black_24dp","drawable", mContext.getPackageName ()) );
            else new DownLoadImageTask(holder.img_thumbnail).execute(mData.get(position).getImg ());

            holder.name.setText ( mData.get ( position ).getName () );
            //  holder.img_thumbnail.setImageResource ( mContext.getResources ().getIdentifier ( mData.get ( position ).getImg (), "drawable", mContext.getPackageName () ) );
            holder.relativeLy.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent ( mContext, productDescriptionAcivity.class );


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
                    intent.putExtra ( "off", mData.get ( position ).getOffered ());
                    intent.putExtra ( "sign", mData.get ( position ).getNbSignal ());
                    intent.putExtra ( "idp", mData.get ( position ).getIdProprietaire () );

                    intent.putExtra ( "id", mData.get ( position ).getIdd ());

                    if(mData.get ( position ).getIsSignaleurs ().contains ( auth.getCurrentUser ().getUid () ))
                        intent.putExtra ( "issign","true");
                    else intent.putExtra ( "issign","false");


                    // start the activity
                    mContext.startActivity ( intent );

                }
            } );


        }

        else {// en est dans le fragmnt d'enregistrement, afficher juste boutton delete

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
                  //  intent.putExtra ( "img", list_enreg.get ( position ).getImg () );
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
                                     * todo delete the article from les enregistrement du user
                                     */
                                }
                            } );


                    builder.show ();

                }
            } );


        }

    }


    @Override
    public int getItemCount() {

        return mData.size();

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
