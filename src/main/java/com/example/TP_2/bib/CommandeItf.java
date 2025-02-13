package com.example.TP_2.bib;

import java.util.List;
import java.util.Optional;

public interface CommandeItf {
	Commande creerCommande(String description, User user);
    List<Commande> getCommandesByUser(User user);
    Optional<Commande> getCommandeById(Long commandeId);
    //void supprimerCommande(Long commandeId);

}
