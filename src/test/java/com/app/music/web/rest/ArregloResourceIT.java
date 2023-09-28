package com.app.music.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.app.music.IntegrationTest;
import com.app.music.domain.Arreglo;
import com.app.music.repository.ArregloRepository;
import com.app.music.service.criteria.ArregloCriteria;
import com.app.music.service.dto.ArregloDTO;
import com.app.music.service.mapper.ArregloMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ArregloResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArregloResourceIT {

    private static final String DEFAULT_RUT_CLIENTE = "AAAAAAAAAA";
    private static final String UPDATED_RUT_CLIENTE = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_RECEPCION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_RECEPCION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_ENTREGA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_ENTREGA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NOMBRE_INSTRUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_INSTRUMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_MARCA = "AAAAAAAAAA";
    private static final String UPDATED_MARCA = "BBBBBBBBBB";

    private static final String DEFAULT_MODELO = "AAAAAAAAAA";
    private static final String UPDATED_MODELO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_SERIE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_SERIE = "BBBBBBBBBB";

    private static final String DEFAULT_DIAGNOSTICO = "AAAAAAAAAA";
    private static final String UPDATED_DIAGNOSTICO = "BBBBBBBBBB";

    private static final String DEFAULT_PROCEDIMIENTO = "AAAAAAAAAA";
    private static final String UPDATED_PROCEDIMIENTO = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/arreglos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ArregloRepository arregloRepository;

    @Autowired
    private ArregloMapper arregloMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArregloMockMvc;

    private Arreglo arreglo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Arreglo createEntity(EntityManager em) {
        Arreglo arreglo = new Arreglo()
            .rutCliente(DEFAULT_RUT_CLIENTE)
            .nombre(DEFAULT_NOMBRE)
            .telefono(DEFAULT_TELEFONO)
            .fechaRecepcion(DEFAULT_FECHA_RECEPCION)
            .fechaEntrega(DEFAULT_FECHA_ENTREGA)
            .nombreInstrumento(DEFAULT_NOMBRE_INSTRUMENTO)
            .marca(DEFAULT_MARCA)
            .modelo(DEFAULT_MODELO)
            .numeroSerie(DEFAULT_NUMERO_SERIE)
            .diagnostico(DEFAULT_DIAGNOSTICO)
            .procedimiento(DEFAULT_PROCEDIMIENTO)
            .observaciones(DEFAULT_OBSERVACIONES)
            .valor(DEFAULT_VALOR);
        return arreglo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Arreglo createUpdatedEntity(EntityManager em) {
        Arreglo arreglo = new Arreglo()
            .rutCliente(UPDATED_RUT_CLIENTE)
            .nombre(UPDATED_NOMBRE)
            .telefono(UPDATED_TELEFONO)
            .fechaRecepcion(UPDATED_FECHA_RECEPCION)
            .fechaEntrega(UPDATED_FECHA_ENTREGA)
            .nombreInstrumento(UPDATED_NOMBRE_INSTRUMENTO)
            .marca(UPDATED_MARCA)
            .modelo(UPDATED_MODELO)
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .diagnostico(UPDATED_DIAGNOSTICO)
            .procedimiento(UPDATED_PROCEDIMIENTO)
            .observaciones(UPDATED_OBSERVACIONES)
            .valor(UPDATED_VALOR);
        return arreglo;
    }

    @BeforeEach
    public void initTest() {
        arreglo = createEntity(em);
    }

    @Test
    @Transactional
    void createArreglo() throws Exception {
        int databaseSizeBeforeCreate = arregloRepository.findAll().size();
        // Create the Arreglo
        ArregloDTO arregloDTO = arregloMapper.toDto(arreglo);
        restArregloMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(arregloDTO)))
            .andExpect(status().isCreated());

        // Validate the Arreglo in the database
        List<Arreglo> arregloList = arregloRepository.findAll();
        assertThat(arregloList).hasSize(databaseSizeBeforeCreate + 1);
        Arreglo testArreglo = arregloList.get(arregloList.size() - 1);
        assertThat(testArreglo.getRutCliente()).isEqualTo(DEFAULT_RUT_CLIENTE);
        assertThat(testArreglo.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testArreglo.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testArreglo.getFechaRecepcion()).isEqualTo(DEFAULT_FECHA_RECEPCION);
        assertThat(testArreglo.getFechaEntrega()).isEqualTo(DEFAULT_FECHA_ENTREGA);
        assertThat(testArreglo.getNombreInstrumento()).isEqualTo(DEFAULT_NOMBRE_INSTRUMENTO);
        assertThat(testArreglo.getMarca()).isEqualTo(DEFAULT_MARCA);
        assertThat(testArreglo.getModelo()).isEqualTo(DEFAULT_MODELO);
        assertThat(testArreglo.getNumeroSerie()).isEqualTo(DEFAULT_NUMERO_SERIE);
        assertThat(testArreglo.getDiagnostico()).isEqualTo(DEFAULT_DIAGNOSTICO);
        assertThat(testArreglo.getProcedimiento()).isEqualTo(DEFAULT_PROCEDIMIENTO);
        assertThat(testArreglo.getObservaciones()).isEqualTo(DEFAULT_OBSERVACIONES);
        assertThat(testArreglo.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    void createArregloWithExistingId() throws Exception {
        // Create the Arreglo with an existing ID
        arreglo.setId(1L);
        ArregloDTO arregloDTO = arregloMapper.toDto(arreglo);

        int databaseSizeBeforeCreate = arregloRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArregloMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(arregloDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Arreglo in the database
        List<Arreglo> arregloList = arregloRepository.findAll();
        assertThat(arregloList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllArreglos() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList
        restArregloMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(arreglo.getId().intValue())))
            .andExpect(jsonPath("$.[*].rutCliente").value(hasItem(DEFAULT_RUT_CLIENTE)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].fechaRecepcion").value(hasItem(DEFAULT_FECHA_RECEPCION.toString())))
            .andExpect(jsonPath("$.[*].fechaEntrega").value(hasItem(DEFAULT_FECHA_ENTREGA.toString())))
            .andExpect(jsonPath("$.[*].nombreInstrumento").value(hasItem(DEFAULT_NOMBRE_INSTRUMENTO)))
            .andExpect(jsonPath("$.[*].marca").value(hasItem(DEFAULT_MARCA)))
            .andExpect(jsonPath("$.[*].modelo").value(hasItem(DEFAULT_MODELO)))
            .andExpect(jsonPath("$.[*].numeroSerie").value(hasItem(DEFAULT_NUMERO_SERIE)))
            .andExpect(jsonPath("$.[*].diagnostico").value(hasItem(DEFAULT_DIAGNOSTICO)))
            .andExpect(jsonPath("$.[*].procedimiento").value(hasItem(DEFAULT_PROCEDIMIENTO)))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }

    @Test
    @Transactional
    void getArreglo() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get the arreglo
        restArregloMockMvc
            .perform(get(ENTITY_API_URL_ID, arreglo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(arreglo.getId().intValue()))
            .andExpect(jsonPath("$.rutCliente").value(DEFAULT_RUT_CLIENTE))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.fechaRecepcion").value(DEFAULT_FECHA_RECEPCION.toString()))
            .andExpect(jsonPath("$.fechaEntrega").value(DEFAULT_FECHA_ENTREGA.toString()))
            .andExpect(jsonPath("$.nombreInstrumento").value(DEFAULT_NOMBRE_INSTRUMENTO))
            .andExpect(jsonPath("$.marca").value(DEFAULT_MARCA))
            .andExpect(jsonPath("$.modelo").value(DEFAULT_MODELO))
            .andExpect(jsonPath("$.numeroSerie").value(DEFAULT_NUMERO_SERIE))
            .andExpect(jsonPath("$.diagnostico").value(DEFAULT_DIAGNOSTICO))
            .andExpect(jsonPath("$.procedimiento").value(DEFAULT_PROCEDIMIENTO))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR));
    }

    @Test
    @Transactional
    void getArreglosByIdFiltering() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        Long id = arreglo.getId();

        defaultArregloShouldBeFound("id.equals=" + id);
        defaultArregloShouldNotBeFound("id.notEquals=" + id);

        defaultArregloShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultArregloShouldNotBeFound("id.greaterThan=" + id);

        defaultArregloShouldBeFound("id.lessThanOrEqual=" + id);
        defaultArregloShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllArreglosByRutClienteIsEqualToSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where rutCliente equals to DEFAULT_RUT_CLIENTE
        defaultArregloShouldBeFound("rutCliente.equals=" + DEFAULT_RUT_CLIENTE);

        // Get all the arregloList where rutCliente equals to UPDATED_RUT_CLIENTE
        defaultArregloShouldNotBeFound("rutCliente.equals=" + UPDATED_RUT_CLIENTE);
    }

    @Test
    @Transactional
    void getAllArreglosByRutClienteIsInShouldWork() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where rutCliente in DEFAULT_RUT_CLIENTE or UPDATED_RUT_CLIENTE
        defaultArregloShouldBeFound("rutCliente.in=" + DEFAULT_RUT_CLIENTE + "," + UPDATED_RUT_CLIENTE);

        // Get all the arregloList where rutCliente equals to UPDATED_RUT_CLIENTE
        defaultArregloShouldNotBeFound("rutCliente.in=" + UPDATED_RUT_CLIENTE);
    }

    @Test
    @Transactional
    void getAllArreglosByRutClienteIsNullOrNotNull() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where rutCliente is not null
        defaultArregloShouldBeFound("rutCliente.specified=true");

        // Get all the arregloList where rutCliente is null
        defaultArregloShouldNotBeFound("rutCliente.specified=false");
    }

    @Test
    @Transactional
    void getAllArreglosByRutClienteContainsSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where rutCliente contains DEFAULT_RUT_CLIENTE
        defaultArregloShouldBeFound("rutCliente.contains=" + DEFAULT_RUT_CLIENTE);

        // Get all the arregloList where rutCliente contains UPDATED_RUT_CLIENTE
        defaultArregloShouldNotBeFound("rutCliente.contains=" + UPDATED_RUT_CLIENTE);
    }

    @Test
    @Transactional
    void getAllArreglosByRutClienteNotContainsSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where rutCliente does not contain DEFAULT_RUT_CLIENTE
        defaultArregloShouldNotBeFound("rutCliente.doesNotContain=" + DEFAULT_RUT_CLIENTE);

        // Get all the arregloList where rutCliente does not contain UPDATED_RUT_CLIENTE
        defaultArregloShouldBeFound("rutCliente.doesNotContain=" + UPDATED_RUT_CLIENTE);
    }

    @Test
    @Transactional
    void getAllArreglosByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where nombre equals to DEFAULT_NOMBRE
        defaultArregloShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the arregloList where nombre equals to UPDATED_NOMBRE
        defaultArregloShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllArreglosByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultArregloShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the arregloList where nombre equals to UPDATED_NOMBRE
        defaultArregloShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllArreglosByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where nombre is not null
        defaultArregloShouldBeFound("nombre.specified=true");

        // Get all the arregloList where nombre is null
        defaultArregloShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    void getAllArreglosByNombreContainsSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where nombre contains DEFAULT_NOMBRE
        defaultArregloShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

        // Get all the arregloList where nombre contains UPDATED_NOMBRE
        defaultArregloShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllArreglosByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where nombre does not contain DEFAULT_NOMBRE
        defaultArregloShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

        // Get all the arregloList where nombre does not contain UPDATED_NOMBRE
        defaultArregloShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllArreglosByTelefonoIsEqualToSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where telefono equals to DEFAULT_TELEFONO
        defaultArregloShouldBeFound("telefono.equals=" + DEFAULT_TELEFONO);

        // Get all the arregloList where telefono equals to UPDATED_TELEFONO
        defaultArregloShouldNotBeFound("telefono.equals=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    void getAllArreglosByTelefonoIsInShouldWork() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where telefono in DEFAULT_TELEFONO or UPDATED_TELEFONO
        defaultArregloShouldBeFound("telefono.in=" + DEFAULT_TELEFONO + "," + UPDATED_TELEFONO);

        // Get all the arregloList where telefono equals to UPDATED_TELEFONO
        defaultArregloShouldNotBeFound("telefono.in=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    void getAllArreglosByTelefonoIsNullOrNotNull() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where telefono is not null
        defaultArregloShouldBeFound("telefono.specified=true");

        // Get all the arregloList where telefono is null
        defaultArregloShouldNotBeFound("telefono.specified=false");
    }

    @Test
    @Transactional
    void getAllArreglosByTelefonoContainsSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where telefono contains DEFAULT_TELEFONO
        defaultArregloShouldBeFound("telefono.contains=" + DEFAULT_TELEFONO);

        // Get all the arregloList where telefono contains UPDATED_TELEFONO
        defaultArregloShouldNotBeFound("telefono.contains=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    void getAllArreglosByTelefonoNotContainsSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where telefono does not contain DEFAULT_TELEFONO
        defaultArregloShouldNotBeFound("telefono.doesNotContain=" + DEFAULT_TELEFONO);

        // Get all the arregloList where telefono does not contain UPDATED_TELEFONO
        defaultArregloShouldBeFound("telefono.doesNotContain=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    void getAllArreglosByFechaRecepcionIsEqualToSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where fechaRecepcion equals to DEFAULT_FECHA_RECEPCION
        defaultArregloShouldBeFound("fechaRecepcion.equals=" + DEFAULT_FECHA_RECEPCION);

        // Get all the arregloList where fechaRecepcion equals to UPDATED_FECHA_RECEPCION
        defaultArregloShouldNotBeFound("fechaRecepcion.equals=" + UPDATED_FECHA_RECEPCION);
    }

    @Test
    @Transactional
    void getAllArreglosByFechaRecepcionIsInShouldWork() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where fechaRecepcion in DEFAULT_FECHA_RECEPCION or UPDATED_FECHA_RECEPCION
        defaultArregloShouldBeFound("fechaRecepcion.in=" + DEFAULT_FECHA_RECEPCION + "," + UPDATED_FECHA_RECEPCION);

        // Get all the arregloList where fechaRecepcion equals to UPDATED_FECHA_RECEPCION
        defaultArregloShouldNotBeFound("fechaRecepcion.in=" + UPDATED_FECHA_RECEPCION);
    }

    @Test
    @Transactional
    void getAllArreglosByFechaRecepcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where fechaRecepcion is not null
        defaultArregloShouldBeFound("fechaRecepcion.specified=true");

        // Get all the arregloList where fechaRecepcion is null
        defaultArregloShouldNotBeFound("fechaRecepcion.specified=false");
    }

    @Test
    @Transactional
    void getAllArreglosByFechaEntregaIsEqualToSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where fechaEntrega equals to DEFAULT_FECHA_ENTREGA
        defaultArregloShouldBeFound("fechaEntrega.equals=" + DEFAULT_FECHA_ENTREGA);

        // Get all the arregloList where fechaEntrega equals to UPDATED_FECHA_ENTREGA
        defaultArregloShouldNotBeFound("fechaEntrega.equals=" + UPDATED_FECHA_ENTREGA);
    }

    @Test
    @Transactional
    void getAllArreglosByFechaEntregaIsInShouldWork() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where fechaEntrega in DEFAULT_FECHA_ENTREGA or UPDATED_FECHA_ENTREGA
        defaultArregloShouldBeFound("fechaEntrega.in=" + DEFAULT_FECHA_ENTREGA + "," + UPDATED_FECHA_ENTREGA);

        // Get all the arregloList where fechaEntrega equals to UPDATED_FECHA_ENTREGA
        defaultArregloShouldNotBeFound("fechaEntrega.in=" + UPDATED_FECHA_ENTREGA);
    }

    @Test
    @Transactional
    void getAllArreglosByFechaEntregaIsNullOrNotNull() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where fechaEntrega is not null
        defaultArregloShouldBeFound("fechaEntrega.specified=true");

        // Get all the arregloList where fechaEntrega is null
        defaultArregloShouldNotBeFound("fechaEntrega.specified=false");
    }

    @Test
    @Transactional
    void getAllArreglosByNombreInstrumentoIsEqualToSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where nombreInstrumento equals to DEFAULT_NOMBRE_INSTRUMENTO
        defaultArregloShouldBeFound("nombreInstrumento.equals=" + DEFAULT_NOMBRE_INSTRUMENTO);

        // Get all the arregloList where nombreInstrumento equals to UPDATED_NOMBRE_INSTRUMENTO
        defaultArregloShouldNotBeFound("nombreInstrumento.equals=" + UPDATED_NOMBRE_INSTRUMENTO);
    }

    @Test
    @Transactional
    void getAllArreglosByNombreInstrumentoIsInShouldWork() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where nombreInstrumento in DEFAULT_NOMBRE_INSTRUMENTO or UPDATED_NOMBRE_INSTRUMENTO
        defaultArregloShouldBeFound("nombreInstrumento.in=" + DEFAULT_NOMBRE_INSTRUMENTO + "," + UPDATED_NOMBRE_INSTRUMENTO);

        // Get all the arregloList where nombreInstrumento equals to UPDATED_NOMBRE_INSTRUMENTO
        defaultArregloShouldNotBeFound("nombreInstrumento.in=" + UPDATED_NOMBRE_INSTRUMENTO);
    }

    @Test
    @Transactional
    void getAllArreglosByNombreInstrumentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where nombreInstrumento is not null
        defaultArregloShouldBeFound("nombreInstrumento.specified=true");

        // Get all the arregloList where nombreInstrumento is null
        defaultArregloShouldNotBeFound("nombreInstrumento.specified=false");
    }

    @Test
    @Transactional
    void getAllArreglosByNombreInstrumentoContainsSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where nombreInstrumento contains DEFAULT_NOMBRE_INSTRUMENTO
        defaultArregloShouldBeFound("nombreInstrumento.contains=" + DEFAULT_NOMBRE_INSTRUMENTO);

        // Get all the arregloList where nombreInstrumento contains UPDATED_NOMBRE_INSTRUMENTO
        defaultArregloShouldNotBeFound("nombreInstrumento.contains=" + UPDATED_NOMBRE_INSTRUMENTO);
    }

    @Test
    @Transactional
    void getAllArreglosByNombreInstrumentoNotContainsSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where nombreInstrumento does not contain DEFAULT_NOMBRE_INSTRUMENTO
        defaultArregloShouldNotBeFound("nombreInstrumento.doesNotContain=" + DEFAULT_NOMBRE_INSTRUMENTO);

        // Get all the arregloList where nombreInstrumento does not contain UPDATED_NOMBRE_INSTRUMENTO
        defaultArregloShouldBeFound("nombreInstrumento.doesNotContain=" + UPDATED_NOMBRE_INSTRUMENTO);
    }

    @Test
    @Transactional
    void getAllArreglosByMarcaIsEqualToSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where marca equals to DEFAULT_MARCA
        defaultArregloShouldBeFound("marca.equals=" + DEFAULT_MARCA);

        // Get all the arregloList where marca equals to UPDATED_MARCA
        defaultArregloShouldNotBeFound("marca.equals=" + UPDATED_MARCA);
    }

    @Test
    @Transactional
    void getAllArreglosByMarcaIsInShouldWork() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where marca in DEFAULT_MARCA or UPDATED_MARCA
        defaultArregloShouldBeFound("marca.in=" + DEFAULT_MARCA + "," + UPDATED_MARCA);

        // Get all the arregloList where marca equals to UPDATED_MARCA
        defaultArregloShouldNotBeFound("marca.in=" + UPDATED_MARCA);
    }

    @Test
    @Transactional
    void getAllArreglosByMarcaIsNullOrNotNull() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where marca is not null
        defaultArregloShouldBeFound("marca.specified=true");

        // Get all the arregloList where marca is null
        defaultArregloShouldNotBeFound("marca.specified=false");
    }

    @Test
    @Transactional
    void getAllArreglosByMarcaContainsSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where marca contains DEFAULT_MARCA
        defaultArregloShouldBeFound("marca.contains=" + DEFAULT_MARCA);

        // Get all the arregloList where marca contains UPDATED_MARCA
        defaultArregloShouldNotBeFound("marca.contains=" + UPDATED_MARCA);
    }

    @Test
    @Transactional
    void getAllArreglosByMarcaNotContainsSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where marca does not contain DEFAULT_MARCA
        defaultArregloShouldNotBeFound("marca.doesNotContain=" + DEFAULT_MARCA);

        // Get all the arregloList where marca does not contain UPDATED_MARCA
        defaultArregloShouldBeFound("marca.doesNotContain=" + UPDATED_MARCA);
    }

    @Test
    @Transactional
    void getAllArreglosByModeloIsEqualToSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where modelo equals to DEFAULT_MODELO
        defaultArregloShouldBeFound("modelo.equals=" + DEFAULT_MODELO);

        // Get all the arregloList where modelo equals to UPDATED_MODELO
        defaultArregloShouldNotBeFound("modelo.equals=" + UPDATED_MODELO);
    }

    @Test
    @Transactional
    void getAllArreglosByModeloIsInShouldWork() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where modelo in DEFAULT_MODELO or UPDATED_MODELO
        defaultArregloShouldBeFound("modelo.in=" + DEFAULT_MODELO + "," + UPDATED_MODELO);

        // Get all the arregloList where modelo equals to UPDATED_MODELO
        defaultArregloShouldNotBeFound("modelo.in=" + UPDATED_MODELO);
    }

    @Test
    @Transactional
    void getAllArreglosByModeloIsNullOrNotNull() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where modelo is not null
        defaultArregloShouldBeFound("modelo.specified=true");

        // Get all the arregloList where modelo is null
        defaultArregloShouldNotBeFound("modelo.specified=false");
    }

    @Test
    @Transactional
    void getAllArreglosByModeloContainsSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where modelo contains DEFAULT_MODELO
        defaultArregloShouldBeFound("modelo.contains=" + DEFAULT_MODELO);

        // Get all the arregloList where modelo contains UPDATED_MODELO
        defaultArregloShouldNotBeFound("modelo.contains=" + UPDATED_MODELO);
    }

    @Test
    @Transactional
    void getAllArreglosByModeloNotContainsSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where modelo does not contain DEFAULT_MODELO
        defaultArregloShouldNotBeFound("modelo.doesNotContain=" + DEFAULT_MODELO);

        // Get all the arregloList where modelo does not contain UPDATED_MODELO
        defaultArregloShouldBeFound("modelo.doesNotContain=" + UPDATED_MODELO);
    }

    @Test
    @Transactional
    void getAllArreglosByNumeroSerieIsEqualToSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where numeroSerie equals to DEFAULT_NUMERO_SERIE
        defaultArregloShouldBeFound("numeroSerie.equals=" + DEFAULT_NUMERO_SERIE);

        // Get all the arregloList where numeroSerie equals to UPDATED_NUMERO_SERIE
        defaultArregloShouldNotBeFound("numeroSerie.equals=" + UPDATED_NUMERO_SERIE);
    }

    @Test
    @Transactional
    void getAllArreglosByNumeroSerieIsInShouldWork() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where numeroSerie in DEFAULT_NUMERO_SERIE or UPDATED_NUMERO_SERIE
        defaultArregloShouldBeFound("numeroSerie.in=" + DEFAULT_NUMERO_SERIE + "," + UPDATED_NUMERO_SERIE);

        // Get all the arregloList where numeroSerie equals to UPDATED_NUMERO_SERIE
        defaultArregloShouldNotBeFound("numeroSerie.in=" + UPDATED_NUMERO_SERIE);
    }

    @Test
    @Transactional
    void getAllArreglosByNumeroSerieIsNullOrNotNull() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where numeroSerie is not null
        defaultArregloShouldBeFound("numeroSerie.specified=true");

        // Get all the arregloList where numeroSerie is null
        defaultArregloShouldNotBeFound("numeroSerie.specified=false");
    }

    @Test
    @Transactional
    void getAllArreglosByNumeroSerieContainsSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where numeroSerie contains DEFAULT_NUMERO_SERIE
        defaultArregloShouldBeFound("numeroSerie.contains=" + DEFAULT_NUMERO_SERIE);

        // Get all the arregloList where numeroSerie contains UPDATED_NUMERO_SERIE
        defaultArregloShouldNotBeFound("numeroSerie.contains=" + UPDATED_NUMERO_SERIE);
    }

    @Test
    @Transactional
    void getAllArreglosByNumeroSerieNotContainsSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where numeroSerie does not contain DEFAULT_NUMERO_SERIE
        defaultArregloShouldNotBeFound("numeroSerie.doesNotContain=" + DEFAULT_NUMERO_SERIE);

        // Get all the arregloList where numeroSerie does not contain UPDATED_NUMERO_SERIE
        defaultArregloShouldBeFound("numeroSerie.doesNotContain=" + UPDATED_NUMERO_SERIE);
    }

    @Test
    @Transactional
    void getAllArreglosByDiagnosticoIsEqualToSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where diagnostico equals to DEFAULT_DIAGNOSTICO
        defaultArregloShouldBeFound("diagnostico.equals=" + DEFAULT_DIAGNOSTICO);

        // Get all the arregloList where diagnostico equals to UPDATED_DIAGNOSTICO
        defaultArregloShouldNotBeFound("diagnostico.equals=" + UPDATED_DIAGNOSTICO);
    }

    @Test
    @Transactional
    void getAllArreglosByDiagnosticoIsInShouldWork() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where diagnostico in DEFAULT_DIAGNOSTICO or UPDATED_DIAGNOSTICO
        defaultArregloShouldBeFound("diagnostico.in=" + DEFAULT_DIAGNOSTICO + "," + UPDATED_DIAGNOSTICO);

        // Get all the arregloList where diagnostico equals to UPDATED_DIAGNOSTICO
        defaultArregloShouldNotBeFound("diagnostico.in=" + UPDATED_DIAGNOSTICO);
    }

    @Test
    @Transactional
    void getAllArreglosByDiagnosticoIsNullOrNotNull() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where diagnostico is not null
        defaultArregloShouldBeFound("diagnostico.specified=true");

        // Get all the arregloList where diagnostico is null
        defaultArregloShouldNotBeFound("diagnostico.specified=false");
    }

    @Test
    @Transactional
    void getAllArreglosByDiagnosticoContainsSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where diagnostico contains DEFAULT_DIAGNOSTICO
        defaultArregloShouldBeFound("diagnostico.contains=" + DEFAULT_DIAGNOSTICO);

        // Get all the arregloList where diagnostico contains UPDATED_DIAGNOSTICO
        defaultArregloShouldNotBeFound("diagnostico.contains=" + UPDATED_DIAGNOSTICO);
    }

    @Test
    @Transactional
    void getAllArreglosByDiagnosticoNotContainsSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where diagnostico does not contain DEFAULT_DIAGNOSTICO
        defaultArregloShouldNotBeFound("diagnostico.doesNotContain=" + DEFAULT_DIAGNOSTICO);

        // Get all the arregloList where diagnostico does not contain UPDATED_DIAGNOSTICO
        defaultArregloShouldBeFound("diagnostico.doesNotContain=" + UPDATED_DIAGNOSTICO);
    }

    @Test
    @Transactional
    void getAllArreglosByProcedimientoIsEqualToSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where procedimiento equals to DEFAULT_PROCEDIMIENTO
        defaultArregloShouldBeFound("procedimiento.equals=" + DEFAULT_PROCEDIMIENTO);

        // Get all the arregloList where procedimiento equals to UPDATED_PROCEDIMIENTO
        defaultArregloShouldNotBeFound("procedimiento.equals=" + UPDATED_PROCEDIMIENTO);
    }

    @Test
    @Transactional
    void getAllArreglosByProcedimientoIsInShouldWork() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where procedimiento in DEFAULT_PROCEDIMIENTO or UPDATED_PROCEDIMIENTO
        defaultArregloShouldBeFound("procedimiento.in=" + DEFAULT_PROCEDIMIENTO + "," + UPDATED_PROCEDIMIENTO);

        // Get all the arregloList where procedimiento equals to UPDATED_PROCEDIMIENTO
        defaultArregloShouldNotBeFound("procedimiento.in=" + UPDATED_PROCEDIMIENTO);
    }

    @Test
    @Transactional
    void getAllArreglosByProcedimientoIsNullOrNotNull() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where procedimiento is not null
        defaultArregloShouldBeFound("procedimiento.specified=true");

        // Get all the arregloList where procedimiento is null
        defaultArregloShouldNotBeFound("procedimiento.specified=false");
    }

    @Test
    @Transactional
    void getAllArreglosByProcedimientoContainsSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where procedimiento contains DEFAULT_PROCEDIMIENTO
        defaultArregloShouldBeFound("procedimiento.contains=" + DEFAULT_PROCEDIMIENTO);

        // Get all the arregloList where procedimiento contains UPDATED_PROCEDIMIENTO
        defaultArregloShouldNotBeFound("procedimiento.contains=" + UPDATED_PROCEDIMIENTO);
    }

    @Test
    @Transactional
    void getAllArreglosByProcedimientoNotContainsSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where procedimiento does not contain DEFAULT_PROCEDIMIENTO
        defaultArregloShouldNotBeFound("procedimiento.doesNotContain=" + DEFAULT_PROCEDIMIENTO);

        // Get all the arregloList where procedimiento does not contain UPDATED_PROCEDIMIENTO
        defaultArregloShouldBeFound("procedimiento.doesNotContain=" + UPDATED_PROCEDIMIENTO);
    }

    @Test
    @Transactional
    void getAllArreglosByObservacionesIsEqualToSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where observaciones equals to DEFAULT_OBSERVACIONES
        defaultArregloShouldBeFound("observaciones.equals=" + DEFAULT_OBSERVACIONES);

        // Get all the arregloList where observaciones equals to UPDATED_OBSERVACIONES
        defaultArregloShouldNotBeFound("observaciones.equals=" + UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    void getAllArreglosByObservacionesIsInShouldWork() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where observaciones in DEFAULT_OBSERVACIONES or UPDATED_OBSERVACIONES
        defaultArregloShouldBeFound("observaciones.in=" + DEFAULT_OBSERVACIONES + "," + UPDATED_OBSERVACIONES);

        // Get all the arregloList where observaciones equals to UPDATED_OBSERVACIONES
        defaultArregloShouldNotBeFound("observaciones.in=" + UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    void getAllArreglosByObservacionesIsNullOrNotNull() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where observaciones is not null
        defaultArregloShouldBeFound("observaciones.specified=true");

        // Get all the arregloList where observaciones is null
        defaultArregloShouldNotBeFound("observaciones.specified=false");
    }

    @Test
    @Transactional
    void getAllArreglosByObservacionesContainsSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where observaciones contains DEFAULT_OBSERVACIONES
        defaultArregloShouldBeFound("observaciones.contains=" + DEFAULT_OBSERVACIONES);

        // Get all the arregloList where observaciones contains UPDATED_OBSERVACIONES
        defaultArregloShouldNotBeFound("observaciones.contains=" + UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    void getAllArreglosByObservacionesNotContainsSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where observaciones does not contain DEFAULT_OBSERVACIONES
        defaultArregloShouldNotBeFound("observaciones.doesNotContain=" + DEFAULT_OBSERVACIONES);

        // Get all the arregloList where observaciones does not contain UPDATED_OBSERVACIONES
        defaultArregloShouldBeFound("observaciones.doesNotContain=" + UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    void getAllArreglosByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where valor equals to DEFAULT_VALOR
        defaultArregloShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the arregloList where valor equals to UPDATED_VALOR
        defaultArregloShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    void getAllArreglosByValorIsInShouldWork() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultArregloShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the arregloList where valor equals to UPDATED_VALOR
        defaultArregloShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    void getAllArreglosByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where valor is not null
        defaultArregloShouldBeFound("valor.specified=true");

        // Get all the arregloList where valor is null
        defaultArregloShouldNotBeFound("valor.specified=false");
    }

    @Test
    @Transactional
    void getAllArreglosByValorContainsSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where valor contains DEFAULT_VALOR
        defaultArregloShouldBeFound("valor.contains=" + DEFAULT_VALOR);

        // Get all the arregloList where valor contains UPDATED_VALOR
        defaultArregloShouldNotBeFound("valor.contains=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    void getAllArreglosByValorNotContainsSomething() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        // Get all the arregloList where valor does not contain DEFAULT_VALOR
        defaultArregloShouldNotBeFound("valor.doesNotContain=" + DEFAULT_VALOR);

        // Get all the arregloList where valor does not contain UPDATED_VALOR
        defaultArregloShouldBeFound("valor.doesNotContain=" + UPDATED_VALOR);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultArregloShouldBeFound(String filter) throws Exception {
        restArregloMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(arreglo.getId().intValue())))
            .andExpect(jsonPath("$.[*].rutCliente").value(hasItem(DEFAULT_RUT_CLIENTE)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].fechaRecepcion").value(hasItem(DEFAULT_FECHA_RECEPCION.toString())))
            .andExpect(jsonPath("$.[*].fechaEntrega").value(hasItem(DEFAULT_FECHA_ENTREGA.toString())))
            .andExpect(jsonPath("$.[*].nombreInstrumento").value(hasItem(DEFAULT_NOMBRE_INSTRUMENTO)))
            .andExpect(jsonPath("$.[*].marca").value(hasItem(DEFAULT_MARCA)))
            .andExpect(jsonPath("$.[*].modelo").value(hasItem(DEFAULT_MODELO)))
            .andExpect(jsonPath("$.[*].numeroSerie").value(hasItem(DEFAULT_NUMERO_SERIE)))
            .andExpect(jsonPath("$.[*].diagnostico").value(hasItem(DEFAULT_DIAGNOSTICO)))
            .andExpect(jsonPath("$.[*].procedimiento").value(hasItem(DEFAULT_PROCEDIMIENTO)))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));

        // Check, that the count call also returns 1
        restArregloMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultArregloShouldNotBeFound(String filter) throws Exception {
        restArregloMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restArregloMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingArreglo() throws Exception {
        // Get the arreglo
        restArregloMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArreglo() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        int databaseSizeBeforeUpdate = arregloRepository.findAll().size();

        // Update the arreglo
        Arreglo updatedArreglo = arregloRepository.findById(arreglo.getId()).get();
        // Disconnect from session so that the updates on updatedArreglo are not directly saved in db
        em.detach(updatedArreglo);
        updatedArreglo
            .rutCliente(UPDATED_RUT_CLIENTE)
            .nombre(UPDATED_NOMBRE)
            .telefono(UPDATED_TELEFONO)
            .fechaRecepcion(UPDATED_FECHA_RECEPCION)
            .fechaEntrega(UPDATED_FECHA_ENTREGA)
            .nombreInstrumento(UPDATED_NOMBRE_INSTRUMENTO)
            .marca(UPDATED_MARCA)
            .modelo(UPDATED_MODELO)
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .diagnostico(UPDATED_DIAGNOSTICO)
            .procedimiento(UPDATED_PROCEDIMIENTO)
            .observaciones(UPDATED_OBSERVACIONES)
            .valor(UPDATED_VALOR);
        ArregloDTO arregloDTO = arregloMapper.toDto(updatedArreglo);

        restArregloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, arregloDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arregloDTO))
            )
            .andExpect(status().isOk());

        // Validate the Arreglo in the database
        List<Arreglo> arregloList = arregloRepository.findAll();
        assertThat(arregloList).hasSize(databaseSizeBeforeUpdate);
        Arreglo testArreglo = arregloList.get(arregloList.size() - 1);
        assertThat(testArreglo.getRutCliente()).isEqualTo(UPDATED_RUT_CLIENTE);
        assertThat(testArreglo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testArreglo.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testArreglo.getFechaRecepcion()).isEqualTo(UPDATED_FECHA_RECEPCION);
        assertThat(testArreglo.getFechaEntrega()).isEqualTo(UPDATED_FECHA_ENTREGA);
        assertThat(testArreglo.getNombreInstrumento()).isEqualTo(UPDATED_NOMBRE_INSTRUMENTO);
        assertThat(testArreglo.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testArreglo.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testArreglo.getNumeroSerie()).isEqualTo(UPDATED_NUMERO_SERIE);
        assertThat(testArreglo.getDiagnostico()).isEqualTo(UPDATED_DIAGNOSTICO);
        assertThat(testArreglo.getProcedimiento()).isEqualTo(UPDATED_PROCEDIMIENTO);
        assertThat(testArreglo.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
        assertThat(testArreglo.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    void putNonExistingArreglo() throws Exception {
        int databaseSizeBeforeUpdate = arregloRepository.findAll().size();
        arreglo.setId(count.incrementAndGet());

        // Create the Arreglo
        ArregloDTO arregloDTO = arregloMapper.toDto(arreglo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArregloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, arregloDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arregloDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Arreglo in the database
        List<Arreglo> arregloList = arregloRepository.findAll();
        assertThat(arregloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArreglo() throws Exception {
        int databaseSizeBeforeUpdate = arregloRepository.findAll().size();
        arreglo.setId(count.incrementAndGet());

        // Create the Arreglo
        ArregloDTO arregloDTO = arregloMapper.toDto(arreglo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArregloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arregloDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Arreglo in the database
        List<Arreglo> arregloList = arregloRepository.findAll();
        assertThat(arregloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArreglo() throws Exception {
        int databaseSizeBeforeUpdate = arregloRepository.findAll().size();
        arreglo.setId(count.incrementAndGet());

        // Create the Arreglo
        ArregloDTO arregloDTO = arregloMapper.toDto(arreglo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArregloMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(arregloDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Arreglo in the database
        List<Arreglo> arregloList = arregloRepository.findAll();
        assertThat(arregloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArregloWithPatch() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        int databaseSizeBeforeUpdate = arregloRepository.findAll().size();

        // Update the arreglo using partial update
        Arreglo partialUpdatedArreglo = new Arreglo();
        partialUpdatedArreglo.setId(arreglo.getId());

        partialUpdatedArreglo
            .telefono(UPDATED_TELEFONO)
            .fechaEntrega(UPDATED_FECHA_ENTREGA)
            .marca(UPDATED_MARCA)
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .diagnostico(UPDATED_DIAGNOSTICO)
            .procedimiento(UPDATED_PROCEDIMIENTO)
            .valor(UPDATED_VALOR);

        restArregloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArreglo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArreglo))
            )
            .andExpect(status().isOk());

        // Validate the Arreglo in the database
        List<Arreglo> arregloList = arregloRepository.findAll();
        assertThat(arregloList).hasSize(databaseSizeBeforeUpdate);
        Arreglo testArreglo = arregloList.get(arregloList.size() - 1);
        assertThat(testArreglo.getRutCliente()).isEqualTo(DEFAULT_RUT_CLIENTE);
        assertThat(testArreglo.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testArreglo.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testArreglo.getFechaRecepcion()).isEqualTo(DEFAULT_FECHA_RECEPCION);
        assertThat(testArreglo.getFechaEntrega()).isEqualTo(UPDATED_FECHA_ENTREGA);
        assertThat(testArreglo.getNombreInstrumento()).isEqualTo(DEFAULT_NOMBRE_INSTRUMENTO);
        assertThat(testArreglo.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testArreglo.getModelo()).isEqualTo(DEFAULT_MODELO);
        assertThat(testArreglo.getNumeroSerie()).isEqualTo(UPDATED_NUMERO_SERIE);
        assertThat(testArreglo.getDiagnostico()).isEqualTo(UPDATED_DIAGNOSTICO);
        assertThat(testArreglo.getProcedimiento()).isEqualTo(UPDATED_PROCEDIMIENTO);
        assertThat(testArreglo.getObservaciones()).isEqualTo(DEFAULT_OBSERVACIONES);
        assertThat(testArreglo.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    void fullUpdateArregloWithPatch() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        int databaseSizeBeforeUpdate = arregloRepository.findAll().size();

        // Update the arreglo using partial update
        Arreglo partialUpdatedArreglo = new Arreglo();
        partialUpdatedArreglo.setId(arreglo.getId());

        partialUpdatedArreglo
            .rutCliente(UPDATED_RUT_CLIENTE)
            .nombre(UPDATED_NOMBRE)
            .telefono(UPDATED_TELEFONO)
            .fechaRecepcion(UPDATED_FECHA_RECEPCION)
            .fechaEntrega(UPDATED_FECHA_ENTREGA)
            .nombreInstrumento(UPDATED_NOMBRE_INSTRUMENTO)
            .marca(UPDATED_MARCA)
            .modelo(UPDATED_MODELO)
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .diagnostico(UPDATED_DIAGNOSTICO)
            .procedimiento(UPDATED_PROCEDIMIENTO)
            .observaciones(UPDATED_OBSERVACIONES)
            .valor(UPDATED_VALOR);

        restArregloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArreglo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArreglo))
            )
            .andExpect(status().isOk());

        // Validate the Arreglo in the database
        List<Arreglo> arregloList = arregloRepository.findAll();
        assertThat(arregloList).hasSize(databaseSizeBeforeUpdate);
        Arreglo testArreglo = arregloList.get(arregloList.size() - 1);
        assertThat(testArreglo.getRutCliente()).isEqualTo(UPDATED_RUT_CLIENTE);
        assertThat(testArreglo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testArreglo.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testArreglo.getFechaRecepcion()).isEqualTo(UPDATED_FECHA_RECEPCION);
        assertThat(testArreglo.getFechaEntrega()).isEqualTo(UPDATED_FECHA_ENTREGA);
        assertThat(testArreglo.getNombreInstrumento()).isEqualTo(UPDATED_NOMBRE_INSTRUMENTO);
        assertThat(testArreglo.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testArreglo.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testArreglo.getNumeroSerie()).isEqualTo(UPDATED_NUMERO_SERIE);
        assertThat(testArreglo.getDiagnostico()).isEqualTo(UPDATED_DIAGNOSTICO);
        assertThat(testArreglo.getProcedimiento()).isEqualTo(UPDATED_PROCEDIMIENTO);
        assertThat(testArreglo.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
        assertThat(testArreglo.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    void patchNonExistingArreglo() throws Exception {
        int databaseSizeBeforeUpdate = arregloRepository.findAll().size();
        arreglo.setId(count.incrementAndGet());

        // Create the Arreglo
        ArregloDTO arregloDTO = arregloMapper.toDto(arreglo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArregloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, arregloDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(arregloDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Arreglo in the database
        List<Arreglo> arregloList = arregloRepository.findAll();
        assertThat(arregloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArreglo() throws Exception {
        int databaseSizeBeforeUpdate = arregloRepository.findAll().size();
        arreglo.setId(count.incrementAndGet());

        // Create the Arreglo
        ArregloDTO arregloDTO = arregloMapper.toDto(arreglo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArregloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(arregloDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Arreglo in the database
        List<Arreglo> arregloList = arregloRepository.findAll();
        assertThat(arregloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArreglo() throws Exception {
        int databaseSizeBeforeUpdate = arregloRepository.findAll().size();
        arreglo.setId(count.incrementAndGet());

        // Create the Arreglo
        ArregloDTO arregloDTO = arregloMapper.toDto(arreglo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArregloMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(arregloDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Arreglo in the database
        List<Arreglo> arregloList = arregloRepository.findAll();
        assertThat(arregloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArreglo() throws Exception {
        // Initialize the database
        arregloRepository.saveAndFlush(arreglo);

        int databaseSizeBeforeDelete = arregloRepository.findAll().size();

        // Delete the arreglo
        restArregloMockMvc
            .perform(delete(ENTITY_API_URL_ID, arreglo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Arreglo> arregloList = arregloRepository.findAll();
        assertThat(arregloList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
