package com.challenge.provider.challengeprovider.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class ProjectFactory {

    private Path storageFolder;
    private String descriptionFilename;

    public ProjectFactory(@Qualifier("storage-folder") Path storageFolder,
                          @Value("${description.filename}") String descriptionFilename) {
        this.storageFolder = storageFolder;
        this.descriptionFilename = descriptionFilename;
    }

    Project
}
