package sia.tacocloud.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import sia.tacocloud.TacoOrder;

@Service
public class KafkaOrderMessagingService implements OrderMessagingService{
    private final KafkaTemplate<String, TacoOrder> kafkaTemplate;
    @Autowired
    public KafkaOrderMessagingService(KafkaTemplate<String, TacoOrder> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }
    @Override
    public void sendOrder(TacoOrder order) {
        kafkaTemplate.send("taco.order.topic",order);
    }
}
