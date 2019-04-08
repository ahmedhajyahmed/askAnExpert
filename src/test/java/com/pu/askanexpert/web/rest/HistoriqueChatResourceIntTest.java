package com.pu.askanexpert.web.rest;

import com.pu.askanexpert.AskAnExpertApp;

import com.pu.askanexpert.domain.HistoriqueChat;
import com.pu.askanexpert.repository.HistoriqueChatRepository;
import com.pu.askanexpert.service.HistoriqueChatService;
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
 * Test class for the HistoriqueChatResource REST controller.
 *
 * @see HistoriqueChatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AskAnExpertApp.class)
public class HistoriqueChatResourceIntTest {

    private static final LocalDate DEFAULT_DATE_APPEL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_APPEL = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_CLIENT = "BBBBBBBBBB";

    @Autowired
    private HistoriqueChatRepository historiqueChatRepository;

    @Autowired
    private HistoriqueChatService historiqueChatService;

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

    private MockMvc restHistoriqueChatMockMvc;

    private HistoriqueChat historiqueChat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HistoriqueChatResource historiqueChatResource = new HistoriqueChatResource(historiqueChatService);
        this.restHistoriqueChatMockMvc = MockMvcBuilders.standaloneSetup(historiqueChatResource)
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
    public static HistoriqueChat createEntity(EntityManager em) {
        HistoriqueChat historiqueChat = new HistoriqueChat()
            .dateAppel(DEFAULT_DATE_APPEL)
            .description(DEFAULT_DESCRIPTION)
            .nomClient(DEFAULT_NOM_CLIENT);
        return historiqueChat;
    }

    @Before
    public void initTest() {
        historiqueChat = createEntity(em);
    }

