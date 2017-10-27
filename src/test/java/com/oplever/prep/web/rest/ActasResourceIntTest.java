package com.oplever.prep.web.rest;

import com.oplever.prep.Prep18App;

import com.oplever.prep.domain.Actas;
import com.oplever.prep.repository.ActasRepository;
import com.oplever.prep.service.ActasService;
import com.oplever.prep.web.rest.errors.ExceptionTranslator;
import com.oplever.prep.service.dto.ActasCriteria;
import com.oplever.prep.service.ActasQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.oplever.prep.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ActasResource REST controller.
 *
 * @see ActasResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Prep18App.class)
public class ActasResourceIntTest {

    private static final Integer DEFAULT_DISTRITO = 1;
    private static final Integer UPDATED_DISTRITO = 2;

    private static final String DEFAULT_SECCION = "AAAAAAAAAA";
    private static final String UPDATED_SECCION = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_ACTA = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_ACTA = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_CASILLA = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_CASILLA = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGEN = "AAAAAAAAAA";
    private static final String UPDATED_IMAGEN = "BBBBBBBBBB";

    private static final String DEFAULT_VOTOS_1 = "AAAAAAAAAA";
    private static final String UPDATED_VOTOS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_VOTOS_2 = "AAAAAAAAAA";
    private static final String UPDATED_VOTOS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_VOTOS_3 = "AAAAAAAAAA";
    private static final String UPDATED_VOTOS_3 = "BBBBBBBBBB";

    private static final String DEFAULT_VALIDACION = "AAAAAAAAAA";
    private static final String UPDATED_VALIDACION = "BBBBBBBBBB";

    private static final Integer DEFAULT_ESTATUS = 1;
    private static final Integer UPDATED_ESTATUS = 2;

    @Autowired
    private ActasRepository actasRepository;

    @Autowired
    private ActasService actasService;

    @Autowired
    private ActasQueryService actasQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restActasMockMvc;

