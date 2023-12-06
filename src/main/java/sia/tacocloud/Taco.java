package sia.tacocloud;



import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.rest.core.annotation.RestResource;

@Data
@RestResource(rel="tacos", path="tacos")
@NoArgsConstructor
public class Taco {
    @Id
    private Long id;
    private Date creatAt = new Date();
    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;
    @NotNull
    @Size(min = 5, message = "Checkbox must be at least 1 ingredient")
    private Set<Long> ingredientList = new HashSet<>();
    public Taco(String name){
        this.name = name;
    }
    public void addIngredient(Ingredient ingredient){
        this.ingredientList.add(ingredient.getId());
    }
}
