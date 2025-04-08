package fr.diginamic.diginamicspring.controllers;

import fr.diginamic.diginamicspring.Ville;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/villes")
public class VilleController {
    @GetMapping
    public List<Ville> listeVilles() {
        List<Ville> villes = new ArrayList<>();
        villes.add(new Ville("Paris", 2148000));
        villes.add(new Ville("Lyon", 515695));
        villes.add(new Ville("Marseille", 861635));
        villes.add(new Ville("Toulouse", 493465));
        villes.add(new Ville("Bordeaux", 257068));
        return villes;
    }
}
