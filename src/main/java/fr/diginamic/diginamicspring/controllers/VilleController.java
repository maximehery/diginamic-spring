package fr.diginamic.diginamicspring.controllers;

import fr.diginamic.diginamicspring.Ville;
import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/villes")
public class VilleController {
    private final List<Ville> villes = new ArrayList<>();

    @PostConstruct
    public void init() {
        villes.add(new Ville(1, "Paris", 2148000));
        villes.add(new Ville(2, "Lyon", 515695));
        villes.add(new Ville(3, "Marseille", 861635));
        villes.add(new Ville(4, "Toulouse", 493465));
        villes.add(new Ville(5, "Bordeaux", 257068));
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
            return ResponseEntity.badRequest().body("La ville existe déjà");
        }

        boolean idExistant =  listeVilles().stream().anyMatch(v -> v.getId() == ville.getId());

        if (idExistant) {
            return ResponseEntity.badRequest().body("L'identifiant existe déjà");
        }

        villes.add(ville);

        return ResponseEntity.ok("Ville insérée avec succès");
    }

    @GetMapping("/{id}")
    public Ville getVille(@PathVariable int id) {
        Optional<Ville> ville = listeVilles().stream()
                .filter(v -> v.getId() == id)
                .findFirst();

        return ville.orElse(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateVille(@PathVariable int id, @RequestBody Ville villeAModifier) {
        Optional<Ville> villeExistante = listeVilles().stream()
                .filter(v -> v.getId() == id)
                .findFirst();

        if (villeExistante.isEmpty()) {
            return ResponseEntity.badRequest().body("Ville non trouvée");
        }

        Ville ville = villeExistante.get();
        ville.setNom(villeAModifier.getNom());
        ville.setNbHabitants(villeAModifier.getNbHabitants());

        return ResponseEntity.ok("Ville modifiée avec succès");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVille(@PathVariable int id) {
        Optional<Ville> villeASupprimer = villes.stream()
                .filter(v -> v.getId() == id)
                .findFirst();

        if (villeASupprimer.isEmpty()) {
            return ResponseEntity.badRequest().body("Ville non trouvée");
        }

        villes.remove(villeASupprimer.get());
        return ResponseEntity.ok("Ville supprimée avec succès");
    }
}
