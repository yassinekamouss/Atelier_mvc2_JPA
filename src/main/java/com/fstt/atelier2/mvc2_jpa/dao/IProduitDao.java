package com.fstt.atelier2.mvc2_jpa.dao;
import com.fstt.atelier2.mvc2_jpa.entities.Produit;
import java.util.List;
import java.util.Optional;

/**
 * Interface DAO pour l'entité Produit.
 */
public interface IProduitDao {
    Optional<Produit> findById(Long id);

    List<Produit> findAll();

    List<Produit> findByKeyword(String keyword);
}
