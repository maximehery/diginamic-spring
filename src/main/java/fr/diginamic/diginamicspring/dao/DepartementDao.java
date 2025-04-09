package fr.diginamic.diginamicspring.dao;

import fr.diginamic.diginamicspring.entities.Departement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartementDao {
    @PersistenceContext
    private EntityManager em;

    public List<Departement> findAll() {
        return em.createQuery("SELECT d FROM Departement d", Departement.class).getResultList();
    }

    public Departement findById(int id) {
        return em.find(Departement.class, id);
    }

    @Transactional
    public void insertDepartement(Departement departement) {
        em.persist(departement);
    }

    @Transactional
    public void updateDepartement(Departement departement) {
        em.merge(departement);
    }

    @Transactional
    public void deleteDepartement(int id) {
        Departement departement = em.find(Departement.class, id);

        if (departement != null) {
            em.remove(departement);
        }
    }
}
