package fr.diginamic.diginamicspring.repositories;

import fr.diginamic.diginamicspring.entities.Ville;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VilleRepository extends JpaRepository<Ville, Integer> {
    List<Ville> findByNomStartingWith(String prefix);
    List<Ville> findByNbHabitantsGreaterThan(int min);
    List<Ville> findByNbHabitantsBetween(int min, int max);
    List<Ville> findByDepartementNomAndNbHabitantsGreaterThan(String nomDepartement, int min);
    List<Ville> findByDepartementNomAndNbHabitantsBetween(String nomDepartement, int min, int max);
    List<Ville> findByDepartementNomOrderByNbHabitantsDesc(String nomDepartement, Pageable pageable);
}
