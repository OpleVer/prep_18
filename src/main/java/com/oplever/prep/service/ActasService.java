package com.oplever.prep.service;

import com.oplever.prep.domain.Actas;
import com.oplever.prep.repository.ActasRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Actas.
 */
@Service
@Transactional
public class ActasService {

    private final Logger log = LoggerFactory.getLogger(ActasService.class);

    private final ActasRepository actasRepository;

    public ActasService(ActasRepository actasRepository) {
        this.actasRepository = actasRepository;
    }

    /**
     * Save a actas.
     *
     * @param actas the entity to save
     * @return the persisted entity
     */
    public Actas save(Actas actas) {
        log.debug("Request to save Actas : {}", actas);
        return actasRepository.save(actas);
    }

    /**
     *  Get all the actas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Actas> findAll(Pageable pageable) {
        log.debug("Request to get all Actas");
        return actasRepository.findAll(pageable);
    }

    /**
     *  Get one actas by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Actas findOne(Long id) {
        log.debug("Request to get Actas : {}", id);
        return actasRepository.findOne(id);
    }

    /**
     *  Delete the  actas by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Actas : {}", id);
        actasRepository.delete(id);
    }
}
