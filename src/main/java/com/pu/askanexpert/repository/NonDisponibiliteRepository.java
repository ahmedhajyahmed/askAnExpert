package com.pu.askanexpert.repository;

import com.pu.askanexpert.domain.NonDisponibilite;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NonDisponibilite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NonDisponibiliteRepository extends JpaRepository<NonDisponibilite, Long> {

}
