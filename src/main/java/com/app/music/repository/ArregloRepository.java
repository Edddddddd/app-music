package com.app.music.repository;

import com.app.music.domain.Arreglo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Arreglo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArregloRepository extends JpaRepository<Arreglo, Long>, JpaSpecificationExecutor<Arreglo> {}
