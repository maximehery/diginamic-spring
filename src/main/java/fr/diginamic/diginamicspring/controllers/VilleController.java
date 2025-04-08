package fr.diginamic.diginamicspring.controllers;

import fr.diginamic.diginamicspring.Ville;
import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/villes")
public class VilleController {
    private final List<Ville> villes = new ArrayList<>();

    @PostConstruct
    public void init() {
        villes.add(new Ville("Paris", 2148000));
        villes.add(new Ville("Lyon", 515695));
        villes.add(new Ville("Marseille", 861635));
        villes.add(new Ville("Toulouse", 493465));
        villes.add(new Ville("Bordeaux", 257068));
    }

    @GetMapping
    public List<Ville> listeVilles() {
        return villes;
    }

    @PostMapping
    public ResponseEntity<String> addVille(@RequestBody  Ville ville) {
        boolean existe = listeVilles().stream()
                .anyMatch(v -> v.getNom().equalsIgnoreCase(ville.getNom()));

        if (existe) {
            return ResponseEntity
                    .badRequest()
                    .body("La ville existe déjà");
        }

        villes.add(ville);

        return ResponseEntity
                .ok("Ville insérée avec succès");
    }
}
