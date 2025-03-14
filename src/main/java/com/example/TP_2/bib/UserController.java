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

    @GetMapping("store/home")
    public ModelAndView home() {
        return new ModelAndView("/store/home");
    }
    
    // Création de compte
    @PostMapping("store/register")
    public RedirectView register(@RequestParam String email,
                                 @RequestParam String mdp,
                                 @RequestParam String nom,
                                 @RequestParam String prenom) {
        userService.register(email, mdp, nom, prenom);
        return new RedirectView("/store/home");
    }

    // Page utilisateur avec la liste des commandes
    @GetMapping("store/user")
    public ModelAndView user(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return new ModelAndView("redirect:/store/home");
        }

        Optional<User> userOpt = userService.getById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            List<Commande> commandes = commandeService.getCommandesByUser(user);

            model.addAttribute("user", user);
            model.addAttribute("commandes", commandes);
        }

        return new ModelAndView("store/user");
    }

 // Authentification 
    @PostMapping("store/authenticate")
    public ModelAndView authenticate(@RequestParam String email, 
                                     @RequestParam String mdp,
                                     HttpSession session) {
        Optional<User> user = userService.authenticate(email, mdp);

        if (user.isPresent()) {
            session.setAttribute("userId", user.get().getId());
            return new ModelAndView("redirect:/store/user");
        } else {
            ModelAndView mav = new ModelAndView("store/home");
            mav.addObject("error", "Email ou mot de passe incorrect !");
            return mav;
        }
    }


    // Déconnexion
    @GetMapping("logout")
    public RedirectView logout(HttpSession session) {
        session.invalidate();
        return new RedirectView("store/home");
    }
}
