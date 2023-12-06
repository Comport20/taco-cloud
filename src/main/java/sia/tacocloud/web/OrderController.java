package sia.tacocloud.web;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import lombok.extern.slf4j.Slf4j;
import sia.tacocloud.TacoOrder;
import org.springframework.validation.Errors;
import jakarta.validation.Valid;
import sia.tacocloud.Person;
import sia.tacocloud.data.OrderRepository;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
    private final OrderProps orderProps;
    private final OrderRepository orderRepo;

    @Autowired
    public OrderController(OrderRepository orderRepo, OrderProps orderProps) {
        this.orderRepo = orderRepo;
        this.orderProps = orderProps;
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }
    @GetMapping
    public String orderForUser(@AuthenticationPrincipal Person person,
                               Model model){
            Pageable pageable = PageRequest.of(0,orderProps.getPageSize());
            model.addAttribute("orders",
                    orderRepo.findByPersonOrderByPlacedAtDesc(person,pageable));
            return "orderList";
    }
    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors,
                               SessionStatus sessionStatus,
                               @AuthenticationPrincipal Person person) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        order.setPerson(person);
        orderRepo.save(order);
        log.info("Order submitted: {}", order);
        sessionStatus.setComplete();
        return "redirect:/";
    }
}
