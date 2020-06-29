package com.example.sint.kafka.application;

import com.example.sint.kafka.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@EnableIntegration
@IntegrationComponentScan
@ComponentScan(basePackages = {"com.example.sint.kafka"})
public class PublisherApplication {

	@Autowired
	private DataPublisher dataPublisher;

	public static void main(String[] args) {
		ConfigurableApplicationContext context =  new SpringApplicationBuilder(PublisherApplication.class).run(args);
		context.getBean(PublisherApplication.class).run(context);
		context.close();
	}

	private void run(ConfigurableApplicationContext context){
		System.out.println("Inside Publisher run method");
		MessageChannel producerChannel = context.getBean("producerChannel", MessageChannel.class);
		List<Transaction>  transactions = dataPublisher.getTransactions();
		for (Transaction transn :transactions){
			Map<String, Object> headers = Collections.singletonMap(KafkaHeaders.TOPIC, transn.getTransactionType().toString());
			producerChannel.send(new GenericMessage(transn.toString(), headers));
		}
		System.out.println("End Publisher run method");
	}

}
