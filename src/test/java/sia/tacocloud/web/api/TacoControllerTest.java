package sia.tacocloud.web.api;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import sia.tacocloud.Ingredient;
import sia.tacocloud.Taco;
import sia.tacocloud.TacoOrder;
import sia.tacocloud.data.OrderRepository;
import sia.tacocloud.data.TacoRepository;

import java.util.ArrayList;
import java.util.List;

import static reactor.core.publisher.Mono.when;

public class TacoControllerTest {
    @Test
    public void shouldReturnRecentTacos(){
        Taco[] tacos = {
                testTaco(1L), testTaco(2L),
                testTaco(3L), testTaco(4L),
                testTaco(5L), testTaco(6L),
                testTaco(7L), testTaco(8L),
                testTaco(9L), testTaco(10L),
                testTaco(11L), testTaco(12L),
                testTaco(13L), testTaco(14L),
                testTaco(15L), testTaco(16L),
        };
        Flux<Taco> tacoFlux = Flux.just(tacos);
        TacoRepository tacoRepository = Mockito.mock(TacoRepository.class);
        when(tacoRepository.findAll()).thenReturn(tacoFlux);
        OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
        WebTestClient testClient = WebTestClient.bindToController(
                new TacoController(tacoRepository,orderRepository)).build();
        testClient.get().uri("/api/tacos?recent")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$").isEmpty()
                .jsonPath("$[0].id").isEqualTo(tacos[0].getId())
                .jsonPath("$[0].name").isEqualTo("Taco1")
                .jsonPath("$[1].id").isEqualTo(tacos[1].getId())
                .jsonPath("$[1].name").isEqualTo("Taco2")
                .jsonPath("$[11].id").isEqualTo(tacos[11].getId())
                .jsonPath("$[11].name").isEqualTo("Taco12")
                .jsonPath("$[12]").doesNotExist();
    }
    private Taco testTaco(Long num){
        Taco taco = new Taco();
        taco.setId(num != null ? num : -1);
        taco.setName("Taco" + num);
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(
                new Ingredient("INGA", "Ingredient A", Ingredient.Type.WRAP));
        ingredients.add(
                new Ingredient("INGB", "Ingredient B", Ingredient.Type.PROTEIN));
        return taco;
    }
}
