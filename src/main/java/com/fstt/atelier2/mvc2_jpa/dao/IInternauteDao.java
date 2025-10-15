package com.fstt.atelier2.mvc2_jpa.dao;

import com.fstt.atelier2.mvc2_jpa.entities.Internaute;

import java.util.Optional;

/**
 * Interface DAO pour l'entité Internaute.
 * pour les opérations CRUD de base.
 */
public interface IInternauteDao {

    void create(Internaute internaute);

    Optional<Internaute> findById(Long id);

    Optional<Internaute> findByEmail(String email);

    void update(Internaute internaute);

}