package com.fstt.atelier2.mvc2_jpa.dao;

import com.fstt.atelier2.mvc2_jpa.entities.Internaute;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.Optional;

/**
 * Implémentation de l'interface DAO pour l'Internaute.
 * Utilise JPA et EntityManager pour les opérations de persistance.
 */

@ApplicationScoped
public class InternauteDaoImpl implements IInternauteDao{

    @PersistenceContext(unitName = "ecommerce-pu")
    private EntityManager em;



    @Override
    public void create(Internaute internaute) {

        em.persist(internaute);

    }

    @Override
    public Optional<Internaute> findById(Long id) {

        return Optional.ofNullable(em.find(Internaute.class, id));
    }

    @Override
    public Optional<Internaute> findByEmail(String email) {

        try{

            Internaute internaute = em.createQuery("SELECT i FROM Internaute i WHERE i.email = :email", Internaute.class)
                    .setParameter("email", email)
                    .getSingleResult();

            return Optional.ofNullable(internaute);

        }catch (NoResultException e){
            return Optional.empty();
        }

    }

    @Override
    public void update(Internaute internaute) {

        em.merge(internaute);

    }
}
