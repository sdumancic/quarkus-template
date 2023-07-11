package auth.filter;

import auth.annotation.Permissions;
import auth.annotation.RequiresToken;
import auth.dto.CustomPrincipal;
import auth.dto.CustomPrincipalImpl;
import auth.dto.JwtResponseDto;
import auth.exception.AuthException;
import auth.service.AuthService;
import jakarta.annotation.Priority;
import jakarta.ejb.ApplicationException;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
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
    ResourceInfo resinfo;

    @SneakyThrows
    @Override
    public void filter(ContainerRequestContext requestContext) {

        Method resourceMethod = this.resinfo.getResourceMethod();

        if (resourceMethod.isAnnotationPresent(RequiresToken.class)){
            String xAuthToken = requestContext.getHeaders().getFirst("X-AUTH-TOKEN");
            if (xAuthToken == null) {
                requestContext.abortWith(Response
                        .status(Response.Status.UNAUTHORIZED)
                        .entity("x-auth-token not provided").build());
                return;
            }

            JwtResponseDto responseDto = AuthService.readJwt(xAuthToken);
            requestContext.setSecurityContext(new SecurityContext() {
                @Override
                public CustomPrincipal getUserPrincipal() {
                    return new CustomPrincipalImpl(responseDto.getUsername(),responseDto.getFirstName(),responseDto.getLastName(),responseDto.getUserId());
                }

                @Override
                public boolean isUserInRole(String r) {
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

        }


    }


}
