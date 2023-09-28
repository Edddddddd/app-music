package com.app.music.web.rest;

import com.app.music.repository.ArregloRepository;
import com.app.music.service.ArregloQueryService;
import com.app.music.service.ArregloService;
import com.app.music.service.criteria.ArregloCriteria;
import com.app.music.service.dto.ArregloDTO;
import com.app.music.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.app.music.domain.Arreglo}.
 */
@RestController
@RequestMapping("/api")
public class ArregloResource {

    private final Logger log = LoggerFactory.getLogger(ArregloResource.class);

    private static final String ENTITY_NAME = "arreglo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArregloService arregloService;

    private final ArregloRepository arregloRepository;

    private final ArregloQueryService arregloQueryService;

    public ArregloResource(ArregloService arregloService, ArregloRepository arregloRepository, ArregloQueryService arregloQueryService) {
        this.arregloService = arregloService;
        this.arregloRepository = arregloRepository;
        this.arregloQueryService = arregloQueryService;
    }

    /**
     * {@code POST  /arreglos} : Create a new arreglo.
     *
     * @param arregloDTO the arregloDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new arregloDTO, or with status {@code 400 (Bad Request)} if the arreglo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/arreglos")
    public ResponseEntity<ArregloDTO> createArreglo(@RequestBody ArregloDTO arregloDTO) throws URISyntaxException {
        log.debug("REST request to save Arreglo : {}", arregloDTO);
        if (arregloDTO.getId() != null) {
            throw new BadRequestAlertException("A new arreglo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArregloDTO result = arregloService.save(arregloDTO);
        return ResponseEntity
            .created(new URI("/api/arreglos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /arreglos/:id} : Updates an existing arreglo.
     *
     * @param id the id of the arregloDTO to save.
     * @param arregloDTO the arregloDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated arregloDTO,
     * or with status {@code 400 (Bad Request)} if the arregloDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the arregloDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/arreglos/{id}")
    public ResponseEntity<ArregloDTO> updateArreglo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArregloDTO arregloDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Arreglo : {}, {}", id, arregloDTO);
        if (arregloDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, arregloDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!arregloRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ArregloDTO result = arregloService.update(arregloDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, arregloDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /arreglos/:id} : Partial updates given fields of an existing arreglo, field will ignore if it is null
     *
     * @param id the id of the arregloDTO to save.
     * @param arregloDTO the arregloDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated arregloDTO,
     * or with status {@code 400 (Bad Request)} if the arregloDTO is not valid,
     * or with status {@code 404 (Not Found)} if the arregloDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the arregloDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/arreglos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ArregloDTO> partialUpdateArreglo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArregloDTO arregloDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Arreglo partially : {}, {}", id, arregloDTO);
        if (arregloDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, arregloDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!arregloRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArregloDTO> result = arregloService.partialUpdate(arregloDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, arregloDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /arreglos} : get all the arreglos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of arreglos in body.
     */
    @GetMapping("/arreglos")
    public ResponseEntity<List<ArregloDTO>> getAllArreglos(
        ArregloCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Arreglos by criteria: {}", criteria);
        Page<ArregloDTO> page = arregloQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /arreglos/count} : count all the arreglos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/arreglos/count")
    public ResponseEntity<Long> countArreglos(ArregloCriteria criteria) {
        log.debug("REST request to count Arreglos by criteria: {}", criteria);
        return ResponseEntity.ok().body(arregloQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /arreglos/:id} : get the "id" arreglo.
     *
     * @param id the id of the arregloDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the arregloDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/arreglos/{id}")
    public ResponseEntity<ArregloDTO> getArreglo(@PathVariable Long id) {
        log.debug("REST request to get Arreglo : {}", id);
        Optional<ArregloDTO> arregloDTO = arregloService.findOne(id);
        return ResponseUtil.wrapOrNotFound(arregloDTO);
    }

    /**
     * {@code DELETE  /arreglos/:id} : delete the "id" arreglo.
     *
     * @param id the id of the arregloDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/arreglos/{id}")
    public ResponseEntity<Void> deleteArreglo(@PathVariable Long id) {
        log.debug("REST request to delete Arreglo : {}", id);
        arregloService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
