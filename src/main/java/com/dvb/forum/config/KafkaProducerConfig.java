//package com.dvb.forum.config;
//
//import com.dvb.forum.dto.activitytracking.ActivityTrackingKafkaEvent;
//import com.dvb.forum.dto.emailsending.EmailSendingKafkaEvent;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//import org.springframework.kafka.support.serializer.JsonSerializer;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//
//@Configuration
//@Slf4j
//public class KafkaProducerConfig {
//
//    @Bean
//    @ConfigurationProperties("kafka")
//    public Properties kafkaProducerProperties() {
//        return new Properties();
//    }
//
//    @Bean
//    public ProducerFactory<String, String> stringProducerFactory() {
//        Properties kafkaProperties = kafkaProducerProperties();
//
//        Map<String, Object> configs = new HashMap<>();
//        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getProperty("bootstrap.servers"));
//        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//
//        return new DefaultKafkaProducerFactory<>(configs);
//    }
//
//    @Bean
//    public KafkaTemplate<String, String> stringKafkaTemplate() {
//        return new KafkaTemplate<>(stringProducerFactory());
//    }
//
//    @Bean
//    public ProducerFactory<String, EmailSendingKafkaEvent> emailSendingProducerFactory() {
//        Properties kafkaProperties = kafkaProducerProperties();
//
//        Map<String, Object> configs = new HashMap<>();
//        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getProperty("bootstrap.servers"));
//        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//
//        return new DefaultKafkaProducerFactory<>(configs);
//    }
//
//    @Bean
//    public KafkaTemplate<String, EmailSendingKafkaEvent> emailSendingKafkaTemplate() {
//        return new KafkaTemplate<>(emailSendingProducerFactory());
//    }
//
//    @Bean
//    public ProducerFactory<String, ActivityTrackingKafkaEvent> activityTrackingProducerFactory() {
//        Properties kafkaProperties = kafkaProducerProperties();
//
//        Map<String, Object> configs = new HashMap<>();
//        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getProperty("bootstrap.servers"));
//        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//
//        return new DefaultKafkaProducerFactory<>(configs);
//    }
//
//    @Bean
//    public KafkaTemplate<String, ActivityTrackingKafkaEvent> activityTrackingKafkaTemplate() {
//        return new KafkaTemplate<>(activityTrackingProducerFactory());
//    }
//
//}
