package com.fstt.atelier2.mvc2_jpa.dao;

import com.fstt.atelier2.mvc2_jpa.entities.Commande;
import com.fstt.atelier2.mvc2_jpa.entities.LigneCommande;
import com.fstt.atelier2.mvc2_jpa.entities.Panier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implémentation de l'interface DAO pour l'entité Commande.
 */

@ApplicationScoped
public class CommandeDaoImpl implements ICommandeDao{

    @PersistenceContext(unitName = "ecommerce-pu")
    private EntityManager em;

    @Override
    public Commande creerDepuisPanier(Panier panier) {

        if (panier == null || panier.getLignePaniers().isEmpty()) {
            throw new IllegalArgumentException("Le panier est vide ou nul.");
        }

        Commande commande = new Commande();
        commande.setInternaute(panier.getInternaute());
        commande.setDateCommande(new Date());
        commande.setStatut("En attente");

        // Copier les lignes du panier vers les lignes de commande
        List<LigneCommande> lignesCommande = panier.getLignePaniers().stream()
                .map(lignePanier -> {
                    LigneCommande ligneCommande = new LigneCommande();
                    ligneCommande.setCommande(commande);
                    ligneCommande.setProduit(lignePanier.getProduit());
                    ligneCommande.setQuantite(lignePanier.getQuantite());
                    ligneCommande.setPrixUnitaire(lignePanier.getProduit().getPrix()); // Prix au moment de l'achat
                    return ligneCommande;
                })
                .collect(Collectors.toList());

        commande.setLigneCommande(lignesCommande);

        // Calculer le montant total
        double total = lignesCommande.stream()
                .mapToDouble(ligne -> ligne.getPrixUnitaire() * ligne.getQuantite())
                .sum();
        commande.setMontantTotal(total);

        em.persist(commande);

        return commande;

    }

    @Override
    public Optional<Commande> findById(Long id) {
        // Charger la commande avec ses lignes et produits pour la vue détail
        List<Commande> res = em.createQuery(
                "SELECT DISTINCT c FROM Commande c " +
                        "LEFT JOIN FETCH c.ligneCommande lc " +
                        "LEFT JOIN FETCH lc.produit " +
                        "WHERE c.id = :id", Commande.class)
                .setParameter("id", id)
                .getResultList();
        return res.stream().findFirst();
    }

    @Override
    public List<Commande> findByInternauteId(Long internauteId) {

        return em.createQuery("SELECT c FROM Commande c WHERE c.internaute.id = :id ORDER BY c.dateCommande DESC", Commande.class)
                .setParameter("id", internauteId)
                .getResultList();

    }
}
