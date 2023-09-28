package com.app.music.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArregloMapperTest {

    private ArregloMapper arregloMapper;

    @BeforeEach
    public void setUp() {
        arregloMapper = new ArregloMapperImpl();
    }
}
