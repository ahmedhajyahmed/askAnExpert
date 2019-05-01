package com.pu.askanexpert.service;

import com.pu.askanexpert.domain.NonDisponibilite;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing NonDisponibilite.
 */
public interface NonDisponibiliteService {

    /**
     * Save a nonDisponibilite.
     *
     * @param nonDisponibilite the entity to save
     * @return the persisted entity
     */
    NonDisponibilite save(NonDisponibilite nonDisponibilite);

    /**
     * Get all the nonDisponibilites.
     *
     * @return the list of entities
     */
    List<NonDisponibilite> findAll();


    /**
     * Get the "id" nonDisponibilite.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<NonDisponibilite> findOne(Long id);

    /**
     * Delete the "id" nonDisponibilite.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
