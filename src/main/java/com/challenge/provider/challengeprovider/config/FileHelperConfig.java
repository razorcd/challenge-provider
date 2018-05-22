package com.challenge.provider.challengeprovider.config;

import com.challenge.provider.challengeprovider.utils.FileHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileHelperConfig {

    /**
     * Global bean for file manipulation.
     *
     * @return [FileHelper]
     */
    @Bean
    FileHelper fileHelper() {
        return new FileHelper();
    }
}
