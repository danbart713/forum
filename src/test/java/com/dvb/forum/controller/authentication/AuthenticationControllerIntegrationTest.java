package com.dvb.forum.controller.authentication;

import com.dvb.forum.dto.authentication.UserRegistrationResponse;
import com.dvb.forum.entity.*;
import com.dvb.forum.enums.UserRoleEnum;
import com.dvb.forum.repository.TokenBlacklistRepository;
import com.dvb.forum.repository.UserLoginRecordRepository;
import com.dvb.forum.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS, scripts = "classpath:sql/forum-test-ddl.sql")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
class AuthenticationControllerIntegrationTest {

    private static final String INTEGRATION_TEST_PAYLOADS_PATH = "integrationtestpayloads";
    private static final String REQUEST_IP_ADDRESS = "0:0:0:0:0:0:0:1";

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final UserLoginRecordRepository userLoginRecordRepository;
    private final TokenBlacklistRepository tokenBlacklistRepository;

    @Autowired
    public AuthenticationControllerIntegrationTest(MockMvc mockMvc,
                                                   ObjectMapper objectMapper,
                                                   UserRepository userRepository,
                                                   UserLoginRecordRepository userLoginRecordRepository,
                                                   TokenBlacklistRepository tokenBlacklistRepository) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
        this.userLoginRecordRepository = userLoginRecordRepository;
        this.tokenBlacklistRepository = tokenBlacklistRepository;
    }

    @BeforeAll
    public static void beforeAllTestsSetup() {

    }

    @AfterAll
    public static void afterAllTestsSetup() {

    }

    @BeforeEach
    public void beforeEachTestSetup() {

    }

    @AfterEach
    public void afterEachTestSetup() {

    }

    // Success Scenarios
    @Test
    @Order(1)
    public void registerIndividualUser() throws Exception {
        log.info("AuthenticationControllerIntegrationTest - registerIndividualUser");

        String endpointPath = "/authentication/register";
        String integrationTestPayloadFileName = "registerIndividualUserRequest.json";

        MvcResult actualMvcResult = callEndpointUnderTest(endpointPath, integrationTestPayloadFileName);
        log.info("AuthenticationControllerIntegrationTest - registerIndividualUser - actualMvcResult: {}", actualMvcResult);

        int actualResponseStatus = actualMvcResult.getResponse().getStatus();
        log.info("AuthenticationControllerIntegrationTest - registerIndividualUser - actualResponseStatus: {}", actualResponseStatus);

        UserRegistrationResponse actualUserRegistrationResponse = objectMapper.readValue(actualMvcResult.getResponse().getContentAsString(),
                UserRegistrationResponse.class);
        log.info("AuthenticationControllerIntegrationTest - registerIndividualUser - actualUserRegistrationResponse: {}", actualUserRegistrationResponse);

        makeAssertionsOnActualUserRegistrationResponse(actualResponseStatus, actualUserRegistrationResponse);

        IndividualEntity actualIndividualEntity = (IndividualEntity) userRepository.findByEmailAddress(getStringValueFromJsonFile(
                        INTEGRATION_TEST_PAYLOADS_PATH + "/" + integrationTestPayloadFileName, "email"))
                .orElseThrow(() -> new Exception("User not found with supplied email for integration test."));
        log.info("AuthenticationControllerIntegrationTest - registerIndividualUser - actualIndividualEntity: {}", actualIndividualEntity);

        makeAssertionsOnActualIndividualEntity(actualIndividualEntity);
        makeAssertionsOnActualUserEntity(actualIndividualEntity, 1L, true, integrationTestPayloadFileName, 1, 0L);
        makeAssertionsOnActualEmailEntity(actualIndividualEntity, 1L, integrationTestPayloadFileName);
        makeAssertionsOnActualUserLoginRecordEntity(actualIndividualEntity, 0, 1L);
    }

    @Test
    @Order(2)
    public void registerBusinessUser() throws Exception {
        log.info("AuthenticationControllerIntegrationTest - registerBusinessUser");

        String endpointPath = "/authentication/register";
        String integrationTestPayloadFileName = "registerBusinessUserRequest.json";

        MvcResult actualMvcResult = callEndpointUnderTest(endpointPath, integrationTestPayloadFileName);
        log.info("AuthenticationControllerIntegrationTest - registerBusinessUser - actualMvcResult: {}", actualMvcResult);

        int actualResponseStatus = actualMvcResult.getResponse().getStatus();
        log.info("AuthenticationControllerIntegrationTest - registerBusinessUser - actualResponseStatus: {}", actualResponseStatus);

        UserRegistrationResponse actualUserRegistrationResponse = objectMapper.readValue(actualMvcResult.getResponse().getContentAsString(),
                UserRegistrationResponse.class);
        log.info("AuthenticationControllerIntegrationTest - registerBusinessUser - actualUserRegistrationResponse: {}", actualUserRegistrationResponse);

        makeAssertionsOnActualUserRegistrationResponse(actualResponseStatus, actualUserRegistrationResponse);

        BusinessEntity actualBusinessEntity = (BusinessEntity) userRepository.findByEmailAddress(getStringValueFromJsonFile(
                        INTEGRATION_TEST_PAYLOADS_PATH + "/" + integrationTestPayloadFileName, "email"))
                .orElseThrow(() -> new Exception("User not found with supplied email for integration test."));
        log.info("AuthenticationControllerIntegrationTest - registerBusinessUser - actualBusinessEntity: {}", actualBusinessEntity);

        makeAssertionsOnActualBusinessEntity(actualBusinessEntity, true, integrationTestPayloadFileName);
        makeAssertionsOnActualUserEntity(actualBusinessEntity, 2L, true, integrationTestPayloadFileName, 1, 0L);
        makeAssertionsOnActualEmailEntity(actualBusinessEntity, 2L, integrationTestPayloadFileName);
        makeAssertionsOnActualUserLoginRecordEntity(actualBusinessEntity, 0, 2L);
    }

    @Test
    @Order(3)
    public void registerAdminUser() {

    }

    @Test
    @Order(4)
    public void loginIndividualUser() {
    }

    @Test
    @Order(5)
    public void loginBusinessUser() {
    }

    @Test
    @Order(6)
    public void loginAdminUser() {
    }

    @Test
    @Order(7)
    public void refreshTokenIndividualUser() {
    }

    @Test
    @Order(8)
    public void refreshTokenBusinessUser() {
    }

    @Test
    @Order(9)
    public void refreshTokenAdminUser() {
    }

    @Test
    @Order(10)
    public void logoutIndividualUser() {
    }

    @Test
    @Order(11)
    public void logoutBusinessUser() {
    }

    @Test
    @Order(12)
    public void logoutAdminUser() {
    }

    // TODO: Failure Scenarios


    private MvcResult callEndpointUnderTest(String endpointPath, String integrationTestPayloadFileName) throws Exception {
        log.info("AuthenticationControllerIntegrationTest - callEndpointUnderTest - endpointPath: {}, integrationTestPayloadFileName: {}",
                endpointPath, integrationTestPayloadFileName);

        MvcResult actualMvcResult = mockMvc.perform(post(endpointPath)
                        .with(request -> {
                            request.setRemoteAddr(REQUEST_IP_ADDRESS);
                            return request;
                        })
                        .contentType("application/json")
                        .content(convertJsonFileToBytes(INTEGRATION_TEST_PAYLOADS_PATH + "/" + integrationTestPayloadFileName)))
                .andReturn();
        log.info("AuthenticationControllerIntegrationTest - callEndpointUnderTest - actualMvcResult: {}", actualMvcResult);

        return actualMvcResult;
    }

    private void makeAssertionsOnActualUserRegistrationResponse(int actualResponseStatus, UserRegistrationResponse actualUserRegistrationResponse) {
        log.info("AuthenticationControllerIntegrationTest - makeAssertionsOnUserRegistrationResponse - actualResponseStatus: {}, actualUserRegistrationResponse: {}",
                actualResponseStatus, actualUserRegistrationResponse);

        Assertions.assertEquals(201, actualResponseStatus);
        Assertions.assertNotNull(actualUserRegistrationResponse);
        Assertions.assertTrue(StringUtils.isNotBlank(actualUserRegistrationResponse.getToken()));
    }

    private void makeAssertionsOnActualIndividualEntity(IndividualEntity actualIndividualEntity) {
        log.info("AuthenticationControllerIntegrationTest - makeAssertionsOnActualIndividualEntity - actualIndividualEntity: {}", actualIndividualEntity);

        // IndividualEntity
        Assertions.assertNotNull(actualIndividualEntity);
//        private UUID individualUuidId;
        Assertions.assertNotNull(actualIndividualEntity.getIndividualUuidId());
//        private LocalDateTime individualCreatedDateTime;
        Assertions.assertNotNull(actualIndividualEntity.getIndividualCreatedDateTime());
//        private LocalDateTime individualUpdatedDateTime;
        Assertions.assertNotNull(actualIndividualEntity.getIndividualUpdatedDateTime());
    }

    private void makeAssertionsOnActualBusinessEntity(BusinessEntity actualBusinessEntity, boolean registerBusinessIntegrationTest, String integrationTestPayloadFileName) throws IOException {
        log.info("AuthenticationControllerIntegrationTest - makeAssertionsOnActualBusinessEntity - actualBusinessEntity: {}, registerBusinessIntegrationTest: {}, integrationTestPayloadFileName: {}",
                actualBusinessEntity, registerBusinessIntegrationTest, integrationTestPayloadFileName);

        // BusinessEntity
        Assertions.assertNotNull(actualBusinessEntity);
//        private UUID businessUuidId;
        Assertions.assertNotNull(actualBusinessEntity.getBusinessUuidId());
//        private String businessName;
        Assertions.assertTrue(StringUtils.isNotBlank(actualBusinessEntity.getBusinessName()));
        if (registerBusinessIntegrationTest) {
            Assertions.assertEquals(getStringValueFromJsonFile(INTEGRATION_TEST_PAYLOADS_PATH + "/" + integrationTestPayloadFileName, "businessName"),
                    actualBusinessEntity.getBusinessName());
        }
//        private String contactFirstName;
        Assertions.assertTrue(StringUtils.isNotBlank(actualBusinessEntity.getContactFirstName()));
        if (registerBusinessIntegrationTest) {
            Assertions.assertEquals(getStringValueFromJsonFile(INTEGRATION_TEST_PAYLOADS_PATH + "/" + integrationTestPayloadFileName, "contactFirstName"),
                    actualBusinessEntity.getContactFirstName());
        }
//        private String contactLastName;
        Assertions.assertTrue(StringUtils.isNotBlank(actualBusinessEntity.getContactLastName()));
        if (registerBusinessIntegrationTest) {
            Assertions.assertEquals(getStringValueFromJsonFile(INTEGRATION_TEST_PAYLOADS_PATH + "/" + integrationTestPayloadFileName, "contactLastName"),
                    actualBusinessEntity.getContactLastName());
        }
//        private LocalDateTime businessCreatedDateTime;
        Assertions.assertNotNull(actualBusinessEntity.getBusinessCreatedDateTime());
//        private LocalDateTime businessUpdatedDateTime;
        Assertions.assertNotNull(actualBusinessEntity.getBusinessUpdatedDateTime());
    }

    private void makeAssertionsOnActualUserEntity(UserEntity actualUserEntity, long expectedUserEntityId, boolean registerUserIntegrationTest, String integrationTestPayloadFileName,
                                                  int expectedUserLoginRecordEntityListSize, long expectedOptimisticLockingVersion) throws IOException {
        log.info("AuthenticationControllerIntegrationTest - makeAssertionsOnActualUserEntity - actualUserEntity: {}, expectedUserEntityId:{}, registerUserIntegrationTest: {}, " +
                        "integrationTestPayloadFileName: {}, expectedUserLoginRecordEntityListSize: {}, expectedOptimisticLockingVersion: {}",
                actualUserEntity, expectedUserEntityId, registerUserIntegrationTest, integrationTestPayloadFileName, expectedUserLoginRecordEntityListSize, expectedOptimisticLockingVersion);

        // UserEntity
//        private Long id;
        Assertions.assertNotNull(actualUserEntity.getId());
        Assertions.assertEquals(expectedUserEntityId, actualUserEntity.getId());
//        private UUID userUuidId;
        Assertions.assertNotNull(actualUserEntity.getUserUuidId());
//        private String displayName;
        Assertions.assertTrue(StringUtils.isNotBlank(actualUserEntity.getDisplayName()));
        if (registerUserIntegrationTest) {
            Assertions.assertEquals(getStringValueFromJsonFile(INTEGRATION_TEST_PAYLOADS_PATH + "/" + integrationTestPayloadFileName, "displayName"),
                    actualUserEntity.getDisplayName());
        }
//        private EmailEntity emailEntity;
        Assertions.assertNotNull(actualUserEntity.getEmailEntity());
//        private String password;
        Assertions.assertTrue(StringUtils.isNotBlank(actualUserEntity.getPassword()));
//        private UserRoleEnum userRole;
        Assertions.assertNotNull(actualUserEntity.getUserRole());
        if (registerUserIntegrationTest) {
            Assertions.assertEquals(UserRoleEnum.fromLabel(getStringValueFromJsonFile(INTEGRATION_TEST_PAYLOADS_PATH + "/" + integrationTestPayloadFileName, "userRole")),
                    actualUserEntity.getUserRole());
        }
//        private String registrationIpAddress;
        Assertions.assertTrue(StringUtils.isNotBlank(actualUserEntity.getRegistrationIpAddress()));
        Assertions.assertEquals(REQUEST_IP_ADDRESS, actualUserEntity.getRegistrationIpAddress());
//        private LocalDateTime lastLoginDateTime;
        Assertions.assertNotNull(actualUserEntity.getLastLoginDateTime());
//        private boolean userSuspended;
        Assertions.assertFalse(actualUserEntity.isUserSuspended());
//        private boolean userBanned;
        Assertions.assertFalse(actualUserEntity.isUserBanned());
//        private boolean passwordExpired;
        Assertions.assertFalse(actualUserEntity.isPasswordExpired());
//        private boolean userLocked;
        Assertions.assertFalse(actualUserEntity.isUserLocked());
//        private boolean userDeleted;
        Assertions.assertFalse(actualUserEntity.isUserDeleted());
//        private List<UserLoginRecordEntity> userLoginRecordEntityList;
        Assertions.assertEquals(expectedUserLoginRecordEntityListSize, actualUserEntity.getUserLoginRecordEntityList().size());
//        private Long optimisticLockingVersion;
        Assertions.assertNotNull(actualUserEntity.getOptimisticLockingVersion());
        Assertions.assertEquals(expectedOptimisticLockingVersion, actualUserEntity.getOptimisticLockingVersion());
//        private LocalDateTime userCreatedDateTime;
        Assertions.assertNotNull(actualUserEntity.getUserCreatedDateTime());
//        private LocalDateTime userUpdatedDateTime;
        Assertions.assertNotNull(actualUserEntity.getUserUpdatedDateTime());
    }

    private void makeAssertionsOnActualEmailEntity(UserEntity actualUserEntity, long expectedEmailEntityId, String integrationTestPayloadFileName) throws IOException {
        log.info("AuthenticationControllerIntegrationTest - makeAssertionsOnActualEmailEntity - actualUserEntity: {}, expectedEmailEntityId: {}, integrationTestPayloadFileName: {}",
                actualUserEntity, expectedEmailEntityId, integrationTestPayloadFileName);

        // EmailEntity
        EmailEntity actualEmailEntity = actualUserEntity.getEmailEntity();
//        private Long id;
        Assertions.assertNotNull(actualEmailEntity.getId());
        Assertions.assertEquals(expectedEmailEntityId, actualEmailEntity.getId());
//        private UUID uuidId;
        Assertions.assertNotNull(actualEmailEntity.getUuidId());
//        private String emailAddress;
        Assertions.assertTrue(StringUtils.isNotBlank(actualEmailEntity.getEmailAddress()));
        Assertions.assertEquals(getStringValueFromJsonFile(INTEGRATION_TEST_PAYLOADS_PATH + "/" + integrationTestPayloadFileName, "email"),
                actualEmailEntity.getEmailAddress());
//        private boolean registered;
        Assertions.assertFalse(actualEmailEntity.isRegistered());
//        private boolean twoFactorAuthenticationEnabled;
        Assertions.assertFalse(actualEmailEntity.isTwoFactorAuthenticationEnabled());
//        private UserEntity userEntity;
        Assertions.assertNotNull(actualEmailEntity.getUserEntity());
//        private Long optimisticLockingVersion;
        Assertions.assertNotNull(actualEmailEntity.getOptimisticLockingVersion());
        Assertions.assertEquals(0, actualEmailEntity.getOptimisticLockingVersion());
//        private LocalDateTime createdDateTime;
        Assertions.assertNotNull(actualEmailEntity.getCreatedDateTime());
//        private LocalDateTime updatedDateTime;
        Assertions.assertNotNull(actualEmailEntity.getUpdatedDateTime());
    }

    private void makeAssertionsOnActualUserLoginRecordEntity(UserEntity actualUserEntity, int actualUserLoginRecordEntityListElement, long expectedUserLoginRecordEntityId) {
        log.info("AuthenticationControllerIntegrationTest - makeAssertionsOnActualUserLoginRecordEntity - actualUserEntity: {}, userLoginRecordEntityListElement: {}, " +
                        "expectedUserLoginRecordEntityId: {}",
                actualUserEntity, actualUserLoginRecordEntityListElement, expectedUserLoginRecordEntityId);

        // UserLoginRecordEntity
        UserLoginRecordEntity actualUserLoginRecordEntity = actualUserEntity.getUserLoginRecordEntityList().get(actualUserLoginRecordEntityListElement);
//        private Long id;
        Assertions.assertNotNull(actualUserLoginRecordEntity.getId());
        Assertions.assertEquals(expectedUserLoginRecordEntityId, actualUserLoginRecordEntity.getId());
//        private UUID uuidId;
        Assertions.assertNotNull(actualUserLoginRecordEntity.getUuidId());
//        private String ipAddress;
        Assertions.assertTrue(StringUtils.isNotBlank(actualUserLoginRecordEntity.getIpAddress()));
        Assertions.assertEquals(REQUEST_IP_ADDRESS, actualUserLoginRecordEntity.getIpAddress());
//        private LocalDateTime loginDateTime;
        Assertions.assertNotNull(actualUserLoginRecordEntity.getLoginDateTime());
//        private UserEntity userEntity;
        Assertions.assertNotNull(actualUserLoginRecordEntity.getUserEntity());
//        private Long optimisticLockingVersion;
        Assertions.assertNotNull(actualUserLoginRecordEntity.getOptimisticLockingVersion());
        Assertions.assertEquals(0, actualUserLoginRecordEntity.getOptimisticLockingVersion());
//        private LocalDateTime createdDateTime;
        Assertions.assertNotNull(actualUserLoginRecordEntity.getCreatedDateTime());
//        private LocalDateTime updatedDateTime;
        Assertions.assertNotNull(actualUserLoginRecordEntity.getUpdatedDateTime());
    }

    private byte[] convertJsonFileToBytes(String path) throws IOException {
        return new ClassPathResource(path).getInputStream().readAllBytes();
    }

    private String getStringValueFromJsonFile(String path, String key) throws IOException {
        String jsonString = new ClassPathResource(path).getContentAsString(Charset.defaultCharset());
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        return jsonNode.get(key).textValue();
    }

}