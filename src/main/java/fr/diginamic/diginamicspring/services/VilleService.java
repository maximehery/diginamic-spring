package fr.diginamic.diginamicspring.services;

import fr.diginamic.diginamicspring.dao.VilleDao;
import fr.diginamic.diginamicspring.entities.Ville;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VilleService {
    private final VilleDao villeDao;

    public VilleService(VilleDao villeDao) {
        this.villeDao = villeDao;
    }

    public List<Ville> extractVilles() {
        return villeDao.findAll();
    }

    public Ville extractVille(int idVille) {
        return villeDao.findVilleById(idVille);
    }

    public Ville extractVilleByNom(String nomVille) {
        return villeDao.findVilleByNom(nomVille);
    }

    public void insertVille(Ville ville) {
        villeDao.insertVille(ville);
    }

    public void modifierVille(int id, Ville villeAModifier) {
        Ville ville = villeDao.findVilleById(id);

        if  (ville != null) {
            ville.setNom(villeAModifier.getNom());
            ville.setNbHabitants(villeAModifier.getNbHabitants());
            villeDao.updateVille(ville);
        }
    }

    public void supprimerVille(int id) {
        villeDao.deleteVille(id);
    }

    public List<Ville> plusGrandesVilles(int departementId, int n) {
        List<Ville> villes = villeDao.findByDepartementIdOrderByPopulationDesc(departementId);
        return villes.stream().limit(n).toList();
    }

    public List<Ville> villesParPopulation(int departementId, int min, int max) {
        return villeDao.findByPopulationBetweenAndDepartementId(min, max, departementId);
    }
}
