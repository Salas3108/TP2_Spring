package com.example.TP_2.bib;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Commande, Long>{
	Optional<Commande> findById(Long id);

}
