package sia.tacocloud.kitchen.messaging.kafka.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import sia.tacocloud.TacoOrder;
import sia.tacocloud.kitchen.KitchenUI;

@Component
public class OrderListener {
    private final KitchenUI ui;
    @Autowired
    public OrderListener(KitchenUI ui){
        this.ui = ui;
    }
    @KafkaListener(topics = "tacocloud.orders.topic")
    public void handle(TacoOrder order){
        ui.displayOrder(order);
    }
}
