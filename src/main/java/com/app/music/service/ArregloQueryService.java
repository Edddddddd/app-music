package com.app.music.service;

import com.app.music.domain.*; // for static metamodels
import com.app.music.domain.Arreglo;
import com.app.music.repository.ArregloRepository;
import com.app.music.service.criteria.ArregloCriteria;
import com.app.music.service.dto.ArregloDTO;
import com.app.music.service.mapper.ArregloMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Arreglo} entities in the database.
 * The main input is a {@link ArregloCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ArregloDTO} or a {@link Page} of {@link ArregloDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ArregloQueryService extends QueryService<Arreglo> {

    private final Logger log = LoggerFactory.getLogger(ArregloQueryService.class);

    private final ArregloRepository arregloRepository;

    private final ArregloMapper arregloMapper;

    public ArregloQueryService(ArregloRepository arregloRepository, ArregloMapper arregloMapper) {
        this.arregloRepository = arregloRepository;
        this.arregloMapper = arregloMapper;
    }

    /**
     * Return a {@link List} of {@link ArregloDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ArregloDTO> findByCriteria(ArregloCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Arreglo> specification = createSpecification(criteria);
        return arregloMapper.toDto(arregloRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ArregloDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ArregloDTO> findByCriteria(ArregloCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Arreglo> specification = createSpecification(criteria);
        return arregloRepository.findAll(specification, page).map(arregloMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ArregloCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Arreglo> specification = createSpecification(criteria);
        return arregloRepository.count(specification);
    }

    /**
     * Function to convert {@link ArregloCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Arreglo> createSpecification(ArregloCriteria criteria) {
        Specification<Arreglo> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Arreglo_.id));
            }
            if (criteria.getRutCliente() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRutCliente(), Arreglo_.rutCliente));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), Arreglo_.nombre));
            }
            if (criteria.getTelefono() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefono(), Arreglo_.telefono));
            }
            if (criteria.getFechaRecepcion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaRecepcion(), Arreglo_.fechaRecepcion));
            }
            if (criteria.getFechaEntrega() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaEntrega(), Arreglo_.fechaEntrega));
            }
            if (criteria.getNombreInstrumento() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombreInstrumento(), Arreglo_.nombreInstrumento));
            }
            if (criteria.getMarca() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMarca(), Arreglo_.marca));
            }
            if (criteria.getModelo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModelo(), Arreglo_.modelo));
            }
            if (criteria.getNumeroSerie() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumeroSerie(), Arreglo_.numeroSerie));
            }
            if (criteria.getDiagnostico() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDiagnostico(), Arreglo_.diagnostico));
            }
            if (criteria.getProcedimiento() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProcedimiento(), Arreglo_.procedimiento));
            }
            if (criteria.getObservaciones() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObservaciones(), Arreglo_.observaciones));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValor(), Arreglo_.valor));
            }
        }
        return specification;
    }
}
