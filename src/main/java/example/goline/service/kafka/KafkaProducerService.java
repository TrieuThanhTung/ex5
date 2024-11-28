package example.goline.service.kafka;

import example.goline.utils.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String name) {
        String message = String.format("Product with name: %s is created at %s", name, LocalDateTime.now().toString());
        kafkaTemplate.send(Util.TOPIC, message);
        log.info("Message sent: " + message);
    }
}
