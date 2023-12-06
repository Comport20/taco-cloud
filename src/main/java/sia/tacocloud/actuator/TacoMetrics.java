package sia.tacocloud.actuator;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import sia.tacocloud.Ingredient;
import sia.tacocloud.Taco;
import sia.tacocloud.data.IngredientRepository;
import sia.tacocloud.data.TacoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TacoMetrics extends AbstractRepositoryEventListener<Taco> {
    private MeterRegistry meterRegistry;
    private IngredientRepository repository;
    public TacoMetrics(MeterRegistry meterRegistry,IngredientRepository repository){
        this.meterRegistry = meterRegistry;
        this.repository = repository;
    }

    @Override
    protected void onAfterCreate(Taco entity) {
        Set<Long> ingredientsId = entity.getIngredientList();
        for (Long ingredientId : ingredientsId){
            meterRegistry.counter("tacocloud",
                    "ingredient",repository
                            .findById(ingredientId)
                            .block()
                            .getSlug()).increment();
        }

    }
}
