package com.app.music.service.mapper;

import com.app.music.domain.Arreglo;
import com.app.music.service.dto.ArregloDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Arreglo} and its DTO {@link ArregloDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArregloMapper extends EntityMapper<ArregloDTO, Arreglo> {}