    @Test
    @Transactional
    public void createHistoriqueChat() throws Exception {
        int databaseSizeBeforeCreate = historiqueChatRepository.findAll().size();

        // Create the HistoriqueChat
        restHistoriqueChatMockMvc.perform(post("/api/historique-chats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiqueChat)))
            .andExpect(status().isCreated());

        // Validate the HistoriqueChat in the database
        List<HistoriqueChat> historiqueChatList = historiqueChatRepository.findAll();
        assertThat(historiqueChatList).hasSize(databaseSizeBeforeCreate + 1);
        HistoriqueChat testHistoriqueChat = historiqueChatList.get(historiqueChatList.size() - 1);
        assertThat(testHistoriqueChat.getDateAppel()).isEqualTo(DEFAULT_DATE_APPEL);
        assertThat(testHistoriqueChat.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testHistoriqueChat.getNomClient()).isEqualTo(DEFAULT_NOM_CLIENT);
    }

    @Test
    @Transactional
    public void createHistoriqueChatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = historiqueChatRepository.findAll().size();

        // Create the HistoriqueChat with an existing ID
        historiqueChat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistoriqueChatMockMvc.perform(post("/api/historique-chats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiqueChat)))
            .andExpect(status().isBadRequest());

        // Validate the HistoriqueChat in the database
        List<HistoriqueChat> historiqueChatList = historiqueChatRepository.findAll();
        assertThat(historiqueChatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateAppelIsRequired() throws Exception {
        int databaseSizeBeforeTest = historiqueChatRepository.findAll().size();
        // set the field null
        historiqueChat.setDateAppel(null);

        // Create the HistoriqueChat, which fails.

        restHistoriqueChatMockMvc.perform(post("/api/historique-chats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiqueChat)))
            .andExpect(status().isBadRequest());

        List<HistoriqueChat> historiqueChatList = historiqueChatRepository.findAll();
        assertThat(historiqueChatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomClientIsRequired() throws Exception {
        int databaseSizeBeforeTest = historiqueChatRepository.findAll().size();
        // set the field null
        historiqueChat.setNomClient(null);

        // Create the HistoriqueChat, which fails.

        restHistoriqueChatMockMvc.perform(post("/api/historique-chats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiqueChat)))
            .andExpect(status().isBadRequest());

        List<HistoriqueChat> historiqueChatList = historiqueChatRepository.findAll();
        assertThat(historiqueChatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHistoriqueChats() throws Exception {
        // Initialize the database
        historiqueChatRepository.saveAndFlush(historiqueChat);

        // Get all the historiqueChatList
        restHistoriqueChatMockMvc.perform(get("/api/historique-chats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historiqueChat.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateAppel").value(hasItem(DEFAULT_DATE_APPEL.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].nomClient").value(hasItem(DEFAULT_NOM_CLIENT.toString())));
    }
    
    @Test
    @Transactional
    public void getHistoriqueChat() throws Exception {
        // Initialize the database
        historiqueChatRepository.saveAndFlush(historiqueChat);

        // Get the historiqueChat
        restHistoriqueChatMockMvc.perform(get("/api/historique-chats/{id}", historiqueChat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(historiqueChat.getId().intValue()))
            .andExpect(jsonPath("$.dateAppel").value(DEFAULT_DATE_APPEL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.nomClient").value(DEFAULT_NOM_CLIENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHistoriqueChat() throws Exception {
        // Get the historiqueChat
        restHistoriqueChatMockMvc.perform(get("/api/historique-chats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHistoriqueChat() throws Exception {
        // Initialize the database
        historiqueChatService.save(historiqueChat);

        int databaseSizeBeforeUpdate = historiqueChatRepository.findAll().size();

        // Update the historiqueChat
        HistoriqueChat updatedHistoriqueChat = historiqueChatRepository.findById(historiqueChat.getId()).get();
        // Disconnect from session so that the updates on updatedHistoriqueChat are not directly saved in db
        em.detach(updatedHistoriqueChat);
        updatedHistoriqueChat
            .dateAppel(UPDATED_DATE_APPEL)
            .description(UPDATED_DESCRIPTION)
            .nomClient(UPDATED_NOM_CLIENT);

        restHistoriqueChatMockMvc.perform(put("/api/historique-chats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHistoriqueChat)))
            .andExpect(status().isOk());

        // Validate the HistoriqueChat in the database
        List<HistoriqueChat> historiqueChatList = historiqueChatRepository.findAll();
        assertThat(historiqueChatList).hasSize(databaseSizeBeforeUpdate);
        HistoriqueChat testHistoriqueChat = historiqueChatList.get(historiqueChatList.size() - 1);
        assertThat(testHistoriqueChat.getDateAppel()).isEqualTo(UPDATED_DATE_APPEL);
        assertThat(testHistoriqueChat.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testHistoriqueChat.getNomClient()).isEqualTo(UPDATED_NOM_CLIENT);
    }

    @Test
    @Transactional
    public void updateNonExistingHistoriqueChat() throws Exception {
        int databaseSizeBeforeUpdate = historiqueChatRepository.findAll().size();

        // Create the HistoriqueChat

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoriqueChatMockMvc.perform(put("/api/historique-chats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiqueChat)))
            .andExpect(status().isBadRequest());

        // Validate the HistoriqueChat in the database
        List<HistoriqueChat> historiqueChatList = historiqueChatRepository.findAll();
        assertThat(historiqueChatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHistoriqueChat() throws Exception {
        // Initialize the database
        historiqueChatService.save(historiqueChat);

        int databaseSizeBeforeDelete = historiqueChatRepository.findAll().size();

        // Delete the historiqueChat
        restHistoriqueChatMockMvc.perform(delete("/api/historique-chats/{id}", historiqueChat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HistoriqueChat> historiqueChatList = historiqueChatRepository.findAll();
        assertThat(historiqueChatList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistoriqueChat.class);
        HistoriqueChat historiqueChat1 = new HistoriqueChat();
        historiqueChat1.setId(1L);
        HistoriqueChat historiqueChat2 = new HistoriqueChat();
        historiqueChat2.setId(historiqueChat1.getId());
        assertThat(historiqueChat1).isEqualTo(historiqueChat2);
        historiqueChat2.setId(2L);
        assertThat(historiqueChat1).isNotEqualTo(historiqueChat2);
        historiqueChat1.setId(null);
        assertThat(historiqueChat1).isNotEqualTo(historiqueChat2);
    }
}
