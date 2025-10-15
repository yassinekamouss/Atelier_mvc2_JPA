package com.fstt.atelier2.mvc2_jpa.services;

import com.fstt.atelier2.mvc2_jpa.dao.ICommandeDao;
import com.fstt.atelier2.mvc2_jpa.entities.Commande;
import com.fstt.atelier2.mvc2_jpa.entities.Panier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CommandeServiceImpl implements ICommandeService {

    @Inject
    private ICommandeDao commandeDao;

    @Override
    @Transactional
    public Commande creerDepuisPanier(Panier panier) {
        return commandeDao.creerDepuisPanier(panier);
    }

    @Override
    public Optional<Commande> getById(Long id) {
        return commandeDao.findById(id);
    }

    @Override
    public List<Commande> getByInternauteId(Long internauteId) {
        return commandeDao.findByInternauteId(internauteId);
    }
}

