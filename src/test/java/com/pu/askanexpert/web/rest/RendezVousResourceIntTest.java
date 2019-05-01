package com.pu.askanexpert.web.rest;

import com.pu.askanexpert.AskAnExpertApp;

import com.pu.askanexpert.domain.RendezVous;
import com.pu.askanexpert.repository.RendezVousRepository;
import com.pu.askanexpert.service.RendezVousService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.pu.askanexpert.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RendezVousResource REST controller.
 *
 * @see RendezVousResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AskAnExpertApp.class)
public class RendezVousResourceIntTest {

    private static final LocalDate DEFAULT_START = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ALL_DAY = false;
    private static final Boolean UPDATED_ALL_DAY = true;

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    @Autowired
    private RendezVousRepository rendezVousRepository;

    @Autowired
    private RendezVousService rendezVousService;

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

    private MockMvc restRendezVousMockMvc;

    private RendezVous rendezVous;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RendezVousResource rendezVousResource = new RendezVousResource(rendezVousService);
        this.restRendezVousMockMvc = MockMvcBuilders.standaloneSetup(rendezVousResource)
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
    public static RendezVous createEntity(EntityManager em) {
        RendezVous rendezVous = new RendezVous()
            .start(DEFAULT_START)
            .end(DEFAULT_END)
            .title(DEFAULT_TITLE)
            .allDay(DEFAULT_ALL_DAY)
            .url(DEFAULT_URL);
        return rendezVous;
    }

    @Before
    public void initTest() {
        rendezVous = createEntity(em);
    }

    @Test
    @Transactional
    public void createRendezVous() throws Exception {
        int databaseSizeBeforeCreate = rendezVousRepository.findAll().size();

        // Create the RendezVous
        restRendezVousMockMvc.perform(post("/api/rendez-vous")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rendezVous)))
            .andExpect(status().isCreated());

        // Validate the RendezVous in the database
        List<RendezVous> rendezVousList = rendezVousRepository.findAll();
        assertThat(rendezVousList).hasSize(databaseSizeBeforeCreate + 1);
        RendezVous testRendezVous = rendezVousList.get(rendezVousList.size() - 1);
        assertThat(testRendezVous.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testRendezVous.getEnd()).isEqualTo(DEFAULT_END);
        assertThat(testRendezVous.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testRendezVous.isAllDay()).isEqualTo(DEFAULT_ALL_DAY);
        assertThat(testRendezVous.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    public void createRendezVousWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rendezVousRepository.findAll().size();

        // Create the RendezVous with an existing ID
        rendezVous.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRendezVousMockMvc.perform(post("/api/rendez-vous")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rendezVous)))
            .andExpect(status().isBadRequest());

        // Validate the RendezVous in the database
        List<RendezVous> rendezVousList = rendezVousRepository.findAll();
        assertThat(rendezVousList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = rendezVousRepository.findAll().size();
        // set the field null
        rendezVous.setStart(null);

        // Create the RendezVous, which fails.

        restRendezVousMockMvc.perform(post("/api/rendez-vous")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rendezVous)))
            .andExpect(status().isBadRequest());

        List<RendezVous> rendezVousList = rendezVousRepository.findAll();
        assertThat(rendezVousList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = rendezVousRepository.findAll().size();
        // set the field null
        rendezVous.setTitle(null);

        // Create the RendezVous, which fails.

        restRendezVousMockMvc.perform(post("/api/rendez-vous")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rendezVous)))
            .andExpect(status().isBadRequest());

        List<RendezVous> rendezVousList = rendezVousRepository.findAll();
        assertThat(rendezVousList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRendezVous() throws Exception {
        // Initialize the database
        rendezVousRepository.saveAndFlush(rendezVous);

        // Get all the rendezVousList
        restRendezVousMockMvc.perform(get("/api/rendez-vous?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rendezVous.getId().intValue())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].allDay").value(hasItem(DEFAULT_ALL_DAY.booleanValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())));
    }
    
    @Test
    @Transactional
    public void getRendezVous() throws Exception {
        // Initialize the database
        rendezVousRepository.saveAndFlush(rendezVous);

        // Get the rendezVous
        restRendezVousMockMvc.perform(get("/api/rendez-vous/{id}", rendezVous.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rendezVous.getId().intValue()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.allDay").value(DEFAULT_ALL_DAY.booleanValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRendezVous() throws Exception {
        // Get the rendezVous
        restRendezVousMockMvc.perform(get("/api/rendez-vous/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRendezVous() throws Exception {
        // Initialize the database
        rendezVousService.save(rendezVous);

        int databaseSizeBeforeUpdate = rendezVousRepository.findAll().size();

        // Update the rendezVous
        RendezVous updatedRendezVous = rendezVousRepository.findById(rendezVous.getId()).get();
        // Disconnect from session so that the updates on updatedRendezVous are not directly saved in db
        em.detach(updatedRendezVous);
        updatedRendezVous
            .start(UPDATED_START)
            .end(UPDATED_END)
            .title(UPDATED_TITLE)
            .allDay(UPDATED_ALL_DAY)
            .url(UPDATED_URL);

        restRendezVousMockMvc.perform(put("/api/rendez-vous")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRendezVous)))
            .andExpect(status().isOk());

        // Validate the RendezVous in the database
        List<RendezVous> rendezVousList = rendezVousRepository.findAll();
        assertThat(rendezVousList).hasSize(databaseSizeBeforeUpdate);
        RendezVous testRendezVous = rendezVousList.get(rendezVousList.size() - 1);
        assertThat(testRendezVous.getStart()).isEqualTo(UPDATED_START);
        assertThat(testRendezVous.getEnd()).isEqualTo(UPDATED_END);
        assertThat(testRendezVous.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testRendezVous.isAllDay()).isEqualTo(UPDATED_ALL_DAY);
        assertThat(testRendezVous.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingRendezVous() throws Exception {
        int databaseSizeBeforeUpdate = rendezVousRepository.findAll().size();

        // Create the RendezVous

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRendezVousMockMvc.perform(put("/api/rendez-vous")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rendezVous)))
            .andExpect(status().isBadRequest());

        // Validate the RendezVous in the database
        List<RendezVous> rendezVousList = rendezVousRepository.findAll();
        assertThat(rendezVousList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRendezVous() throws Exception {
        // Initialize the database
        rendezVousService.save(rendezVous);

        int databaseSizeBeforeDelete = rendezVousRepository.findAll().size();

        // Delete the rendezVous
        restRendezVousMockMvc.perform(delete("/api/rendez-vous/{id}", rendezVous.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RendezVous> rendezVousList = rendezVousRepository.findAll();
        assertThat(rendezVousList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RendezVous.class);
        RendezVous rendezVous1 = new RendezVous();
        rendezVous1.setId(1L);
        RendezVous rendezVous2 = new RendezVous();
        rendezVous2.setId(rendezVous1.getId());
        assertThat(rendezVous1).isEqualTo(rendezVous2);
        rendezVous2.setId(2L);
        assertThat(rendezVous1).isNotEqualTo(rendezVous2);
        rendezVous1.setId(null);
        assertThat(rendezVous1).isNotEqualTo(rendezVous2);
    }
}
