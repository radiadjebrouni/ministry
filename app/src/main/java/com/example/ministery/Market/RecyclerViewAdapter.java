package com.example.ministery.Market;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.ministery.R;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext ;
    private List<product> mData ;


    public RecyclerViewAdapter(Context mContext, List<product> mData) {
        this.mContext = mContext;
        this.mData = mData;
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

        holder.name.setText(mData.get(position).getName ());
        holder.img_thumbnail.setImageResource ( mContext.getResources().getIdentifier(mData.get(position).getImg (), "drawable", mContext.getPackageName()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
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
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById( R.id.title_id) ;
            img_thumbnail = (ImageView) itemView.findViewById( R.id.img_id);
            cardView = (CardView) itemView.findViewById( R.id.cardview_id);


        }
    }


}
