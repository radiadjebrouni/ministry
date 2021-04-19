package com.example.ministerymob.Market;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.example.ministerymob.Profile.modifierProductActivity;
import com.example.ministerymob.R;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext ;
    private List<product> mData ;
    private boolean  myProducts=false ; //if we are on the profile fragment or the market pour afficher les bouttons du modification


    public RecyclerViewAdapter(Context mContext, List<product> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public RecyclerViewAdapter(Context mContext, List<product> mData,boolean myp) {
        this.mContext = mContext;
        this.mData = mData;
        this.myProducts=myp;
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

        if(!myProducts)
        {
            holder.modif.setVisibility ( holder.itemView.GONE );
            holder.supp.setVisibility (  holder.itemView.GONE );
        }
        else {

            holder.modif.setVisibility ( holder.itemView.VISIBLE );
            holder.supp.setVisibility (  holder.itemView.VISIBLE );
            holder.modif.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick(View v) {

                    /***************************************
                     *
                     * modefy the article
                     */

                    Intent modif =new Intent ( mContext, modifierProductActivity.class );
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
                            })
                            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {


                                    /*********************************
                                     * todo delete the article from the db
                                     */
                                }
                            });


                    builder.show ();

                }
            } );
        }

        holder.name.setText(mData.get(position).getName ());
        holder.img_thumbnail.setImageResource ( mContext.getResources().getIdentifier(mData.get(position).getImg (), "drawable", mContext.getPackageName()));
        holder.relativeLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, productDescriptionAcivity.class);

                // passing data to the book activity
                intent.putExtra("name",mData.get(position).getName ());
                intent.putExtra("Description",mData.get(position).getDescription ());
                intent.putExtra("img",mData.get(position).getImg ());
                // start the activity
                mContext.startActivity(intent);

            }
        });



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
            super(itemView);

            name = (TextView) itemView.findViewById( R.id.title_id) ;
            img_thumbnail = (ImageView) itemView.findViewById( R.id.img_id);
            //  cardView = (CardView) itemView.findViewById( R.id.cardview_id);
            relativeLy= itemView.findViewById( R.id.relativl);
            modif=itemView.findViewById ( R.id.modifier_article );
            supp=itemView.findViewById ( R.id.supprimer_article );


        }
    }


}
