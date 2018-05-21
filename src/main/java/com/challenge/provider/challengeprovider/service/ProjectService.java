package com.challenge.provider.challengeprovider.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProjectService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    @Qualifier("storage-folder")
    private Path storageFolder;

    @Value("description-filename")
    private String descriptionFilename;

    /**
     * Service to manage projects.
     */
    @Autowired
    public ProjectService() {
    }

    /**
     * Create a new project with an auto generated ID.
     * Stores project file and description in a folder defined by the project ID.
     *
     * @param filePath the project file path
     * @param description the project description
     * @return [String] project ID
     */
    public String createProject(Path filePath, String description) {
        String projectId =UUID.randomUUID().toString();
        Path projectDir = this.storageFolder.resolve(projectId);

        storeFilesTo(projectDir, description, filePath);
        return projectId;
    }


    private void storeFilesTo(Path projectDir, String description, Path filePath) {
        try {
            Files.createDirectory(projectDir);
            if (Objects.nonNull(filePath)) {
                Files.copy(filePath, projectDir.resolve(filePath.getFileName()));
            }
            Files.write(projectDir.resolve(descriptionFilename), description.getBytes(), StandardOpenOption.CREATE_NEW);
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
