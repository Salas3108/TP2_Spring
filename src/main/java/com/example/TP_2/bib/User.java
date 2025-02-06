package com.example.TP_2.bib;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String email;
    private String mdp;
    private String nom;
    private String prenom;

    // Constructeur sans argument (obligatoire pour JPA)
    public User() {
    }

    // Constructeur avec arguments
    public User(String email, String mdp, String nom, String prenom) {
    	this.email = email;
        this.mdp = mdp;
    	this.nom = nom;
        this.prenom = prenom;
    }

    // Getters
    public long getId() {
        return id;
    }
    
    public String getEmail() {
        return email;
    }

    public String getMdp() {
        return mdp;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    // Setters
    public void setId(long id) {
        this.id = id;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}



