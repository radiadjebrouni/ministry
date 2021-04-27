package com.example.ministery.Fav;

import android.net.Uri;

import com.example.ministery.Market.Adresse;
import com.example.ministery.Market.product;

public class Enregistrement extends product {

    private String dateEnregistrement;
    private String IdEnreg;
   // private String idEnregistreur;

    public String getDateEnregistrement() {
        return dateEnregistrement;
    }

    public void setDateEnregistrement(String dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
    }

    public Enregistrement(String name, String type, String description, String price, String nomUser, String emailUser, Long num, Adresse adresse, String img) {
        super ( name, type, description, price, nomUser, emailUser, num, adresse, img );
    }

    public Enregistrement(product p,String date) {
        this.dateEnregistrement=date;
        this.setType ( p.getType () );
        this.setName ( p.getName () );
        this.setDescription ( p.getDescription () );
        this.setEmailUser ( p.getEmailUser () );
        //this.setImg ( p.getImg () );
        this.setNomUser ( p.getNomUser () );
        this.setNumTel ( p.getNumTel () );
        this.setPrice ( p.getPrice () );
        this.setAdresse ( p.getAdresse () );
        this.setNbSignal ( 0 );
        this.setOffered ( p.getOffered () );
        this.setDate_creation ( p.getDate_creation () );
        this.setAdresseInput ( p.getAdresseInput () );
        this.setIdEnreg ( p.getIdd () );
      //  this.setIdEnregistreur ( p.get );

    }

  /*  public String getIdEnregistreur() {
        return idEnregistreur;
    }

    public void setIdEnregistreur(String idEnregistreur) {
        this.idEnregistreur = idEnregistreur;
    }
*/
    public Enregistrement(){}
    public String getIdEnreg() {
        return IdEnreg;
    }

    public void setIdEnreg(String idEnreg) {
        IdEnreg = idEnreg;
    }

    @Override
    public Long getNumTel() {
        return super.getNumTel ();
    }

    @Override
    public void setNumTel(Long numTel) {
        super.setNumTel ( numTel );
    }



    @Override
    public String getName() {
        return super.getName ();
    }

    @Override
    public String getType() {
        return super.getType ();
    }

    @Override
    public void setType(String type) {
        super.setType ( type );
    }

    @Override
    public String getDescription() {
        return super.getDescription ();
    }

    @Override
    public void setDescription(String description) {
        super.setDescription ( description );
    }

    @Override
    public String getPrice() {
        return super.getPrice ();
    }

    @Override
    public void setPrice(String price) {
        super.setPrice ( price );
    }

    @Override
    public String getNomUser() {
        return super.getNomUser ();
    }

    @Override
    public void setNomUser(String nomUser) {
        super.setNomUser ( nomUser );
    }

    @Override
    public String getEmailUser() {
        return super.getEmailUser ();
    }

    @Override
    public void setEmailUser(String emailUser) {
        super.setEmailUser ( emailUser );
    }

    @Override
    public Adresse getAdresse() {
        return super.getAdresse ();
    }

    @Override
    public void setAdresse(Adresse adresse) {
        super.setAdresse ( adresse );
    }
}
