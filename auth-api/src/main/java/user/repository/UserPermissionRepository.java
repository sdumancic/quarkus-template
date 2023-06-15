package user.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import user.entity.UserPermission;

@ApplicationScoped
public class UserPermissionRepository implements PanacheRepository<UserPermission> {
    public  UserPermission findByUserIdAndPermissionCode(Long userId, String permissionCode){
        return find("#UserPermission.findByUserIdAndPermissionCode", Parameters.with("userId",userId).and("permissionCode",permissionCode)).firstResult();
    }
}
