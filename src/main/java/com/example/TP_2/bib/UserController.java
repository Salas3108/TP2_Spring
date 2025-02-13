package com.example.TP_2.bib;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommandeItf commandeService;

    @GetMapping("/home")
    public ModelAndView home() {
        return new ModelAndView("/home");
    }
    
    // Création de compte
    @PostMapping("/register")
    public RedirectView register(@RequestParam String email,
                                 @RequestParam String mdp,
                                 @RequestParam String nom,
                                 @RequestParam String prenom) {
        userService.register(email, mdp, nom, prenom);
        return new RedirectView("/home");
    }

    // Page utilisateur avec la liste des commandes
    @GetMapping("/user")
    public ModelAndView user(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return new ModelAndView("redirect:/home");
        }

        Optional<User> userOpt = userService.getById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            List<Commande> commandes = commandeService.getCommandesByUser(user);

            model.addAttribute("user", user);
            model.addAttribute("commandes", commandes);
        }

        return new ModelAndView("/user");
    }

    // Authentification 
    @PostMapping("/authenticate")
    public RedirectView authenticate(@RequestParam String email, 
                                     @RequestParam String mdp,
                                     HttpSession session) {
        Optional<User> user = userService.authenticate(email, mdp);

        if (user.isPresent()) {
            session.setAttribute("userId", user.get().getId());  
            return new RedirectView("/user");
        } else {
            return new RedirectView("/home?error=true");
        }
    }	

    // Déconnexion
    @GetMapping("/logout")
    public RedirectView logout(HttpSession session) {
        session.invalidate();
        return new RedirectView("/home");
    }
}
