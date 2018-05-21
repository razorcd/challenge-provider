package com.challenge.provider.challengeprovider.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static org.junit.Assert.*;

public class ProjectServiceTest {

    private static final String TEMP_FOLDER = "temp";

    @Autowired
    @Qualifier("storage-folder")
    private Path storageFolder;

    private Path tempFolder;

    @Autowired
    ProjectService projectService;

    @BeforeClass
    public void beforeClass() throws IOException {
        tempFolder = Files.createTempDirectory(TEMP_FOLDER);
    }

    @Test
    public void createProject() throws IOException {
        Path tempFile = Files.createTempFile(tempFolder,"1", null);
        String description = "description from ProjectService.";

        String projectId = projectService.createProject(tempFile, description);

        assertTrue(Files.exists(storageFolder.resolve(projectId)));
    }
}