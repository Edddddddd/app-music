package com.app.music.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.app.music.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArregloDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArregloDTO.class);
        ArregloDTO arregloDTO1 = new ArregloDTO();
        arregloDTO1.setId(1L);
        ArregloDTO arregloDTO2 = new ArregloDTO();
        assertThat(arregloDTO1).isNotEqualTo(arregloDTO2);
        arregloDTO2.setId(arregloDTO1.getId());
        assertThat(arregloDTO1).isEqualTo(arregloDTO2);
        arregloDTO2.setId(2L);
        assertThat(arregloDTO1).isNotEqualTo(arregloDTO2);
        arregloDTO1.setId(null);
        assertThat(arregloDTO1).isNotEqualTo(arregloDTO2);
    }
}
