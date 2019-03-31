package com.pu.askanexpert.web.rest;

import com.pu.askanexpert.AskAnExpertApp;

import com.pu.askanexpert.domain.Expert;
import com.pu.askanexpert.repository.ExpertRepository;
import com.pu.askanexpert.service.ExpertService;
import com.pu.askanexpert.web.rest.errors.ExceptionTranslator;
import com.pu.askanexpert.service.dto.ExpertCriteria;
import com.pu.askanexpert.service.ExpertQueryService;

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
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.pu.askanexpert.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ExpertResource REST controller.
 *
 * @see ExpertResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AskAnExpertApp.class)
public class ExpertResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DOMAINE = "AAAAAAAAAA";
    private static final String UPDATED_DOMAINE = "BBBBBBBBBB";

    private static final String DEFAULT_PROFESSION = "AAAAAAAAAA";
    private static final String UPDATED_PROFESSION = "BBBBBBBBBB";

    private static final Long DEFAULT_PRIX = 1L;
    private static final Long UPDATED_PRIX = 2L;

    private static final Instant DEFAULT_DISPONIBILITE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DISPONIBILITE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_NOTE = 1;
    private static final Integer UPDATED_NOTE = 2;

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final Long DEFAULT_NUM_RIB = 1L;
    private static final Long UPDATED_NUM_RIB = 2L;

    @Autowired
    private ExpertRepository expertRepository;

    @Autowired
    private ExpertService expertService;

    @Autowired
    private ExpertQueryService expertQueryService;

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

    private MockMvc restExpertMockMvc;

    private Expert expert;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExpertResource expertResource = new ExpertResource(expertService, expertQueryService);
        this.restExpertMockMvc = MockMvcBuilders.standaloneSetup(expertResource)
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
    public static Expert createEntity(EntityManager em) {
        Expert expert = new Expert()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .date_naissance(DEFAULT_DATE_NAISSANCE)
            .adresse(DEFAULT_ADRESSE)
            .description(DEFAULT_DESCRIPTION)
            .domaine(DEFAULT_DOMAINE)
            .profession(DEFAULT_PROFESSION)
            .prix(DEFAULT_PRIX)
            .disponibilite(DEFAULT_DISPONIBILITE)
            .note(DEFAULT_NOTE)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .num_rib(DEFAULT_NUM_RIB);
        return expert;
    }

    @Before
    public void initTest() {
        expert = createEntity(em);
    }

    @Test
    @Transactional
    public void createExpert() throws Exception {
        int databaseSizeBeforeCreate = expertRepository.findAll().size();

        // Create the Expert
        restExpertMockMvc.perform(post("/api/experts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expert)))
            .andExpect(status().isCreated());

        // Validate the Expert in the database
        List<Expert> expertList = expertRepository.findAll();
        assertThat(expertList).hasSize(databaseSizeBeforeCreate + 1);
        Expert testExpert = expertList.get(expertList.size() - 1);
        assertThat(testExpert.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testExpert.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testExpert.getDate_naissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testExpert.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testExpert.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testExpert.getDomaine()).isEqualTo(DEFAULT_DOMAINE);
        assertThat(testExpert.getProfession()).isEqualTo(DEFAULT_PROFESSION);
        assertThat(testExpert.getPrix()).isEqualTo(DEFAULT_PRIX);
        assertThat(testExpert.getDisponibilite()).isEqualTo(DEFAULT_DISPONIBILITE);
        assertThat(testExpert.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testExpert.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testExpert.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testExpert.getNum_rib()).isEqualTo(DEFAULT_NUM_RIB);
    }

    @Test
    @Transactional
    public void createExpertWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = expertRepository.findAll().size();

        // Create the Expert with an existing ID
        expert.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExpertMockMvc.perform(post("/api/experts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expert)))
            .andExpect(status().isBadRequest());

        // Validate the Expert in the database
        List<Expert> expertList = expertRepository.findAll();
        assertThat(expertList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = expertRepository.findAll().size();
        // set the field null
        expert.setNom(null);

        // Create the Expert, which fails.

        restExpertMockMvc.perform(post("/api/experts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expert)))
            .andExpect(status().isBadRequest());

        List<Expert> expertList = expertRepository.findAll();
        assertThat(expertList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = expertRepository.findAll().size();
        // set the field null
        expert.setPrenom(null);

        // Create the Expert, which fails.

        restExpertMockMvc.perform(post("/api/experts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expert)))
            .andExpect(status().isBadRequest());

        List<Expert> expertList = expertRepository.findAll();
        assertThat(expertList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDate_naissanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = expertRepository.findAll().size();
        // set the field null
        expert.setDate_naissance(null);

        // Create the Expert, which fails.

        restExpertMockMvc.perform(post("/api/experts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expert)))
            .andExpect(status().isBadRequest());

        List<Expert> expertList = expertRepository.findAll();
        assertThat(expertList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdresseIsRequired() throws Exception {
        int databaseSizeBeforeTest = expertRepository.findAll().size();
        // set the field null
        expert.setAdresse(null);

        // Create the Expert, which fails.

        restExpertMockMvc.perform(post("/api/experts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expert)))
            .andExpect(status().isBadRequest());

        List<Expert> expertList = expertRepository.findAll();
        assertThat(expertList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDomaineIsRequired() throws Exception {
        int databaseSizeBeforeTest = expertRepository.findAll().size();
        // set the field null
        expert.setDomaine(null);

        // Create the Expert, which fails.

        restExpertMockMvc.perform(post("/api/experts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expert)))
            .andExpect(status().isBadRequest());

        List<Expert> expertList = expertRepository.findAll();
        assertThat(expertList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProfessionIsRequired() throws Exception {
        int databaseSizeBeforeTest = expertRepository.findAll().size();
        // set the field null
        expert.setProfession(null);

        // Create the Expert, which fails.

        restExpertMockMvc.perform(post("/api/experts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expert)))
            .andExpect(status().isBadRequest());

        List<Expert> expertList = expertRepository.findAll();
        assertThat(expertList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrixIsRequired() throws Exception {
        int databaseSizeBeforeTest = expertRepository.findAll().size();
        // set the field null
        expert.setPrix(null);

        // Create the Expert, which fails.

        restExpertMockMvc.perform(post("/api/experts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expert)))
            .andExpect(status().isBadRequest());

        List<Expert> expertList = expertRepository.findAll();
        assertThat(expertList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDisponibiliteIsRequired() throws Exception {
        int databaseSizeBeforeTest = expertRepository.findAll().size();
        // set the field null
        expert.setDisponibilite(null);

        // Create the Expert, which fails.

        restExpertMockMvc.perform(post("/api/experts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expert)))
            .andExpect(status().isBadRequest());

        List<Expert> expertList = expertRepository.findAll();
        assertThat(expertList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNum_ribIsRequired() throws Exception {
        int databaseSizeBeforeTest = expertRepository.findAll().size();
        // set the field null
        expert.setNum_rib(null);

        // Create the Expert, which fails.

        restExpertMockMvc.perform(post("/api/experts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expert)))
            .andExpect(status().isBadRequest());

        List<Expert> expertList = expertRepository.findAll();
        assertThat(expertList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExperts() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList
        restExpertMockMvc.perform(get("/api/experts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expert.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].date_naissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].domaine").value(hasItem(DEFAULT_DOMAINE.toString())))
            .andExpect(jsonPath("$.[*].profession").value(hasItem(DEFAULT_PROFESSION.toString())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.intValue())))
            .andExpect(jsonPath("$.[*].disponibilite").value(hasItem(DEFAULT_DISPONIBILITE.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].num_rib").value(hasItem(DEFAULT_NUM_RIB.intValue())));
    }
    
    @Test
    @Transactional
    public void getExpert() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get the expert
        restExpertMockMvc.perform(get("/api/experts/{id}", expert.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(expert.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.date_naissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.domaine").value(DEFAULT_DOMAINE.toString()))
            .andExpect(jsonPath("$.profession").value(DEFAULT_PROFESSION.toString()))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.intValue()))
            .andExpect(jsonPath("$.disponibilite").value(DEFAULT_DISPONIBILITE.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.num_rib").value(DEFAULT_NUM_RIB.intValue()));
    }

    @Test
    @Transactional
    public void getAllExpertsByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where nom equals to DEFAULT_NOM
        defaultExpertShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the expertList where nom equals to UPDATED_NOM
        defaultExpertShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllExpertsByNomIsInShouldWork() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultExpertShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the expertList where nom equals to UPDATED_NOM
        defaultExpertShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllExpertsByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where nom is not null
        defaultExpertShouldBeFound("nom.specified=true");

        // Get all the expertList where nom is null
        defaultExpertShouldNotBeFound("nom.specified=false");
    }

    @Test
    @Transactional
    public void getAllExpertsByPrenomIsEqualToSomething() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where prenom equals to DEFAULT_PRENOM
        defaultExpertShouldBeFound("prenom.equals=" + DEFAULT_PRENOM);

        // Get all the expertList where prenom equals to UPDATED_PRENOM
        defaultExpertShouldNotBeFound("prenom.equals=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllExpertsByPrenomIsInShouldWork() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where prenom in DEFAULT_PRENOM or UPDATED_PRENOM
        defaultExpertShouldBeFound("prenom.in=" + DEFAULT_PRENOM + "," + UPDATED_PRENOM);

        // Get all the expertList where prenom equals to UPDATED_PRENOM
        defaultExpertShouldNotBeFound("prenom.in=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllExpertsByPrenomIsNullOrNotNull() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where prenom is not null
        defaultExpertShouldBeFound("prenom.specified=true");

        // Get all the expertList where prenom is null
        defaultExpertShouldNotBeFound("prenom.specified=false");
    }

    @Test
    @Transactional
    public void getAllExpertsByDate_naissanceIsEqualToSomething() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where date_naissance equals to DEFAULT_DATE_NAISSANCE
        defaultExpertShouldBeFound("date_naissance.equals=" + DEFAULT_DATE_NAISSANCE);

        // Get all the expertList where date_naissance equals to UPDATED_DATE_NAISSANCE
        defaultExpertShouldNotBeFound("date_naissance.equals=" + UPDATED_DATE_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllExpertsByDate_naissanceIsInShouldWork() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where date_naissance in DEFAULT_DATE_NAISSANCE or UPDATED_DATE_NAISSANCE
        defaultExpertShouldBeFound("date_naissance.in=" + DEFAULT_DATE_NAISSANCE + "," + UPDATED_DATE_NAISSANCE);

        // Get all the expertList where date_naissance equals to UPDATED_DATE_NAISSANCE
        defaultExpertShouldNotBeFound("date_naissance.in=" + UPDATED_DATE_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllExpertsByDate_naissanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where date_naissance is not null
        defaultExpertShouldBeFound("date_naissance.specified=true");

        // Get all the expertList where date_naissance is null
        defaultExpertShouldNotBeFound("date_naissance.specified=false");
    }

    @Test
    @Transactional
    public void getAllExpertsByDate_naissanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where date_naissance greater than or equals to DEFAULT_DATE_NAISSANCE
        defaultExpertShouldBeFound("date_naissance.greaterOrEqualThan=" + DEFAULT_DATE_NAISSANCE);

        // Get all the expertList where date_naissance greater than or equals to UPDATED_DATE_NAISSANCE
        defaultExpertShouldNotBeFound("date_naissance.greaterOrEqualThan=" + UPDATED_DATE_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllExpertsByDate_naissanceIsLessThanSomething() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where date_naissance less than or equals to DEFAULT_DATE_NAISSANCE
        defaultExpertShouldNotBeFound("date_naissance.lessThan=" + DEFAULT_DATE_NAISSANCE);

        // Get all the expertList where date_naissance less than or equals to UPDATED_DATE_NAISSANCE
        defaultExpertShouldBeFound("date_naissance.lessThan=" + UPDATED_DATE_NAISSANCE);
    }


    @Test
    @Transactional
    public void getAllExpertsByAdresseIsEqualToSomething() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where adresse equals to DEFAULT_ADRESSE
        defaultExpertShouldBeFound("adresse.equals=" + DEFAULT_ADRESSE);

        // Get all the expertList where adresse equals to UPDATED_ADRESSE
        defaultExpertShouldNotBeFound("adresse.equals=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllExpertsByAdresseIsInShouldWork() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where adresse in DEFAULT_ADRESSE or UPDATED_ADRESSE
        defaultExpertShouldBeFound("adresse.in=" + DEFAULT_ADRESSE + "," + UPDATED_ADRESSE);

        // Get all the expertList where adresse equals to UPDATED_ADRESSE
        defaultExpertShouldNotBeFound("adresse.in=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllExpertsByAdresseIsNullOrNotNull() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where adresse is not null
        defaultExpertShouldBeFound("adresse.specified=true");

        // Get all the expertList where adresse is null
        defaultExpertShouldNotBeFound("adresse.specified=false");
    }

    @Test
    @Transactional
    public void getAllExpertsByDomaineIsEqualToSomething() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where domaine equals to DEFAULT_DOMAINE
        defaultExpertShouldBeFound("domaine.equals=" + DEFAULT_DOMAINE);

        // Get all the expertList where domaine equals to UPDATED_DOMAINE
        defaultExpertShouldNotBeFound("domaine.equals=" + UPDATED_DOMAINE);
    }

    @Test
    @Transactional
    public void getAllExpertsByDomaineIsInShouldWork() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where domaine in DEFAULT_DOMAINE or UPDATED_DOMAINE
        defaultExpertShouldBeFound("domaine.in=" + DEFAULT_DOMAINE + "," + UPDATED_DOMAINE);

        // Get all the expertList where domaine equals to UPDATED_DOMAINE
        defaultExpertShouldNotBeFound("domaine.in=" + UPDATED_DOMAINE);
    }

    @Test
    @Transactional
    public void getAllExpertsByDomaineIsNullOrNotNull() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where domaine is not null
        defaultExpertShouldBeFound("domaine.specified=true");

        // Get all the expertList where domaine is null
        defaultExpertShouldNotBeFound("domaine.specified=false");
    }

    @Test
    @Transactional
    public void getAllExpertsByProfessionIsEqualToSomething() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where profession equals to DEFAULT_PROFESSION
        defaultExpertShouldBeFound("profession.equals=" + DEFAULT_PROFESSION);

        // Get all the expertList where profession equals to UPDATED_PROFESSION
        defaultExpertShouldNotBeFound("profession.equals=" + UPDATED_PROFESSION);
    }

    @Test
    @Transactional
    public void getAllExpertsByProfessionIsInShouldWork() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where profession in DEFAULT_PROFESSION or UPDATED_PROFESSION
        defaultExpertShouldBeFound("profession.in=" + DEFAULT_PROFESSION + "," + UPDATED_PROFESSION);

        // Get all the expertList where profession equals to UPDATED_PROFESSION
        defaultExpertShouldNotBeFound("profession.in=" + UPDATED_PROFESSION);
    }

    @Test
    @Transactional
    public void getAllExpertsByProfessionIsNullOrNotNull() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where profession is not null
        defaultExpertShouldBeFound("profession.specified=true");

        // Get all the expertList where profession is null
        defaultExpertShouldNotBeFound("profession.specified=false");
    }

    @Test
    @Transactional
    public void getAllExpertsByPrixIsEqualToSomething() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where prix equals to DEFAULT_PRIX
        defaultExpertShouldBeFound("prix.equals=" + DEFAULT_PRIX);

        // Get all the expertList where prix equals to UPDATED_PRIX
        defaultExpertShouldNotBeFound("prix.equals=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllExpertsByPrixIsInShouldWork() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where prix in DEFAULT_PRIX or UPDATED_PRIX
        defaultExpertShouldBeFound("prix.in=" + DEFAULT_PRIX + "," + UPDATED_PRIX);

        // Get all the expertList where prix equals to UPDATED_PRIX
        defaultExpertShouldNotBeFound("prix.in=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllExpertsByPrixIsNullOrNotNull() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where prix is not null
        defaultExpertShouldBeFound("prix.specified=true");

        // Get all the expertList where prix is null
        defaultExpertShouldNotBeFound("prix.specified=false");
    }

    @Test
    @Transactional
    public void getAllExpertsByPrixIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where prix greater than or equals to DEFAULT_PRIX
        defaultExpertShouldBeFound("prix.greaterOrEqualThan=" + DEFAULT_PRIX);

        // Get all the expertList where prix greater than or equals to UPDATED_PRIX
        defaultExpertShouldNotBeFound("prix.greaterOrEqualThan=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllExpertsByPrixIsLessThanSomething() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where prix less than or equals to DEFAULT_PRIX
        defaultExpertShouldNotBeFound("prix.lessThan=" + DEFAULT_PRIX);

        // Get all the expertList where prix less than or equals to UPDATED_PRIX
        defaultExpertShouldBeFound("prix.lessThan=" + UPDATED_PRIX);
    }


    @Test
    @Transactional
    public void getAllExpertsByDisponibiliteIsEqualToSomething() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where disponibilite equals to DEFAULT_DISPONIBILITE
        defaultExpertShouldBeFound("disponibilite.equals=" + DEFAULT_DISPONIBILITE);

        // Get all the expertList where disponibilite equals to UPDATED_DISPONIBILITE
        defaultExpertShouldNotBeFound("disponibilite.equals=" + UPDATED_DISPONIBILITE);
    }

    @Test
    @Transactional
    public void getAllExpertsByDisponibiliteIsInShouldWork() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where disponibilite in DEFAULT_DISPONIBILITE or UPDATED_DISPONIBILITE
        defaultExpertShouldBeFound("disponibilite.in=" + DEFAULT_DISPONIBILITE + "," + UPDATED_DISPONIBILITE);

        // Get all the expertList where disponibilite equals to UPDATED_DISPONIBILITE
        defaultExpertShouldNotBeFound("disponibilite.in=" + UPDATED_DISPONIBILITE);
    }

    @Test
    @Transactional
    public void getAllExpertsByDisponibiliteIsNullOrNotNull() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where disponibilite is not null
        defaultExpertShouldBeFound("disponibilite.specified=true");

        // Get all the expertList where disponibilite is null
        defaultExpertShouldNotBeFound("disponibilite.specified=false");
    }

    @Test
    @Transactional
    public void getAllExpertsByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where note equals to DEFAULT_NOTE
        defaultExpertShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the expertList where note equals to UPDATED_NOTE
        defaultExpertShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllExpertsByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultExpertShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the expertList where note equals to UPDATED_NOTE
        defaultExpertShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllExpertsByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where note is not null
        defaultExpertShouldBeFound("note.specified=true");

        // Get all the expertList where note is null
        defaultExpertShouldNotBeFound("note.specified=false");
    }

    @Test
    @Transactional
    public void getAllExpertsByNoteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where note greater than or equals to DEFAULT_NOTE
        defaultExpertShouldBeFound("note.greaterOrEqualThan=" + DEFAULT_NOTE);

        // Get all the expertList where note greater than or equals to UPDATED_NOTE
        defaultExpertShouldNotBeFound("note.greaterOrEqualThan=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllExpertsByNoteIsLessThanSomething() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where note less than or equals to DEFAULT_NOTE
        defaultExpertShouldNotBeFound("note.lessThan=" + DEFAULT_NOTE);

        // Get all the expertList where note less than or equals to UPDATED_NOTE
        defaultExpertShouldBeFound("note.lessThan=" + UPDATED_NOTE);
    }


    @Test
    @Transactional
    public void getAllExpertsByNum_ribIsEqualToSomething() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where num_rib equals to DEFAULT_NUM_RIB
        defaultExpertShouldBeFound("num_rib.equals=" + DEFAULT_NUM_RIB);

        // Get all the expertList where num_rib equals to UPDATED_NUM_RIB
        defaultExpertShouldNotBeFound("num_rib.equals=" + UPDATED_NUM_RIB);
    }

    @Test
    @Transactional
    public void getAllExpertsByNum_ribIsInShouldWork() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where num_rib in DEFAULT_NUM_RIB or UPDATED_NUM_RIB
        defaultExpertShouldBeFound("num_rib.in=" + DEFAULT_NUM_RIB + "," + UPDATED_NUM_RIB);

        // Get all the expertList where num_rib equals to UPDATED_NUM_RIB
        defaultExpertShouldNotBeFound("num_rib.in=" + UPDATED_NUM_RIB);
    }

    @Test
    @Transactional
    public void getAllExpertsByNum_ribIsNullOrNotNull() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where num_rib is not null
        defaultExpertShouldBeFound("num_rib.specified=true");

        // Get all the expertList where num_rib is null
        defaultExpertShouldNotBeFound("num_rib.specified=false");
    }

    @Test
    @Transactional
    public void getAllExpertsByNum_ribIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where num_rib greater than or equals to DEFAULT_NUM_RIB
        defaultExpertShouldBeFound("num_rib.greaterOrEqualThan=" + DEFAULT_NUM_RIB);

        // Get all the expertList where num_rib greater than or equals to UPDATED_NUM_RIB
        defaultExpertShouldNotBeFound("num_rib.greaterOrEqualThan=" + UPDATED_NUM_RIB);
    }

    @Test
    @Transactional
    public void getAllExpertsByNum_ribIsLessThanSomething() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList where num_rib less than or equals to DEFAULT_NUM_RIB
        defaultExpertShouldNotBeFound("num_rib.lessThan=" + DEFAULT_NUM_RIB);

        // Get all the expertList where num_rib less than or equals to UPDATED_NUM_RIB
        defaultExpertShouldBeFound("num_rib.lessThan=" + UPDATED_NUM_RIB);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultExpertShouldBeFound(String filter) throws Exception {
        restExpertMockMvc.perform(get("/api/experts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expert.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].date_naissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].domaine").value(hasItem(DEFAULT_DOMAINE)))
            .andExpect(jsonPath("$.[*].profession").value(hasItem(DEFAULT_PROFESSION)))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.intValue())))
            .andExpect(jsonPath("$.[*].disponibilite").value(hasItem(DEFAULT_DISPONIBILITE.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].num_rib").value(hasItem(DEFAULT_NUM_RIB.intValue())));

        // Check, that the count call also returns 1
        restExpertMockMvc.perform(get("/api/experts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultExpertShouldNotBeFound(String filter) throws Exception {
        restExpertMockMvc.perform(get("/api/experts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restExpertMockMvc.perform(get("/api/experts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingExpert() throws Exception {
        // Get the expert
        restExpertMockMvc.perform(get("/api/experts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExpert() throws Exception {
        // Initialize the database
        expertService.save(expert);

        int databaseSizeBeforeUpdate = expertRepository.findAll().size();

        // Update the expert
        Expert updatedExpert = expertRepository.findById(expert.getId()).get();
        // Disconnect from session so that the updates on updatedExpert are not directly saved in db
        em.detach(updatedExpert);
        updatedExpert
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .date_naissance(UPDATED_DATE_NAISSANCE)
            .adresse(UPDATED_ADRESSE)
            .description(UPDATED_DESCRIPTION)
            .domaine(UPDATED_DOMAINE)
            .profession(UPDATED_PROFESSION)
            .prix(UPDATED_PRIX)
            .disponibilite(UPDATED_DISPONIBILITE)
            .note(UPDATED_NOTE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .num_rib(UPDATED_NUM_RIB);

        restExpertMockMvc.perform(put("/api/experts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedExpert)))
            .andExpect(status().isOk());

        // Validate the Expert in the database
        List<Expert> expertList = expertRepository.findAll();
        assertThat(expertList).hasSize(databaseSizeBeforeUpdate);
        Expert testExpert = expertList.get(expertList.size() - 1);
        assertThat(testExpert.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testExpert.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testExpert.getDate_naissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testExpert.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testExpert.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testExpert.getDomaine()).isEqualTo(UPDATED_DOMAINE);
        assertThat(testExpert.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testExpert.getPrix()).isEqualTo(UPDATED_PRIX);
        assertThat(testExpert.getDisponibilite()).isEqualTo(UPDATED_DISPONIBILITE);
        assertThat(testExpert.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testExpert.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testExpert.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testExpert.getNum_rib()).isEqualTo(UPDATED_NUM_RIB);
    }

    @Test
    @Transactional
    public void updateNonExistingExpert() throws Exception {
        int databaseSizeBeforeUpdate = expertRepository.findAll().size();

        // Create the Expert

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExpertMockMvc.perform(put("/api/experts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expert)))
            .andExpect(status().isBadRequest());

        // Validate the Expert in the database
        List<Expert> expertList = expertRepository.findAll();
        assertThat(expertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExpert() throws Exception {
        // Initialize the database
        expertService.save(expert);

        int databaseSizeBeforeDelete = expertRepository.findAll().size();

        // Delete the expert
        restExpertMockMvc.perform(delete("/api/experts/{id}", expert.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Expert> expertList = expertRepository.findAll();
        assertThat(expertList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Expert.class);
        Expert expert1 = new Expert();
        expert1.setId(1L);
        Expert expert2 = new Expert();
        expert2.setId(expert1.getId());
        assertThat(expert1).isEqualTo(expert2);
        expert2.setId(2L);
        assertThat(expert1).isNotEqualTo(expert2);
        expert1.setId(null);
        assertThat(expert1).isNotEqualTo(expert2);
    }
}
