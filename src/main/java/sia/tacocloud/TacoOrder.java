package sia.tacocloud;

import lombok.Data;

import java.io.Serializable;
import java.util.*;

import org.hibernate.validator.constraints.CreditCardNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Digits;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

@Data
public class TacoOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private long id;
    private Date placedAt;
    @NotBlank(message = "Name is required")
    private String deliveryName;
    @NotBlank(message = "Street is required")
    private String deliveryStreet;
    @NotBlank(message = "City is required")
    private String deliveryCity;
    @NotBlank(message = "State is required")
    private String deliveryState;
    @NotBlank(message = "Zip is required")
    private String deliveryZip;
    @CreditCardNumber(message = "Not a valid credit card number")
    private String ccNumber;
    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private String ccCVV;
    @Pattern(regexp = "^(0[1-9]|1[0-2])(/)([2-9][0-9])$",
            message = "Must be formatted MM/YY")
    private String ccExpiration;
    private Set<Long> tacoIds = new LinkedHashSet<>();
    @Transient
    private transient List<Taco> tacoList = new ArrayList<>();
    private Person person;
    public void addTaco(Taco taco) {
        this.tacoList.add(taco);
        if(taco.getId() != null)
            this.tacoIds.add(taco.getId());
    }
}
