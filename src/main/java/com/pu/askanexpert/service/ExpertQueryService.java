package com.pu.askanexpert.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.pu.askanexpert.domain.Expert;
import com.pu.askanexpert.domain.*; // for static metamodels
import com.pu.askanexpert.repository.ExpertRepository;
import com.pu.askanexpert.service.dto.ExpertCriteria;

/**
 * Service for executing complex queries for Expert entities in the database.
 * The main input is a {@link ExpertCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Expert} or a {@link Page} of {@link Expert} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ExpertQueryService extends QueryService<Expert> {

    private final Logger log = LoggerFactory.getLogger(ExpertQueryService.class);

    private final ExpertRepository expertRepository;

    public ExpertQueryService(ExpertRepository expertRepository) {
        this.expertRepository = expertRepository;
    }

    /**
     * Return a {@link List} of {@link Expert} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Expert> findByCriteria(ExpertCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Expert> specification = createSpecification(criteria);
        return expertRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Expert} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Expert> findByCriteria(ExpertCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Expert> specification = createSpecification(criteria);
        return expertRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ExpertCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Expert> specification = createSpecification(criteria);
        return expertRepository.count(specification);
    }

    /**
     * Function to convert ExpertCriteria to a {@link Specification}
     */
    private Specification<Expert> createSpecification(ExpertCriteria criteria) {
        Specification<Expert> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Expert_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Expert_.nom));
            }
            if (criteria.getPrenom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrenom(), Expert_.prenom));
            }
            if (criteria.getDate_naissance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate_naissance(), Expert_.date_naissance));
            }
            if (criteria.getAdresse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdresse(), Expert_.adresse));
            }
            if (criteria.getDomaine() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDomaine(), Expert_.domaine));
            }
            if (criteria.getProfession() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProfession(), Expert_.profession));
            }
            if (criteria.getPrix() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrix(), Expert_.prix));
            }
            if (criteria.getDisponibilite() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDisponibilite(), Expert_.disponibilite));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNote(), Expert_.note));
            }
            if (criteria.getNum_rib() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNum_rib(), Expert_.num_rib));
            }
        }
        return specification;
    }
}
