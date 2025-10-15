package com.fstt.atelier2.mvc2_jpa.services;

import com.fstt.atelier2.mvc2_jpa.dao.IInternauteDao;
import com.fstt.atelier2.mvc2_jpa.entities.Internaute;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class InternauteServiceImpl implements IInternauteService {

    @Inject
    private IInternauteDao internauteDao;

    @Override
    @Transactional
    public void create(Internaute internaute) {
        internauteDao.create(internaute);
    }

    @Override
    public Optional<Internaute> getById(Long id) {
        return internauteDao.findById(id);
    }

    @Override
    public Optional<Internaute> getByEmail(String email) {
        return internauteDao.findByEmail(email);
    }

    @Override
    @Transactional
    public void update(Internaute internaute) {
        internauteDao.update(internaute);
    }
}

