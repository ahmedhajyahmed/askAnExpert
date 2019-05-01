package com.pu.askanexpert.web.rest;
import com.pu.askanexpert.domain.NonDisponibilite;
import com.pu.askanexpert.service.NonDisponibiliteService;
import com.pu.askanexpert.web.rest.errors.BadRequestAlertException;
import com.pu.askanexpert.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing NonDisponibilite.
 */
@RestController
@RequestMapping("/api")
public class NonDisponibiliteResource {

    private final Logger log = LoggerFactory.getLogger(NonDisponibiliteResource.class);

    private static final String ENTITY_NAME = "nonDisponibilite";

    private final NonDisponibiliteService nonDisponibiliteService;

    public NonDisponibiliteResource(NonDisponibiliteService nonDisponibiliteService) {
        this.nonDisponibiliteService = nonDisponibiliteService;
    }

    /**
     * POST  /non-disponibilites : Create a new nonDisponibilite.
     *
     * @param nonDisponibilite the nonDisponibilite to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nonDisponibilite, or with status 400 (Bad Request) if the nonDisponibilite has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/non-disponibilites")
    public ResponseEntity<NonDisponibilite> createNonDisponibilite(@Valid @RequestBody NonDisponibilite nonDisponibilite) throws URISyntaxException {
        log.debug("REST request to save NonDisponibilite : {}", nonDisponibilite);
        if (nonDisponibilite.getId() != null) {
            throw new BadRequestAlertException("A new nonDisponibilite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NonDisponibilite result = nonDisponibiliteService.save(nonDisponibilite);
        return ResponseEntity.created(new URI("/api/non-disponibilites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /non-disponibilites : Updates an existing nonDisponibilite.
     *
     * @param nonDisponibilite the nonDisponibilite to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nonDisponibilite,
     * or with status 400 (Bad Request) if the nonDisponibilite is not valid,
     * or with status 500 (Internal Server Error) if the nonDisponibilite couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/non-disponibilites")
    public ResponseEntity<NonDisponibilite> updateNonDisponibilite(@Valid @RequestBody NonDisponibilite nonDisponibilite) throws URISyntaxException {
        log.debug("REST request to update NonDisponibilite : {}", nonDisponibilite);
        if (nonDisponibilite.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NonDisponibilite result = nonDisponibiliteService.save(nonDisponibilite);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nonDisponibilite.getId().toString()))
            .body(result);
    }

    /**
     * GET  /non-disponibilites : get all the nonDisponibilites.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of nonDisponibilites in body
     */
    @GetMapping("/non-disponibilites")
    public List<NonDisponibilite> getAllNonDisponibilites() {
        log.debug("REST request to get all NonDisponibilites");
        return nonDisponibiliteService.findAll();
    }

    /**
     * GET  /non-disponibilites/:id : get the "id" nonDisponibilite.
     *
     * @param id the id of the nonDisponibilite to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nonDisponibilite, or with status 404 (Not Found)
     */
    @GetMapping("/non-disponibilites/{id}")
    public ResponseEntity<NonDisponibilite> getNonDisponibilite(@PathVariable Long id) {
        log.debug("REST request to get NonDisponibilite : {}", id);
        Optional<NonDisponibilite> nonDisponibilite = nonDisponibiliteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nonDisponibilite);
    }

    /**
     * DELETE  /non-disponibilites/:id : delete the "id" nonDisponibilite.
     *
     * @param id the id of the nonDisponibilite to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/non-disponibilites/{id}")
    public ResponseEntity<Void> deleteNonDisponibilite(@PathVariable Long id) {
        log.debug("REST request to delete NonDisponibilite : {}", id);
        nonDisponibiliteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
