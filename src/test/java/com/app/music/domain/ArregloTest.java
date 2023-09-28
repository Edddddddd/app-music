package com.app.music.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.app.music.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArregloTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Arreglo.class);
        Arreglo arreglo1 = new Arreglo();
        arreglo1.setId(1L);
        Arreglo arreglo2 = new Arreglo();
        arreglo2.setId(arreglo1.getId());
        assertThat(arreglo1).isEqualTo(arreglo2);
        arreglo2.setId(2L);
        assertThat(arreglo1).isNotEqualTo(arreglo2);
        arreglo1.setId(null);
        assertThat(arreglo1).isNotEqualTo(arreglo2);
    }
}
