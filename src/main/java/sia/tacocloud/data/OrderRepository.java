package sia.tacocloud.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import sia.tacocloud.Person;
import sia.tacocloud.TacoOrder;

import java.util.Date;

public interface OrderRepository extends ReactiveCrudRepository<TacoOrder, Long> {
    Flux<TacoOrder> findByDeliveryZip(String deliveryZip);
    Flux<TacoOrder> readByDeliveryZipAndPlacedAtBetween(
            String deliveryZip, Date startDate, Date endDate);
    Flux<TacoOrder> findByPersonOrderByPlacedAtDesc(Person person, Pageable pageable);
}
