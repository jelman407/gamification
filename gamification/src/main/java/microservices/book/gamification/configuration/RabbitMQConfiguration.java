package microservices.book.gamification.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * 이벤트를 사용하기 위한 RabbitMQ 설정
 */
@Configuration
@Slf4j
public class RabbitMQConfiguration implements RabbitListenerConfigurer {

	@Bean
	public TopicExchange multiplicationExchange(@Value("${multiplication.exchange}") final String exchangeName) {
		
		log.info("------------ RabbitMQConfiguration : multiplicationExchange START ------------");
		return new TopicExchange(exchangeName);
	}

	@Bean
	public Queue gamificationMultiplicationQueue(@Value("${multiplication.queue}") final String queueName) {
		
		log.info("------------ RabbitMQConfiguration : gamificationMultiplicationQueue START ------------");
		return new Queue(queueName, true);
	}

	@Bean
	Binding binding(final Queue queue, final TopicExchange exchange,
			@Value("${multiplication.anything.routing-key}") final String routingKey) {
		
		log.info("------------ RabbitMQConfiguration : binding START ------------");
		return BindingBuilder.bind(queue).to(exchange).with(routingKey);
	}

	@Bean
	public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
		
		log.info("------------ RabbitMQConfiguration : consumerJackson2MessageConverter START ------------");
		return new MappingJackson2MessageConverter();
	}
	
	@Bean
	public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
		
		log.info("------------ RabbitMQConfiguration : messageHandlerMethodFactory START ------------");
		DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
		
		factory.setMessageConverter(consumerJackson2MessageConverter());
		return factory;
	}
	
	@Override
	public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
		
		log.info("------------ RabbitMQConfiguration : configureRabbitListeners START ------------");
		registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
	}
}
