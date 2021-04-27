package com.example.ministery.Model;


public class Key {
    private String val; //val of ID
    private String dateAjout; //date of creation or adding to fav


    public Key(String val, String date)
    {
        this.val = val;
        this.val = val;
        this.dateAjout = date;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(String dateAjout) {
        this.dateAjout = dateAjout;
    }
}

