package com.challenge.provider.challengeprovider.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ClockConfig {

    /**
     * Clock bean to use over the app and to mock in tests.
     *
     * @return [Clock] with default system timezone.
     */
    @Bean
    Clock getClock() {
        return Clock.systemDefaultZone();
    }

}
