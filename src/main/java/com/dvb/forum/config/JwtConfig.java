package com.dvb.forum.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@Slf4j
public class JwtConfig {

    @Bean("jwtProperties")
    @ConfigurationProperties("jwt")
    public Properties jwtProperties() {
        return new Properties();
    }

}
