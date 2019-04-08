package com.pu.askanexpert.service.impl;

import com.pu.askanexpert.service.DisponibiliteService;
import com.pu.askanexpert.domain.Disponibilite;
import com.pu.askanexpert.repository.DisponibiliteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Disponibilite.
 */
@Service
@Transactional
public class DisponibiliteServiceImpl implements DisponibiliteService {

    private final Logger log = LoggerFactory.getLogger(DisponibiliteServiceImpl.class);

    private final DisponibiliteRepository disponibiliteRepository;

    public DisponibiliteServiceImpl(DisponibiliteRepository disponibiliteRepository) {
        this.disponibiliteRepository = disponibiliteRepository;
    }

    /**
     * Save a disponibilite.
     *
     * @param disponibilite the entity to save
     * @return the persisted entity
     */
    @Override
    public Disponibilite save(Disponibilite disponibilite) {
        log.debug("Request to save Disponibilite : {}", disponibilite);
        return disponibiliteRepository.save(disponibilite);
    }

    /**
     * Get all the disponibilites.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Disponibilite> findAll() {
        log.debug("Request to get all Disponibilites");
        return disponibiliteRepository.findAll();
    }


    /**
     * Get one disponibilite by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Disponibilite> findOne(Long id) {
        log.debug("Request to get Disponibilite : {}", id);
        return disponibiliteRepository.findById(id);
    }

    /**
     * Delete the disponibilite by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Disponibilite : {}", id);
        disponibiliteRepository.deleteById(id);
    }
}
