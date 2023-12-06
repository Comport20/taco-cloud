package sia.tacocloud;


import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor(access=AccessLevel.PUBLIC, force = true)
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class Ingredient{
    @Id
    private long id;
    private @NonNull String slug;
    private @NonNull String name;
    private @NonNull Type type;
    public Ingredient(String slug, String name){
        this.slug = slug;
        this.name = name;
    }
    public enum Type{
        WRAP, CHEESE, SAUCE, PROTEIN, VEGGIES
    }
}
