package com.pu.askanexpert.service;

import com.pu.askanexpert.domain.Disponibilite;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Disponibilite.
 */
public interface DisponibiliteService {

    /**
     * Save a disponibilite.
     *
     * @param disponibilite the entity to save
     * @return the persisted entity
     */
    Disponibilite save(Disponibilite disponibilite);

    /**
     * Get all the disponibilites.
     *
     * @return the list of entities
     */
    List<Disponibilite> findAll();


    /**
     * Get the "id" disponibilite.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Disponibilite> findOne(Long id);

    /**
     * Delete the "id" disponibilite.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
