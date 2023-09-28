package com.app.music.service;

import com.app.music.domain.Arreglo;
import com.app.music.repository.ArregloRepository;
import com.app.music.service.dto.ArregloDTO;
import com.app.music.service.mapper.ArregloMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Arreglo}.
 */
@Service
@Transactional
public class ArregloService {

    private final Logger log = LoggerFactory.getLogger(ArregloService.class);

    private final ArregloRepository arregloRepository;

    private final ArregloMapper arregloMapper;

    public ArregloService(ArregloRepository arregloRepository, ArregloMapper arregloMapper) {
        this.arregloRepository = arregloRepository;
        this.arregloMapper = arregloMapper;
    }

    /**
     * Save a arreglo.
     *
     * @param arregloDTO the entity to save.
     * @return the persisted entity.
     */
    public ArregloDTO save(ArregloDTO arregloDTO) {
        log.debug("Request to save Arreglo : {}", arregloDTO);
        Arreglo arreglo = arregloMapper.toEntity(arregloDTO);
        arreglo = arregloRepository.save(arreglo);
        return arregloMapper.toDto(arreglo);
    }

    /**
     * Update a arreglo.
     *
     * @param arregloDTO the entity to save.
     * @return the persisted entity.
     */
    public ArregloDTO update(ArregloDTO arregloDTO) {
        log.debug("Request to update Arreglo : {}", arregloDTO);
        Arreglo arreglo = arregloMapper.toEntity(arregloDTO);
        arreglo = arregloRepository.save(arreglo);
        return arregloMapper.toDto(arreglo);
    }

    /**
     * Partially update a arreglo.
     *
     * @param arregloDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ArregloDTO> partialUpdate(ArregloDTO arregloDTO) {
        log.debug("Request to partially update Arreglo : {}", arregloDTO);

        return arregloRepository
            .findById(arregloDTO.getId())
            .map(existingArreglo -> {
                arregloMapper.partialUpdate(existingArreglo, arregloDTO);

                return existingArreglo;
            })
            .map(arregloRepository::save)
            .map(arregloMapper::toDto);
    }

    /**
     * Get all the arreglos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ArregloDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Arreglos");
        return arregloRepository.findAll(pageable).map(arregloMapper::toDto);
    }

    /**
     * Get one arreglo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ArregloDTO> findOne(Long id) {
        log.debug("Request to get Arreglo : {}", id);
        return arregloRepository.findById(id).map(arregloMapper::toDto);
    }

    /**
     * Delete the arreglo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Arreglo : {}", id);
        arregloRepository.deleteById(id);
    }
}
