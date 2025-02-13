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
        if (userId == null) {
            return new ModelAndView("redirect:/home"); // Redirection si l'utilisateur n'est pas connecté
        }

        Optional<Commande> commandeOpt = commandeService.getCommandeById(commandeId);
        ModelAndView modelAndView = new ModelAndView();
        if (commandeOpt.isPresent()) {
            modelAndView.addObject("commande", commandeOpt.get());
            modelAndView.setViewName("ajout-article");
        } else {
            modelAndView.setViewName("redirect:/commandes/");
        }

        return modelAndView;
    }

    // Ajouter un produit à la commande
    @PostMapping("/{commandeId}/ajout-article")
    public RedirectView ajouterProduit(@PathVariable Long commandeId, @RequestParam String nom, @RequestParam int quantite, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return new RedirectView("/home"); // Redirection si l'utilisateur n'est pas connecté
        }

        Optional<Commande> commandeOpt = commandeService.getCommandeById(commandeId);
        
        if (commandeOpt.isPresent()) {
            Commande commande = commandeOpt.get();
            commande.addArticle(nom, quantite, 10.0); // Ajout d'un produit avec un prix fixe de 10.0
            commandeService.creerCommande(commande.getDescription(), commande.getUser()); // Sauvegarde
        }
        
        return new RedirectView("/commandes/" + commandeId); // Redirection vers la commande
    }
}
