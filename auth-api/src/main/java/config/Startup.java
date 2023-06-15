package config;

import io.quarkus.runtime.StartupEvent;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.enterprise.event.Observes;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import user.entity.Permission;
import user.entity.User;
import user.entity.Role;
import user.entity.UserPermission;
import user.repository.RoleRepository;
import user.repository.UserPermissionRepository;
import user.repository.PermissionRepository;
import user.repository.UserRepository;

import java.util.List;


@Singleton
public class Startup {
    @Inject
    EntityManager em;

    @Inject
    UserPermissionRepository repo;

    @Inject
    PermissionRepository permissionRepository;
    @Inject
    RoleRepository roleRepository;
    @Inject
    UserRepository userRepository;

    @Transactional
    public void loadUsers(@Observes StartupEvent evt) {

        if (1==2) {
            repo.deleteAll();
            // reset and load all test users

            roleRepository.deleteAll();
            Role adminRole = roleRepository.create("admin");
            Role userRole = roleRepository.create("user");

            permissionRepository.deleteAll();
            Permission uR = permissionRepository.create("USER_R", "Read user");
            Permission uC = permissionRepository.create("USER_C", "Create user");
            Permission uU = permissionRepository.create("USER_U", "Update user");
            Permission uD = permissionRepository.create("USER_D", "Delete user");

            userRepository.deleteAll();
            User u1 = userRepository.create("admin", "admin", "Admin", "Admin", List.of(adminRole));
            User u2 = userRepository.create("user", "user", "User", "User", List.of(userRole));

            UserPermission link1 = new UserPermission("test", u1, uR);

            UserPermission link2 = new UserPermission("test", u1, uC);
            UserPermission link3 = new UserPermission("test", u1, uU);
            UserPermission link4 = new UserPermission("test", u1, uD);
            em.persist(link1);
            em.persist(link2);
            em.persist(link3);
            em.persist(link4);
            em.flush();
        }

    }
}
