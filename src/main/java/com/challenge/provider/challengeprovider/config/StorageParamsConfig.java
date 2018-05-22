package com.challenge.provider.challengeprovider.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class StorageParamsConfig {

    @Value("${app.storage.folder}") String mainStorageFolder;
    @Value("${app.storage.source.filename}") String sourceFilename;
    @Value("${app.storage.solution.filename}") String solutionFilename;

    /**
     * Bean defining global storage params.
     *
     * @return [StorageParams]
     */
    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public StorageParams storageParams() {
        return new StorageParams(mainStorageFolder, sourceFilename, solutionFilename);
    }
}
