package com.example.sint.kafka.consumer.application;

import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
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

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.sint.kafka.consumer"})
public class SpringintKafkaConsumerApplication {

	@Autowired
	PollableChannel consumerChannel;

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(SpringintKafkaConsumerApplication.class).run(args);
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
		context.getBean(SpringintKafkaConsumerApplication.class).run(context, topics);
	}

	private void run(ConfigurableApplicationContext context, List<String> topics){
		System.out.println("Inside ConsumerApplication run method...");
		PollableChannel consumerChannel = context.getBean("consumerChannel", PollableChannel.class);
		System.out.println("Adding new topics" + topics.size());
		for (String topic: topics){
			System.out.println("New Topic to add:" +topic);
			addAnotherListenerForTopics(topic);
		}
		Message received = consumerChannel.receive();
		System.out.println("Message Received is:" +received.getPayload());
		while(received != null){
			received = consumerChannel.receive();
			System.out.println("Message Received is: "+received.getPayload());
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
