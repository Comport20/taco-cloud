package sia.tacocloud.security;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import sia.tacocloud.Person;

@Data
public class RegistrationForm {
    private String username;
    private String password;
    private String fullName;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;
    public Person toUser(PasswordEncoder passwordEncoder){
        return new Person(username,passwordEncoder.encode(password),
                fullName,street,city, state,zip,phone);
    }
}
