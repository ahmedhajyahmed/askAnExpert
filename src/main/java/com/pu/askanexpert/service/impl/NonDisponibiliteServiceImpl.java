package com.pu.askanexpert.service.impl;

import com.pu.askanexpert.service.NonDisponibiliteService;
import com.pu.askanexpert.domain.NonDisponibilite;
import com.pu.askanexpert.repository.NonDisponibiliteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing NonDisponibilite.
 */
@Service
@Transactional
public class NonDisponibiliteServiceImpl implements NonDisponibiliteService {

    private final Logger log = LoggerFactory.getLogger(NonDisponibiliteServiceImpl.class);

    private final NonDisponibiliteRepository nonDisponibiliteRepository;

    public NonDisponibiliteServiceImpl(NonDisponibiliteRepository nonDisponibiliteRepository) {
        this.nonDisponibiliteRepository = nonDisponibiliteRepository;
    }

    /**
     * Save a nonDisponibilite.
     *
     * @param nonDisponibilite the entity to save
     * @return the persisted entity
     */
    @Override
    public NonDisponibilite save(NonDisponibilite nonDisponibilite) {
        log.debug("Request to save NonDisponibilite : {}", nonDisponibilite);
        return nonDisponibiliteRepository.save(nonDisponibilite);
    }

    /**
     * Get all the nonDisponibilites.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<NonDisponibilite> findAll() {
        log.debug("Request to get all NonDisponibilites");
        return nonDisponibiliteRepository.findAll();
    }


    /**
     * Get one nonDisponibilite by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<NonDisponibilite> findOne(Long id) {
        log.debug("Request to get NonDisponibilite : {}", id);
        return nonDisponibiliteRepository.findById(id);
    }

    /**
     * Delete the nonDisponibilite by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete NonDisponibilite : {}", id);
        nonDisponibiliteRepository.deleteById(id);
    }
}
