package com.LilBro.LitBro.Models;

import java.io.Serializable;
import java.util.Date;

public class Utilisateur implements Serializable {
    private String login;
    private String motDePasse;
    private String utilisateurType;
    private Date dateDernierChangement;
    private Boolean modifLogin;

    public Utilisateur() {}

    public Utilisateur (String login, String motDePasse, String utilisateurType, Date date, Boolean modifLogin){
        this.login = login;
        this.motDePasse = motDePasse;
        this.utilisateurType = utilisateurType;
        this.dateDernierChangement = date;
        this.modifLogin = modifLogin;
    }

    public void setLogin(String login){
        this.login = login;
    }
    public String getLogin(){
        return this.login;
    }

    public void setMotDePasse(String mdp){
        this.motDePasse = mdp;
    }
    public String getMotDePasse(){
        return this.motDePasse;
    }

    public void setUtilisateurType(String type){
        this.utilisateurType = type;
    }
    public String getUtilisateurType(){
        return this.utilisateurType;
    }

    public Date getDateDernierChangement(){
        return this.dateDernierChangement;
    }
    public void setDateDernierChangement(Date dateDernierChangement) {
        this.dateDernierChangement = dateDernierChangement;
    }

    public Boolean getModifLogin() {
        return modifLogin;
    }
    public void setModifLogin(Boolean modifLogin) {
        this.modifLogin = modifLogin;
    }
}
