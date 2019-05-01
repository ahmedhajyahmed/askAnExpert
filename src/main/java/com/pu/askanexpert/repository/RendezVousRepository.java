package com.pu.askanexpert.repository;

import com.pu.askanexpert.domain.RendezVous;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the RendezVous entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {

    //@Query("select redezVous from RendezVous redezVous where rendezVous.expert= ?#{id}")
    Optional<RendezVous> findRendezVousByExpertId(Long id);
}
