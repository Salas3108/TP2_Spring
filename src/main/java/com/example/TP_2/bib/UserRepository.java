package com.example.TP_2.bib;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // On peut ajouter des méthodes de recherche personnalisées ici
    Optional<User> findByEmail(String email);
    Optional<User> findById(long id);
    
    
}