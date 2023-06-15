package auth.filter;

import auth.annotation.Permissions;
import auth.annotation.RequiresToken;
import auth.dto.CustomPrincipal;
import auth.dto.JwtResponseDto;
import auth.exception.AuthException;
import auth.service.AuthService;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import user.service.UserService;

import java.io.IOException;
import java.lang.reflect.Method;

@Provider
@Priority(value=30)
@Slf4j
public class SecurityOverrideFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resinfo;

    @Inject
    UserService userService;

    @SneakyThrows
    @Override
    public void filter(ContainerRequestContext requestContext) {

        Method resourceMethod = this.resinfo.getResourceMethod();

        if (resourceMethod.isAnnotationPresent(RequiresToken.class)){
            String xAuthToken = requestContext.getHeaders().getFirst("X-AUTH-TOKEN");
            if (xAuthToken == null) {
                throw new AuthException("Header x-auth-token was not provided");
            }

            JwtResponseDto responseDto = AuthService.readJwt(xAuthToken);
            requestContext.setSecurityContext(new SecurityContext() {
                @Override
                public CustomPrincipal getUserPrincipal() {
                    return new CustomPrincipal() {
                        @Override
                        public String getName() {
                            return responseDto.getUsername();
                        }
                        @Override
                        public String getFirstName() {
                            return responseDto.getFirstName();
                        }
                        @Override
                        public String getLastName() {
                            return responseDto.getLastName();
                        }
                    };
                }

                @Override
                public boolean isUserInRole(String r) {
                    log.info("responseDto.getRoles() {} , {}" ,responseDto.getRoles(), r);
                    return responseDto.getRoles().contains(r);

                }

                @Override
                public boolean isSecure() {
                    return false;
                }

                @Override
                public String getAuthenticationScheme() {
                    return "basic";
                }
            });

            if (resourceMethod.isAnnotationPresent(Permissions.class)){
                Permissions annotation = resourceMethod.getAnnotation(Permissions.class);
                String[] values = annotation.value();
                if (!this.userHasPermissions(responseDto.getUserId(), values, annotation.inclusive())){
                    throw new AuthException("User does not have permission");
                }
            }
        }


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
