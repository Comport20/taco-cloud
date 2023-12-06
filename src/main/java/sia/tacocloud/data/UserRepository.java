package sia.tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import sia.tacocloud.Person;

public interface UserRepository extends CrudRepository<Person, Long>{
    Person findByUsername(String username);
}
