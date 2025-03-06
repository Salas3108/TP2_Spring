package com.example.TP_2.bib;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CommandeService implements CommandeItf {

    @Autowired
    private CommandeRepository commandeRepository;

    @Override
    public Commande creerCommande(String description, User user) {
        Commande commande = new Commande(description, user);
        return commandeRepository.save(commande);
    }

    @Override
    public List<Commande> getCommandesByUser(User user) {
        return commandeRepository.findByUser(user);
    }

    // Méthode pour récupérer une commande par son ID
    public Optional<Commande> getCommandeById(Long commandeId) {
        return commandeRepository.findById(commandeId);
    }

    // Méthode pour ajouter un article à une commande (si nécessaire)
    public void addArticleToCommande(Long commandeId, String nom, int quantite, double prix) {
        Optional<Commande> commandeOpt = commandeRepository.findById(commandeId);
        if (commandeOpt.isPresent()) {
            Commande commande = commandeOpt.get();
            commande.addArticle(nom, quantite, prix);
            commandeRepository.save(commande);
        }
    }
    
    
    // Méthode pour supprimer un article à une commande
    @Override
    public void supprimerArticleDeCommande(Long commandeId, Long articleId) {
        Optional<Commande> commandeOpt = commandeRepository.findById(commandeId);
        if (commandeOpt.isPresent()) {
            Commande commande = commandeOpt.get();
            commande.getArticles().removeIf(article -> article.getId().equals(articleId));
            commandeRepository.save(commande);
        }
    }
    
    

}
