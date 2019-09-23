package microservices.book.gamification.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import microservices.book.gamification.client.dto.MultiplicationResultAttempt;

/**
 * Multiplication 마이크로서비스와 REST 로 연결하기 위한 MultiplicationResultAttemptClient
 * 인터페이스의 구현체
 */
@Component
@Slf4j
class MultiplicationResultAttemptClientImpl implements MultiplicationResultAttemptClient {

	private final RestTemplate restTemplate;
	private final String multiplicationHost;

	@Autowired
	public MultiplicationResultAttemptClientImpl(final RestTemplate restTemplate,
			@Value("${multiplicationHost}") final String multiplicationHost) {
		
		log.info("------------ MultiplicationResultAttemptClientImpl : MultiplicationResultAttemptClientImpl START ------------");
		
		this.restTemplate = restTemplate;
		this.multiplicationHost = multiplicationHost;
		
		log.info("------------ MultiplicationResultAttemptClientImpl : MultiplicationResultAttemptClientImpl END ------------");
	}

	@Override
	public MultiplicationResultAttempt retrieveMultiplicationResultAttemptById(
			final Long multiplicationResultAttemptId) {
		
		log.info("------------ MultiplicationResultAttemptClientImpl : MultiplicationResultAttempt START ------------");
		
		log.info("------------ MultiplicationResultAttemptClientImpl : MultiplicationResultAttempt : " + multiplicationHost + "/results/" + multiplicationResultAttemptId + " ------------");
		
		return restTemplate.getForObject(multiplicationHost + "/results/" + multiplicationResultAttemptId,
				MultiplicationResultAttempt.class);
		
	}
}