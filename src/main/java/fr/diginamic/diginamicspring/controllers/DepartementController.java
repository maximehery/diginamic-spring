package fr.diginamic.diginamicspring.controllers;

import fr.diginamic.diginamicspring.entities.Departement;
import fr.diginamic.diginamicspring.services.DepartementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departements")
public class DepartementController {
    @Autowired
    private DepartementService departementService;

    public DepartementController(DepartementService departementService) {
        this.departementService = departementService;
    }

    @GetMapping
    public List<Departement> listeDepartements() {
        return departementService.extractDepartements();
    }

    @GetMapping("/{id}")
    public Departement getDepartement(@PathVariable int id) {
        return departementService.extractDepartement(id);
    }

    @PostMapping
    public ResponseEntity<String> addDepartement(@Valid @RequestBody Departement departement) {
        departementService.insertDepartement(departement);

        return ResponseEntity.ok("Département inséré avec succès");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDepartement(@PathVariable int id, @Valid @RequestBody Departement departement) {
        departement.setId(id);
        departementService.modifierDepartement(id, departement);

        return ResponseEntity.ok("Département modifié avec succès");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartement(@PathVariable int id) {
        departementService.supprimerDepartement(id);

        return ResponseEntity.ok("Département supprimé avec succès");
    }
}
