package com.example.sint.kafka.consumer.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.kafka.inbound.KafkaMessageDrivenChannelAdapter;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.messaging.PollableChannel;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableIntegration
@IntegrationComponentScan
public class ConsumerChannelConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.topic}")
    private String springIntegrationKafkaTopic;

    @Bean
    public PollableChannel consumerChannel(){
        return new QueueChannel();
    }

    @Bean
    public KafkaMessageDrivenChannelAdapter kafkaMessageDrivenChannelAdapter(){
        KafkaMessageDrivenChannelAdapter adapter =  new KafkaMessageDrivenChannelAdapter(kafkaListenerContainer());
        adapter.setOutputChannel(consumerChannel());
        return adapter;
    }

    @Bean
    public ConcurrentMessageListenerContainer kafkaListenerContainer(){
        ContainerProperties containerProperties = new ContainerProperties(springIntegrationKafkaTopic);
        return (ConcurrentMessageListenerContainer) new ConcurrentMessageListenerContainer(consumerFactory(), containerProperties);
    }

    @Bean
    public ConsumerFactory consumerFactory(){
        return new DefaultKafkaConsumerFactory(consumerConfigs());
    }

    @Bean
    public Map consumerConfigs(){
        Map properties = new HashMap();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "dummy");
        return properties;
    }
}
