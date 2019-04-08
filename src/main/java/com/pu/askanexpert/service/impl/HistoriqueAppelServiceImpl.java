package com.pu.askanexpert.service.impl;

import com.pu.askanexpert.service.HistoriqueAppelService;
import com.pu.askanexpert.domain.HistoriqueAppel;
import com.pu.askanexpert.repository.HistoriqueAppelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing HistoriqueAppel.
 */
@Service
@Transactional
public class HistoriqueAppelServiceImpl implements HistoriqueAppelService {

    private final Logger log = LoggerFactory.getLogger(HistoriqueAppelServiceImpl.class);

    private final HistoriqueAppelRepository historiqueAppelRepository;

    public HistoriqueAppelServiceImpl(HistoriqueAppelRepository historiqueAppelRepository) {
        this.historiqueAppelRepository = historiqueAppelRepository;
    }

    /**
     * Save a historiqueAppel.
     *
     * @param historiqueAppel the entity to save
     * @return the persisted entity
     */
    @Override
    public HistoriqueAppel save(HistoriqueAppel historiqueAppel) {
        log.debug("Request to save HistoriqueAppel : {}", historiqueAppel);
        return historiqueAppelRepository.save(historiqueAppel);
    }

    /**
     * Get all the historiqueAppels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HistoriqueAppel> findAll(Pageable pageable) {
        log.debug("Request to get all HistoriqueAppels");
        return historiqueAppelRepository.findAll(pageable);
    }


    /**
     * Get one historiqueAppel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HistoriqueAppel> findOne(Long id) {
        log.debug("Request to get HistoriqueAppel : {}", id);
        return historiqueAppelRepository.findById(id);
    }

    /**
     * Delete the historiqueAppel by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HistoriqueAppel : {}", id);
        historiqueAppelRepository.deleteById(id);
    }
}
