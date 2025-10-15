package com.fstt.atelier2.mvc2_jpa.services;

import com.fstt.atelier2.mvc2_jpa.entities.Internaute;
import com.fstt.atelier2.mvc2_jpa.entities.Panier;
import com.fstt.atelier2.mvc2_jpa.entities.Produit;

import java.util.Optional;

public interface IPanierService {
    Optional<Panier> getByInternauteId(Long internauteId);
    Panier getOrCreateForInternaute(Internaute internaute);
    void addProduit(Panier panier, Produit produit, int quantite);
    void updateQuantite(Panier panier, Long produitId, int nouvelleQuantite);
    void removeProduit(Panier panier, Long produitId);
    void vider(Panier panier);
}

