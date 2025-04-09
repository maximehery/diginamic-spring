package fr.diginamic.diginamicspring.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Departement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;

    @OneToMany(mappedBy = "departement")
    private List<Ville> villes;

    public Departement(int id, String nom, List<Ville> villes) {
        this.id = id;
        this.nom = nom;
        this.villes = villes;
    }

    public Departement() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Ville> getVilles() {
        return villes;
    }

    public void setVilles(List<Ville> villes) {
        this.villes = villes;
    }
}
