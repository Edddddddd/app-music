package com.app.music.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.app.music.IntegrationTest;
import com.app.music.domain.User;
import com.app.music.domain.UserExtra;
import com.app.music.repository.UserExtraRepository;
import com.app.music.service.criteria.UserExtraCriteria;
import com.app.music.service.dto.UserExtraDTO;
import com.app.music.service.mapper.UserExtraMapper;
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
 * Integration tests for the {@link UserExtraResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserExtraResourceIT {

    private static final String DEFAULT_RUT = "AAAAAAAAAA";
    private static final String UPDATED_RUT = "BBBBBBBBBB";

    private static final Integer DEFAULT_TELEFONO = 1;
    private static final Integer UPDATED_TELEFONO = 2;
    private static final Integer SMALLER_TELEFONO = 1 - 1;

    private static final String ENTITY_API_URL = "/api/user-extras";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserExtraRepository userExtraRepository;

    @Autowired
    private UserExtraMapper userExtraMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserExtraMockMvc;

    private UserExtra userExtra;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserExtra createEntity(EntityManager em) {
        UserExtra userExtra = new UserExtra().rut(DEFAULT_RUT).telefono(DEFAULT_TELEFONO);
        return userExtra;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserExtra createUpdatedEntity(EntityManager em) {
        UserExtra userExtra = new UserExtra().rut(UPDATED_RUT).telefono(UPDATED_TELEFONO);
        return userExtra;
    }

    @BeforeEach
    public void initTest() {
        userExtra = createEntity(em);
    }

    @Test
    @Transactional
    void createUserExtra() throws Exception {
        int databaseSizeBeforeCreate = userExtraRepository.findAll().size();
        // Create the UserExtra
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);
        restUserExtraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userExtraDTO)))
            .andExpect(status().isCreated());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeCreate + 1);
        UserExtra testUserExtra = userExtraList.get(userExtraList.size() - 1);
        assertThat(testUserExtra.getRut()).isEqualTo(DEFAULT_RUT);
        assertThat(testUserExtra.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
    }

    @Test
    @Transactional
    void createUserExtraWithExistingId() throws Exception {
        // Create the UserExtra with an existing ID
        userExtra.setId(1L);
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);

        int databaseSizeBeforeCreate = userExtraRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserExtraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userExtraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserExtras() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get all the userExtraList
        restUserExtraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userExtra.getId().intValue())))
            .andExpect(jsonPath("$.[*].rut").value(hasItem(DEFAULT_RUT)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)));
    }

    @Test
    @Transactional
    void getUserExtra() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get the userExtra
        restUserExtraMockMvc
            .perform(get(ENTITY_API_URL_ID, userExtra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userExtra.getId().intValue()))
            .andExpect(jsonPath("$.rut").value(DEFAULT_RUT))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO));
    }

    @Test
    @Transactional
    void getUserExtrasByIdFiltering() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        Long id = userExtra.getId();

        defaultUserExtraShouldBeFound("id.equals=" + id);
        defaultUserExtraShouldNotBeFound("id.notEquals=" + id);

        defaultUserExtraShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUserExtraShouldNotBeFound("id.greaterThan=" + id);

        defaultUserExtraShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUserExtraShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUserExtrasByRutIsEqualToSomething() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get all the userExtraList where rut equals to DEFAULT_RUT
        defaultUserExtraShouldBeFound("rut.equals=" + DEFAULT_RUT);

        // Get all the userExtraList where rut equals to UPDATED_RUT
        defaultUserExtraShouldNotBeFound("rut.equals=" + UPDATED_RUT);
    }

    @Test
    @Transactional
    void getAllUserExtrasByRutIsInShouldWork() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get all the userExtraList where rut in DEFAULT_RUT or UPDATED_RUT
        defaultUserExtraShouldBeFound("rut.in=" + DEFAULT_RUT + "," + UPDATED_RUT);

        // Get all the userExtraList where rut equals to UPDATED_RUT
        defaultUserExtraShouldNotBeFound("rut.in=" + UPDATED_RUT);
    }

    @Test
    @Transactional
    void getAllUserExtrasByRutIsNullOrNotNull() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get all the userExtraList where rut is not null
        defaultUserExtraShouldBeFound("rut.specified=true");

        // Get all the userExtraList where rut is null
        defaultUserExtraShouldNotBeFound("rut.specified=false");
    }

    @Test
    @Transactional
    void getAllUserExtrasByRutContainsSomething() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get all the userExtraList where rut contains DEFAULT_RUT
        defaultUserExtraShouldBeFound("rut.contains=" + DEFAULT_RUT);

        // Get all the userExtraList where rut contains UPDATED_RUT
        defaultUserExtraShouldNotBeFound("rut.contains=" + UPDATED_RUT);
    }

    @Test
    @Transactional
    void getAllUserExtrasByRutNotContainsSomething() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get all the userExtraList where rut does not contain DEFAULT_RUT
        defaultUserExtraShouldNotBeFound("rut.doesNotContain=" + DEFAULT_RUT);

        // Get all the userExtraList where rut does not contain UPDATED_RUT
        defaultUserExtraShouldBeFound("rut.doesNotContain=" + UPDATED_RUT);
    }

    @Test
    @Transactional
    void getAllUserExtrasByTelefonoIsEqualToSomething() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get all the userExtraList where telefono equals to DEFAULT_TELEFONO
        defaultUserExtraShouldBeFound("telefono.equals=" + DEFAULT_TELEFONO);

        // Get all the userExtraList where telefono equals to UPDATED_TELEFONO
        defaultUserExtraShouldNotBeFound("telefono.equals=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    void getAllUserExtrasByTelefonoIsInShouldWork() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get all the userExtraList where telefono in DEFAULT_TELEFONO or UPDATED_TELEFONO
        defaultUserExtraShouldBeFound("telefono.in=" + DEFAULT_TELEFONO + "," + UPDATED_TELEFONO);

        // Get all the userExtraList where telefono equals to UPDATED_TELEFONO
        defaultUserExtraShouldNotBeFound("telefono.in=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    void getAllUserExtrasByTelefonoIsNullOrNotNull() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get all the userExtraList where telefono is not null
        defaultUserExtraShouldBeFound("telefono.specified=true");

        // Get all the userExtraList where telefono is null
        defaultUserExtraShouldNotBeFound("telefono.specified=false");
    }

    @Test
    @Transactional
    void getAllUserExtrasByTelefonoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get all the userExtraList where telefono is greater than or equal to DEFAULT_TELEFONO
        defaultUserExtraShouldBeFound("telefono.greaterThanOrEqual=" + DEFAULT_TELEFONO);

        // Get all the userExtraList where telefono is greater than or equal to UPDATED_TELEFONO
        defaultUserExtraShouldNotBeFound("telefono.greaterThanOrEqual=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    void getAllUserExtrasByTelefonoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get all the userExtraList where telefono is less than or equal to DEFAULT_TELEFONO
        defaultUserExtraShouldBeFound("telefono.lessThanOrEqual=" + DEFAULT_TELEFONO);

        // Get all the userExtraList where telefono is less than or equal to SMALLER_TELEFONO
        defaultUserExtraShouldNotBeFound("telefono.lessThanOrEqual=" + SMALLER_TELEFONO);
    }

    @Test
    @Transactional
    void getAllUserExtrasByTelefonoIsLessThanSomething() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get all the userExtraList where telefono is less than DEFAULT_TELEFONO
        defaultUserExtraShouldNotBeFound("telefono.lessThan=" + DEFAULT_TELEFONO);

        // Get all the userExtraList where telefono is less than UPDATED_TELEFONO
        defaultUserExtraShouldBeFound("telefono.lessThan=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    void getAllUserExtrasByTelefonoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get all the userExtraList where telefono is greater than DEFAULT_TELEFONO
        defaultUserExtraShouldNotBeFound("telefono.greaterThan=" + DEFAULT_TELEFONO);

        // Get all the userExtraList where telefono is greater than SMALLER_TELEFONO
        defaultUserExtraShouldBeFound("telefono.greaterThan=" + SMALLER_TELEFONO);
    }

    @Test
    @Transactional
    void getAllUserExtrasByUserIsEqualToSomething() throws Exception {
        User user;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            userExtraRepository.saveAndFlush(userExtra);
            user = UserResourceIT.createEntity(em);
        } else {
            user = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(user);
        em.flush();
        userExtra.setUser(user);
        userExtraRepository.saveAndFlush(userExtra);
        Long userId = user.getId();

        // Get all the userExtraList where user equals to userId
        defaultUserExtraShouldBeFound("userId.equals=" + userId);

        // Get all the userExtraList where user equals to (userId + 1)
        defaultUserExtraShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserExtraShouldBeFound(String filter) throws Exception {
        restUserExtraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userExtra.getId().intValue())))
            .andExpect(jsonPath("$.[*].rut").value(hasItem(DEFAULT_RUT)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)));

        // Check, that the count call also returns 1
        restUserExtraMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserExtraShouldNotBeFound(String filter) throws Exception {
        restUserExtraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserExtraMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUserExtra() throws Exception {
        // Get the userExtra
        restUserExtraMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserExtra() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();

        // Update the userExtra
        UserExtra updatedUserExtra = userExtraRepository.findById(userExtra.getId()).get();
        // Disconnect from session so that the updates on updatedUserExtra are not directly saved in db
        em.detach(updatedUserExtra);
        updatedUserExtra.rut(UPDATED_RUT).telefono(UPDATED_TELEFONO);
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(updatedUserExtra);

        restUserExtraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userExtraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userExtraDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
        UserExtra testUserExtra = userExtraList.get(userExtraList.size() - 1);
        assertThat(testUserExtra.getRut()).isEqualTo(UPDATED_RUT);
        assertThat(testUserExtra.getTelefono()).isEqualTo(UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    void putNonExistingUserExtra() throws Exception {
        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();
        userExtra.setId(count.incrementAndGet());

        // Create the UserExtra
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserExtraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userExtraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userExtraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserExtra() throws Exception {
        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();
        userExtra.setId(count.incrementAndGet());

        // Create the UserExtra
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserExtraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userExtraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserExtra() throws Exception {
        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();
        userExtra.setId(count.incrementAndGet());

        // Create the UserExtra
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserExtraMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userExtraDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserExtraWithPatch() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();

        // Update the userExtra using partial update
        UserExtra partialUpdatedUserExtra = new UserExtra();
        partialUpdatedUserExtra.setId(userExtra.getId());

        partialUpdatedUserExtra.rut(UPDATED_RUT);

        restUserExtraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserExtra.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserExtra))
            )
            .andExpect(status().isOk());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
        UserExtra testUserExtra = userExtraList.get(userExtraList.size() - 1);
        assertThat(testUserExtra.getRut()).isEqualTo(UPDATED_RUT);
        assertThat(testUserExtra.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
    }

    @Test
    @Transactional
    void fullUpdateUserExtraWithPatch() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();

        // Update the userExtra using partial update
        UserExtra partialUpdatedUserExtra = new UserExtra();
        partialUpdatedUserExtra.setId(userExtra.getId());

        partialUpdatedUserExtra.rut(UPDATED_RUT).telefono(UPDATED_TELEFONO);

        restUserExtraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserExtra.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserExtra))
            )
            .andExpect(status().isOk());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
        UserExtra testUserExtra = userExtraList.get(userExtraList.size() - 1);
        assertThat(testUserExtra.getRut()).isEqualTo(UPDATED_RUT);
        assertThat(testUserExtra.getTelefono()).isEqualTo(UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    void patchNonExistingUserExtra() throws Exception {
        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();
        userExtra.setId(count.incrementAndGet());

        // Create the UserExtra
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserExtraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userExtraDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userExtraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserExtra() throws Exception {
        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();
        userExtra.setId(count.incrementAndGet());

        // Create the UserExtra
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserExtraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userExtraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserExtra() throws Exception {
        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();
        userExtra.setId(count.incrementAndGet());

        // Create the UserExtra
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserExtraMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userExtraDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserExtra() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        int databaseSizeBeforeDelete = userExtraRepository.findAll().size();

        // Delete the userExtra
        restUserExtraMockMvc
            .perform(delete(ENTITY_API_URL_ID, userExtra.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
