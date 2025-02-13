package com.example.TP_2.bib;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class UserService implements UserItf{

	@Autowired
	private UserRepository userRepository;

 // Inscription 
    public void register(String email, String mdp, String nom, String prenom) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Cet email est déjà utilisé.");
        }
        User user = new User(email, mdp, nom, prenom);
        userRepository.save(user);
    }

    // Authentification 
    public Optional<User> authenticate(String email, String mdp) {
        return userRepository.findByEmail(email);
    }
    
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }


}
