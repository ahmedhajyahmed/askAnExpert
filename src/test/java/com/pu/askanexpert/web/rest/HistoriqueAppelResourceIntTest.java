package com.pu.askanexpert.web.rest;

import com.pu.askanexpert.AskAnExpertApp;

import com.pu.askanexpert.domain.HistoriqueAppel;
import com.pu.askanexpert.repository.HistoriqueAppelRepository;
import com.pu.askanexpert.service.HistoriqueAppelService;
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
import org.springframework.util.Base64Utils;
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
 * Test class for the HistoriqueAppelResource REST controller.
 *
 * @see HistoriqueAppelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AskAnExpertApp.class)
public class HistoriqueAppelResourceIntTest {

    private static final LocalDate DEFAULT_DATE_APPEL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_APPEL = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_CLIENT = "BBBBBBBBBB";

    @Autowired
    private HistoriqueAppelRepository historiqueAppelRepository;

    @Autowired
    private HistoriqueAppelService historiqueAppelService;

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

    private MockMvc restHistoriqueAppelMockMvc;

    private HistoriqueAppel historiqueAppel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HistoriqueAppelResource historiqueAppelResource = new HistoriqueAppelResource(historiqueAppelService);
        this.restHistoriqueAppelMockMvc = MockMvcBuilders.standaloneSetup(historiqueAppelResource)
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
    public static HistoriqueAppel createEntity(EntityManager em) {
        HistoriqueAppel historiqueAppel = new HistoriqueAppel()
            .dateAppel(DEFAULT_DATE_APPEL)
            .description(DEFAULT_DESCRIPTION)
            .nomClient(DEFAULT_NOM_CLIENT);
        return historiqueAppel;
    }

    @Before
    public void initTest() {
        historiqueAppel = createEntity(em);
    }

