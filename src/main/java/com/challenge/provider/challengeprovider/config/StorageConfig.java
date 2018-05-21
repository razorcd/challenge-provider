package com.challenge.provider.challengeprovider.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class StorageConfig {

    @Value("${environment}")
    private String environment;

    /**
     * Bean defining global storage folder.
     *
     * @return [Path] Path to global storage folder.
     */
    @Bean("storage-folder")
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public Path storageFolder() {
        Path storageFolder = Paths.get("projects", environment);

        try {
            Files.createDirectory(storageFolder);
        } catch (IOException e) {
            throw new RuntimeException("IO error during storage folder creation. " + e.getMessage());
        }
        return storageFolder;
    }
}
