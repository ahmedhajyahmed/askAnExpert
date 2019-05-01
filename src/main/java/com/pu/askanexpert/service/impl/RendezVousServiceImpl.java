package com.pu.askanexpert.service.impl;

import com.pu.askanexpert.service.RendezVousService;
import com.pu.askanexpert.domain.RendezVous;
import com.pu.askanexpert.repository.RendezVousRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing RendezVous.
 */
@Service
@Transactional
public class RendezVousServiceImpl implements RendezVousService {

    private final Logger log = LoggerFactory.getLogger(RendezVousServiceImpl.class);

    private final RendezVousRepository rendezVousRepository;

    public RendezVousServiceImpl(RendezVousRepository rendezVousRepository) {
        this.rendezVousRepository = rendezVousRepository;
    }

    /**
     * Save a rendezVous.
     *
     * @param rendezVous the entity to save
     * @return the persisted entity
     */
    @Override
    public RendezVous save(RendezVous rendezVous) {
        log.debug("Request to save RendezVous : {}", rendezVous);
        return rendezVousRepository.save(rendezVous);
    }

    /**
     * Get all the rendezVous.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RendezVous> findAll(Pageable pageable) {
        log.debug("Request to get all RendezVous");
        return rendezVousRepository.findAll(pageable);
    }


    /**
     * Get one rendezVous by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RendezVous> findOne(Long id) {
        log.debug("Request to get RendezVous : {}", id);
        return rendezVousRepository.findById(id);
    }

    /**
     * Delete the rendezVous by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RendezVous : {}", id);
        rendezVousRepository.deleteById(id);
    }
}
