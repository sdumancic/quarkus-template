package auth.service;


import auth.dto.JwtResponseDto;
import auth.dto.LoginResponseDto;
import auth.exception.AuthException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;
import user.entity.Role;
import user.entity.User;
import user.service.UserService;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@ApplicationScoped
@Slf4j
public class AuthService {

    @ConfigProperty(name = "jwt.key")
    String JWT_KEY;

    @ConfigProperty(name = "jwt.issuer")
    String JWT_ISSUER;

    @ConfigProperty(name = "jwt.token-validity-ms")
    Integer tokenValidityInMilliseconds;

    @Inject
    UserService userService;

    public SecretKey generalKey(){
        byte[] encodeKey = Base64.decode(this.JWT_KEY);
        return Keys.hmacShaKeyFor(encodeKey);
    }

    public LoginResponseDto login(String username, String password) throws AuthException {
        User user = userService.findByUsername(username);
        if (user == null){
            throw new AuthException(String.format("User with username %s was not found", username));
        }
        if (!BcryptUtil.matches(password, user.getHashedPassword())){
            throw new AuthException("Password does not match");
        }
        List<String> rolesList = user.getRoles().stream().map(Role::getRoleName).toList();
        long currentTime = new Date().getTime();
        String jws = Jwts.builder()
                .setIssuer(this.JWT_ISSUER)
                .setSubject(username)
                .claim("userId", user.id)
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .claim("roles",rolesList)
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(currentTime + this.tokenValidityInMilliseconds))
                .signWith(this.generalKey(),SignatureAlgorithm.HS256)
                .compact();
        LoginResponseDto response = new LoginResponseDto();
        response.setUserId(user.id);
        response.setToken(jws);
        response.setUsername(username);
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setRoles(rolesList);
        response.setPermissions(user.getUserPermissions().stream().map(p -> p.getPermission().getCode()).toList());
        return response;
    }

    public static JwtResponseDto readJwt(String jwtBase64) throws AuthException {
        try {
            DecodedJWT decodedJWT = JWT.decode(jwtBase64);
            JwtResponseDto responseDto = new JwtResponseDto();
            if (AuthService.isJWTExpired(decodedJWT)){
                throw new AuthException("Token expired");
            }
            responseDto.setFirstName(decodedJWT.getClaim("firstName").asString());
            responseDto.setLastName(decodedJWT.getClaim("lastName").asString());
            responseDto.setUsername(decodedJWT.getSubject());
            responseDto.setUserId(decodedJWT.getClaim("userId").asLong());
            responseDto.setRoles(decodedJWT.getClaim("roles").asList(String.class));
            return responseDto;
        } catch (JWTDecodeException e) {
            throw new AuthException(e.getMessage());
        }

    }

    static boolean isJWTExpired(DecodedJWT decodedJWT) {
        Date expiresAt = decodedJWT.getExpiresAt();
        return expiresAt.before(new Date());
    }
}
