package com.fstt.atelier2.mvc2_jpa.services;

import com.fstt.atelier2.mvc2_jpa.dao.IPanierDao;
import com.fstt.atelier2.mvc2_jpa.entities.Internaute;
import com.fstt.atelier2.mvc2_jpa.entities.Panier;
import com.fstt.atelier2.mvc2_jpa.entities.Produit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class PanierServiceImpl implements IPanierService {

    @Inject
    private IPanierDao panierDao;

    @Override
    public Optional<Panier> getByInternauteId(Long internauteId) {
        return panierDao.findByInternauteId(internauteId);
    }

    @Override
    @Transactional
    public Panier getOrCreateForInternaute(Internaute internaute) {
        return panierDao.getOrCreatePanierForInternaute(internaute);
    }

    @Override
    @Transactional
    public void addProduit(Panier panier, Produit produit, int quantite) {
        panierDao.addProduit(panier, produit, quantite);
    }

    @Override
    @Transactional
    public void updateQuantite(Panier panier, Long produitId, int nouvelleQuantite) {
        panierDao.updateQuantite(panier, produitId, nouvelleQuantite);
    }

    @Override
    @Transactional
    public void removeProduit(Panier panier, Long produitId) {
        panierDao.removeProduit(panier, produitId);
    }

    @Override
    @Transactional
    public void vider(Panier panier) {
        panierDao.viderPanier(panier);
    }
}

