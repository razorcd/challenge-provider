package com.challenge.provider.challengeprovider.config;

import com.challenge.provider.challengeprovider.utils.JsonStringConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonStringConverterConfig {

    @Autowired
    ObjectMapper objectMapper;

    /**
     * Bean defining global Json serializer/deserializer.
     *
     * @return [JsonStringConverter]
     */
    @Bean
    JsonStringConverter jsonStringConverter() {
        return new JsonStringConverter(objectMapper);
    }
}
