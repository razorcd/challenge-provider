package com.challenge.provider.challengeprovider.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProjectService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectService.class);

    private Path storageFolder;

    @Value("${description.filename}") String descriptionFilename;

    /**
     * Service to manage projects.
     */
    @Autowired
    public ProjectService(@Qualifier("storage-folder") Path storageFolder,
                          @Value("${description.filename}") String descriptionFilename) {
        this.storageFolder = storageFolder;
        this.descriptionFilename = descriptionFilename;
    }

    /**
     * Create a new project with an auto generated ID.
     * Stores project file and description in a folder defined by the project ID.
     *
     * @param file the uploaded project file
     * @param description the project description
     * @return [String] project ID
     */
    public String createProject(MultipartFile file, String description) {
        String projectId = UUID.randomUUID().toString();
        Path projectDir = this.storageFolder.resolve(projectId);

        createProjectFolder(projectId);
        createDescriptionFile()

        Path descriptionFile = null;
        try {
            descriptionFile = Files.createTempFile(projectDir.resolve(descriptionFilename), null, null);
        } catch (IOException e) {}

        storeFilesTo(projectDir, descriptionFile, file);

        return projectId;
    }


    private void storeFilesTo(Path projectDir, Path file1, MultipartFile file2) {
        try {
            Files.createDirectory(projectDir);
            if (Objects.nonNull(file1)) {
                Files.copy(file2.getInputStream(), projectDir.resolve(file2.getOriginalFilename()));
            }
            Files.copy(file1, projectDir.resolve(file1));
        } catch (IOException e) {
            rollbackProjectCreation(projectDir);
            throw new RuntimeException("IO error while storing project at '" + projectDir.toString() + "'. " + e.getMessage());
        }
    }

    private void rollbackProjectCreation(Path projectDir) {
        try {
            Files.deleteIfExists(projectDir);
        } catch (IOException e) {
            LOGGER.warn("IO error during rollback project creation from '{}'. {}", projectDir, e.getMessage());
        }
    }

}