    private Actas actas;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActasResource actasResource = new ActasResource(actasService, actasQueryService);
        this.restActasMockMvc = MockMvcBuilders.standaloneSetup(actasResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Actas createEntity(EntityManager em) {
        Actas actas = new Actas()
            .distrito(DEFAULT_DISTRITO)
            .seccion(DEFAULT_SECCION)
            .tipo_acta(DEFAULT_TIPO_ACTA)
            .tipo_casilla(DEFAULT_TIPO_CASILLA)
            .imagen(DEFAULT_IMAGEN)
            .votos_1(DEFAULT_VOTOS_1)
            .votos_2(DEFAULT_VOTOS_2)
            .votos_3(DEFAULT_VOTOS_3)
            .validacion(DEFAULT_VALIDACION)
            .estatus(DEFAULT_ESTATUS);
        return actas;
    }

    @Before
    public void initTest() {
        actas = createEntity(em);
    }

    @Test
    @Transactional
    public void createActas() throws Exception {
        int databaseSizeBeforeCreate = actasRepository.findAll().size();

        // Create the Actas
        restActasMockMvc.perform(post("/api/actas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actas)))
            .andExpect(status().isCreated());

        // Validate the Actas in the database
        List<Actas> actasList = actasRepository.findAll();
        assertThat(actasList).hasSize(databaseSizeBeforeCreate + 1);
        Actas testActas = actasList.get(actasList.size() - 1);
        assertThat(testActas.getDistrito()).isEqualTo(DEFAULT_DISTRITO);
        assertThat(testActas.getSeccion()).isEqualTo(DEFAULT_SECCION);
        assertThat(testActas.getTipo_acta()).isEqualTo(DEFAULT_TIPO_ACTA);
        assertThat(testActas.getTipo_casilla()).isEqualTo(DEFAULT_TIPO_CASILLA);
        assertThat(testActas.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testActas.getVotos_1()).isEqualTo(DEFAULT_VOTOS_1);
        assertThat(testActas.getVotos_2()).isEqualTo(DEFAULT_VOTOS_2);
        assertThat(testActas.getVotos_3()).isEqualTo(DEFAULT_VOTOS_3);
        assertThat(testActas.getValidacion()).isEqualTo(DEFAULT_VALIDACION);
        assertThat(testActas.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
    }

    @Test
    @Transactional
    public void createActasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = actasRepository.findAll().size();

        // Create the Actas with an existing ID
        actas.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActasMockMvc.perform(post("/api/actas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actas)))
            .andExpect(status().isBadRequest());

        // Validate the Actas in the database
        List<Actas> actasList = actasRepository.findAll();
        assertThat(actasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllActas() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList
        restActasMockMvc.perform(get("/api/actas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actas.getId().intValue())))
            .andExpect(jsonPath("$.[*].distrito").value(hasItem(DEFAULT_DISTRITO)))
            .andExpect(jsonPath("$.[*].seccion").value(hasItem(DEFAULT_SECCION.toString())))
            .andExpect(jsonPath("$.[*].tipo_acta").value(hasItem(DEFAULT_TIPO_ACTA.toString())))
            .andExpect(jsonPath("$.[*].tipo_casilla").value(hasItem(DEFAULT_TIPO_CASILLA.toString())))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(DEFAULT_IMAGEN.toString())))
            .andExpect(jsonPath("$.[*].votos_1").value(hasItem(DEFAULT_VOTOS_1.toString())))
            .andExpect(jsonPath("$.[*].votos_2").value(hasItem(DEFAULT_VOTOS_2.toString())))
            .andExpect(jsonPath("$.[*].votos_3").value(hasItem(DEFAULT_VOTOS_3.toString())))
            .andExpect(jsonPath("$.[*].validacion").value(hasItem(DEFAULT_VALIDACION.toString())))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS)));
    }

    @Test
    @Transactional
    public void getActas() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get the actas
        restActasMockMvc.perform(get("/api/actas/{id}", actas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(actas.getId().intValue()))
            .andExpect(jsonPath("$.distrito").value(DEFAULT_DISTRITO))
            .andExpect(jsonPath("$.seccion").value(DEFAULT_SECCION.toString()))
            .andExpect(jsonPath("$.tipo_acta").value(DEFAULT_TIPO_ACTA.toString()))
            .andExpect(jsonPath("$.tipo_casilla").value(DEFAULT_TIPO_CASILLA.toString()))
            .andExpect(jsonPath("$.imagen").value(DEFAULT_IMAGEN.toString()))
            .andExpect(jsonPath("$.votos_1").value(DEFAULT_VOTOS_1.toString()))
            .andExpect(jsonPath("$.votos_2").value(DEFAULT_VOTOS_2.toString()))
            .andExpect(jsonPath("$.votos_3").value(DEFAULT_VOTOS_3.toString()))
            .andExpect(jsonPath("$.validacion").value(DEFAULT_VALIDACION.toString()))
            .andExpect(jsonPath("$.estatus").value(DEFAULT_ESTATUS));
    }

    @Test
    @Transactional
    public void getAllActasByDistritoIsEqualToSomething() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where distrito equals to DEFAULT_DISTRITO
        defaultActasShouldBeFound("distrito.equals=" + DEFAULT_DISTRITO);

        // Get all the actasList where distrito equals to UPDATED_DISTRITO
        defaultActasShouldNotBeFound("distrito.equals=" + UPDATED_DISTRITO);
    }

    @Test
    @Transactional
    public void getAllActasByDistritoIsInShouldWork() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where distrito in DEFAULT_DISTRITO or UPDATED_DISTRITO
        defaultActasShouldBeFound("distrito.in=" + DEFAULT_DISTRITO + "," + UPDATED_DISTRITO);

        // Get all the actasList where distrito equals to UPDATED_DISTRITO
        defaultActasShouldNotBeFound("distrito.in=" + UPDATED_DISTRITO);
    }

    @Test
    @Transactional
    public void getAllActasByDistritoIsNullOrNotNull() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where distrito is not null
        defaultActasShouldBeFound("distrito.specified=true");

        // Get all the actasList where distrito is null
        defaultActasShouldNotBeFound("distrito.specified=false");
    }

    @Test
    @Transactional
    public void getAllActasByDistritoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where distrito greater than or equals to DEFAULT_DISTRITO
        defaultActasShouldBeFound("distrito.greaterOrEqualThan=" + DEFAULT_DISTRITO);

