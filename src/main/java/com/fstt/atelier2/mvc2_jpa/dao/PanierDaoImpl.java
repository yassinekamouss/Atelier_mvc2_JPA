package com.fstt.atelier2.mvc2_jpa.dao;

import com.fstt.atelier2.mvc2_jpa.entities.Internaute;
import com.fstt.atelier2.mvc2_jpa.entities.LignePanier;
import com.fstt.atelier2.mvc2_jpa.entities.Panier;
import com.fstt.atelier2.mvc2_jpa.entities.Produit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Implémentation de l'interface IPanierDao pour la gestion du Panier.
 */

@ApplicationScoped
public class PanierDaoImpl implements IPanierDao{

    @PersistenceContext(unitName = "ecommerce-pu")
    private EntityManager em;


    @Override
    public Optional<Panier> findByInternauteId(Long internauteId) {
        try{

            Panier panier = em.createQuery("SELECT p FROM Panier p LEFT JOIN FETCH p.lignePaniers WHERE p.internaute.id = :id", Panier.class)
                    .setParameter("id", internauteId)
                    .getSingleResult();
            return Optional.of(panier);

        }catch(NoResultException e){
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Panier getOrCreatePanierForInternaute(Internaute internaute) {

        return findByInternauteId(internaute.getId()).orElseGet(() -> {
            Panier newPanier = new Panier();
            newPanier.setInternaute(internaute);
            newPanier.setLignePaniers(new ArrayList<>()); // assurer une liste initialisée
            em.persist(newPanier);
            return newPanier;
        });

    }

    @Override
    @Transactional
    public void addProduit(Panier panier, Produit produit, int quantite) {
        if (panier.getLignePaniers() == null) {
            panier.setLignePaniers(new ArrayList<>());
        }
        // Vérifier si le produit est déjà dans le panier
        Optional<LignePanier> ligneExistante = panier.getLignePaniers().stream()
                .filter(ligne -> ligne.getProduit().getId().equals(produit.getId()))
                .findFirst();

        if (ligneExistante.isPresent()) {
            // Mettre à jour la quantité
            LignePanier ligne = ligneExistante.get();
            ligne.setQuantite(ligne.getQuantite() + quantite);
            em.merge(ligne);
        } else {
            // Ajouter une nouvelle ligne
            LignePanier nouvelleLigne = new LignePanier();
            nouvelleLigne.setPanier(panier);
            nouvelleLigne.setProduit(produit);
            nouvelleLigne.setQuantite(quantite);
            panier.getLignePaniers().add(nouvelleLigne);
            em.persist(nouvelleLigne);
        }

    }

    @Override
    @Transactional
    public void updateQuantite(Panier panier, Long produitId, int nouvelleQuantite) {
        if (panier.getLignePaniers() == null) return;
        panier.getLignePaniers().stream()
                .filter(ligne -> ligne.getProduit().getId().equals(produitId))
                .findFirst()
                .ifPresent(ligne -> {
                    ligne.setQuantite(nouvelleQuantite);
                    em.merge(ligne);
                });
    }

    @Override
    @Transactional
    public void removeProduit(Panier panier, Long produitId) {
        if (panier.getLignePaniers() == null) return;
        panier.getLignePaniers().stream()
                .filter(ligne -> ligne.getProduit().getId().equals(produitId))
                .findFirst()
                .ifPresent(ligne -> {
                    panier.getLignePaniers().remove(ligne);
                    em.remove(em.contains(ligne) ? ligne : em.merge(ligne));
                });

    }

    @Override
    @Transactional
    public void viderPanier(Panier panier) {
        if (panier.getLignePaniers() == null) return;
        for (LignePanier ligne : panier.getLignePaniers()) {
            em.remove(em.contains(ligne) ? ligne : em.merge(ligne));
        }
        panier.getLignePaniers().clear();
        em.merge(panier);

    }
}
