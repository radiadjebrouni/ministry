package com.example.ministerymob.Market;

public class product {

    private String name;
    private String type ;
    private String Description ;
    private String price ;
    private String nomUser ;
    private int numTel;

    int offered =0;  // 0 if offered, 1 if needed
    private String EmailUser ;
    private Adresse adresse;
    private String img;


    public product(String name, String type, String description, String price, String nomUser, String emailUser,int num,Adresse adresse,String img) {
        name = name;
        this.type = type;
        Description = description;
        this.price = price;
        this.nomUser = nomUser;
        EmailUser = emailUser;
         this.adresse=adresse;
        this. img=img;
        this.numTel=num;

    }


    public int getNumTel() {
        return numTel;
    }

    public void setNumTel(int numTel) {
        this.numTel = numTel;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setTitle(String title) {
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