        // Get all the actasList where distrito greater than or equals to UPDATED_DISTRITO
        defaultActasShouldNotBeFound("distrito.greaterOrEqualThan=" + UPDATED_DISTRITO);
    }

    @Test
    @Transactional
    public void getAllActasByDistritoIsLessThanSomething() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where distrito less than or equals to DEFAULT_DISTRITO
        defaultActasShouldNotBeFound("distrito.lessThan=" + DEFAULT_DISTRITO);

        // Get all the actasList where distrito less than or equals to UPDATED_DISTRITO
        defaultActasShouldBeFound("distrito.lessThan=" + UPDATED_DISTRITO);
    }


    @Test
    @Transactional
    public void getAllActasBySeccionIsEqualToSomething() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where seccion equals to DEFAULT_SECCION
        defaultActasShouldBeFound("seccion.equals=" + DEFAULT_SECCION);

        // Get all the actasList where seccion equals to UPDATED_SECCION
        defaultActasShouldNotBeFound("seccion.equals=" + UPDATED_SECCION);
    }

    @Test
    @Transactional
    public void getAllActasBySeccionIsInShouldWork() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where seccion in DEFAULT_SECCION or UPDATED_SECCION
        defaultActasShouldBeFound("seccion.in=" + DEFAULT_SECCION + "," + UPDATED_SECCION);

        // Get all the actasList where seccion equals to UPDATED_SECCION
        defaultActasShouldNotBeFound("seccion.in=" + UPDATED_SECCION);
    }

    @Test
    @Transactional
    public void getAllActasBySeccionIsNullOrNotNull() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where seccion is not null
        defaultActasShouldBeFound("seccion.specified=true");

        // Get all the actasList where seccion is null
        defaultActasShouldNotBeFound("seccion.specified=false");
    }

    @Test
    @Transactional
    public void getAllActasByTipo_actaIsEqualToSomething() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where tipo_acta equals to DEFAULT_TIPO_ACTA
        defaultActasShouldBeFound("tipo_acta.equals=" + DEFAULT_TIPO_ACTA);

        // Get all the actasList where tipo_acta equals to UPDATED_TIPO_ACTA
        defaultActasShouldNotBeFound("tipo_acta.equals=" + UPDATED_TIPO_ACTA);
    }

    @Test
    @Transactional
    public void getAllActasByTipo_actaIsInShouldWork() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where tipo_acta in DEFAULT_TIPO_ACTA or UPDATED_TIPO_ACTA
        defaultActasShouldBeFound("tipo_acta.in=" + DEFAULT_TIPO_ACTA + "," + UPDATED_TIPO_ACTA);

        // Get all the actasList where tipo_acta equals to UPDATED_TIPO_ACTA
        defaultActasShouldNotBeFound("tipo_acta.in=" + UPDATED_TIPO_ACTA);
    }

    @Test
    @Transactional
    public void getAllActasByTipo_actaIsNullOrNotNull() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where tipo_acta is not null
        defaultActasShouldBeFound("tipo_acta.specified=true");

        // Get all the actasList where tipo_acta is null
        defaultActasShouldNotBeFound("tipo_acta.specified=false");
    }

    @Test
    @Transactional
    public void getAllActasByTipo_casillaIsEqualToSomething() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where tipo_casilla equals to DEFAULT_TIPO_CASILLA
        defaultActasShouldBeFound("tipo_casilla.equals=" + DEFAULT_TIPO_CASILLA);

        // Get all the actasList where tipo_casilla equals to UPDATED_TIPO_CASILLA
        defaultActasShouldNotBeFound("tipo_casilla.equals=" + UPDATED_TIPO_CASILLA);
    }

    @Test
    @Transactional
    public void getAllActasByTipo_casillaIsInShouldWork() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where tipo_casilla in DEFAULT_TIPO_CASILLA or UPDATED_TIPO_CASILLA
        defaultActasShouldBeFound("tipo_casilla.in=" + DEFAULT_TIPO_CASILLA + "," + UPDATED_TIPO_CASILLA);

        // Get all the actasList where tipo_casilla equals to UPDATED_TIPO_CASILLA
        defaultActasShouldNotBeFound("tipo_casilla.in=" + UPDATED_TIPO_CASILLA);
    }

    @Test
    @Transactional
    public void getAllActasByTipo_casillaIsNullOrNotNull() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where tipo_casilla is not null
        defaultActasShouldBeFound("tipo_casilla.specified=true");

        // Get all the actasList where tipo_casilla is null
        defaultActasShouldNotBeFound("tipo_casilla.specified=false");
    }

    @Test
    @Transactional
    public void getAllActasByImagenIsEqualToSomething() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where imagen equals to DEFAULT_IMAGEN
        defaultActasShouldBeFound("imagen.equals=" + DEFAULT_IMAGEN);

        // Get all the actasList where imagen equals to UPDATED_IMAGEN
        defaultActasShouldNotBeFound("imagen.equals=" + UPDATED_IMAGEN);
    }

    @Test
    @Transactional
    public void getAllActasByImagenIsInShouldWork() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where imagen in DEFAULT_IMAGEN or UPDATED_IMAGEN
        defaultActasShouldBeFound("imagen.in=" + DEFAULT_IMAGEN + "," + UPDATED_IMAGEN);

        // Get all the actasList where imagen equals to UPDATED_IMAGEN
        defaultActasShouldNotBeFound("imagen.in=" + UPDATED_IMAGEN);
    }

    @Test
    @Transactional
    public void getAllActasByImagenIsNullOrNotNull() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where imagen is not null
        defaultActasShouldBeFound("imagen.specified=true");

        // Get all the actasList where imagen is null
        defaultActasShouldNotBeFound("imagen.specified=false");
    }

    @Test
    @Transactional
    public void getAllActasByVotos_1IsEqualToSomething() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where votos_1 equals to DEFAULT_VOTOS_1
        defaultActasShouldBeFound("votos_1.equals=" + DEFAULT_VOTOS_1);

        // Get all the actasList where votos_1 equals to UPDATED_VOTOS_1
        defaultActasShouldNotBeFound("votos_1.equals=" + UPDATED_VOTOS_1);
    }

    @Test
    @Transactional
    public void getAllActasByVotos_1IsInShouldWork() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where votos_1 in DEFAULT_VOTOS_1 or UPDATED_VOTOS_1
        defaultActasShouldBeFound("votos_1.in=" + DEFAULT_VOTOS_1 + "," + UPDATED_VOTOS_1);

        // Get all the actasList where votos_1 equals to UPDATED_VOTOS_1
        defaultActasShouldNotBeFound("votos_1.in=" + UPDATED_VOTOS_1);
    }

    @Test
    @Transactional
    public void getAllActasByVotos_1IsNullOrNotNull() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where votos_1 is not null
        defaultActasShouldBeFound("votos_1.specified=true");

        // Get all the actasList where votos_1 is null
        defaultActasShouldNotBeFound("votos_1.specified=false");
    }

    @Test
    @Transactional
    public void getAllActasByVotos_2IsEqualToSomething() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where votos_2 equals to DEFAULT_VOTOS_2
        defaultActasShouldBeFound("votos_2.equals=" + DEFAULT_VOTOS_2);

        // Get all the actasList where votos_2 equals to UPDATED_VOTOS_2
        defaultActasShouldNotBeFound("votos_2.equals=" + UPDATED_VOTOS_2);
    }

    @Test
    @Transactional
    public void getAllActasByVotos_2IsInShouldWork() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where votos_2 in DEFAULT_VOTOS_2 or UPDATED_VOTOS_2
        defaultActasShouldBeFound("votos_2.in=" + DEFAULT_VOTOS_2 + "," + UPDATED_VOTOS_2);

        // Get all the actasList where votos_2 equals to UPDATED_VOTOS_2
        defaultActasShouldNotBeFound("votos_2.in=" + UPDATED_VOTOS_2);
    }

    @Test
    @Transactional
    public void getAllActasByVotos_2IsNullOrNotNull() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where votos_2 is not null
        defaultActasShouldBeFound("votos_2.specified=true");

        // Get all the actasList where votos_2 is null
        defaultActasShouldNotBeFound("votos_2.specified=false");
    }

    @Test
    @Transactional
    public void getAllActasByVotos_3IsEqualToSomething() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where votos_3 equals to DEFAULT_VOTOS_3
        defaultActasShouldBeFound("votos_3.equals=" + DEFAULT_VOTOS_3);

        // Get all the actasList where votos_3 equals to UPDATED_VOTOS_3
        defaultActasShouldNotBeFound("votos_3.equals=" + UPDATED_VOTOS_3);
    }

    @Test
    @Transactional
    public void getAllActasByVotos_3IsInShouldWork() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where votos_3 in DEFAULT_VOTOS_3 or UPDATED_VOTOS_3
        defaultActasShouldBeFound("votos_3.in=" + DEFAULT_VOTOS_3 + "," + UPDATED_VOTOS_3);

        // Get all the actasList where votos_3 equals to UPDATED_VOTOS_3
        defaultActasShouldNotBeFound("votos_3.in=" + UPDATED_VOTOS_3);
    }

    @Test
    @Transactional
    public void getAllActasByVotos_3IsNullOrNotNull() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where votos_3 is not null
        defaultActasShouldBeFound("votos_3.specified=true");

        // Get all the actasList where votos_3 is null
        defaultActasShouldNotBeFound("votos_3.specified=false");
    }

    @Test
    @Transactional
    public void getAllActasByValidacionIsEqualToSomething() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where validacion equals to DEFAULT_VALIDACION
        defaultActasShouldBeFound("validacion.equals=" + DEFAULT_VALIDACION);

        // Get all the actasList where validacion equals to UPDATED_VALIDACION
        defaultActasShouldNotBeFound("validacion.equals=" + UPDATED_VALIDACION);
    }

    @Test
    @Transactional
    public void getAllActasByValidacionIsInShouldWork() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where validacion in DEFAULT_VALIDACION or UPDATED_VALIDACION
        defaultActasShouldBeFound("validacion.in=" + DEFAULT_VALIDACION + "," + UPDATED_VALIDACION);

        // Get all the actasList where validacion equals to UPDATED_VALIDACION
        defaultActasShouldNotBeFound("validacion.in=" + UPDATED_VALIDACION);
    }

    @Test
    @Transactional
    public void getAllActasByValidacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where validacion is not null
        defaultActasShouldBeFound("validacion.specified=true");

        // Get all the actasList where validacion is null
        defaultActasShouldNotBeFound("validacion.specified=false");
    }

    @Test
    @Transactional
    public void getAllActasByEstatusIsEqualToSomething() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where estatus equals to DEFAULT_ESTATUS
        defaultActasShouldBeFound("estatus.equals=" + DEFAULT_ESTATUS);

        // Get all the actasList where estatus equals to UPDATED_ESTATUS
        defaultActasShouldNotBeFound("estatus.equals=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void getAllActasByEstatusIsInShouldWork() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where estatus in DEFAULT_ESTATUS or UPDATED_ESTATUS
        defaultActasShouldBeFound("estatus.in=" + DEFAULT_ESTATUS + "," + UPDATED_ESTATUS);

        // Get all the actasList where estatus equals to UPDATED_ESTATUS
        defaultActasShouldNotBeFound("estatus.in=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void getAllActasByEstatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where estatus is not null
        defaultActasShouldBeFound("estatus.specified=true");

        // Get all the actasList where estatus is null
        defaultActasShouldNotBeFound("estatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllActasByEstatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where estatus greater than or equals to DEFAULT_ESTATUS
        defaultActasShouldBeFound("estatus.greaterOrEqualThan=" + DEFAULT_ESTATUS);

        // Get all the actasList where estatus greater than or equals to UPDATED_ESTATUS
        defaultActasShouldNotBeFound("estatus.greaterOrEqualThan=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void getAllActasByEstatusIsLessThanSomething() throws Exception {
        // Initialize the database
        actasRepository.saveAndFlush(actas);

        // Get all the actasList where estatus less than or equals to DEFAULT_ESTATUS
        defaultActasShouldNotBeFound("estatus.lessThan=" + DEFAULT_ESTATUS);

        // Get all the actasList where estatus less than or equals to UPDATED_ESTATUS
        defaultActasShouldBeFound("estatus.lessThan=" + UPDATED_ESTATUS);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultActasShouldBeFound(String filter) throws Exception {
        restActasMockMvc.perform(get("/api/actas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actas.getId().intValue())))
            .andExpect(jsonPath("$.[*].distrito").value(hasItem(DEFAULT_DISTRITO)))
            .andExpect(jsonPath("$.[*].seccion").value(hasItem(DEFAULT_SECCION.toString())))
            .andExpect(jsonPath("$.[*].tipo_acta").value(hasItem(DEFAULT_TIPO_ACTA.toString())))
            .andExpect(jsonPath("$.[*].tipo_casilla").value(hasItem(DEFAULT_TIPO_CASILLA.toString())))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(DEFAULT_IMAGEN.toString())))
            .andExpect(jsonPath("$.[*].votos_1").value(hasItem(DEFAULT_VOTOS_1.toString())))
            .andExpect(jsonPath("$.[*].votos_2").value(hasItem(DEFAULT_VOTOS_2.toString())))
            .andExpect(jsonPath("$.[*].votos_3").value(hasItem(DEFAULT_VOTOS_3.toString())))
            .andExpect(jsonPath("$.[*].validacion").value(hasItem(DEFAULT_VALIDACION.toString())))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultActasShouldNotBeFound(String filter) throws Exception {
        restActasMockMvc.perform(get("/api/actas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingActas() throws Exception {
        // Get the actas
        restActasMockMvc.perform(get("/api/actas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActas() throws Exception {
        // Initialize the database
        actasService.save(actas);

        int databaseSizeBeforeUpdate = actasRepository.findAll().size();

        // Update the actas
        Actas updatedActas = actasRepository.findOne(actas.getId());
        updatedActas
            .distrito(UPDATED_DISTRITO)
            .seccion(UPDATED_SECCION)
            .tipo_acta(UPDATED_TIPO_ACTA)
            .tipo_casilla(UPDATED_TIPO_CASILLA)
            .imagen(UPDATED_IMAGEN)
            .votos_1(UPDATED_VOTOS_1)
            .votos_2(UPDATED_VOTOS_2)
            .votos_3(UPDATED_VOTOS_3)
            .validacion(UPDATED_VALIDACION)
            .estatus(UPDATED_ESTATUS);

        restActasMockMvc.perform(put("/api/actas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedActas)))
            .andExpect(status().isOk());

        // Validate the Actas in the database
        List<Actas> actasList = actasRepository.findAll();
        assertThat(actasList).hasSize(databaseSizeBeforeUpdate);
        Actas testActas = actasList.get(actasList.size() - 1);
        assertThat(testActas.getDistrito()).isEqualTo(UPDATED_DISTRITO);
        assertThat(testActas.getSeccion()).isEqualTo(UPDATED_SECCION);
        assertThat(testActas.getTipo_acta()).isEqualTo(UPDATED_TIPO_ACTA);
        assertThat(testActas.getTipo_casilla()).isEqualTo(UPDATED_TIPO_CASILLA);
        assertThat(testActas.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testActas.getVotos_1()).isEqualTo(UPDATED_VOTOS_1);
        assertThat(testActas.getVotos_2()).isEqualTo(UPDATED_VOTOS_2);
        assertThat(testActas.getVotos_3()).isEqualTo(UPDATED_VOTOS_3);
        assertThat(testActas.getValidacion()).isEqualTo(UPDATED_VALIDACION);
        assertThat(testActas.getEstatus()).isEqualTo(UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingActas() throws Exception {
        int databaseSizeBeforeUpdate = actasRepository.findAll().size();

        // Create the Actas

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restActasMockMvc.perform(put("/api/actas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actas)))
            .andExpect(status().isCreated());

        // Validate the Actas in the database
        List<Actas> actasList = actasRepository.findAll();
        assertThat(actasList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteActas() throws Exception {
        // Initialize the database
        actasService.save(actas);

        int databaseSizeBeforeDelete = actasRepository.findAll().size();

        // Get the actas
        restActasMockMvc.perform(delete("/api/actas/{id}", actas.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Actas> actasList = actasRepository.findAll();
        assertThat(actasList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Actas.class);
        Actas actas1 = new Actas();
        actas1.setId(1L);
        Actas actas2 = new Actas();
        actas2.setId(actas1.getId());
        assertThat(actas1).isEqualTo(actas2);
        actas2.setId(2L);
        assertThat(actas1).isNotEqualTo(actas2);
        actas1.setId(null);
        assertThat(actas1).isNotEqualTo(actas2);
    }
}
