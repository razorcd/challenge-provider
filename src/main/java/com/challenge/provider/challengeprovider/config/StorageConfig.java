package com.challenge.provider.challengeprovider.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public Path storageFolder() {
        return Paths.get("projects", environment);
    }

    /**
     * Create bean for temporary folder storage.
     *
     * @return [Path] Path to temporary folder.
     */
    @Bean("temp-folder")
    public Path tempFolder() {
        try {
            return Files.createTempDirectory("challenge_provider_temp");
        } catch (IOException e) {
            throw new RuntimeException("IO error durring temporary folder creation. "+e.getMessage());
        }
    }
}
