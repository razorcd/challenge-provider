package com.challenge.provider.challengeprovider.service;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static org.junit.Assert.*;

public class ProjectServiceTest {

    private static final String TEMP_FOLDER = "temp";
    private static final String DESCRIPTION_FILENAME = "testDescription.txt";

    private static Path tempFolder;

    ProjectService projectService;

    @BeforeClass
    public static void beforeClass() throws IOException {
        tempFolder = Files.createTempDirectory(TEMP_FOLDER);
    }

    @Before
    public void before() {
        projectService = new ProjectService(tempFolder, DESCRIPTION_FILENAME);
    }

    @Test
    public void createProject() throws IOException {
        MockMultipartFile multipartFile = new MockMultipartFile("projectFileName", "fakeProject.zip",
                "application/octet-stream", "uploaded data".getBytes());
        String description = "description data";


        String projectId = projectService.createProject(multipartFile, description);

        Path expectedTempFolder = tempFolder.resolve(projectId);
        Path expectedUploadedFile = tempFolder.resolve(projectId).resolve(multipartFile.getOriginalFilename());
        Path expectedDescriptionFile = tempFolder.resolve(projectId).resolve(DESCRIPTION_FILENAME);

        assertTrue("ChallengeSource folder should be defined.", Files.exists(expectedTempFolder));
        assertTrue("Uploaded file should be stored.", Files.exists(expectedUploadedFile));
        assertTrue("Description file should be created.", Files.exists(expectedDescriptionFile));

        assertEquals("Uploaded file content is saved.", "uploaded data", new String(Files.readAllBytes(expectedUploadedFile)));
        assertEquals("Description file content is saved.", "description data", new String(Files.readAllBytes(expectedDescriptionFile)));
    }
}