    @Test
    @Transactional
    public void createHistoriqueAppel() throws Exception {
        int databaseSizeBeforeCreate = historiqueAppelRepository.findAll().size();

        // Create the HistoriqueAppel
        restHistoriqueAppelMockMvc.perform(post("/api/historique-appels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiqueAppel)))
            .andExpect(status().isCreated());

        // Validate the HistoriqueAppel in the database
        List<HistoriqueAppel> historiqueAppelList = historiqueAppelRepository.findAll();
        assertThat(historiqueAppelList).hasSize(databaseSizeBeforeCreate + 1);
        HistoriqueAppel testHistoriqueAppel = historiqueAppelList.get(historiqueAppelList.size() - 1);
        assertThat(testHistoriqueAppel.getDateAppel()).isEqualTo(DEFAULT_DATE_APPEL);
        assertThat(testHistoriqueAppel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testHistoriqueAppel.getNomClient()).isEqualTo(DEFAULT_NOM_CLIENT);
    }

    @Test
    @Transactional
    public void createHistoriqueAppelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = historiqueAppelRepository.findAll().size();

        // Create the HistoriqueAppel with an existing ID
        historiqueAppel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistoriqueAppelMockMvc.perform(post("/api/historique-appels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiqueAppel)))
            .andExpect(status().isBadRequest());

        // Validate the HistoriqueAppel in the database
        List<HistoriqueAppel> historiqueAppelList = historiqueAppelRepository.findAll();
        assertThat(historiqueAppelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateAppelIsRequired() throws Exception {
        int databaseSizeBeforeTest = historiqueAppelRepository.findAll().size();
        // set the field null
        historiqueAppel.setDateAppel(null);

        // Create the HistoriqueAppel, which fails.

        restHistoriqueAppelMockMvc.perform(post("/api/historique-appels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiqueAppel)))
            .andExpect(status().isBadRequest());

        List<HistoriqueAppel> historiqueAppelList = historiqueAppelRepository.findAll();
        assertThat(historiqueAppelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomClientIsRequired() throws Exception {
        int databaseSizeBeforeTest = historiqueAppelRepository.findAll().size();
        // set the field null
        historiqueAppel.setNomClient(null);

        // Create the HistoriqueAppel, which fails.

        restHistoriqueAppelMockMvc.perform(post("/api/historique-appels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiqueAppel)))
            .andExpect(status().isBadRequest());

        List<HistoriqueAppel> historiqueAppelList = historiqueAppelRepository.findAll();
        assertThat(historiqueAppelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHistoriqueAppels() throws Exception {
        // Initialize the database
        historiqueAppelRepository.saveAndFlush(historiqueAppel);

        // Get all the historiqueAppelList
        restHistoriqueAppelMockMvc.perform(get("/api/historique-appels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historiqueAppel.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateAppel").value(hasItem(DEFAULT_DATE_APPEL.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].nomClient").value(hasItem(DEFAULT_NOM_CLIENT.toString())));
    }
    
    @Test
    @Transactional
    public void getHistoriqueAppel() throws Exception {
        // Initialize the database
        historiqueAppelRepository.saveAndFlush(historiqueAppel);

        // Get the historiqueAppel
        restHistoriqueAppelMockMvc.perform(get("/api/historique-appels/{id}", historiqueAppel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(historiqueAppel.getId().intValue()))
            .andExpect(jsonPath("$.dateAppel").value(DEFAULT_DATE_APPEL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.nomClient").value(DEFAULT_NOM_CLIENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHistoriqueAppel() throws Exception {
        // Get the historiqueAppel
        restHistoriqueAppelMockMvc.perform(get("/api/historique-appels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHistoriqueAppel() throws Exception {
        // Initialize the database
        historiqueAppelService.save(historiqueAppel);

        int databaseSizeBeforeUpdate = historiqueAppelRepository.findAll().size();

        // Update the historiqueAppel
        HistoriqueAppel updatedHistoriqueAppel = historiqueAppelRepository.findById(historiqueAppel.getId()).get();
        // Disconnect from session so that the updates on updatedHistoriqueAppel are not directly saved in db
        em.detach(updatedHistoriqueAppel);
        updatedHistoriqueAppel
            .dateAppel(UPDATED_DATE_APPEL)
            .description(UPDATED_DESCRIPTION)
            .nomClient(UPDATED_NOM_CLIENT);

        restHistoriqueAppelMockMvc.perform(put("/api/historique-appels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHistoriqueAppel)))
            .andExpect(status().isOk());

        // Validate the HistoriqueAppel in the database
        List<HistoriqueAppel> historiqueAppelList = historiqueAppelRepository.findAll();
        assertThat(historiqueAppelList).hasSize(databaseSizeBeforeUpdate);
        HistoriqueAppel testHistoriqueAppel = historiqueAppelList.get(historiqueAppelList.size() - 1);
        assertThat(testHistoriqueAppel.getDateAppel()).isEqualTo(UPDATED_DATE_APPEL);
        assertThat(testHistoriqueAppel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testHistoriqueAppel.getNomClient()).isEqualTo(UPDATED_NOM_CLIENT);
    }

    @Test
    @Transactional
    public void updateNonExistingHistoriqueAppel() throws Exception {
        int databaseSizeBeforeUpdate = historiqueAppelRepository.findAll().size();

        // Create the HistoriqueAppel

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoriqueAppelMockMvc.perform(put("/api/historique-appels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiqueAppel)))
            .andExpect(status().isBadRequest());

        // Validate the HistoriqueAppel in the database
        List<HistoriqueAppel> historiqueAppelList = historiqueAppelRepository.findAll();
        assertThat(historiqueAppelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHistoriqueAppel() throws Exception {
        // Initialize the database
        historiqueAppelService.save(historiqueAppel);

        int databaseSizeBeforeDelete = historiqueAppelRepository.findAll().size();

        // Delete the historiqueAppel
        restHistoriqueAppelMockMvc.perform(delete("/api/historique-appels/{id}", historiqueAppel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HistoriqueAppel> historiqueAppelList = historiqueAppelRepository.findAll();
        assertThat(historiqueAppelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistoriqueAppel.class);
        HistoriqueAppel historiqueAppel1 = new HistoriqueAppel();
        historiqueAppel1.setId(1L);
        HistoriqueAppel historiqueAppel2 = new HistoriqueAppel();
        historiqueAppel2.setId(historiqueAppel1.getId());
        assertThat(historiqueAppel1).isEqualTo(historiqueAppel2);
        historiqueAppel2.setId(2L);
        assertThat(historiqueAppel1).isNotEqualTo(historiqueAppel2);
        historiqueAppel1.setId(null);
        assertThat(historiqueAppel1).isNotEqualTo(historiqueAppel2);
    }
}
