package com.oplever.prep.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.oplever.prep.domain.Actas;
import com.oplever.prep.domain.*; // for static metamodels
import com.oplever.prep.repository.ActasRepository;
import com.oplever.prep.service.dto.ActasCriteria;


/**
 * Service for executing complex queries for Actas entities in the database.
 * The main input is a {@link ActasCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link Actas} or a {@link Page} of {%link Actas} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class ActasQueryService extends QueryService<Actas> {

    private final Logger log = LoggerFactory.getLogger(ActasQueryService.class);


    private final ActasRepository actasRepository;

    public ActasQueryService(ActasRepository actasRepository) {
        this.actasRepository = actasRepository;
    }

    /**
     * Return a {@link List} of {%link Actas} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Actas> findByCriteria(ActasCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Actas> specification = createSpecification(criteria);
        return actasRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link Actas} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Actas> findByCriteria(ActasCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Actas> specification = createSpecification(criteria);
        return actasRepository.findAll(specification, page);
    }

    /**
     * Function to convert ActasCriteria to a {@link Specifications}
     */
    private Specifications<Actas> createSpecification(ActasCriteria criteria) {
        Specifications<Actas> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Actas_.id));
            }
            if (criteria.getDistrito() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDistrito(), Actas_.distrito));
            }
            if (criteria.getSeccion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSeccion(), Actas_.seccion));
            }
            if (criteria.getTipo_acta() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipo_acta(), Actas_.tipo_acta));
            }
            if (criteria.getTipo_casilla() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipo_casilla(), Actas_.tipo_casilla));
            }
            if (criteria.getImagen() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImagen(), Actas_.imagen));
            }
            if (criteria.getVotos_1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVotos_1(), Actas_.votos_1));
            }
            if (criteria.getVotos_2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVotos_2(), Actas_.votos_2));
            }
            if (criteria.getVotos_3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVotos_3(), Actas_.votos_3));
            }
            if (criteria.getValidacion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValidacion(), Actas_.validacion));
            }
            if (criteria.getEstatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEstatus(), Actas_.estatus));
            }
        }
        return specification;
    }

}
