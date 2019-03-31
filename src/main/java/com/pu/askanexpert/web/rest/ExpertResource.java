package com.pu.askanexpert.web.rest;
import com.pu.askanexpert.domain.Expert;
import com.pu.askanexpert.service.ExpertService;
import com.pu.askanexpert.web.rest.errors.BadRequestAlertException;
import com.pu.askanexpert.web.rest.util.HeaderUtil;
import com.pu.askanexpert.web.rest.util.PaginationUtil;
import com.pu.askanexpert.service.dto.ExpertCriteria;
import com.pu.askanexpert.service.ExpertQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Expert.
 */
@RestController
@RequestMapping("/api")
public class ExpertResource {

    private final Logger log = LoggerFactory.getLogger(ExpertResource.class);

    private static final String ENTITY_NAME = "expert";

    private final ExpertService expertService;

    private final ExpertQueryService expertQueryService;

    public ExpertResource(ExpertService expertService, ExpertQueryService expertQueryService) {
        this.expertService = expertService;
        this.expertQueryService = expertQueryService;
    }

    /**
     * POST  /experts : Create a new expert.
     *
     * @param expert the expert to create
     * @return the ResponseEntity with status 201 (Created) and with body the new expert, or with status 400 (Bad Request) if the expert has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/experts")
    public ResponseEntity<Expert> createExpert(@Valid @RequestBody Expert expert) throws URISyntaxException {
        log.debug("REST request to save Expert : {}", expert);
        if (expert.getId() != null) {
            throw new BadRequestAlertException("A new expert cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Expert result = expertService.save(expert);
        return ResponseEntity.created(new URI("/api/experts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /experts : Updates an existing expert.
     *
     * @param expert the expert to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated expert,
     * or with status 400 (Bad Request) if the expert is not valid,
     * or with status 500 (Internal Server Error) if the expert couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/experts")
    public ResponseEntity<Expert> updateExpert(@Valid @RequestBody Expert expert) throws URISyntaxException {
        log.debug("REST request to update Expert : {}", expert);
        if (expert.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Expert result = expertService.save(expert);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, expert.getId().toString()))
            .body(result);
    }

    /**
     * GET  /experts : get all the experts.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of experts in body
     */
    @GetMapping("/experts")
    public ResponseEntity<List<Expert>> getAllExperts(ExpertCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Experts by criteria: {}", criteria);
        Page<Expert> page = expertQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/experts");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /experts/count : count all the experts.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/experts/count")
    public ResponseEntity<Long> countExperts(ExpertCriteria criteria) {
        log.debug("REST request to count Experts by criteria: {}", criteria);
        return ResponseEntity.ok().body(expertQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /experts/:id : get the "id" expert.
     *
     * @param id the id of the expert to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the expert, or with status 404 (Not Found)
     */
    @GetMapping("/experts/{id}")
    public ResponseEntity<Expert> getExpert(@PathVariable Long id) {
        log.debug("REST request to get Expert : {}", id);
        Optional<Expert> expert = expertService.findOne(id);
        return ResponseUtil.wrapOrNotFound(expert);
    }

    /**
     * DELETE  /experts/:id : delete the "id" expert.
     *
     * @param id the id of the expert to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/experts/{id}")
    public ResponseEntity<Void> deleteExpert(@PathVariable Long id) {
        log.debug("REST request to delete Expert : {}", id);
        expertService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
