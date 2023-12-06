package sia.tacocloud.web.clientapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import sia.tacocloud.Ingredient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import sia.tacocloud.IngredientRef;

import java.time.Duration;

@RestController
public class ClientApi {
    private final WebClient webClient;

    public ClientApi(WebClient webClient) {
        this.webClient = webClient;
    }

    public void getIngredients() {
        Flux<Ingredient> ingredientFlux = webClient.get()
                .uri("/api/ingredients")
                .retrieve()
                .bodyToFlux(Ingredient.class);
        ingredientFlux
                .timeout(Duration.ofSeconds(1))
                .subscribe();
    }

    public Mono<Ingredient> getIngredientById(String ingredientId) {
        Mono<Ingredient> ingredientMono = webClient
                .get()
                .uri("/api/ingredients/{id}", ingredientId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.just(new Exception()))
                .bodyToMono(Ingredient.class);
        ingredientMono.subscribe();
        return ingredientMono;
    }

    public Mono<Ingredient> postIngredient() {
        Mono<Ingredient> ingredientMono = Mono.just(
                new Ingredient("INGC", "Ingredient C", Ingredient.Type.VEGGIES));
        Mono<Ingredient> postIngredient = webClient
                .post()
                .uri("/ingredients")
                .body(ingredientMono, Ingredient.class)
                .retrieve()
                .bodyToMono(Ingredient.class);
        postIngredient.subscribe();
        return postIngredient;
    }

    public void putIngredientById() {
        Ingredient ingredient = new Ingredient();
        Mono<Void> putIngredientMono = webClient
                .put()
                .uri("/ingredients/{id}", ingredient.getId())
                .bodyValue(ingredient)
                .retrieve()
                .bodyToMono(Void.class);
        putIngredientMono.subscribe();
    }

    public void deleteIngredientById(String ingredientId) {
        Mono<Void> deleteIngredient = webClient
                .delete()
                .uri("/ingredients/{id}", ingredientId)
                .retrieve()
                .bodyToMono(Void.class);
        deleteIngredient.subscribe();
    }
}
