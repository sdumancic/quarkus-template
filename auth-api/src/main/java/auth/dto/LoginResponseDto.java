package auth.dto;

import lombok.Data;

import java.util.List;

@Data
public class LoginResponseDto {
    private Long userId;
    private String username;
    private String firstName;
    private String lastName;
    private List<String> roles;
    private List<String> permissions;
    private String token;
}
