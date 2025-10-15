package com.fstt.atelier2.mvc2_jpa.services;

import com.fstt.atelier2.mvc2_jpa.dao.IProduitDao;
import com.fstt.atelier2.mvc2_jpa.entities.Produit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProduitServiceImpl implements IProduitService {

    @Inject
    private IProduitDao produitDao;

    @Override
    public Optional<Produit> getById(Long id) {
        return produitDao.findById(id);
    }

    @Override
    public List<Produit> getAll() {
        return produitDao.findAll();
    }

    @Override
    public List<Produit> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return produitDao.findAll();
        }
        return produitDao.findByKeyword(keyword.trim());
    }
}
