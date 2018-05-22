package com.challenge.provider.challengeprovider.repository;

import com.challenge.provider.challengeprovider.config.JacksonConfig;
import com.challenge.provider.challengeprovider.config.StorageParams;
import com.challenge.provider.challengeprovider.model.ChallengeId;
import com.challenge.provider.challengeprovider.model.ChallengeSolution;
import com.challenge.provider.challengeprovider.utils.FileHelper;
import com.challenge.provider.challengeprovider.utils.JsonStringConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.*;

public class ChallengeSolutionRepositoryTest {

    private static final String MAIN_STORAGE_FOLDER = org.assertj.core.util.Files.temporaryFolderPath()+"/challengeTestFolder1";
    private static final String SOLUTION_JSON_FILENAME = "solution.json";

    private StorageParams storageParams = new StorageParams(MAIN_STORAGE_FOLDER, null, SOLUTION_JSON_FILENAME);
    private ChallengeSolutionRepository challengeSolutionRepository;
    private Clock clock;

    @Before
    public void before() {
        clock = Clock.fixed(Instant.ofEpochMilli(999999), ZoneId.systemDefault());
        challengeSolutionRepository = new ChallengeSolutionRepository(storageParams,
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
    public void createNewChallengeSolutionWithoutFile() throws IOException {
        String challengeId = UUID.randomUUID().toString();
        ChallengeSolution challengeSolution = generateChallengeSolution(challengeId);

        challengeSolutionRepository.createChallengeSolution(challengeSolution);

        Path projectDir = Paths.get(MAIN_STORAGE_FOLDER, challengeId);
        Path solutionFile = projectDir.resolve(SOLUTION_JSON_FILENAME);

        assertTrue("Expect that project folder is created.", Files.exists(projectDir));  // TODO: maybe? expect that project exists and check at save
        assertTrue("Expect that solution file is created.", Files.exists(solutionFile));

        String createdSolutionFile = new String(Files.readAllBytes(solutionFile));
        assertTrue("Solution file contains project id.", createdSolutionFile.contains(challengeId));
        assertTrue("Solution file contains project solver name.", createdSolutionFile.contains(challengeSolution.getSolversName()));
        assertTrue("Solution file contains project solver email.", createdSolutionFile.contains(challengeSolution.getSolversEmail()));
        assertTrue("Solution file contains project comments.", createdSolutionFile.contains(challengeSolution.getDescription()));
        assertTrue("Solution file contains NO project uploaded file.", createdSolutionFile.contains("null"));
        assertEquals("Solution file contains the current timestamp.", LocalDateTime.now(clock), challengeSolution.getCreateTimestamp());
    }

    @Test
    public void saveNewChallengeSolutionWithFile() throws IOException {
        String challengeId = UUID.randomUUID().toString();
        ChallengeSolution challengeSolution = generateChallengeSolution(challengeId);

        MockMultipartFile multipartFile = new MockMultipartFile("projectFileName", "fakeProject.zip",
                "application/octet-stream", "uploaded data".getBytes());

        challengeSolutionRepository.createChallengeSolution(challengeSolution, multipartFile);

        Path projectDir = Paths.get(MAIN_STORAGE_FOLDER, challengeId);
        Path solutionFile = projectDir.resolve(SOLUTION_JSON_FILENAME);

        assertTrue("Expect that project folder is created.", Files.exists(projectDir));  // TODO: maybe? expect that project exists and check at save
        assertTrue("Expect that solution file is created.", Files.exists(solutionFile));
        assertTrue("Expect that multipart solution file is copied.", Files.exists(projectDir.resolve(multipartFile.getOriginalFilename())));

        String createdSolutionFile = new String(Files.readAllBytes(solutionFile));
        assertTrue("Solution file contains project id.", createdSolutionFile.contains(challengeId));
        assertTrue("Solution file contains project solver name.", createdSolutionFile.contains(challengeSolution.getSolversName()));
        assertTrue("Solution file contains project solver email.", createdSolutionFile.contains(challengeSolution.getSolversEmail()));
        assertTrue("Solution file contains project comments.", createdSolutionFile.contains(challengeSolution.getDescription()));
        assertTrue("Solution file contains project uploaded file.", createdSolutionFile.contains(multipartFile.getOriginalFilename()));
    }

    @Test
    public void getChallengeSolutionByIdWhenExists() {
        String challengeId = UUID.randomUUID().toString();
        ChallengeSolution challengeSolution = generateChallengeSolution(challengeId);

        challengeSolutionRepository.createChallengeSolution(challengeSolution);

        ChallengeSolution returnedChallengeSolution = challengeSolutionRepository.getChallengeSolutionById(new ChallengeId(challengeId));

        assertNotNull("Expect response to NOT be null.", returnedChallengeSolution);
        assertEquals("Expect to retrieve correct challenge solution by id.", challengeSolution, returnedChallengeSolution);
    }

    @Test
    public void getChallengeSolutionByIdWhenDoesNotExist() {
        String challengeId = UUID.randomUUID().toString();

        ChallengeSolution returnedChallengeSolution = challengeSolutionRepository.getChallengeSolutionById(new ChallengeId(challengeId));

        assertNull("Expect response to be null.", returnedChallengeSolution);
    }

    private ChallengeSolution generateChallengeSolution(String challengeId) {
        return new ChallengeSolution( // TODO: extract to data generator
                new ChallengeId(challengeId),
                "Solver Name " + new Random().nextInt(),
                "solverName" + new Random().nextInt() + "@example.com",
                "comments " + new Random().nextInt(),
                null
        );
    }
}