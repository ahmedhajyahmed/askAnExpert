package com.pu.askanexpert.web.rest;
import com.pu.askanexpert.domain.RendezVous;
import com.pu.askanexpert.service.RendezVousService;
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
 * REST controller for managing RendezVous.
 */
@RestController
@RequestMapping("/api")
public class RendezVousResource {

    private final Logger log = LoggerFactory.getLogger(RendezVousResource.class);

    private static final String ENTITY_NAME = "rendezVous";

    private final RendezVousService rendezVousService;

    public RendezVousResource(RendezVousService rendezVousService) {
        this.rendezVousService = rendezVousService;
    }

    /**
     * POST  /rendez-vous : Create a new rendezVous.
     *
     * @param rendezVous the rendezVous to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rendezVous, or with status 400 (Bad Request) if the rendezVous has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rendez-vous")
    public ResponseEntity<RendezVous> createRendezVous(@Valid @RequestBody RendezVous rendezVous) throws URISyntaxException {
        log.debug("REST request to save RendezVous : {}", rendezVous);
        if (rendezVous.getId() != null) {
            throw new BadRequestAlertException("A new rendezVous cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RendezVous result = rendezVousService.save(rendezVous);
        return ResponseEntity.created(new URI("/api/rendez-vous/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rendez-vous : Updates an existing rendezVous.
     *
     * @param rendezVous the rendezVous to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rendezVous,
     * or with status 400 (Bad Request) if the rendezVous is not valid,
     * or with status 500 (Internal Server Error) if the rendezVous couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rendez-vous")
    public ResponseEntity<RendezVous> updateRendezVous(@Valid @RequestBody RendezVous rendezVous) throws URISyntaxException {
        log.debug("REST request to update RendezVous : {}", rendezVous);
        if (rendezVous.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RendezVous result = rendezVousService.save(rendezVous);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rendezVous.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rendez-vous : get all the rendezVous.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rendezVous in body
     */
    @GetMapping("/rendez-vous")
    public ResponseEntity<List<RendezVous>> getAllRendezVous(Pageable pageable) {
        log.debug("REST request to get a page of RendezVous");
        Page<RendezVous> page = rendezVousService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rendez-vous");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /rendez-vous/:id : get the "id" rendezVous.
     *
     * @param id the id of the rendezVous to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rendezVous, or with status 404 (Not Found)
     */
    @GetMapping("/rendez-vous/{id}")
    public ResponseEntity<RendezVous> getRendezVous(@PathVariable Long id) {
        log.debug("REST request to get RendezVous : {}", id);
        Optional<RendezVous> rendezVous = rendezVousService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rendezVous);
    }

    /**
     * DELETE  /rendez-vous/:id : delete the "id" rendezVous.
     *
     * @param id the id of the rendezVous to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rendez-vous/{id}")
    public ResponseEntity<Void> deleteRendezVous(@PathVariable Long id) {
        log.debug("REST request to delete RendezVous : {}", id);
        rendezVousService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
