package com.pu.askanexpert.service.impl;

import com.pu.askanexpert.service.HistoriqueChatService;
import com.pu.askanexpert.domain.HistoriqueChat;
import com.pu.askanexpert.repository.HistoriqueChatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing HistoriqueChat.
 */
@Service
@Transactional
public class HistoriqueChatServiceImpl implements HistoriqueChatService {

    private final Logger log = LoggerFactory.getLogger(HistoriqueChatServiceImpl.class);

    private final HistoriqueChatRepository historiqueChatRepository;

    public HistoriqueChatServiceImpl(HistoriqueChatRepository historiqueChatRepository) {
        this.historiqueChatRepository = historiqueChatRepository;
    }

    /**
     * Save a historiqueChat.
     *
     * @param historiqueChat the entity to save
     * @return the persisted entity
     */
    @Override
    public HistoriqueChat save(HistoriqueChat historiqueChat) {
        log.debug("Request to save HistoriqueChat : {}", historiqueChat);
        return historiqueChatRepository.save(historiqueChat);
    }

    /**
     * Get all the historiqueChats.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HistoriqueChat> findAll(Pageable pageable) {
        log.debug("Request to get all HistoriqueChats");
        return historiqueChatRepository.findAll(pageable);
    }


    /**
     * Get one historiqueChat by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HistoriqueChat> findOne(Long id) {
        log.debug("Request to get HistoriqueChat : {}", id);
        return historiqueChatRepository.findById(id);
    }

    /**
     * Delete the historiqueChat by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HistoriqueChat : {}", id);
        historiqueChatRepository.deleteById(id);
    }
}
