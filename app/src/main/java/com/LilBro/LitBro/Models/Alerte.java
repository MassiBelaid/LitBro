package com.LilBro.LitBro.Models;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class Alerte implements Serializable {
    private Date date;
    private String local;
    private String utilisateur;
    private String utilisateurProp;
    private String vidAlerte;


    public Alerte() {
    }

    public Alerte(Date date, String local, String utilisateur, String utilisateurProp, String vidAlerte) {
        this.date = date;
        this.local = local;
        this.utilisateur = utilisateur;
        this.utilisateurProp = utilisateurProp;
        this.vidAlerte = vidAlerte;
    }

    public String dateToString(){
        Date d = this.getDate();
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM,new Locale("FR","fr"));
        return df.format(d);
    }


    public Date getDate() {
        return date;
    }

    public String getLocal() {
        return local;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public String getUtilisateurProp() {
        return utilisateurProp;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public void setUtilisateurProp(String utilisateurProp) {
        this.utilisateurProp = utilisateurProp;
    }

    public String getVidAlerte() {
        return vidAlerte;
    }

    public void setVidAlerte(String vidAlerte) {
        this.vidAlerte = vidAlerte;
    }
}
