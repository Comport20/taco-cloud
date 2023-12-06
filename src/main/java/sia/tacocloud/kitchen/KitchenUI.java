package sia.tacocloud.kitchen;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sia.tacocloud.TacoOrder;
@Slf4j
@Data
@Component
public class KitchenUI {
    public void displayOrder(TacoOrder order){
      log.info("info tacoOrder: {}",order);
    }
}
