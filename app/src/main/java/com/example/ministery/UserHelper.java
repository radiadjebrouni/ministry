package com.example.ministery;


import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ministery.Market.product;
import com.example.ministery.Model.Key;
import com.example.ministery.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UserHelper {

    private static final String USER_COLLECTION_NAME = "users";
    private static final String CREATED_ARTICLES_COLLECTION_NAME = "created_articles";
    private static final String FAVORIT_ARTICLES_COLLECTION_NAME = "favorit_articles";

    //Collection reference
    public static CollectionReference getUsersCollection()
    {
        return FirebaseFirestore.getInstance().collection(USER_COLLECTION_NAME);
    }

    // -- CREAT --
    public static Task<Void> insertUser(User user)
    {
        String uid = user.getUserId();
        return UserHelper.getUsersCollection().document(uid).set(user);
    }

    // -- GET --
    public static Task<DocumentSnapshot> getUser(String uid)
    {
        return UserHelper.getUsersCollection().document(uid).get();
    }



    // -- UPDATE
    public static Task<Void> updateUserUsername(String uid, String newUsername)
    {
        return UserHelper.getUsersCollection().document(uid).update("username",newUsername);
    }

    // -- UPDATE
    public static Task<Void> updateUserProfilePic(String uid, String profilepic)
    {
        return UserHelper.getUsersCollection().document(uid).update("profilePic",profilepic)
                .addOnCompleteListener ( new OnCompleteListener<Void> () {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                } );
    }


    public static Task<Void> updateUsernSignal(String uid, String id, int nbSignal)
    {
        return UserHelper.getUsersCollection().document(uid).collection ( CREATED_ARTICLES_COLLECTION_NAME )
                .document (id).update("nbSignal",nbSignal)
                .addOnSuccessListener ( new OnSuccessListener<Void> () {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                } ).addOnFailureListener ( new OnFailureListener () {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                } );
    }
    public static Task<Void> updateidSignaleur(String uid, String id, ArrayList<String> ids)
    {
        return UserHelper.getUsersCollection().document(uid).collection ( CREATED_ARTICLES_COLLECTION_NAME )
                .document (id).update("isSignaleurs",ids)
                .addOnSuccessListener ( new OnSuccessListener<Void> () {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                } ).addOnFailureListener ( new OnFailureListener () {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                } );
    }

    public static Task<Void> updateUserNbArticlesTotal(String uid, int newNb)
    {
        return UserHelper.getUsersCollection().document(uid).update("nbrArticlesTotal",newNb);
    }

    public static Task<Void> updateArtcle(String id,String uid, product p)
    {
        return UserHelper.getUsersCollection().document(uid).collection ( CREATED_ARTICLES_COLLECTION_NAME )
        .document (id).set ( p ). addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // on successful completion of this process
                        // we are displaying the toast message
                     Log.i ( "moddd","success" );
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    // inside on failure method we are
                    // displaying a failure message.
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i ( "moddd","fail "+e.getMessage () );

                    }
                });

    }


    // -- DELETE --
    public static Task<Void> deleteUser(String uid)
    {
        return UserHelper.getUsersCollection().document(uid).delete();
    }

    // -- ADDING CREATED ARTICLE --
    public static Task<Void> addArticleToUser(String uid, String articleId, String dateCreation)
    {
        Key key = new Key(articleId,dateCreation);
        return UserHelper.getUsersCollection().document(uid)
                .collection(CREATED_ARTICLES_COLLECTION_NAME).document(articleId).set(key);
    }

    // -- GETTING CREATED ARTICLES --
    public static Task<QuerySnapshot> gerArticlesFromUser(String uid,Integer offerOrNeed)
    {
        if(offerOrNeed==null) {
            return UserHelper.getUsersCollection ().document ( uid )
                    .collection ( CREATED_ARTICLES_COLLECTION_NAME ).get ();
        }
        else {if( offerOrNeed==0){
            return UserHelper.getUsersCollection ().document ( uid )
                    .collection ( CREATED_ARTICLES_COLLECTION_NAME ).whereEqualTo ( "offered",0 ).get ();
        }else {
            return UserHelper.getUsersCollection ().document ( uid )
                    .collection ( CREATED_ARTICLES_COLLECTION_NAME ).whereEqualTo ( "offered",1 ).get ();
        }
}
    }

    // -- GETTING CREATED ARTICLES --
    public static Task<QuerySnapshot> getAllArticles(int offer)
    {
        return UserHelper.getUsersCollection().document()
                .collection(CREATED_ARTICLES_COLLECTION_NAME).whereEqualTo ( "offered",offer ).get();
    }

    // -- DELETING CREATED ARTICLE --
    public static Task<Void> deleteArticleFromUser(String uid, String articleId)
    {
        return UserHelper.getUsersCollection().document(uid)
                .collection(CREATED_ARTICLES_COLLECTION_NAME).document(articleId).delete()
                .addOnSuccessListener ( new OnSuccessListener<Void> () {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                } ).addOnFailureListener ( new OnFailureListener () {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                } );
    }

    // -- ADDING FAVORITE ARTICLE --
    public static Task<Void> addFavoriteToUser(String uid, String articleId, String dateAjoutFav)
    {
        Key key = new Key(articleId,dateAjoutFav);
        return UserHelper.getUsersCollection().document(uid)
                .collection(FAVORIT_ARTICLES_COLLECTION_NAME).document(articleId).set(key);
    }

    // -- GETTING FAVORITE ARTICLES
    public static Task<QuerySnapshot> getFavoritesFromUser(String uid)
    {
        return UserHelper.getUsersCollection().document(uid)
                .collection(FAVORIT_ARTICLES_COLLECTION_NAME).get();
    }
    public static Task<DocumentSnapshot> getSignaleursFromProduct(String uid, String id)
    {
        return UserHelper.getUsersCollection().document(uid)
                .collection(CREATED_ARTICLES_COLLECTION_NAME).document (id).get();
    }

    // -- DELETING FAVORITE FROM USER --
    public static Task<Void> deleteFavoriteFromUser(String uid, String articleId)
    {
        return UserHelper.getUsersCollection().document(uid)
                .collection(FAVORIT_ARTICLES_COLLECTION_NAME).document(articleId).delete();
    }


}