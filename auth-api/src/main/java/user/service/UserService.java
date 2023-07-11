package user.service;

import core.response.MetadataDto;
import core.response.PagedResponseDto;
import db.tables.records.UserRecord;
import io.agroal.api.AgroalDataSource;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jooq.*;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import user.dto.UserDto;
import user.entity.Role;
import user.entity.User;
import user.entity.UserPermission;
import user.repository.RoleRepository;
import user.repository.UserPermissionRepository;
import user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static db.Tables.USER;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.noCondition;
import static org.jooq.impl.SQLDataType.LOCALDATETIME;

@ApplicationScoped
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class UserService {

    @NonNull
    private final DSLContext dslContext;

    @ConfigProperty(name = "user.role.id")
    Long userRoleId;

    @Inject
    UserPermissionRepository userPermissionRepository;
    @Inject
    RoleRepository roleRepository;
    @Inject
    UserRepository userRepository;



    public static StopWatch stopWatch = new StopWatch();


    public boolean userHasPermission(Long userId, String permissionCode) {
        UserPermission foundUserPermission = userPermissionRepository.findByUserIdAndPermissionCode(userId, permissionCode);
        return foundUserPermission != null;
    }

    public PagedResponseDto<List<UserDto>> paginate(int pageIndex, int pageSize) throws InterruptedException {


       // DSLContext dslContext = DSL.using(dataSource, SQLDialect.POSTGRES, new Settings().withRenderFormatted(true));

        Condition firstCondition = DSL.lower(USER.FIRST_NAME).like("a%");
        Condition secondCondition = DSL.lower(USER.LAST_NAME).like("a%");
        List<Condition> conditions = new ArrayList<>();
        conditions.add(firstCondition);
        conditions.add(secondCondition);
        stopWatch.reset();
        stopWatch.start();

        CompletableFuture<Integer> exCount = CompletableFuture.supplyAsync(() -> {
            try {
                return countRecords(dslContext, conditions);
            } catch (InterruptedException e) {
                return 0;
            }
        });

        CompletableFuture<List<UserDto>> exQuery = CompletableFuture.supplyAsync(() -> {
            try {
                return executeQuery(dslContext, conditions);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        PagedResponseDto<List<UserDto>> response = exCount
                .thenCombine(exQuery, (recordsCount, records) -> new PagedResponseDto<>(records, mapToMetadata(pageIndex, pageSize, recordsCount, null)))
                .join();
        stopWatch.stop();
        log.info("Total Time Taken : " +stopWatch.getTime());
        return response;

        /*
        final int totalRecordsCount = countRecords(dslContext, conditions);
        log.info("xxx count " + totalRecordsCount);
        List<UserDto> fetch = executeQuery(dslContext, conditions);
        stopWatch.stop();
        log.info("Total Time Taken : " +stopWatch.getTime());
        Page page = new Page(pageIndex, pageSize);

        return new PagedResponseDto<>(fetch, mapToMetadata(pageIndex, pageSize, totalRecordsCount,null));

         */

    }

    private List<UserDto> executeQuery(DSLContext dslContext, List<Condition> conditions) throws InterruptedException {
        Thread.sleep(1000);
        return dslContext.select(USER.ID, USER.USERNAME, USER.FIRST_NAME, USER.LAST_NAME, USER.CREATED_ON, USER.MODIFIED_ON)
                .from(USER)
                .where(conditions)
                .orderBy(USER.ID)
                .limit(10)
                .offset(0)
                .fetch(val -> new UserDto(val.value1(), val.value2(), val.value3(), val.value4(), val.value5(), val.value6()));
    }

    private int countRecords(DSLContext dslContext,List<Condition> conditions) throws InterruptedException {
        Thread.sleep(1000);
        return dslContext.fetchCount(USER, conditions);
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
