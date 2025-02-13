package com.example.TP_2.bib;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> articles = new ArrayList<>();

    // Constructeurs
    public Commande() {}

    public Commande(String description, User user) {
        this.description = description;
        this.user = user;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Article> getArticles() {
        return articles;
    }

     //Méthodes pour gérer les articles
    public void addArticle(String nom, int quantite, double prix) {
        articles.add(new Article(nom, quantite, prix, this));
    }

    public void removeArticle(Long articleId) {
        articles.removeIf(article -> article.getId().equals(articleId));
    }
}
