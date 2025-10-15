package com.fstt.atelier2.mvc2_jpa.dao;


import com.fstt.atelier2.mvc2_jpa.entities.Commande;
import com.fstt.atelier2.mvc2_jpa.entities.Panier;

import java.util.List;
import java.util.Optional;

/**
 * Interface DAO pour l'entité Commande.
 */
public interface ICommandeDao {
    Commande creerDepuisPanier(Panier panier);

    Optional<Commande> findById(Long id);

    List<Commande> findByInternauteId(Long internauteId);
}
