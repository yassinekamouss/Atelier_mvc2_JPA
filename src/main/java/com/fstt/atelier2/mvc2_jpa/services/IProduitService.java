package com.fstt.atelier2.mvc2_jpa.services;

import com.fstt.atelier2.mvc2_jpa.entities.Produit;

import java.util.List;
import java.util.Optional;

public interface IProduitService {
    Optional<Produit> getById(Long id);
    List<Produit> getAll();
    List<Produit> search(String keyword);
}

