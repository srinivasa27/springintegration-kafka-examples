package com.example.sint.kafka.consumer.application;

import com.example.sint.kafka.consumer.service.ConsumerKafkaService;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@EnableIntegration
@IntegrationComponentScan
@SpringBootApplication
@ComponentScan(basePackages = {"com.example.sint.kafka.consumer"})
public class SpringintKafkaConsumerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(SpringintKafkaConsumerApplication.class).run(args);
		ConsumerKafkaService consumerKafkaService = (ConsumerKafkaService)context.getBean("consumerKafkaService");
		List<String> validTopics = Arrays.asList("Credit", "Refund", "Debit", "Void");
		List<String> topics = new ArrayList();
		System.out.println("Valid topics" + validTopics.size());
		System.out.println("Argument topics" + args.length);
		args = args[0].split(",");
		if(args.length > 0){
			for (String arg: args){
				System.out.println("Argument" + arg);
				if(validTopics.contains(arg)){
					topics.add(arg);
				}
			}
		}
		consumerKafkaService.addTopics(topics);
	}



}
