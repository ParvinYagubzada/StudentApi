package az.code.springweb.repositories;

import az.code.springweb.models.MyUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<MyUser, Long> {
    MyUser findUserByUsername(String username);
}
