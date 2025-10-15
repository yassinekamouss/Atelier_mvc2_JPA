package com.fstt.atelier2.mvc2_jpa.dao;

import com.fstt.atelier2.mvc2_jpa.entities.Produit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

/**
 * Classe d'implémentation de l'interface IProduitDao.
 */

@ApplicationScoped
public class ProduitDaoImpl implements IProduitDao{

    @PersistenceContext(unitName = "ecommerce-pu")
    private EntityManager em;

    @Override
    public Optional<Produit> findById(Long id) {

        return Optional.ofNullable(em.find(Produit.class, id));

    }

    @Override
    public List<Produit> findAll() {

        return em.createQuery("SELECT p FROM Produit p", Produit.class)
                .getResultList();

    }

    @Override
    public List<Produit> findByKeyword(String keyword) {

        return em.createQuery("SELECT p FROM Produit p WHERE p.nom LIKE :kw OR p.description LIKE :kw", Produit.class)
                .setParameter("kw", "%" + keyword + "%")
                .getResultList();

    }

}
