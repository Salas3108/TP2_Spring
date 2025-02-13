package com.example.TP_2.bib;

//import com.example.TP_2.bib.Commande;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommandeRepository extends CrudRepository<Commande, Long> {

    List<Commande> findByUser(User user);
}

