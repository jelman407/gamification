package microservices.book.gamification.client;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.slf4j.Slf4j;
import microservices.book.gamification.client.dto.MultiplicationResultAttempt;

import java.io.IOException;

/**
 * Multiplication 마이크로서비스로부터 오는 답안을 Gamification 에서 사용하는 형태로 역직렬화
 */

@Slf4j
public class MultiplicationResultAttemptDeserializer extends JsonDeserializer<MultiplicationResultAttempt> {

	@Override
	public MultiplicationResultAttempt deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
			throws IOException, JsonProcessingException {
		
		log.info("------------ MultiplicationResultAttemptDeserializer : MultiplicationResultAttempt START ------------");
		
		log.info("------------ MultiplicationResultAttemptDeserializer : MultiplicationResultAttempt : " + jsonParser.getText());
		
		ObjectCodec oc = jsonParser.getCodec();
		JsonNode node = oc.readTree(jsonParser);
		
		log.info("------------ MultiplicationResultAttemptDeserializer : MultiplicationResultAttempt : " + node);
		
		return new MultiplicationResultAttempt(node.get("user").get("alias").asText(),
				node.get("multiplication").get("factorA").asInt(), node.get("multiplication").get("factorB").asInt(),
				node.get("resultAttempt").asInt(), node.get("correct").asBoolean());
	}
}
