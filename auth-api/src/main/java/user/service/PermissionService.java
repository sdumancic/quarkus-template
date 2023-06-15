package user.service;

import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import user.dto.MetadataDto;
import user.dto.PagedResponseDto;
import user.dto.PermissionDto;
import user.entity.Permission;
import user.repository.PermissionRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
@Slf4j
public class PermissionService {

    @Inject
    PermissionRepository permissionRepository;


    public Permission findOne(Long id){
        return permissionRepository.findById(id);
    }

    public PagedResponseDto<List<PermissionDto>> paginate(int pageIndex, int pageSize, String code){

        Map<String, Object> params = new HashMap<>();
        String query = null;

        if (StringUtils.isNotBlank(code)) {
            params.put("code", code.concat("%"));
            query = "select p from Permission p where lower(p.code) like lower(:code) order by id asc";
        } else {
            query = "select p from Permission p order by p.id asc";
        }
        Page page = new Page(pageIndex, pageSize);
        List<Permission> permissions = permissionRepository.find(query, params).page(page).list();
        long count = permissionRepository.find(
                        query, params)
                .count();
        return new PagedResponseDto<>(mapToDto(permissions), mapToMetadata(pageIndex, pageSize, count,query));

    }

    private MetadataDto mapToMetadata(int pageIndex, int pageSize, long count, String query) {
        return new MetadataDto(pageIndex, pageSize, count, query);
    }

    private List<PermissionDto> mapToDto(List<Permission> permissions) {
        return permissions.stream().map(p -> new PermissionDto(p.id,p.code, p.description,p.dateCreated, p.dateModified)).toList();

    }

    public PermissionDto mapToDto(Permission permission) {
        return new PermissionDto(permission.id,permission.getCode(), permission.getDescription(),
                permission.getDateCreated(), permission.getDateModified());
    }

    public PermissionDto createPermission(PermissionDto dto) {
        Permission createdPermission = permissionRepository.create(dto.getCode(), dto.getDescription());
        return mapToDto(createdPermission);
    }

    public PermissionDto updatePermission(Long id, PermissionDto dto){
        Permission permForUpdate = permissionRepository.findById(id);
        if (permForUpdate != null){
            Permission updatedPermission = permissionRepository.updatePermission(id,dto);
            return mapToDto(updatedPermission);
        }
        return dto;
    }

    public void deletePermission(Long id){
        Permission permForUpdate = permissionRepository.findById(id);
        if (permForUpdate != null){
            permissionRepository.delete(permForUpdate);
        }
    }

}
