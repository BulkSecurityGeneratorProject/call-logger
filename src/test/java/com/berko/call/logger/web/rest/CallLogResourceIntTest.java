package com.berko.call.logger.web.rest;

import com.berko.call.logger.CallLoggerApp;

import com.berko.call.logger.domain.CallLog;
import com.berko.call.logger.repository.CallLogRepository;
import com.berko.call.logger.web.rest.errors.ExceptionTranslator;

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

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.berko.call.logger.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.berko.call.logger.domain.enumeration.Direction;
/**
 * Test class for the CallLogResource REST controller.
 *
 * @see CallLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CallLoggerApp.class)
public class CallLogResourceIntTest {

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSCRIPT = "AAAAAAAAAA";
    private static final String UPDATED_TRANSCRIPT = "BBBBBBBBBB";

    private static final String DEFAULT_RECORDING = "AAAAAAAAAA";
    private static final String UPDATED_RECORDING = "BBBBBBBBBB";

    private static final Direction DEFAULT_DIRECTION = Direction.INCOMING;
    private static final Direction UPDATED_DIRECTION = Direction.OUTGOING;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_CALL_BACK = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CALL_BACK = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CallLogRepository callLogRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restCallLogMockMvc;

    private CallLog callLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CallLogResource callLogResource = new CallLogResource(callLogRepository);
        this.restCallLogMockMvc = MockMvcBuilders.standaloneSetup(callLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CallLog createEntity() {
        CallLog callLog = new CallLog()
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .transcript(DEFAULT_TRANSCRIPT)
            .recording(DEFAULT_RECORDING)
            .direction(DEFAULT_DIRECTION)
            .notes(DEFAULT_NOTES)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .callBack(DEFAULT_CALL_BACK);
        return callLog;
    }

    @Before
    public void initTest() {
        callLogRepository.deleteAll();
        callLog = createEntity();
    }

    @Test
    public void createCallLog() throws Exception {
        int databaseSizeBeforeCreate = callLogRepository.findAll().size();

        // Create the CallLog
        restCallLogMockMvc.perform(post("/api/call-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(callLog)))
            .andExpect(status().isCreated());

        // Validate the CallLog in the database
        List<CallLog> callLogList = callLogRepository.findAll();
        assertThat(callLogList).hasSize(databaseSizeBeforeCreate + 1);
        CallLog testCallLog = callLogList.get(callLogList.size() - 1);
        assertThat(testCallLog.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testCallLog.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCallLog.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCallLog.getTranscript()).isEqualTo(DEFAULT_TRANSCRIPT);
        assertThat(testCallLog.getRecording()).isEqualTo(DEFAULT_RECORDING);
        assertThat(testCallLog.getDirection()).isEqualTo(DEFAULT_DIRECTION);
        assertThat(testCallLog.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testCallLog.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testCallLog.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testCallLog.getCallBack()).isEqualTo(DEFAULT_CALL_BACK);
    }

    @Test
    public void createCallLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = callLogRepository.findAll().size();

        // Create the CallLog with an existing ID
        callLog.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCallLogMockMvc.perform(post("/api/call-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(callLog)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CallLog> callLogList = callLogRepository.findAll();
        assertThat(callLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllCallLogs() throws Exception {
        // Initialize the database
        callLogRepository.save(callLog);

        // Get all the callLogList
        restCallLogMockMvc.perform(get("/api/call-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(callLog.getId())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].transcript").value(hasItem(DEFAULT_TRANSCRIPT.toString())))
            .andExpect(jsonPath("$.[*].recording").value(hasItem(DEFAULT_RECORDING.toString())))
            .andExpect(jsonPath("$.[*].direction").value(hasItem(DEFAULT_DIRECTION.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(sameInstant(DEFAULT_START_TIME))))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(sameInstant(DEFAULT_END_TIME))))
            .andExpect(jsonPath("$.[*].callBack").value(hasItem(sameInstant(DEFAULT_CALL_BACK))));
    }

    @Test
    public void getCallLog() throws Exception {
        // Initialize the database
        callLogRepository.save(callLog);

        // Get the callLog
        restCallLogMockMvc.perform(get("/api/call-logs/{id}", callLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(callLog.getId()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.transcript").value(DEFAULT_TRANSCRIPT.toString()))
            .andExpect(jsonPath("$.recording").value(DEFAULT_RECORDING.toString()))
            .andExpect(jsonPath("$.direction").value(DEFAULT_DIRECTION.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()))
            .andExpect(jsonPath("$.startTime").value(sameInstant(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.endTime").value(sameInstant(DEFAULT_END_TIME)))
            .andExpect(jsonPath("$.callBack").value(sameInstant(DEFAULT_CALL_BACK)));
    }

    @Test
    public void getNonExistingCallLog() throws Exception {
        // Get the callLog
        restCallLogMockMvc.perform(get("/api/call-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCallLog() throws Exception {
        // Initialize the database
        callLogRepository.save(callLog);
        int databaseSizeBeforeUpdate = callLogRepository.findAll().size();

        // Update the callLog
        CallLog updatedCallLog = callLogRepository.findOne(callLog.getId());
        updatedCallLog
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .transcript(UPDATED_TRANSCRIPT)
            .recording(UPDATED_RECORDING)
            .direction(UPDATED_DIRECTION)
            .notes(UPDATED_NOTES)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .callBack(UPDATED_CALL_BACK);

        restCallLogMockMvc.perform(put("/api/call-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCallLog)))
            .andExpect(status().isOk());

        // Validate the CallLog in the database
        List<CallLog> callLogList = callLogRepository.findAll();
        assertThat(callLogList).hasSize(databaseSizeBeforeUpdate);
        CallLog testCallLog = callLogList.get(callLogList.size() - 1);
        assertThat(testCallLog.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testCallLog.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCallLog.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCallLog.getTranscript()).isEqualTo(UPDATED_TRANSCRIPT);
        assertThat(testCallLog.getRecording()).isEqualTo(UPDATED_RECORDING);
        assertThat(testCallLog.getDirection()).isEqualTo(UPDATED_DIRECTION);
        assertThat(testCallLog.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testCallLog.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testCallLog.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testCallLog.getCallBack()).isEqualTo(UPDATED_CALL_BACK);
    }

    @Test
    public void updateNonExistingCallLog() throws Exception {
        int databaseSizeBeforeUpdate = callLogRepository.findAll().size();

        // Create the CallLog

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCallLogMockMvc.perform(put("/api/call-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(callLog)))
            .andExpect(status().isCreated());

        // Validate the CallLog in the database
        List<CallLog> callLogList = callLogRepository.findAll();
        assertThat(callLogList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteCallLog() throws Exception {
        // Initialize the database
        callLogRepository.save(callLog);
        int databaseSizeBeforeDelete = callLogRepository.findAll().size();

        // Get the callLog
        restCallLogMockMvc.perform(delete("/api/call-logs/{id}", callLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CallLog> callLogList = callLogRepository.findAll();
        assertThat(callLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CallLog.class);
        CallLog callLog1 = new CallLog();
        callLog1.setId("id1");
        CallLog callLog2 = new CallLog();
        callLog2.setId(callLog1.getId());
        assertThat(callLog1).isEqualTo(callLog2);
        callLog2.setId("id2");
        assertThat(callLog1).isNotEqualTo(callLog2);
        callLog1.setId(null);
        assertThat(callLog1).isNotEqualTo(callLog2);
    }
}
