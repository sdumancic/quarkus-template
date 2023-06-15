package user.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import user.dto.PermissionDto;
import user.dto.RoleDto;
import user.entity.Permission;
import user.entity.Role;

@ApplicationScoped
public class RoleRepository implements PanacheRepository<Role> {

    public Role create(String roleName) {
        Role role = new Role();
        role.setRoleName(roleName);
        persistAndFlush(role);
        return role;
    }

    public Role updateRole(Long id, RoleDto dto) {
        Role role = findById(id);
        role.setId(id);
        role.setRoleName(dto.getRoleName());
        persistAndFlush(role);
        return role;
    }

}
