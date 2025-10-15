package com.fstt.atelier2.mvc2_jpa.dao;

import com.fstt.atelier2.mvc2_jpa.entities.Internaute;
import com.fstt.atelier2.mvc2_jpa.entities.Panier;
import com.fstt.atelier2.mvc2_jpa.entities.Produit;

import java.util.Optional;

/**
 * Interface DAO pour la gestion du Panier.
 * Les opérations sont spécifiques et ne suivent pas un CRUD classique.
 */
public interface IPanierDao {
    Optional<Panier> findByInternauteId(Long internauteId);

    Panier getOrCreatePanierForInternaute(Internaute internaute);

    void addProduit(Panier panier, Produit produit, int quantite);

    void updateQuantite(Panier panier, Long produitId, int nouvelleQuantite);

    void removeProduit(Panier panier, Long produitId);

    void viderPanier(Panier panier);
}
