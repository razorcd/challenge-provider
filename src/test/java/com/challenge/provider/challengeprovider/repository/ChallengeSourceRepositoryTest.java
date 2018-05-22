package com.challenge.provider.challengeprovider.repository;

import com.challenge.provider.challengeprovider.config.JacksonConfig;
import com.challenge.provider.challengeprovider.config.StorageParams;
import com.challenge.provider.challengeprovider.model.ChallengeId;
import com.challenge.provider.challengeprovider.model.ChallengeSource;
import com.challenge.provider.challengeprovider.utils.FileHelper;
import com.challenge.provider.challengeprovider.utils.JsonStringConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.*;

public class ChallengeSourceRepositoryTest {

    private static final String MAIN_STORAGE_FOLDER = org.assertj.core.util.Files.temporaryFolderPath()+"/challengeTestFolder1";
    private static final String SOURCE_JSON_FILENAME = "source.json";

    private StorageParams storageParams = new StorageParams(MAIN_STORAGE_FOLDER, SOURCE_JSON_FILENAME, null);
    private ChallengeSourceRepository challengeSourceRepository;
    private Clock clock;

    @Before
    public void before() {
        clock = Clock.fixed(Instant.ofEpochSecond(999999), ZoneId.systemDefault());
        challengeSourceRepository = new ChallengeSourceRepository(storageParams,
                                                                  new FileHelper(),
                                                                  new JsonStringConverter(new JacksonConfig().getObjectMapper()),
                                                                  clock);
    }

    @After
    public void after() throws IOException {
        FileSystemUtils.deleteRecursively(Paths.get(MAIN_STORAGE_FOLDER));
    }

    @Test
    public void createsSourceFolderOnInitialization() {
        assertTrue("Expect that project folder is created.", Files.exists(Paths.get(MAIN_STORAGE_FOLDER)));
    }

    @Test
    public void createNewChallengeSource() throws IOException {
        String challengeId = UUID.randomUUID().toString();
        ChallengeSource challengeSource = generateChallengeSource(challengeId);

        challengeSourceRepository.createChallengeSource(challengeSource);

        Path projectDir = Paths.get(MAIN_STORAGE_FOLDER, challengeId);
        Path sourceFile = projectDir.resolve(SOURCE_JSON_FILENAME);

        assertTrue("Expect that project folder is created.", Files.exists(projectDir));
        assertTrue("Expect that source file is created.", Files.exists(sourceFile));

        String createdSourceFile = new String(Files.readAllBytes(sourceFile));
        assertTrue("Source file contains project id.", createdSourceFile.contains(challengeId));
        assertTrue("Source file contains project name.", createdSourceFile.contains(challengeSource.getProjectName()));
        assertTrue("Source file contains project description.", createdSourceFile.contains(challengeSource.getDescription()));
        assertTrue("Source file contains project expected solver name.", createdSourceFile.contains(challengeSource.getExpectedSolversName()));
        assertTrue("Source file contains project expected solver email.", createdSourceFile.contains(challengeSource.getExpectedSolversEmail()));
        assertEquals("Source file contains the current timestamp.", LocalDateTime.now(clock), challengeSource.getCreateTimestamp());

    }

    @Test
    public void getChallengeSourceByIdWhenExists() {
        String challengeId = UUID.randomUUID().toString();
        ChallengeSource challengeSource = generateChallengeSource(challengeId);

        challengeSourceRepository.createChallengeSource(challengeSource);

        ChallengeSource requestedChallengeSource = challengeSourceRepository.getChallengeSourceById(new ChallengeId(challengeId));

        assertNotNull("Expect response to NOT be null.", requestedChallengeSource);
        assertEquals("Expect to retrieve correct challenge source by id.", challengeSource, requestedChallengeSource);
    }

    @Test
    public void getChallengeSourceByIdWhenDoesNotExist() {
        String challengeId = UUID.randomUUID().toString();

        ChallengeSource requestedChallengeSource = challengeSourceRepository.getChallengeSourceById(new ChallengeId(challengeId));

        assertNull("Expect response to be null.", requestedChallengeSource);
    }

    @Test
    public void overwriteChallengeSource() {
        String challengeId = UUID.randomUUID().toString();
        ChallengeSource challengeSource = generateChallengeSource(challengeId);
        challengeSourceRepository.createChallengeSource(challengeSource);

        ChallengeSource challengeSource2 = generateChallengeSource(challengeId);
        challengeSourceRepository.overwriteChallengeSource(challengeSource2);

        ChallengeSource requestedChallengeSource = challengeSourceRepository.getChallengeSourceById(new ChallengeId(challengeId));

        assertNotNull("Expect response to NOT be null.", requestedChallengeSource);
        assertEquals("Expect to retrieve the overwritten object.", challengeSource2, requestedChallengeSource);
    }

    @Test
    public void getChallengeSourceList() {
        List<String> challengeIds = Arrays.asList(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString());
        challengeIds.forEach((challengeId) -> {
            ChallengeSource challengeSource = generateChallengeSource(challengeId);
            challengeSourceRepository.createChallengeSource(challengeSource);
        });

        List<ChallengeSource> challengeSources = challengeSourceRepository.getChallengeSourceList();

        assertEquals("Expect 3 returned chalange sources.", challengeSources.size(), 3);
        challengeSources.forEach((challengeSource -> {
            assertTrue("Expect challenge source list is returned.", challengeIds.contains(challengeSource.getChallengeId().getId()));
        }));
    }


    private ChallengeSource generateChallengeSource(String challengeId) {
        return new ChallengeSource(
                new ChallengeId(challengeId),
                "Project Name " + new Random().nextInt(),
                "description " + new Random().nextInt(),
                "Solver Name " + new Random().nextInt(),
                "solverName" + new Random().nextInt() + "@example.com",
                9000L
        );
    }
}