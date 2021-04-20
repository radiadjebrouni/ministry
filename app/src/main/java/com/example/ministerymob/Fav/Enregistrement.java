package com.example.ministerymob.Fav;

import com.example.ministerymob.Market.Adresse;
import com.example.ministerymob.Market.product;

import java.util.Date;

public class Enregistrement extends product {

    private Date dateEnregistrement;

    public Date getDateEnregistrement() {
        return dateEnregistrement;
    }

    public void setDateEnregistrement(Date dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
    }

    public Enregistrement(String name, String type, String description, String price, String nomUser, String emailUser, int num, Adresse adresse, String img) {
        super ( name, type, description, price, nomUser, emailUser, num, adresse, img );
    }

    public Enregistrement(product p,Date date) {
        this.dateEnregistrement=date;
    }

    @Override
    public int getNumTel() {
        return super.getNumTel ();
    }

    @Override
    public void setNumTel(int numTel) {
        super.setNumTel ( numTel );
    }

    @Override
    public String getImg() {
        return super.getImg ();
    }

    @Override
    public void setImg(String img) {
        super.setImg ( img );
    }

    @Override
    public String getName() {
        return super.getName ();
    }

    @Override
    public void setTitle(String title) {
        super.setTitle ( title );
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
