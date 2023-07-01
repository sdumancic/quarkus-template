package user.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.ejb.Singleton;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import user.dto.UserDto;
import user.entity.Permission;
import user.entity.Role;
import user.entity.User;
import user.entity.UserPermission;
import user.repository.PermissionRepository;
import user.repository.RoleRepository;
import user.repository.UserPermissionRepository;
import user.repository.UserRepository;
import user.rest.UserResource;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@ApplicationScoped
public class UserGenerationService {

    @Inject
    UserRepository userRepository;

    @Inject
    UserPermissionRepository userPermissionRepository;

    @Inject
    PermissionRepository permissionRepository;

    @Inject
    RoleRepository roleRepository;



    @Transactional
    public User createRandomUser() {
        String username = RandomStringUtils.random(10, true, true);
        String password = RandomStringUtils.random(10, true, true);
        String firstname = RandomStringUtils.random(10, true, false);
        String lastname = RandomStringUtils.random(10, true, false);
        User createdUser = userRepository.create(username, password, firstname, lastname, Collections.EMPTY_LIST);
        log.info("Created user {}",createdUser);
        Permission permission = permissionRepository.find("code", "PERMISSION_R").firstResult();
        log.info("Found permission {}",permission);
        UserPermission userPermission = userPermissionRepository.create(createdUser, permission);
        log.info("Created userPermission {}",userPermission);
        return createdUser;
    }

    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public void createFiveUsers() {
        for (int i = 0;i<5;i++){
            User randomUser = this.createRandomUser();
            log.info("Create user {}",randomUser);
        }
    }
}
