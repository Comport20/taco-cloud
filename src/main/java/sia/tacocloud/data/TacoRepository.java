package sia.tacocloud.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import sia.tacocloud.Taco;

public interface TacoRepository extends ReactiveCrudRepository<Taco, Long> {
}
