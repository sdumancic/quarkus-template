package user.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import user.dto.PermissionDto;
import user.entity.Permission;

@ApplicationScoped
public class PermissionRepository implements PanacheRepository<Permission> {

    public Permission create(String code, String description) {
        Permission perm = new Permission();
        perm.setCode(code);
        perm.setDescription(description);
        persistAndFlush(perm);
        return perm;
    }

    public Permission updatePermission(Long id, PermissionDto dto) {
        Permission perm = findById(id);
        perm.setId(id);
        perm.setCode(dto.getCode());
        perm.setDescription(dto.getDescription());
        persistAndFlush(perm);
        return perm;
    }

}
