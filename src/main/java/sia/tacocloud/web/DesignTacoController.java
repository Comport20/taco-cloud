package sia.tacocloud.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.validation.Errors;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

import sia.tacocloud.Taco;
import sia.tacocloud.Ingredient;
import sia.tacocloud.Ingredient.Type;
import sia.tacocloud.TacoOrder;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import sia.tacocloud.data.IngredientRepository;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo){
        this.ingredientRepo = ingredientRepo;
    }
    @ModelAttribute
    public void addIngredientToModel(Model model){
        List<Ingredient> ingredients = (List<Ingredient>)ingredientRepo.findAll();
        Type[] types = Type.values();
        for(Type type : types){
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients,type));
        }
    }
    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order(){
        return new TacoOrder();
    }
    @ModelAttribute(name = "taco")
    public Taco taco(){
        return new Taco();
    }

    @GetMapping
    public String showDesignForm(){
        return "design";
    }
    private Iterable<Ingredient> filterByType(List<Ingredient> ingredientList, Type type){
        return ingredientList.stream()
                .filter(
                        x -> x.getType().equals(type)).collect(Collectors.toList());
    }
    @PostMapping
    public String processTaco(@Valid Taco taco, Errors errors,
                              @ModelAttribute("tacoOrder") TacoOrder tacoOrder){
        if(errors.hasErrors()){
            return "design";
        }
        tacoOrder.addTaco(taco);
        log.info("Processing taco: {} ", taco);
        return "redirect:/orders/current";
    }
}
