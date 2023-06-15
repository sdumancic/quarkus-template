package user.service;

import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import user.dto.MetadataDto;
import user.dto.PagedResponseDto;
import user.dto.PermissionDto;
import user.dto.RoleDto;
import user.entity.Permission;
import user.entity.Role;
import user.repository.PermissionRepository;
import user.repository.RoleRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
@Slf4j
public class RoleService {

    @Inject
    RoleRepository roleRepository;


    public Role findOne(Long id){
        return roleRepository.findById(id);
    }

    public PagedResponseDto<List<RoleDto>> paginate(int pageIndex, int pageSize, String code){

        Map<String, Object> params = new HashMap<>();
        String query = null;

        if (StringUtils.isNotBlank(code)) {
            params.put("roleName", code.concat("%"));
            query = "select r from Role r where lower(p.roleName) like lower(:roleName) order by r.id asc";
        } else {
            query = "select r from Role r order by r.id asc";
        }
        Page page = new Page(pageIndex, pageSize);
        List<Role> roles = roleRepository.find(query, params).page(page).list();
        long count = roleRepository.find(
                        query, params)
                .count();
        return new PagedResponseDto<>(mapToDto(roles), mapToMetadata(pageIndex, pageSize, count,query));

    }

    private MetadataDto mapToMetadata(int pageIndex, int pageSize, long count, String query) {
        return new MetadataDto(pageIndex, pageSize, count, query);
    }

    private List<RoleDto> mapToDto(List<Role> roles) {
        return roles.stream().map(r -> new RoleDto(r.id,r.roleName,r.dateCreated, r.dateModified)).toList();

    }

    public RoleDto mapToDto(Role role) {
        return new RoleDto(role.id, role.getRoleName(),
                role.getDateCreated(), role.getDateModified());
    }

    public RoleDto createRole(RoleDto dto) {
        Role createdRole = roleRepository.create(dto.getRoleName());
        return mapToDto(createdRole);
    }

    public RoleDto updateRole(Long id, RoleDto dto){
        Role roleForUpdate = roleRepository.findById(id);
        if (roleForUpdate != null){
            Role updatedRole = roleRepository.updateRole(id,dto);
            return mapToDto(updatedRole);
        }
        return dto;
    }

    public void deleteRole(Long id){
        Role roleForUpdate = roleRepository.findById(id);
        if (roleForUpdate != null){
            roleRepository.delete(roleForUpdate);
        }
    }

}
