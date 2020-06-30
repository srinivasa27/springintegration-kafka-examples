package com.example.sint.kafka.consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ConsumerKafkaService {

    @Autowired
    PollableChannel consumerChannel;

    @ServiceActivator(inputChannel = "consumerChannel", poller = @Poller(fixedRate = "5000", maxMessagesPerPoll = "10"))
    public void read(){
        System.out.println("Inside ConsumerApplication run method...");
        Message received = consumerChannel.receive();
        System.out.println("Message Received is: " +received.getPayload());
        while(received != null){
            System.out.println("Message Received is not null: "+received.getPayload());
        }
    }

    public void addTopics(List<String> topics){
        System.out.println("Adding new topics: " + topics.size());
        for (String topic: topics){
            System.out.println("New Topic to add: " +topic);
            addAnotherListenerForTopics(topic);
        }
    }

    @Autowired
    private IntegrationFlowContext flowContext;

    @Autowired
    private KafkaProperties kafkaProperties;

    public void addAnotherListenerForTopics(String... topics){
        Map consumerProperties = kafkaProperties.buildConsumerProperties();
        IntegrationFlow flow = IntegrationFlows.from(Kafka.messageDrivenChannelAdapter(new
                DefaultKafkaConsumerFactory(consumerProperties), topics)).channel("consumerChannel").get();
        this.flowContext.registration(flow).register();
    }
}
