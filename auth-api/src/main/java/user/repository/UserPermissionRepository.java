package user.repository;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.lang3.RandomUtils;
import user.entity.Permission;
import user.entity.Role;
import user.entity.User;
import user.entity.UserPermission;

import java.util.List;

@ApplicationScoped
public class UserPermissionRepository implements PanacheRepository<UserPermission> {
    public  UserPermission findByUserIdAndPermissionCode(Long userId, String permissionCode){
        return find("#UserPermission.findByUserIdAndPermissionCode", Parameters.with("userId",userId).and("permissionCode",permissionCode)).firstResult();
    }

    public UserPermission create(User user, Permission permission) {
        boolean b = RandomUtils.nextBoolean();
        if (b){
            throw new RuntimeException("Runtime exception raised, should start rollback");
        }

        UserPermission userPermission = new UserPermission("test",user,permission);
        persist(userPermission);
        return userPermission;
    }
}
