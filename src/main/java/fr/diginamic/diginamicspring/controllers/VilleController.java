package fr.diginamic.diginamicspring.controllers;

import fr.diginamic.diginamicspring.entities.Ville;
import fr.diginamic.diginamicspring.repositories.VilleRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/villes")
public class VilleController {

    @Autowired
    private VilleRepository villeRepository;

    @GetMapping
    public Page<Ville> listeVilles(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return villeRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ville> getVille(@PathVariable int id) {
        return villeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/rechercher")
    public List<Ville> getVilleByNom(@RequestParam String nom) {
        return villeRepository.findByNomStartingWith(nom);
    }

    @GetMapping("/habitants/sup")
    public List<Ville> getVillesByNbHabitantsMin(@RequestParam int min) {
        return villeRepository.findByNbHabitantsGreaterThan(min);
    }

    @GetMapping("/habitants/entre")
    public List<Ville> getVillesByNbHabitantsBetween(@RequestParam int min,
                                                    @RequestParam int max) {
        return villeRepository.findByNbHabitantsBetween(min, max);
    }

    @GetMapping("/departement")
    public List<Ville> getVillesByDepartementAndNbHabitantsMin(@RequestParam String nomDepartement,
                                                              @RequestParam int min) {
        return villeRepository.findByDepartementNomAndNbHabitantsGreaterThan(nomDepartement, min);
    }

    @GetMapping("/departement/entre")
    public List<Ville> getVillesByDepartementAndNbHabitantsBetween(@RequestParam String nomDepartement,
                                                                  @RequestParam int min,
                                                                  @RequestParam int max) {
        return villeRepository.findByDepartementNomAndNbHabitantsBetween(nomDepartement, min, max);
    }

    @GetMapping("/departement/top")
    public List<Ville> getTopVillesByDepartement(@RequestParam String nomDepartement,
                                                 @RequestParam(defaultValue = "5") int n) {
        Pageable pageable = PageRequest.of(0, n);
        return villeRepository.findByDepartementNomOrderByNbHabitantsDesc(nomDepartement, pageable);
    }

    @PostMapping
    public ResponseEntity<String> addVille(@Valid @RequestBody Ville ville) {
        if (villeRepository.existsById(ville.getId())) {
            return ResponseEntity.badRequest().body("L'identifiant existe déjà");
        }
        boolean existe = villeRepository.findAll().stream()
                .anyMatch(v -> v.getNom().equalsIgnoreCase(ville.getNom()));

        if (existe) {
            return ResponseEntity.badRequest().body("La ville existe déjà");
        }

        villeRepository.save(ville);
        return ResponseEntity.ok("Ville insérée avec succès");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateVille(@PathVariable int id, @Valid @RequestBody Ville villeAModifier) {
        return villeRepository.findById(id)
                .map(existing -> {
                    villeAModifier.setId(id);
                    villeRepository.save(villeAModifier);
                    return ResponseEntity.ok("Ville modifiée avec succès");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVille(@PathVariable int id) {
        if (!villeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        villeRepository.deleteById(id);
        return ResponseEntity.ok("Ville supprimée avec succès");
    }
}