package com.pu.askanexpert.web.rest;
import com.pu.askanexpert.domain.HistoriqueAppel;
import com.pu.askanexpert.service.HistoriqueAppelService;
import com.pu.askanexpert.web.rest.errors.BadRequestAlertException;
import com.pu.askanexpert.web.rest.util.HeaderUtil;
import com.pu.askanexpert.web.rest.util.PaginationUtil;
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
 * REST controller for managing HistoriqueAppel.
 */
@RestController
@RequestMapping("/api")
public class HistoriqueAppelResource {

    private final Logger log = LoggerFactory.getLogger(HistoriqueAppelResource.class);

    private static final String ENTITY_NAME = "historiqueAppel";

    private final HistoriqueAppelService historiqueAppelService;

    public HistoriqueAppelResource(HistoriqueAppelService historiqueAppelService) {
        this.historiqueAppelService = historiqueAppelService;
    }

    /**
     * POST  /historique-appels : Create a new historiqueAppel.
     *
     * @param historiqueAppel the historiqueAppel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new historiqueAppel, or with status 400 (Bad Request) if the historiqueAppel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/historique-appels")
    public ResponseEntity<HistoriqueAppel> createHistoriqueAppel(@Valid @RequestBody HistoriqueAppel historiqueAppel) throws URISyntaxException {
        log.debug("REST request to save HistoriqueAppel : {}", historiqueAppel);
        if (historiqueAppel.getId() != null) {
            throw new BadRequestAlertException("A new historiqueAppel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HistoriqueAppel result = historiqueAppelService.save(historiqueAppel);
        return ResponseEntity.created(new URI("/api/historique-appels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /historique-appels : Updates an existing historiqueAppel.
     *
     * @param historiqueAppel the historiqueAppel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated historiqueAppel,
     * or with status 400 (Bad Request) if the historiqueAppel is not valid,
     * or with status 500 (Internal Server Error) if the historiqueAppel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/historique-appels")
    public ResponseEntity<HistoriqueAppel> updateHistoriqueAppel(@Valid @RequestBody HistoriqueAppel historiqueAppel) throws URISyntaxException {
        log.debug("REST request to update HistoriqueAppel : {}", historiqueAppel);
        if (historiqueAppel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HistoriqueAppel result = historiqueAppelService.save(historiqueAppel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, historiqueAppel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /historique-appels : get all the historiqueAppels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of historiqueAppels in body
     */
    @GetMapping("/historique-appels")
    public ResponseEntity<List<HistoriqueAppel>> getAllHistoriqueAppels(Pageable pageable) {
        log.debug("REST request to get a page of HistoriqueAppels");
        Page<HistoriqueAppel> page = historiqueAppelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/historique-appels");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /historique-appels/:id : get the "id" historiqueAppel.
     *
     * @param id the id of the historiqueAppel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the historiqueAppel, or with status 404 (Not Found)
     */
    @GetMapping("/historique-appels/{id}")
    public ResponseEntity<HistoriqueAppel> getHistoriqueAppel(@PathVariable Long id) {
        log.debug("REST request to get HistoriqueAppel : {}", id);
        Optional<HistoriqueAppel> historiqueAppel = historiqueAppelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(historiqueAppel);
    }

    /**
     * DELETE  /historique-appels/:id : delete the "id" historiqueAppel.
     *
     * @param id the id of the historiqueAppel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/historique-appels/{id}")
    public ResponseEntity<Void> deleteHistoriqueAppel(@PathVariable Long id) {
        log.debug("REST request to delete HistoriqueAppel : {}", id);
        historiqueAppelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
