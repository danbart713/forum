//package com.dvb.forum.config;
//
//import com.dvb.forum.dto.activitytracking.ActivityTrackingKafkaEvent;
//import com.dvb.forum.dto.emailsending.EmailSendingKafkaEvent;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//
//@Configuration
//@Slf4j
//public class KafkaConsumerConfig {
//
//    @Bean
//    @ConfigurationProperties("kafka")
//    public Properties kafkaConsumerProperties() {
//        return new Properties();
//    }
//
//    public ConsumerFactory<String, String> stringConsumerFactory(String groupId) {
//        Properties kafkaProperties = kafkaConsumerProperties();
//
//        Map<String, Object> configs = new HashMap<>();
//        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getProperty("bootstrap.servers"));
//        configs.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
//        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        configs.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, "20971520");
//        configs.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, "20971520");
//
//        return new DefaultKafkaConsumerFactory<>(configs);
//    }
//
//    public ConcurrentKafkaListenerContainerFactory<String, String> stringKafkaListenerContainerFactory(String groupId) {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(stringConsumerFactory(groupId));
//
//        return factory;
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, String> stringKafkaListenerContainerFactory() {
//        return stringKafkaListenerContainerFactory("testKafka");
//    }
//
//    public ConsumerFactory<String, EmailSendingKafkaEvent> emailSendingConsumerFactory() {
//        Properties kafkaProperties = kafkaConsumerProperties();
//
//        Map<String, Object> configs = new HashMap<>();
//        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getProperty("bootstrap.servers"));
//        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "emailSending");
//
//        return new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), new JsonDeserializer<>(EmailSendingKafkaEvent.class));
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, EmailSendingKafkaEvent> emailSendingKafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, EmailSendingKafkaEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(emailSendingConsumerFactory());
//
//        return factory;
//    }
//
//    public ConsumerFactory<String, ActivityTrackingKafkaEvent> activityTrackingConsumerFactory() {
//        Properties kafkaProperties = kafkaConsumerProperties();
//
//        Map<String, Object> configs = new HashMap<>();
//        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getProperty("bootstrap.servers"));
//        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "activityTracking");
//
//        return new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), new JsonDeserializer<>(ActivityTrackingKafkaEvent.class));
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, ActivityTrackingKafkaEvent> activityTrackingKafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, ActivityTrackingKafkaEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(activityTrackingConsumerFactory());
//
//        return factory;
//    }
//
//}
