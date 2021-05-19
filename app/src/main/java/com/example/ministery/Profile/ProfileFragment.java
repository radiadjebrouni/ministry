package com.example.ministery.Profile;

import android.Manifest;
import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ministery.Market.Adresse;
import com.example.ministery.Market.AjouterProductActivity;
import com.example.ministery.Market.DownLoadImageTask;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.example.ministery.Profile.App.CHANNEL_1_ID;

public class ProfileFragment extends Fragment {
    private static ArrayList<product> MyProducts;
    Adresse a= new Adresse ("latitude","emptitude");
    private Button param;
    private AppCompatTextView nom;
    private AppCompatTextView email;
    private TextView vide;
    private CircleImageView profilepic;
    private NotificationManagerCompat notificationManager;

    private String pic;
    public static final String CHANNEL_ID = "channel_id";
    public static final String CHANNEL_NAME = "channel_name";
    public static final String NOTIFICATION_TYPE = "type_notif";
    public static String NOTIFICATION_ID = "notification-id" ;
    /* public static String NOTIFICATION_TITLE = "notif titre" ;
     public static String NOTIFICATION_CONTENT = "notif_contenu";*/
    private Notification notification;
    private FirebaseAuth auth;
    private  static User u=new User (  );


    private static final int REQUEST_GALLERY = 0;
    private static final int REQUEST_CAMERA = 1;
    private static final int MY_PERMISSIONS_REQUESTS = 0;
    private static final int RC_HANDLE_CAMERA_PERM = 2;
    public static Uri imageUri;
    public static Uri imagedata;
    public  String img="";
    private StorageReference mStorageRef;

    public FirebaseFirestore db = FirebaseFirestore.getInstance ();
    public CollectionReference notebookRef ;
    public CollectionReference uploads=db.collection ( "uploads" ) ;

    private  FirebaseAuth.AuthStateListener mAuthListener;
    private   String UserId;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view =inflater.inflate ( R.layout.profile_fragment,container ,false);
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");

        nom=view.findViewById ( R.id.nom_user );
        email=view.findViewById ( R.id.email_user );
        param=view.findViewById ( R.id.param_button );
        param.setBackgroundResource ( R.drawable.bouton_commencer );
        vide=view.findViewById ( R.id.avide );
        vide.setVisibility ( View.GONE );

        MyProducts = new ArrayList<> (  );
        auth= FirebaseAuth.getInstance ();

//        Log.i ( "proo", auth.getCurrentUser ().getUid () + "" );
       /* mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                     UserId = user.getUid();
                    Toast.makeText( getContext (), "USER ID\n"+UserId,Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext (), "no id got", Toast.LENGTH_SHORT).show();
                }

            }
        };*/
        String uid;
        if(auth.getCurrentUser ()!=null
        &&(getActivity ().getIntent ().getExtras ()==null||getActivity ().getIntent ().getExtras ().getString ( "id" )==null) ) uid=auth.getCurrentUser ().getUid ();
        else uid=getActivity ().getIntent ().getExtras ().getString ( "id" );

        Task<DocumentSnapshot> du = UserHelper.getUser (uid );
        du.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot> () {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                u = du.getResult ().toObject ( User.class );

                assert u != null;
//                Log.i ( "proo", u.getEmail () + "" );
                /*********************
                 * TODO (DONE )display the users main info
                 */

                nom.setText ( u.getUsername () );
                email.setText ( u.getEmail () );
                if( u.getProfilePic ()!=null && !u.getProfilePic ().equals ( "" ))
                    new DownLoadImageTask (profilepic).execute(u.getProfilePic ());

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

        Task<QuerySnapshot> da = UserHelper.gerArticlesFromUser (uid,null );
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

                if(MyProducts.size ()>0){
                    LinearLayoutManager linearLayoutManager =new LinearLayoutManager ( getContext (),LinearLayoutManager.HORIZONTAL,false );
                    RecyclerView myrv = (RecyclerView)view. findViewById(R.id.recyclerview_profile);
                    RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getActivity (),MyProducts,true);

                    myrv.setLayoutManager ( linearLayoutManager );
                    myrv.setAdapter(myAdapter);
                    myrv.setItemAnimator (new DefaultItemAnimator() );
                }
                else {
                    vide.setVisibility ( View.VISIBLE );
                }



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




        profilepic=view.findViewById ( R.id.profile_image );
        profilepic.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                selectImage ( view.getContext () );
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

    private void selectImage(final Context context) {
        final CharSequence[] options = {"Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder ( getActivity ());
        builder.setTitle ( "Add Photo!" );
        builder.setItems ( options, new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialog, int item) {
               if (options[item].equals ( "Choose from Gallery" )) {
/*
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                    */

                    Intent intent = new Intent ();
                    intent.setType ( "image/*" );
                    intent.setAction ( Intent.ACTION_GET_CONTENT );
                    startActivityForResult ( intent, REQUEST_GALLERY );

                } else if (options[item].equals ( "Cancel" )) {
                    dialog.dismiss ();
                }
            }
        } );
        builder.show ();
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult ( requestCode, resultCode, data );
        switch (requestCode) {
            case REQUEST_GALLERY:
                if (resultCode == RESULT_OK) {
                    profilepic.setImageURI ( null );
                    profilepic.setImageURI ( data.getData () );
                    imagedata=data.getData ();
                }
                break;

            /*********************
             * todo (done ) add the image uri the new product data
             */

        }  uploadFile();
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContext ().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile() {
        if (imagedata != null) {
            Log.i ( "urrrr","yrrrrrrrr" );
            StorageReference fileReference = mStorageRef.child ( System.currentTimeMillis ()
                    + "." + getFileExtension ( imagedata ) );
            final UploadTask uploadTask = fileReference.putFile ( imagedata );
            uploadTask.addOnSuccessListener ( new OnSuccessListener<UploadTask.TaskSnapshot> () {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    uploadTask.continueWithTask ( new Continuation<UploadTask.TaskSnapshot, Task<Uri>> () {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful ()) {
                                throw task.getException ();

                            }
                            // Continue with the task to get the download URL
                            return fileReference.getDownloadUrl ();

                        }
                    } ).addOnCompleteListener ( new OnCompleteListener<Uri> () {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful ()) {
                                img = task.getResult ().toString ();
                                Log.i ( "loog",img+ "loo" );

                                //update firstore

                                UserHelper.updateUserProfilePic ( auth.getCurrentUser ().getUid (),img );



                            }
                        }
                    } );

                }
            } ).addOnFailureListener ( new OnFailureListener () {
                @Override
                public void onFailure(@NonNull Exception e) {


                }
            } );
        }
    }


}

