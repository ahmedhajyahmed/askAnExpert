package com.pu.askanexpert.service;

import com.pu.askanexpert.domain.HistoriqueChat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing HistoriqueChat.
 */
public interface HistoriqueChatService {

    /**
     * Save a historiqueChat.
     *
     * @param historiqueChat the entity to save
     * @return the persisted entity
     */
    HistoriqueChat save(HistoriqueChat historiqueChat);

    /**
     * Get all the historiqueChats.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<HistoriqueChat> findAll(Pageable pageable);


    /**
     * Get the "id" historiqueChat.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<HistoriqueChat> findOne(Long id);

    /**
     * Delete the "id" historiqueChat.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
