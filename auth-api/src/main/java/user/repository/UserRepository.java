package user.repository;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import user.entity.Role;
import user.entity.User;

import java.util.List;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public User findByUsername(String username){
        return find("username", username).firstResult();
    }

    /**
     * Adds a new user to the database
     * @param username the username
     * @param password the unencrypted password (it will be encrypted with bcrypt)
     */
    public User create(String username, String password, String firstName, String lastName, List<Role> roles) {
        User user = new User();
        user.username = username;
        user.hashedPassword = BcryptUtil.bcryptHash(password);
        user.firstName = firstName;
        user.lastName = lastName;
        user.roles = roles;
        persistAndFlush(user);
        return user;
    }

}
