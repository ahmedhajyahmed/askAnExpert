package com.pu.askanexpert.service;

import com.pu.askanexpert.domain.HistoriqueAppel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing HistoriqueAppel.
 */
public interface HistoriqueAppelService {

    /**
     * Save a historiqueAppel.
     *
     * @param historiqueAppel the entity to save
     * @return the persisted entity
     */
    HistoriqueAppel save(HistoriqueAppel historiqueAppel);

    /**
     * Get all the historiqueAppels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<HistoriqueAppel> findAll(Pageable pageable);


    /**
     * Get the "id" historiqueAppel.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<HistoriqueAppel> findOne(Long id);

    /**
     * Delete the "id" historiqueAppel.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
