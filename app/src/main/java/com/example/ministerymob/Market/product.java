package com.example.ministerymob.Market;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public class product implements Serializable {

    private String name;
    private String type ;
    private String Description ;
    private String price ;
    private String nomUser ;
    private Long numTel;
    private String id;
    private String date_creation;
    private String adresseInput;
    private static int nbSignal;


    int offered =0;  // 0 if offered, 1 if needed
    private String EmailUser ;
    private Adresse adresse;
    private String img;

    public product() {

    }

    public String getAdresseInput() {
        return adresseInput;
    }

    public void setAdresseInput(String adresseInput) {
        this.adresseInput = adresseInput;
    }

    public String getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(String date_creation) {
        this.date_creation = date_creation;
    }

    public product(product p)
    {
        this.setType ( p.getType () );
        this.setName ( p.getName () );
        this.setDescription ( p.getDescription () );
        this.setEmailUser ( p.getEmailUser () );
        this.setImg ( p.getImg () );
        this.setNomUser ( p.getNomUser () );
        this.setNumTel ( p.getNumTel () );
        this.setPrice ( p.getPrice () );
        this.setAdresse ( p.getAdresse () );
        this.setNbSignal ( 0 );
        this.setOffered ( p.getOffered () );
        this.setDate_creation ( p.getDate_creation () );
        this.setAdresseInput ( p.getAdresseInput () );
        this.setId ( p.getId () );
    }
    public product(String name, String type, String description, String price, String nomUser, String emailUser,Long num,Adresse adresse,String img) {
        this.setName ( name );
        this.setType ( type );
        this.setDescription ( description );
        this.setEmailUser ( emailUser );
        this.setImg ( img );
        this.setNomUser ( nomUser );
        this.setNumTel ( num );
        this.setPrice ( price );
        this.setAdresse ( adresse );
        this.setNbSignal ( 0 );



    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }

    public int getOffered() {
        return offered;
    }

    public void setOffered(int offered) {
        this.offered = offered;
    }

    public static int getNbSignal() {
        return nbSignal;
    }

    public static void setNbSignal(int nbSignal) {
        product.nbSignal = nbSignal;
    }

    public Long getNumTel() {
        return numTel;
    }

    public void setNumTel(Long numTel) {
        this.numTel = numTel;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


    public void setName(String title) {
        name = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNomUser() {
        return nomUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public String getEmailUser() {
        return EmailUser;
    }

    public void setEmailUser(String emailUser) {
        EmailUser = emailUser;
    }


    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }
}


