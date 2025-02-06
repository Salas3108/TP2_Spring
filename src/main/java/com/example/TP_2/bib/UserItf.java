package com.example.TP_2.bib;
import java.util.Optional;

public interface UserItf {
	
	void register(String email, String mdp, String nom, String prenom);
	Optional<User> authenticate(String email, String mdp);

}
