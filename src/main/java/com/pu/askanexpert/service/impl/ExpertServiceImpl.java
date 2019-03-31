package com.pu.askanexpert.service.impl;

import com.pu.askanexpert.service.ExpertService;
import com.pu.askanexpert.domain.Expert;
import com.pu.askanexpert.repository.ExpertRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Expert.
 */
@Service
@Transactional
public class ExpertServiceImpl implements ExpertService {

    private final Logger log = LoggerFactory.getLogger(ExpertServiceImpl.class);

    private final ExpertRepository expertRepository;

    public ExpertServiceImpl(ExpertRepository expertRepository) {
        this.expertRepository = expertRepository;
    }

    /**
     * Save a expert.
     *
     * @param expert the entity to save
     * @return the persisted entity
     */
    @Override
    public Expert save(Expert expert) {
        log.debug("Request to save Expert : {}", expert);
        return expertRepository.save(expert);
    }

    /**
     * Get all the experts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Expert> findAll(Pageable pageable) {
        log.debug("Request to get all Experts");
        return expertRepository.findAll(pageable);
    }


    /**
     * Get one expert by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Expert> findOne(Long id) {
        log.debug("Request to get Expert : {}", id);
        return expertRepository.findById(id);
    }

    /**
     * Delete the expert by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Expert : {}", id);
        expertRepository.deleteById(id);
    }
}
