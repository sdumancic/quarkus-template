package auth.dto;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponseDto {
    private Long userId;
    private String username;
    private String firstName;
    private String lastName;
    private List<String> roles;
}
