package com.pu.askanexpert.service;

import com.pu.askanexpert.domain.Expert;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Expert.
 */
public interface ExpertService {

    /**
     * Save a expert.
     *
     * @param expert the entity to save
     * @return the persisted entity
     */
    Expert save(Expert expert);

    /**
     * Get all the experts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Expert> findAll(Pageable pageable);


    /**
     * Get the "id" expert.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Expert> findOne(Long id);

    /**
     * Delete the "id" expert.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
