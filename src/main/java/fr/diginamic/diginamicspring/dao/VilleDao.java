package fr.diginamic.diginamicspring.dao;

import fr.diginamic.diginamicspring.entities.Ville;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VilleDao {
    @PersistenceContext
    private EntityManager em;

    public List<Ville> findAll() {
        return em.createQuery("SELECT v FROM Ville v", Ville.class).getResultList();
    }

    @Transactional
    public void insertVille(Ville ville) {
        em.persist(ville);
    }

    public Ville findVilleById(int idVille) {
        return em.find(Ville.class, idVille);
    }

    public Ville findVilleByNom(String nomVille) {
        return em.createQuery("SELECT v FROM Ville v WHERE v.nom = :nom", Ville.class)
                .setParameter("nom", nomVille)
                .getSingleResult();
    }

    @Transactional
    public void updateVille(Ville ville) {
        em.merge(ville);
    }

    @Transactional
    public void deleteVille(int id) {
        Ville ville = em.find(Ville.class, id);

        if (ville != null) {
            em.remove(ville);
        }
    }

    public List<Ville> findByDepartementIdOrderByPopulationDesc(int departementId) {
        return em.createQuery("SELECT v FROM Ville v WHERE v.departement.id = :departementId ORDER BY v.nbHabitants DESC", Ville.class)
                .setParameter("departementId", departementId)
                .getResultList();
    }

    public List<Ville> findByPopulationBetweenAndDepartementId(int min, int max, int departementId) {
        return em.createQuery("SELECT v FROM Ville v WHERE v.nbHabitants BETWEEN :min AND :max AND v.departement.id = :departementId", Ville.class)
                .setParameter("min", min)
                .setParameter("max", max)
                .setParameter("departementId", departementId)
                .getResultList();
    }
}
