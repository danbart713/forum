//package com.dvb.forum.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.admin.AdminClientConfig;
//import org.apache.kafka.clients.admin.NewTopic;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.KafkaAdmin;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//
//@Configuration
//@Slf4j
//public class KafkaTopicConfig {
//
//    @Bean
//    @ConfigurationProperties("kafka")
//    public Properties kafkaTopicProperties() {
//        return new Properties();
//    }
//
//    @Bean
//    public KafkaAdmin kafkaAdmin() {
//        Properties kafkaProperties = kafkaTopicProperties();
//
//        Map<String, Object> configs = new HashMap<>();
//        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getProperty("bootstrap.servers"));
//
//        return new KafkaAdmin(configs);
//    }
//
//    @Bean
//    public NewTopic topicTestKafka() {
//        Properties kafkaProperties = kafkaTopicProperties();
//
//        return new NewTopic((String) kafkaProperties.getProperty("topic.name.test-kafka"), 4, (short) 1);
//    }
//
//    @Bean
//    public NewTopic topicEmailSending() {
//        Properties kafkaProperties = kafkaTopicProperties();
//
//        return new NewTopic((String) kafkaProperties.getProperty("topic.name.email-sending"), 4, (short) 1);
//    }
//
//    @Bean
//    public NewTopic topicActivityTracking() {
//        Properties kafkaProperties = kafkaTopicProperties();
//
//        return new NewTopic((String) kafkaProperties.getProperty("topic.name.activity-tracking"), 4, (short) 1);
//    }
//
//}
