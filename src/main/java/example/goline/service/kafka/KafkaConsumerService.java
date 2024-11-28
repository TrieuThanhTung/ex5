package example.goline.service.kafka;

import example.goline.utils.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {

    @KafkaListener(topics = Util.TOPIC, groupId = "group_id")
    public void consume(String message) {
        log.info("Message received: " + message);
    }
}