package com.fstt.atelier2.mvc2_jpa.services;

import com.fstt.atelier2.mvc2_jpa.entities.Commande;
import com.fstt.atelier2.mvc2_jpa.entities.Panier;

import java.util.List;
import java.util.Optional;

public interface ICommandeService {
    Commande creerDepuisPanier(Panier panier);
    Optional<Commande> getById(Long id);
    List<Commande> getByInternauteId(Long internauteId);
}

