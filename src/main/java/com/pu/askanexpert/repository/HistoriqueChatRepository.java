package com.pu.askanexpert.repository;

import com.pu.askanexpert.domain.HistoriqueChat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the HistoriqueChat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistoriqueChatRepository extends JpaRepository<HistoriqueChat, Long> {

}
