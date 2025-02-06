package com.example.TP_2.bib;

import com.example.TP_2.bib.User;
import com.example.TP_2.bib.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/home")
    public ModelAndView home() {
        return new ModelAndView("/home");
    }
    
    
    // Creation de compte
    @PostMapping("/register")
    public RedirectView register(@RequestParam String email,
                                 @RequestParam String mdp,
                                 @RequestParam String nom,
                                 @RequestParam String prenom) {
    userService.register(email, mdp, nom, prenom);
    return new RedirectView("/home");
    }
    
    
    @GetMapping("/user")
    public ModelAndView user() {
        return new ModelAndView("/user");
    }
    
    
    // Authentification 
    @PostMapping("/authenticate")
    public RedirectView authenticate(@RequestParam String email, 
                                       @RequestParam String mdp,
                                       HttpSession session) {
    Optional<User> user = userService.authenticate(email, mdp);
    if (user.isPresent()) {
        // Si l'authentification réussit
    	session.setAttribute("user", user.get());
        return new RedirectView("/user");
    } else {
        // Si l'authentification échoue, redirige vers la page de connexion avec un message d'erreur
        return new RedirectView("/home?error=true");
    }
    
    }
    
    
    // Deconnexion
    public RedirectView logout(HttpSession session) {
    	session.invalidate();
        return new RedirectView("/home"); // Redirige vers la page d'accueil après déconnexion
    }

   
}
