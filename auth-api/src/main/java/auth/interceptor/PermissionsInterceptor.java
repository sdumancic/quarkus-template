package auth.interceptor;

import auth.annotation.Permissions;
import auth.dto.CustomPrincipal;
import auth.exception.AuthException;
import io.quarkus.security.Authenticated;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import lombok.extern.slf4j.Slf4j;
import user.service.UserService;

import java.lang.reflect.Method;

@Interceptor
@Permissions(value = {}, inclusive = false)
@Priority(Interceptor.Priority.APPLICATION)
@Slf4j
public class PermissionsInterceptor {

    @Context
    SecurityContext securityCtx;

    @Inject
    UserService userService;

    @AroundInvoke
    public Object aroundInvoke(InvocationContext context) throws Exception {
        Permissions requiredPermissions = retrieveAnnotation(context);
        if (requiredPermissions != null) {
            String[] values = requiredPermissions.value();
            boolean inclusive = requiredPermissions.inclusive();
            if (securityCtx.getUserPrincipal() == null) {
                throw new AuthException("User is not logged in");
            }
            if (!this.userHasPermissions(((CustomPrincipal) securityCtx.getUserPrincipal()).getUserId(), values, inclusive)) {
                throw new AuthException("User does not have permission");
            }

        }
        return context.proceed();

    }

    private Permissions retrieveAnnotation(InvocationContext context) {
        Permissions annotation = context.getMethod().getAnnotation(Permissions.class);
        if (annotation == null){
            annotation = context.getMethod().getDeclaringClass().getAnnotation(Permissions.class);
        }
        return annotation;
    }

    private boolean userHasPermissions(Long userId, String[] values, boolean inclusive) {
        log.info("values {}, inclusive {}",values, inclusive);
        if (inclusive){
            boolean userHasPermissions = true;
            for (String code: values){
                if (!this.userService.userHasPermission(userId, code)){
                    userHasPermissions = false;
                    break;
                }
            }
            return userHasPermissions;
        } else {
            boolean userHasPermissions = false;
            for (String code: values){
                if (this.userService.userHasPermission(userId, code)){
                    userHasPermissions = true;
                    break;
                }
            }
            return userHasPermissions;
        }
    }
}
