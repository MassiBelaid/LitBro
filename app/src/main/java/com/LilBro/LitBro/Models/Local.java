package com.LilBro.LitBro.Models;

import java.io.Serializable;

public class Local implements Serializable {
    private String nomLocal;
    private String categorie;
    private String image;
    private String live;
    private String surveillant;
    private String telephone;
    private String utilisateurProp;
    private String zone;


    public Local(){}
    public Local(String nom){
        this.nomLocal = nom;
    }
    public Local(String nom,String categorie, String image, String live, String surv, String tel, String uP, String zone){
        this.nomLocal = nom;
        this.categorie = categorie;
        this.image = image;
        this.live = live;
        this.surveillant = surv;
        this.telephone = tel;
        this.utilisateurProp = uP;
        this.zone = zone;
    }


    public String getNomLocal(){
        return this.nomLocal;
    }
    public void setNomLocal(String nom){
        this.nomLocal = nom;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLive(String live) {
        this.live = live;
    }

    public void setSurveillant(String surveillant) {
        this.surveillant = surveillant;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setUtilisateurProp(String utilisateurProp) {
        this.utilisateurProp = utilisateurProp;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getCategorie() {
        return categorie;
    }

    public String getImage() {
        return image;
    }

    public String getLive() {
        return live;
    }

    public String getSurveillant() {
        return surveillant;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getUtilisateurProp() {
        return utilisateurProp;
    }

    public String getZone() {
        return zone;
    }
}
