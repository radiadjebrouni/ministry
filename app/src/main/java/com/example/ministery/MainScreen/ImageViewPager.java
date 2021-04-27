package com.example.ministery.MainScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ministery.Market.AjouterProductActivity;
import com.example.ministery.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class ImageViewPager extends Fragment implements AjouterProductActivity.passData {

    // widgets
    private ImageView mImage;
    private TextView mTitle, mPrice;

    // vars
    private ImageFirstScreen mHat;

    public static ImageViewPager getInstance(ImageFirstScreen hat){
        ImageViewPager fragment = new ImageViewPager();

        if(hat != null){
            Bundle bundle = new Bundle();
            bundle.putParcelable("hat", hat);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mHat = getArguments().getParcelable("hat");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate( R.layout.image_view_pager_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mImage = view.findViewById(R.id.image);

        init();
    }

    private void init(){
        if(mHat != null){
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);

            Glide.with(getActivity())
                    .setDefaultRequestOptions(requestOptions)
                    .load(mHat.getImage())
                    .into(mImage);

        }
    }

    @Override
    public void apply(FirebaseFirestore db) {

    }
}

