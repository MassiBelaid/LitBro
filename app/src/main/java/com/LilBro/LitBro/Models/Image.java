package com.LilBro.LitBro.Models;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class Image implements Serializable {
    private String nomLocal;
    private Date date;
    private String addrImage;

    public Image(){}

    public Image(String nomLocal, Date date, String addrImage) {
        this.nomLocal = nomLocal;
        this.date = date;
        this.addrImage = addrImage;
    }

    public String getNomLocal() {
        return nomLocal;
    }

    public void setNomLocal(String nomLocal) {
        this.nomLocal = nomLocal;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddrImage() {
        return addrImage;
    }

    public void setAddrImage(String addrImage) {
        this.addrImage = addrImage;
    }

    public String dateToString(){
        Date d = this.getDate();
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM,new Locale("FR","fr"));
        return df.format(d);
    }

}
