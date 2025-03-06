package com.example.TP_2.bib;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/commandes")
public class CommandeController {

    @Autowired
    private CommandeItf commandeService;

    @Autowired
    private UserRepository userRepository;

    
    
    // Créer une commande
    @PostMapping("/ajouter")
    public RedirectView creerCommande(@RequestParam String description, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        
        if (userId == null) {
            return new RedirectView("/home"); // Redirection si l'utilisateur n'est pas connecté
        }

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            Commande commande = commandeService.creerCommande(description, userOpt.get());
            return new RedirectView("/commandes/" + commande.getId()); // Redirection vers la commande
        }

        return new RedirectView("/commandes/"); // Si l'utilisateur n'existe pas
    }

    // Afficher la page d'ajout de produits pour une commande spécifique
    @GetMapping("/{commandeId}")
    public ModelAndView afficherPageAjoutProduit(@PathVariable Long commandeId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null || !estProprietaireDeCommande(userId, commandeId)) {
            return new ModelAndView("redirect:/home"); // Redirection si non connecté ou non propriétaire
        }

        Optional<Commande> commandeOpt = commandeService.getCommandeById(commandeId);
        ModelAndView modelAndView = new ModelAndView();

        if (commandeOpt.isPresent()) {
            Commande commande = commandeOpt.get();
            modelAndView.addObject("commande", commande);
            modelAndView.addObject("articles", commande.getArticles()); // Récupérer la liste des articles
            modelAndView.setViewName("ajout-article");
        } else {
            modelAndView.setViewName("redirect:/commandes/");
        }

        return modelAndView;
    }


    // Ajouter un produit à la commande
    @PostMapping("/{commandeId}/ajout-article")
    public RedirectView ajouterProduit(
            @PathVariable Long commandeId,
            @RequestParam String nom,
            @RequestParam int quantite,
            @RequestParam int prix,
            HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null || !estProprietaireDeCommande(userId, commandeId)) {
            return new RedirectView("/home");
        }

        // Ajouter l'article à la commande
        commandeService.addArticleToCommande(commandeId, nom, quantite, prix);

        return new RedirectView("/commandes/" + commandeId); // Redirection vers la commande
    }
    
 // Supprimer un article d'une commande
    @PostMapping("/{commandeId}/supprimer-article/{articleId}")
    public RedirectView supprimerArticle(
            @PathVariable Long commandeId,
            @PathVariable Long articleId,
            HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return new RedirectView("/home"); // Redirection si l'utilisateur n'est pas connecté
        }

        commandeService.supprimerArticleDeCommande(commandeId, articleId);

        return new RedirectView("/commandes/" + commandeId); // Redirection vers la commande après suppression
    }
    
 // Afficher la page d'impression des articles d'une commande
    @GetMapping("/{commandeId}/imprimer")
    public ModelAndView afficherPageImpression(@PathVariable Long commandeId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null || !estProprietaireDeCommande(userId, commandeId)) {
            return new ModelAndView("redirect:/home");
        }

        Optional<Commande> commandeOpt = commandeService.getCommandeById(commandeId);
        ModelAndView modelAndView = new ModelAndView();
        if (commandeOpt.isPresent()) {
            modelAndView.addObject("commande", commandeOpt.get());
            modelAndView.setViewName("impression-articles");
        } else {
            modelAndView.setViewName("redirect:/commandes/");
        }

        return modelAndView;
    }
    
    
    
    private boolean estProprietaireDeCommande(Long userId, Long commandeId) {
        Optional<Commande> commandeOpt = commandeService.getCommandeById(commandeId);
        return commandeOpt.isPresent() && commandeOpt.get().getUser().getId() == userId;
    }


}
