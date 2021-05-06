package com.example.ministery.Profile;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ministery.Market.Adresse;
import com.example.ministery.Market.RecyclerViewAdapter;
import com.example.ministery.Market.product;
import com.example.ministery.Model.User;
import com.example.ministery.R;
import com.example.ministery.UserHelper;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.ministery.Profile.App.CHANNEL_1_ID;

public class ProfileFragment extends Fragment {
    private static ArrayList<product> MyProducts;
    Adresse a= new Adresse ("latitude","emptitude");
    private Button param;
    private AppCompatTextView nom;
    private AppCompatTextView email;
    private NotificationManagerCompat notificationManager;

    public static final String CHANNEL_ID = "channel_id";
    public static final String CHANNEL_NAME = "channel_name";
    public static final String NOTIFICATION_TYPE = "type_notif";
    public static String NOTIFICATION_ID = "notification-id" ;
    /* public static String NOTIFICATION_TITLE = "notif titre" ;
     public static String NOTIFICATION_CONTENT = "notif_contenu";*/
    private Notification notification;
    private FirebaseAuth auth= FirebaseAuth.getInstance ();
    private  static User u=new User (  );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view =inflater.inflate ( R.layout.profile_fragment,container ,false);
        nom=view.findViewById ( R.id.nom_user );
        email=view.findViewById ( R.id.email_user );
        param=view.findViewById ( R.id.param_button );
        param.setBackgroundResource ( R.drawable.bouton_commencer );

        MyProducts = new ArrayList<> (  );


        Log.i ( "proo", auth.getCurrentUser ().getUid () + "" );

        Task<DocumentSnapshot> du = UserHelper.getUser (auth.getCurrentUser ().getUid () );
        du.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot> () {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                u = du.getResult ().toObject ( User.class );

                Log.i ( "proo", u.getEmail () + "" );
                /*********************
                 * TODO (DONE )display the users main info
                 */

                nom.setText ( u.getUsername () );
                email.setText ( u.getEmail () );
            }   }).addOnFailureListener ( new OnFailureListener () {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("proo",e.getMessage ());
            }
        } );


        /************************
         *
         * TODO (DONE)
         * Geting my products from db
         */

        Task<QuerySnapshot> da = UserHelper.gerArticlesFromUser (auth.getCurrentUser ().getUid (),null );
        da.addOnSuccessListener(new OnSuccessListener<QuerySnapshot> () {
            @Override
            public void onSuccess(QuerySnapshot querySnapshot) {
                Log.i("myarticl","success");
                for (DocumentSnapshot d : querySnapshot){

                    /****todo check the number of signals***/
                    if(d.toObject ( product.class ) .getNbSignal ()>=10){
                        //delete the article
                        //send notif
                        notificationManager = NotificationManagerCompat.from(getActivity ().getApplicationContext ());
                        sendOnChannel ( view );

                        //delete the article
                        UserHelper.deleteArticleFromUser ( auth.getCurrentUser ().getUid (),d.toObject ( product.class ) .getIdd () );

                    }

                    else {
                        MyProducts.add ( d.toObject ( product.class ) );
                        Log.i ( "myarticl", "success " + d.toObject ( product.class ).getName () );
                    }
                }
                MyProducts.add ( new product ( "title","type","description","price","nom user","email user",0123l,a,null  ));


                LinearLayoutManager linearLayoutManager =new LinearLayoutManager ( getContext (),LinearLayoutManager.HORIZONTAL,false );
                RecyclerView myrv = (RecyclerView)view. findViewById(R.id.recyclerview_profile);
                RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getActivity (),MyProducts,true);

                myrv.setLayoutManager ( linearLayoutManager );
                myrv.setAdapter(myAdapter);
                myrv.setItemAnimator (new DefaultItemAnimator() );

            }   }).addOnFailureListener ( new OnFailureListener () {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("proo",e.getMessage ());
            }
        } );


        param.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent para=new Intent ( getActivity (),parametreActivity.class );
                startActivity ( para );
            }
        } );



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated ( view, savedInstanceState );


    }

    public void sendOnChannel(View v) {

       /* Notification notification = new NotificationCompat.Builder( v.getContext (), CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_signaler)
                .setContentTitle(getString( R.string.signal_detectes))
                .setContentText(getString( R.string.signal_msg))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1, notification);*/
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification notification = new NotificationCompat.Builder(v.getContext (), CHANNEL_ID)

            .setContentTitle(getString( R.string.signal_detectes))
            //  builder.setContentText(description);
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSmallIcon(R.drawable.ic_launcher_foreground)

            .setSound(alarmSound)
            .setOnlyAlertOnce ( true )
            .setAutoCancel(true)

            .setChannelId(CHANNEL_ID)
            .setContentText (getString( R.string.signal_msg)).build ();


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(v.getContext ());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            channel.setLightColor( Color.BLUE);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{500, 1000});
            notificationManager.createNotificationChannel(channel);
        }

        //assert notificationManager != null;
//        Log.i("NotifsR", String.valueOf(notification));
        notificationManager.notify(1, notification) ;
        }

        private void notifier (Context context){


        }

    }
