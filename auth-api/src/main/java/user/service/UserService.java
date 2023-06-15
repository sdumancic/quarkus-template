package user.service;

import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import user.dto.MetadataDto;
import user.dto.PagedResponseDto;
import user.dto.UserDto;
import user.entity.Role;
import user.entity.User;
import user.entity.UserPermission;
import user.repository.RoleRepository;
import user.repository.UserPermissionRepository;
import user.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@ApplicationScoped
@Slf4j
public class UserService {

    @ConfigProperty(name = "user.role.id")
    Long userRoleId;

    @Inject
    UserPermissionRepository userPermissionRepository;
    @Inject
    RoleRepository roleRepository;
    @Inject
    UserRepository userRepository;
    public boolean userHasPermission(Long userId, String permissionCode) {
        UserPermission foundUserPermission = userPermissionRepository.findByUserIdAndPermissionCode(userId, permissionCode);
        return foundUserPermission != null;
    }

    public PagedResponseDto<List<UserDto>> paginate(int pageIndex, int pageSize){

        Map<String, Object> params = new HashMap<>();
        params.put("firstName", "A%");
        params.put("lastName", "u%");
        String query = "lower(firstName) like lower(:firstName) or lower(lastName) like lower(:lastName) order by id desc";

        Page page = new Page(pageIndex, pageSize);
        List<User> users = userRepository.find(
                query, params)
                .page(page)
                .list();
        long count = userRepository.find(
                        query, params)
                .count();
        return new PagedResponseDto<>(mapToDto(users), mapToMetadata(pageIndex, pageSize, count,query));

    }

    private MetadataDto mapToMetadata(int pageIndex, int pageSize, long count, String query) {
        return new MetadataDto(pageIndex, pageSize, count, query);
    }

    private List<UserDto> mapToDto(List<User> users) {
        return users.stream().map(u -> new UserDto(u.id,u.username, u.firstName, u.lastName,
                u.hashedPassword,
                u.roles.stream().map(Role::getRoleName).toList(),
                u.dateCreated, u.dateModified)).toList();

    }

    private UserDto mapToDto(User user) {
        return new UserDto(user.id,user.username, user.firstName, user.lastName,
                user.hashedPassword,
                user.roles.stream().map(Role::getRoleName).toList(),
                user.dateCreated, user.dateModified);

    }


    public UserDto createUser(UserDto dto) {
        Role role = roleRepository.findById(this.userRoleId);
        User createdUser = userRepository.create(dto.getUsername(), dto.getPassword(), dto.getFirstName(), dto.getLastName(), List.of(role));
        return mapToDto(createdUser);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

}
