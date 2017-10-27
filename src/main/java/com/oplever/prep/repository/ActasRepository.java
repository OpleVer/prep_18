package com.oplever.prep.repository;

import com.oplever.prep.domain.Actas;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Actas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActasRepository extends JpaRepository<Actas, Long>, JpaSpecificationExecutor<Actas> {

}
