package sia.tacocloud.web.api;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import sia.tacocloud.TacoOrder;
import sia.tacocloud.data.OrderRepository;
import sia.tacocloud.messaging.OrderMessagingService;
import org.springframework.http.HttpStatus;
@RestController
@RequestMapping(path = "/api/orders",
        produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
public class OrderApiController {
    private OrderRepository repo;
    private OrderMessagingService messagingService;

    public OrderApiController(OrderRepository repo,
                              OrderMessagingService messagingService) {
        this.repo = repo;
        this.messagingService = messagingService;
    }
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TacoOrder> postOrder(@RequestBody TacoOrder tacoOrder){
        messagingService.sendOrder(tacoOrder);
        return repo.save(tacoOrder);
    }
}
