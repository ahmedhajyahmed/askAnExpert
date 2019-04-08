package com.pu.askanexpert.repository;

import com.pu.askanexpert.domain.HistoriqueAppel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the HistoriqueAppel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistoriqueAppelRepository extends JpaRepository<HistoriqueAppel, Long> {

}
