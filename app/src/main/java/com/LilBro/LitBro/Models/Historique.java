package com.LilBro.LitBro.Models;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Historique implements Serializable {

    private Date date;
    private String local;
    private String utilisateur;
    private String utilisateurProp;

    public Historique() {}

    public Historique(Date date, String local, String utilisateur, String utilisateurProp) {
        this.date = date;
        this.local = local;
        this.utilisateur = utilisateur;
        this.utilisateurProp = utilisateurProp;
    }

    public String dateToString(){
        Date d = this.getDate();
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM,new Locale("FR","fr"));
        return df.format(d);
    }

    public Date getDate() {
        return this.date;
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
}
