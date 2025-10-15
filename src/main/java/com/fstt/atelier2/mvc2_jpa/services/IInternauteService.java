package com.fstt.atelier2.mvc2_jpa.services;

import com.fstt.atelier2.mvc2_jpa.entities.Internaute;

import java.util.Optional;

public interface IInternauteService {
    void create(Internaute internaute);
    Optional<Internaute> getById(Long id);
    Optional<Internaute> getByEmail(String email);
    void update(Internaute internaute);
}

