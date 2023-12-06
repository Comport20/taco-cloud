package sia.tacocloud.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sia.tacocloud.Ingredient;
import sia.tacocloud.data.IngredientRepository;

@RestController
@RequestMapping(path="/api/ingredients", produces="application/json")
@CrossOrigin(origins="http://localhost:8080")
public class IngredientController {
    private IngredientRepository ingRepository;

    @Autowired
    public IngredientController(IngredientRepository ingRepository){
        this.ingRepository = ingRepository;
    }

    @GetMapping
    public Flux<Ingredient> findIngredient(){
        return ingRepository.findAll();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
        public Mono<Ingredient> saveIngredient(@RequestBody Ingredient ingredient){
        return ingRepository.save(ingredient);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIngredient(@PathVariable("id") Long ingredientId){
        ingRepository.deleteById(ingredientId);
    }
}