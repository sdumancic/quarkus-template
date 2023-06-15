package auth.dto;

import java.security.Principal;

public interface CustomPrincipal extends Principal {
    public String getFirstName();
    public String getLastName();
}
