package fr.diginamic.diginamicspring.controllers;

import fr.diginamic.diginamicspring.entities.Ville;
import fr.diginamic.diginamicspring.services.VilleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/villes")
public class VilleController {
    @Autowired
    private VilleService villeService;

    @GetMapping
    public List<Ville> listeVilles() {
        return villeService.extractVilles();
    }

    @GetMapping("/{id}")
    public Ville getVille(@PathVariable int id) {
        return villeService.extractVille(id);
    }

    @GetMapping("/rechercher")
    public Ville getVilleByNom(@RequestParam String nom) {
        return villeService.extractVilleByNom(nom);
    }

    @PostMapping
    public ResponseEntity<String> addVille(@Valid @RequestBody  Ville ville, BindingResult result) {
        boolean existe = villeService.extractVilles().stream()
                .anyMatch(v -> v.getNom().equalsIgnoreCase(ville.getNom()));

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().getFirst().getDefaultMessage());
        }

        if (existe) {
            return ResponseEntity.badRequest().body("La ville existe déjà");
        }

        boolean idExistant =  villeService.extractVilles().stream().anyMatch(v -> v.getId() == ville.getId());

        if (idExistant) {
            return ResponseEntity.badRequest().body("L'identifiant existe déjà");
        }

        villeService.insertVille(ville);

        return ResponseEntity.ok("Ville insérée avec succès");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateVille(@PathVariable int id, @Valid @RequestBody Ville villeAModifier) {
        villeService.modifierVille(id, villeAModifier);
        return ResponseEntity.ok("Ville modifiée avec succès");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVille(@PathVariable int id) {
        villeService.supprimerVille(id);
        return ResponseEntity.ok("Ville supprimée avec succès");
    }
}
