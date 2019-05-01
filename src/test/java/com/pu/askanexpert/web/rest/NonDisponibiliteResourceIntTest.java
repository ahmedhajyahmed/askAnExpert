package com.pu.askanexpert.web.rest;

import com.pu.askanexpert.AskAnExpertApp;

import com.pu.askanexpert.domain.NonDisponibilite;
import com.pu.askanexpert.repository.NonDisponibiliteRepository;
import com.pu.askanexpert.service.NonDisponibiliteService;
import com.pu.askanexpert.web.rest.errors.ExceptionTranslator;

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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.pu.askanexpert.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the NonDisponibiliteResource REST controller.
 *
 * @see NonDisponibiliteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AskAnExpertApp.class)
public class NonDisponibiliteResourceIntTest {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private NonDisponibiliteRepository nonDisponibiliteRepository;

    @Autowired
    private NonDisponibiliteService nonDisponibiliteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restNonDisponibiliteMockMvc;

    private NonDisponibilite nonDisponibilite;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NonDisponibiliteResource nonDisponibiliteResource = new NonDisponibiliteResource(nonDisponibiliteService);
        this.restNonDisponibiliteMockMvc = MockMvcBuilders.standaloneSetup(nonDisponibiliteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NonDisponibilite createEntity(EntityManager em) {
        NonDisponibilite nonDisponibilite = new NonDisponibilite()
            .date(DEFAULT_DATE);
        return nonDisponibilite;
    }

    @Before
    public void initTest() {
        nonDisponibilite = createEntity(em);
    }

    @Test
    @Transactional
    public void createNonDisponibilite() throws Exception {
        int databaseSizeBeforeCreate = nonDisponibiliteRepository.findAll().size();

        // Create the NonDisponibilite
        restNonDisponibiliteMockMvc.perform(post("/api/non-disponibilites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nonDisponibilite)))
            .andExpect(status().isCreated());

        // Validate the NonDisponibilite in the database
        List<NonDisponibilite> nonDisponibiliteList = nonDisponibiliteRepository.findAll();
        assertThat(nonDisponibiliteList).hasSize(databaseSizeBeforeCreate + 1);
        NonDisponibilite testNonDisponibilite = nonDisponibiliteList.get(nonDisponibiliteList.size() - 1);
        assertThat(testNonDisponibilite.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createNonDisponibiliteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nonDisponibiliteRepository.findAll().size();

        // Create the NonDisponibilite with an existing ID
        nonDisponibilite.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNonDisponibiliteMockMvc.perform(post("/api/non-disponibilites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nonDisponibilite)))
            .andExpect(status().isBadRequest());

        // Validate the NonDisponibilite in the database
        List<NonDisponibilite> nonDisponibiliteList = nonDisponibiliteRepository.findAll();
        assertThat(nonDisponibiliteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = nonDisponibiliteRepository.findAll().size();
        // set the field null
        nonDisponibilite.setDate(null);

        // Create the NonDisponibilite, which fails.

        restNonDisponibiliteMockMvc.perform(post("/api/non-disponibilites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nonDisponibilite)))
            .andExpect(status().isBadRequest());

        List<NonDisponibilite> nonDisponibiliteList = nonDisponibiliteRepository.findAll();
        assertThat(nonDisponibiliteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNonDisponibilites() throws Exception {
        // Initialize the database
        nonDisponibiliteRepository.saveAndFlush(nonDisponibilite);

        // Get all the nonDisponibiliteList
        restNonDisponibiliteMockMvc.perform(get("/api/non-disponibilites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nonDisponibilite.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getNonDisponibilite() throws Exception {
        // Initialize the database
        nonDisponibiliteRepository.saveAndFlush(nonDisponibilite);

        // Get the nonDisponibilite
        restNonDisponibiliteMockMvc.perform(get("/api/non-disponibilites/{id}", nonDisponibilite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nonDisponibilite.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNonDisponibilite() throws Exception {
        // Get the nonDisponibilite
        restNonDisponibiliteMockMvc.perform(get("/api/non-disponibilites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNonDisponibilite() throws Exception {
        // Initialize the database
        nonDisponibiliteService.save(nonDisponibilite);

        int databaseSizeBeforeUpdate = nonDisponibiliteRepository.findAll().size();

        // Update the nonDisponibilite
        NonDisponibilite updatedNonDisponibilite = nonDisponibiliteRepository.findById(nonDisponibilite.getId()).get();
        // Disconnect from session so that the updates on updatedNonDisponibilite are not directly saved in db
        em.detach(updatedNonDisponibilite);
        updatedNonDisponibilite
            .date(UPDATED_DATE);

        restNonDisponibiliteMockMvc.perform(put("/api/non-disponibilites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNonDisponibilite)))
            .andExpect(status().isOk());

        // Validate the NonDisponibilite in the database
        List<NonDisponibilite> nonDisponibiliteList = nonDisponibiliteRepository.findAll();
        assertThat(nonDisponibiliteList).hasSize(databaseSizeBeforeUpdate);
        NonDisponibilite testNonDisponibilite = nonDisponibiliteList.get(nonDisponibiliteList.size() - 1);
        assertThat(testNonDisponibilite.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingNonDisponibilite() throws Exception {
        int databaseSizeBeforeUpdate = nonDisponibiliteRepository.findAll().size();

        // Create the NonDisponibilite

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNonDisponibiliteMockMvc.perform(put("/api/non-disponibilites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nonDisponibilite)))
            .andExpect(status().isBadRequest());

        // Validate the NonDisponibilite in the database
        List<NonDisponibilite> nonDisponibiliteList = nonDisponibiliteRepository.findAll();
        assertThat(nonDisponibiliteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNonDisponibilite() throws Exception {
        // Initialize the database
        nonDisponibiliteService.save(nonDisponibilite);

        int databaseSizeBeforeDelete = nonDisponibiliteRepository.findAll().size();

        // Delete the nonDisponibilite
        restNonDisponibiliteMockMvc.perform(delete("/api/non-disponibilites/{id}", nonDisponibilite.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NonDisponibilite> nonDisponibiliteList = nonDisponibiliteRepository.findAll();
        assertThat(nonDisponibiliteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NonDisponibilite.class);
        NonDisponibilite nonDisponibilite1 = new NonDisponibilite();
        nonDisponibilite1.setId(1L);
        NonDisponibilite nonDisponibilite2 = new NonDisponibilite();
        nonDisponibilite2.setId(nonDisponibilite1.getId());
        assertThat(nonDisponibilite1).isEqualTo(nonDisponibilite2);
        nonDisponibilite2.setId(2L);
        assertThat(nonDisponibilite1).isNotEqualTo(nonDisponibilite2);
        nonDisponibilite1.setId(null);
        assertThat(nonDisponibilite1).isNotEqualTo(nonDisponibilite2);
    }
}
