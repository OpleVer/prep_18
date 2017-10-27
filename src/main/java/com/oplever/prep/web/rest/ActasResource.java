package com.oplever.prep.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.oplever.prep.domain.Actas;
import com.oplever.prep.service.ActasService;
import com.oplever.prep.web.rest.errors.BadRequestAlertException;
import com.oplever.prep.web.rest.util.HeaderUtil;
import com.oplever.prep.web.rest.util.PaginationUtil;
import com.oplever.prep.service.dto.ActasCriteria;
import com.oplever.prep.service.ActasQueryService;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Actas.
 */
@RestController
@RequestMapping("/api")
public class ActasResource {

    private final Logger log = LoggerFactory.getLogger(ActasResource.class);

    private static final String ENTITY_NAME = "actas";

    private final ActasService actasService;

    private final ActasQueryService actasQueryService;

    public ActasResource(ActasService actasService, ActasQueryService actasQueryService) {
        this.actasService = actasService;
        this.actasQueryService = actasQueryService;
    }

    /**
     * POST  /actas : Create a new actas.
     *
     * @param actas the actas to create
     * @return the ResponseEntity with status 201 (Created) and with body the new actas, or with status 400 (Bad Request) if the actas has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/actas")
    @Timed
    public ResponseEntity<Actas> createActas(@RequestBody Actas actas) throws URISyntaxException {
        log.debug("REST request to save Actas : {}", actas);
        if (actas.getId() != null) {
            throw new BadRequestAlertException("A new actas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Actas result = actasService.save(actas);
        return ResponseEntity.created(new URI("/api/actas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /actas : Updates an existing actas.
     *
     * @param actas the actas to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated actas,
     * or with status 400 (Bad Request) if the actas is not valid,
     * or with status 500 (Internal Server Error) if the actas couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/actas")
    @Timed
    public ResponseEntity<Actas> updateActas(@RequestBody Actas actas) throws URISyntaxException {
        log.debug("REST request to update Actas : {}", actas);
        if (actas.getId() == null) {
            return createActas(actas);
        }
        Actas result = actasService.save(actas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, actas.getId().toString()))
            .body(result);
    }

    /**
     * GET  /actas : get all the actas.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of actas in body
     */
    @GetMapping("/actas")
    @Timed
    public ResponseEntity<List<Actas>> getAllActas(ActasCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get Actas by criteria: {}", criteria);
        Page<Actas> page = actasQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/actas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /actas/:id : get the "id" actas.
     *
     * @param id the id of the actas to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the actas, or with status 404 (Not Found)
     */
    @GetMapping("/actas/{id}")
    @Timed
    public ResponseEntity<Actas> getActas(@PathVariable Long id) {
        log.debug("REST request to get Actas : {}", id);
        Actas actas = actasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(actas));
    }

    /**
     * DELETE  /actas/:id : delete the "id" actas.
     *
     * @param id the id of the actas to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/actas/{id}")
    @Timed
    public ResponseEntity<Void> deleteActas(@PathVariable Long id) {
        log.debug("REST request to delete Actas : {}", id);
        actasService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
