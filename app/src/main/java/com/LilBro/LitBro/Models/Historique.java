package com.LilBro.LitBro.Models;

import java.io.Serializable;
import java.util.Date;

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
}
